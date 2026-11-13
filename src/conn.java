package bank.management.system;

import java.sql.*;

/** JDBC connection helper. Edit the settings below (or set the DB_URL / DB_USER / DB_PASSWORD env variables). */
public class conn implements AutoCloseable
{
    private static final String URL      = System.getenv().getOrDefault("DB_URL", "jdbc:mysql://localhost:3306/bankmanagementsystem");
    private static final String USER     = System.getenv().getOrDefault("DB_USER", "root");
    private static final String PASSWORD = System.getenv().getOrDefault("DB_PASSWORD", "MyNewPass123");

    Connection c;
    Statement s;

    public conn()
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection(URL, USER, PASSWORD);
            s = c.createStatement();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            Util.message("Database connection failed:\n" + e.getMessage()
                + "\n\nCheck that MySQL is running, schema.sql was imported and the password in conn.java is correct.");
        }
    }

    public boolean isConnected() { return c != null; }

    @Override
    public void close()
    {
        try { if(c != null) c.close(); } catch(SQLException ignored) {}
    }
}
