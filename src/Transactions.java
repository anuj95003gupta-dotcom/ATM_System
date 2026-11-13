package bank.management.system;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * ATM front panel. The background picture is icons/atm.png (any picture of the same layout works;
 * the coordinates below are measured in pixels of a 1000-px-wide picture and scaled automatically).
 * The menu is drawn on the ATM screen next to the 8 side buttons; the side buttons, the on-screen labels,
 * the keypad (0-9, red = Cancel, yellow = Clear, green = Enter) and the computer keyboard all work.
 */
public class Transactions extends JFrame
{
    // ---------- layout of the picture (image pixels) ----------
    static final int GLASS_X = 232, GLASS_Y = 375, GLASS_W = 383, GLASS_H = 247;
    static final int[] SLOT_Y = {503, 534, 564, 595};          // vertical centre of each side button
    static final int LEFT_BTN_X = 177, RIGHT_BTN_X = 634, BTN_W = 32, BTN_H = 23;
    // digit keys: x, y, w, h
    static final int[][] DIGIT_KEYS = {
        /*0*/ {330, 942, 38, 20},
        /*1*/ {300, 866, 36, 17}, /*2*/ {343, 866, 35, 17}, /*3*/ {387, 866, 34, 17},
        /*4*/ {295, 890, 36, 18}, /*5*/ {339, 890, 35, 18}, /*6*/ {383, 890, 36, 18},
        /*7*/ {289, 915, 37, 19}, /*8*/ {335, 915, 36, 19}, /*9*/ {380, 915, 36, 19}};
    static final int[] RED = {446, 865, 63, 18}, YELLOW = {444, 890, 65, 18}, WHITE = {442, 915, 67, 19}, GREEN = {441, 942, 68, 20};

    enum Mode { MENU, FAST, AMOUNT, RESULT, BALANCE, STATEMENT, PIN_OLD, PIN_NEW, PIN_CONFIRM }

    final String cardnumber;
    Mode mode = Mode.MENU;
    String amountType;                       // "Deposit" or "Withdrawal" while in AMOUNT mode
    final StringBuilder input = new StringBuilder();
    String note = "";                        // small red message under the input box
    String[] resultLines = {};
    String oldPin = "", newPin = "";
    List<Bank.Txn> statementRows;
    long balanceShown;

    private final BufferedImage bg;
    private final double k;                  // scale: display pixels per picture pixel
    private final AtmPanel panel;

    Transactions(String cardnumber)
    {
        this.cardnumber = cardnumber;
        bg = loadBackground();
        Dimension scr = Toolkit.getDefaultToolkit().getScreenSize();
        k = Math.min(0.9, (scr.height - 70.0) / bg.getHeight());
        int w = (int) Math.round(bg.getWidth() * k), h = (int) Math.round(bg.getHeight() * k);

        panel = new AtmPanel();
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(w, h));
        addHotspots();
        setContentPane(panel);
        installKeyboard();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        pack();
        setLocation(Math.max(0, (scr.width - w) / 2), 0);
        setVisible(true);
    }

    // ------------------------------------------------------------------ resources
    private static BufferedImage loadBackground()
    {
        for(String name : new String[]{"icons/atm.png", "icons/atm.jpg"})
        {
            try
            {
                URL u = ClassLoader.getSystemResource(name);
                if(u != null) return ImageIO.read(u);
            }
            catch(Exception e) { e.printStackTrace(); }
        }
        System.err.println("Missing icons/atm.png - using plain placeholder");
        BufferedImage b = new BufferedImage(1000, 1080, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = b.createGraphics();
        g.setColor(new Color(150, 150, 160)); g.fillRect(0, 0, 1000, 1080);
        g.setColor(new Color(50, 50, 55)); g.fillRect(GLASS_X - 20, GLASS_Y - 20, GLASS_W + 40, GLASS_H + 40);
        g.setColor(new Color(200, 200, 210));
        for(int y : SLOT_Y) { g.fillRect(LEFT_BTN_X, y - 11, BTN_W, BTN_H); g.fillRect(RIGHT_BTN_X, y - 11, BTN_W, BTN_H); }
        g.dispose();
        return b;
    }

    private int s(double v) { return (int) Math.round(v * k); }

    // ------------------------------------------------------------------ clickable areas
    private void addHotspots()
    {
        for(int i = 0; i < 4; i++)
        {
            final int left = i, right = i + 4;
            hotspot(LEFT_BTN_X, SLOT_Y[i] - 11, BTN_W, BTN_H, () -> slot(left));
            hotspot(RIGHT_BTN_X, SLOT_Y[i] - 11, BTN_W, BTN_H, () -> slot(right));
        }
        for(int d = 0; d <= 9; d++)
        {
            final char c = (char) ('0' + d);
            hotspot(DIGIT_KEYS[d][0], DIGIT_KEYS[d][1], DIGIT_KEYS[d][2], DIGIT_KEYS[d][3], () -> digit(c));
        }
        hotspot(RED[0], RED[1], RED[2], RED[3], this::red);
        hotspot(YELLOW[0], YELLOW[1], YELLOW[2], YELLOW[3], this::yellow);
        hotspot(WHITE[0], WHITE[1], WHITE[2], WHITE[3], this::white);
        hotspot(GREEN[0], GREEN[1], GREEN[2], GREEN[3], this::green);

        // clicking a label on the screen itself also works
        panel.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e)
            {
                int gx = s(GLASS_X), gw = s(GLASS_W);
                if(e.getX() < gx || e.getX() > gx + gw) return;
                boolean isLeft = e.getX() < gx + gw / 2;
                for(int i = 0; i < 4; i++)
                    if(Math.abs(e.getY() - s(SLOT_Y[i])) <= s(14)) { slot(isLeft ? i : i + 4); return; }
            }
        });
    }

    private void hotspot(int x, int y, int w, int h, Runnable action)
    {
        Hotspot hs = new Hotspot(action);
        hs.setBounds(s(x), s(y), Math.max(8, s(w)), Math.max(8, s(h)));
        panel.add(hs);
    }

    private void installKeyboard()
    {
        InputMap im = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getRootPane().getActionMap();
        for(int d = 0; d <= 9; d++)
        {
            final char c = (char) ('0' + d);
            im.put(KeyStroke.getKeyStroke(c), "d" + d);
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD0 + d, 0), "d" + d);
            am.put("d" + d, new AbstractAction() { public void actionPerformed(ActionEvent e) { digit(c); } });
        }
        bind(im, am, KeyEvent.VK_ENTER, "enter", this::green);
        bind(im, am, KeyEvent.VK_ESCAPE, "esc", this::red);
        bind(im, am, KeyEvent.VK_DELETE, "del", this::yellow);
        bind(im, am, KeyEvent.VK_BACK_SPACE, "bksp", () -> { if(input.length() > 0) input.setLength(input.length() - 1); note = ""; repaintScreen(); });
    }

    private void bind(InputMap im, ActionMap am, int vk, String name, Runnable r)
    {
        im.put(KeyStroke.getKeyStroke(vk, 0), name);
        am.put(name, new AbstractAction() { public void actionPerformed(ActionEvent e) { r.run(); } });
    }

    // ------------------------------------------------------------------ what each side button says
    /** Label for side button i (0-3 left top-to-bottom, 4-7 right top-to-bottom), or null if unused. */
    String label(int i)
    {
        switch(mode)
        {
            case MENU:
                return new String[]{"Deposit", "Cash Withdrawal", "Fast Cash", "Mini Statement", "Pin Change", "Balance Enquiry", null, "Exit"}[i];
            case FAST:
                return new String[]{"Rs. 100", "Rs. 500", "Rs. 1000", "Rs. 2000", "Rs. 5000", "Rs. 10000", null, "Back"}[i];
            case AMOUNT: case PIN_OLD: case PIN_NEW: case PIN_CONFIRM:
                return new String[]{null, null, null, "Clear", null, null, "Enter", "Cancel"}[i];
            default: // RESULT, BALANCE, STATEMENT
                return i == 7 ? "Main Menu" : null;
        }
    }

    // ------------------------------------------------------------------ actions (also used by the tests)
    void slot(int i)
    {
        if(label(i) == null) return;
        switch(mode)
        {
            case MENU:
                switch(i)
                {
                    case 0: startAmount("Deposit"); break;
                    case 1: startAmount("Withdrawal"); break;
                    case 2: mode = Mode.FAST; break;
                    case 3: showStatement(); break;
                    case 4: input.setLength(0); note = ""; mode = Mode.PIN_OLD; break;
                    case 5: showBalance(); break;
                    case 7: logout(); return;
                }
                break;
            case FAST:
                if(i == 7) mode = Mode.MENU;
                else
                {
                    long amt = Long.parseLong(label(i).replace("Rs. ", ""));
                    result(Bank.withdraw(cardnumber, amt), "Rs. " + amt + " withdrawn.", "Please collect your cash.");
                }
                break;
            case AMOUNT: case PIN_OLD: case PIN_NEW: case PIN_CONFIRM:
                if(i == 3) yellow(); else if(i == 6) green(); else if(i == 7) red();
                return;
            default:
                mode = Mode.MENU;
        }
        repaintScreen();
    }

    void digit(char c)
    {
        switch(mode)
        {
            case AMOUNT:
                if(input.length() < 9) input.append(c);
                break;
            case PIN_OLD: case PIN_NEW: case PIN_CONFIRM:
                if(input.length() < 4) input.append(c);
                break;
            case RESULT: case BALANCE: case STATEMENT:
                mode = Mode.MENU;
                break;
            default:
                return;
        }
        note = "";
        repaintScreen();
    }

    void yellow() { if(isEntry()) { input.setLength(0); note = ""; repaintScreen(); } }

    void red()
    {
        if(mode == Mode.MENU) { logout(); return; }
        mode = Mode.MENU; input.setLength(0); note = ""; oldPin = newPin = "";
        repaintScreen();
    }

    void white() { }

    void green()
    {
        switch(mode)
        {
            case AMOUNT:
                if(input.length() == 0 || Long.parseLong(input.toString()) <= 0) { note = "Enter an amount greater than 0"; break; }
                long amt = Long.parseLong(input.toString());
                if(amountType.equals("Deposit")) result(Bank.deposit(cardnumber, amt), "Rs. " + amt + " deposited successfully.", "");
                else result(Bank.withdraw(cardnumber, amt), "Rs. " + amt + " withdrawn.", "Please collect your cash.");
                break;
            case PIN_OLD:
                if(input.length() != 4) { note = "PIN must be 4 digits"; break; }
                oldPin = input.toString(); input.setLength(0); mode = Mode.PIN_NEW;
                break;
            case PIN_NEW:
                if(input.length() != 4) { note = "PIN must be 4 digits"; break; }
                newPin = input.toString(); input.setLength(0); mode = Mode.PIN_CONFIRM;
                break;
            case PIN_CONFIRM:
                if(!input.toString().equals(newPin)) { note = "PINs do not match - try again"; input.setLength(0); break; }
                String err = Bank.changePin(cardnumber, oldPin, newPin);
                oldPin = newPin = "";
                resultLines = err != null ? new String[]{err} : new String[]{"PIN changed successfully."};
                input.setLength(0); mode = Mode.RESULT;
                break;
            case RESULT: case BALANCE: case STATEMENT:
                mode = Mode.MENU;
                break;
            default:
                return;
        }
        repaintScreen();
    }

    private boolean isEntry() { return mode == Mode.AMOUNT || mode == Mode.PIN_OLD || mode == Mode.PIN_NEW || mode == Mode.PIN_CONFIRM; }

    private void startAmount(String type) { amountType = type; input.setLength(0); note = ""; mode = Mode.AMOUNT; }

    private void showBalance()
    {
        balanceShown = Bank.balance(cardnumber);
        mode = Mode.BALANCE;
    }

    private void showStatement()
    {
        statementRows = Bank.statement(cardnumber, 10);
        balanceShown = Bank.balance(cardnumber);
        mode = Mode.STATEMENT;
    }

    /** Shows the outcome of a deposit/withdrawal: the error, or the success text plus the new balance. */
    private void result(String error, String success, String extra)
    {
        if(error != null) resultLines = new String[]{error};
        else
        {
            long bal = Bank.balance(cardnumber);
            resultLines = extra.isEmpty() ? new String[]{success, "Available balance: Rs. " + bal}
                                          : new String[]{success, extra, "Available balance: Rs. " + bal};
        }
        input.setLength(0);
        mode = Mode.RESULT;
    }

    private void logout()
    {
        setVisible(false);
        dispose();
        new Login();
    }

    private void repaintScreen() { panel.repaint(); }

    // ------------------------------------------------------------------ painting
    private class AtmPanel extends JPanel
    {
        AtmPanel() { setOpaque(true); }

        @Override protected void paintComponent(Graphics g0)
        {
            Graphics2D g = (Graphics2D) g0.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g.drawImage(bg, 0, 0, getWidth(), getHeight(), null);
            drawScreen(g);
            g.dispose();
        }
    }

    private Font font(int style, double size) { return new Font("SansSerif", style, Math.max(9, (int) Math.round(size * k / 0.9))); }

    private void drawScreen(Graphics2D g)
    {
        int gx = s(GLASS_X), gy = s(GLASS_Y), gw = s(GLASS_W), gh = s(GLASS_H);
        // dark blue ATM display
        g.setPaint(new GradientPaint(0, gy, new Color(14, 52, 120), 0, gy + gh, new Color(5, 24, 70)));
        g.fillRoundRect(gx, gy, gw, gh, 6, 6);
        g.setColor(new Color(255, 255, 255, 35));
        g.fillRect(gx, gy, gw, Math.max(2, gh / 14));

        Shape oldClip = g.getClip();
        g.setClip(gx, gy, gw, gh);

        // side button labels
        for(int i = 0; i < 8; i++)
        {
            String t = label(i);
            if(t == null) continue;
            boolean left = i < 4;
            int cy = s(SLOT_Y[i % 4]);
            g.setFont(font(Font.BOLD, 14));
            FontMetrics fm = g.getFontMetrics();
            int tw = fm.stringWidth(t);
            g.setColor(new Color(255, 215, 90));
            int tri = Math.max(5, s(6));
            if(left)
            {
                g.fillPolygon(new int[]{gx + 2, gx + 2 + tri, gx + 2 + tri}, new int[]{cy, cy - tri / 2 - 1, cy + tri / 2 + 1}, 3);
                g.setColor(Color.WHITE);
                g.drawString(t, gx + 4 + tri + 4, cy + fm.getAscent() / 2 - 1);
            }
            else
            {
                g.fillPolygon(new int[]{gx + gw - 2, gx + gw - 2 - tri, gx + gw - 2 - tri}, new int[]{cy, cy - tri / 2 - 1, cy + tri / 2 + 1}, 3);
                g.setColor(Color.WHITE);
                g.drawString(t, gx + gw - 4 - tri - 4 - tw, cy + fm.getAscent() / 2 - 1);
            }
        }

        int top = gy + s(8);
        switch(mode)
        {
            case MENU:
                center(g, "Welcome to ATM", gx, gw, top + s(22), font(Font.BOLD, 20), Color.WHITE);
                center(g, "Please select your transaction", gx, gw, top + s(48), font(Font.PLAIN, 14), new Color(170, 215, 255));
                center(g, "Card  XXXX-XXXX-XXXX-" + cardnumber.substring(Math.max(0, cardnumber.length() - 4)), gx, gw, top + s(74), font(Font.PLAIN, 12), new Color(140, 180, 220));
                break;
            case FAST:
                center(g, "Fast Cash", gx, gw, top + s(24), font(Font.BOLD, 20), Color.WHITE);
                center(g, "Select the amount to withdraw", gx, gw, top + s(50), font(Font.PLAIN, 14), new Color(170, 215, 255));
                break;
            case AMOUNT:
                center(g, amountType.equals("Deposit") ? "Deposit" : "Cash Withdrawal", gx, gw, top + s(22), font(Font.BOLD, 20), Color.WHITE);
                center(g, "Enter amount using the keypad", gx, gw, top + s(46), font(Font.PLAIN, 13), new Color(170, 215, 255));
                entryBox(g, gx, gw, top + s(56), "Rs. " + input + "_", false);
                break;
            case PIN_OLD: case PIN_NEW: case PIN_CONFIRM:
                center(g, "Change PIN", gx, gw, top + s(22), font(Font.BOLD, 20), Color.WHITE);
                String p = mode == Mode.PIN_OLD ? "Enter your CURRENT PIN" : mode == Mode.PIN_NEW ? "Enter your NEW 4-digit PIN" : "Re-enter the NEW PIN";
                center(g, p, gx, gw, top + s(46), font(Font.PLAIN, 13), new Color(170, 215, 255));
                entryBox(g, gx, gw, top + s(56), "*".repeat(input.length()) + "_", true);
                break;
            case RESULT:
                int y = top + s(40);
                for(int i = 0; i < resultLines.length; i++)
                {
                    boolean last = i == resultLines.length - 1 && resultLines.length > 1;
                    center(g, resultLines[i], gx, gw, y, font(last ? Font.BOLD : Font.PLAIN, 15), last ? new Color(255, 225, 120) : Color.WHITE);
                    y += s(28);
                }
                break;
            case BALANCE:
                center(g, "Balance Enquiry", gx, gw, top + s(24), font(Font.BOLD, 20), Color.WHITE);
                center(g, "Your current balance is", gx, gw, top + s(60), font(Font.PLAIN, 14), new Color(170, 215, 255));
                center(g, "Rs. " + balanceShown, gx, gw, top + s(98), font(Font.BOLD, 28), new Color(255, 225, 120));
                break;
            case STATEMENT:
                drawStatement(g, gx, gw, top);
                break;
        }
        g.setClip(oldClip);
    }

    private void entryBox(Graphics2D g, int gx, int gw, int y, String text, boolean pin)
    {
        int bw = s(200), bh = s(34), bx = gx + (gw - bw) / 2;
        g.setColor(new Color(255, 255, 255, 235));
        g.fillRoundRect(bx, y, bw, bh, 8, 8);
        g.setColor(new Color(10, 30, 80));
        g.setFont(font(Font.BOLD, pin ? 24 : 20));
        FontMetrics fm = g.getFontMetrics();
        g.drawString(text, bx + (bw - fm.stringWidth(text)) / 2, y + (bh + fm.getAscent()) / 2 - 3);
        if(!note.isEmpty()) center(g, note, gx, gw, y + bh + s(18), font(Font.BOLD, 13), new Color(255, 140, 120));
    }

    private void drawStatement(Graphics2D g, int gx, int gw, int top)
    {
        center(g, "Mini Statement (last 10)", gx, gw, top + s(16), font(Font.BOLD, 16), Color.WHITE);
        SimpleDateFormat f = new SimpleDateFormat("dd-MM-yy HH:mm");
        int rows = Math.max(1, statementRows.size());
        int avail = s(SLOT_Y[3] - 12) - (top + s(30));
        int lh = Math.max(11, Math.min(s(17), avail / (rows + 1)));
        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, Math.max(9, lh - 3)));
        int x = gx + s(26), y = top + s(30) + lh;
        if(statementRows.isEmpty()) { g.setColor(Color.WHITE); g.drawString("No transactions yet.", x, y); y += lh; }
        for(Bank.Txn t : statementRows)
        {
            boolean dep = t.type.equals("Deposit");
            g.setColor(dep ? new Color(150, 240, 170) : new Color(255, 170, 150));
            g.drawString(String.format("%s %-10s Rs.%d", f.format(t.date), t.type, t.amount), x, y);
            y += lh;
        }
        g.setColor(new Color(255, 225, 120));
        g.drawString("Balance: Rs. " + balanceShown, x, Math.min(y + 2, s(SLOT_Y[3]) + s(4)));
    }

    private void center(Graphics2D g, String text, int gx, int gw, int y, Font f, Color c)
    {
        g.setFont(f);
        FontMetrics fm = g.getFontMetrics();
        while(fm.stringWidth(text) > gw - s(90) && f.getSize() > 9) { f = f.deriveFont((float) f.getSize() - 1); g.setFont(f); fm = g.getFontMetrics(); }
        g.setColor(c);
        g.drawString(text, gx + (gw - fm.stringWidth(text)) / 2, y);
    }

    // ------------------------------------------------------------------ invisible button laid over a key of the picture
    private static class Hotspot extends JComponent
    {
        private boolean hover, down;

        Hotspot(Runnable action)
        {
            setFocusable(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered(MouseEvent e) { hover = true; repaint(); }
                @Override public void mouseExited(MouseEvent e)  { hover = false; down = false; repaint(); }
                @Override public void mousePressed(MouseEvent e) { down = true; repaint(); }
                @Override public void mouseReleased(MouseEvent e)
                {
                    boolean fire = down && contains(e.getPoint());
                    down = false; repaint();
                    if(fire) action.run();
                }
            });
        }

        @Override protected void paintComponent(Graphics g0)
        {
            if(!hover && !down) return;
            Graphics2D g = (Graphics2D) g0.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(down ? new Color(0, 0, 0, 70) : new Color(255, 255, 255, 70));
            g.fillRoundRect(0, 0, getWidth(), getHeight(), 4, 4);
            g.dispose();
        }
    }

    public static void main(String[] args)
    {
        new Login();
    }
}
