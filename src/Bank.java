package bank.management.system;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** All account operations (balance, deposit, withdraw, statement, PIN change). Amounts are whole rupees. */
public class Bank
{
    public static class Txn
    {
        public final Timestamp date; public final String type; public final long amount;
        Txn(Timestamp d, String t, long a) { date = d; type = t; amount = a; }
    }

    /** Current balance, or -1 if the database is unreachable. */
    public static long balance(String card)
    {
        try(conn k = new conn())
        {
            if(!k.isConnected()) return -1;
            return balance(k.c, card);
        }
        catch(SQLException e) { e.printStackTrace(); return -1; }
    }

    private static long balance(Connection c, String card) throws SQLException
    {
        try(PreparedStatement ps = c.prepareStatement(
            "select coalesce(sum(case when type='Deposit' then amount else -amount end),0) from bank where cardnumber=?"))
        {
            ps.setString(1, card);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getLong(1);
        }
    }

    /** Returns null on success, otherwise an error message to show the user. */
    public static String deposit(String card, long amount)
    {
        if(amount <= 0) return "Enter an amount greater than 0.";
        return record(card, "Deposit", amount);
    }

    /** Returns null on success, otherwise an error message to show the user. */
    public static String withdraw(String card, long amount)
    {
        if(amount <= 0) return "Enter an amount greater than 0.";
        return record(card, "Withdrawal", amount);
    }

    private static String record(String card, String type, long amount)
    {
        try(conn k = new conn())
        {
            if(!k.isConnected()) return "Database connection failed.";
            Connection c = k.c;
            c.setAutoCommit(false);
            try
            {
                if(type.equals("Withdrawal"))
                {
                    // lock this account's rows so two withdrawals can't overdraw
                    try(PreparedStatement lock = c.prepareStatement("select id from bank where cardnumber=? for update"))
                    { lock.setString(1, card); lock.executeQuery(); }
                    if(balance(c, card) < amount) { c.rollback(); return "Insufficient balance."; }
                }
                try(PreparedStatement ps = c.prepareStatement("insert into bank (cardnumber,type,amount) values (?,?,?)"))
                {
                    ps.setString(1, card); ps.setString(2, type); ps.setLong(3, amount);
                    ps.executeUpdate();
                }
                c.commit();
                return null;
            }
            catch(SQLException e) { c.rollback(); throw e; }
        }
        catch(SQLException e) { e.printStackTrace(); return "Transaction failed: " + e.getMessage(); }
    }

    /** Most recent transactions, newest first. */
    public static List<Txn> statement(String card, int limit)
    {
        List<Txn> out = new ArrayList<>();
        try(conn k = new conn())
        {
            if(!k.isConnected()) return out;
            try(PreparedStatement ps = k.c.prepareStatement(
                "select txn_date,type,amount from bank where cardnumber=? order by id desc limit ?"))
            {
                ps.setString(1, card); ps.setInt(2, limit);
                ResultSet rs = ps.executeQuery();
                while(rs.next()) out.add(new Txn(rs.getTimestamp(1), rs.getString(2), rs.getLong(3)));
            }
        }
        catch(SQLException e) { e.printStackTrace(); }
        return out;
    }

    /** Returns null on success, otherwise an error message to show the user. */
    public static String changePin(String card, String oldPin, String newPin)
    {
        if(!newPin.matches("\\d{4}")) return "New PIN must be exactly 4 digits.";
        try(conn k = new conn())
        {
            if(!k.isConnected()) return "Database connection failed.";
            Connection c = k.c;
            c.setAutoCommit(false);
            try
            {
                int n;
                try(PreparedStatement ps = c.prepareStatement("update login set pin=? where cardnumber=? and pin=?"))
                { ps.setString(1, newPin); ps.setString(2, card); ps.setString(3, oldPin); n = ps.executeUpdate(); }
                if(n == 0) { c.rollback(); return "Current PIN is incorrect."; }
                try(PreparedStatement ps = c.prepareStatement("update signupthree set pin=? where cardnumber=?"))
                { ps.setString(1, newPin); ps.setString(2, card); ps.executeUpdate(); }
                c.commit();
                return null;
            }
            catch(SQLException e) { c.rollback(); throw e; }
        }
        catch(SQLException e) { e.printStackTrace(); return "PIN change failed: " + e.getMessage(); }
    }
}
