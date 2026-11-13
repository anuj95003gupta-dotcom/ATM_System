package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

public class Util
{
    /** Loads icons/<name> from the classpath. If the file is missing, returns a plain placeholder instead of crashing. */
    public static ImageIcon icon(String path, int w, int h)
    {
        URL url = ClassLoader.getSystemResource(path);
        if(url != null)
        {
            Image img = new ImageIcon(url).getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT);
            return new ImageIcon(img);
        }
        System.err.println("Missing image resource: " + path + " (using placeholder)");
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        g.setColor(new Color(40, 60, 90));
        g.fillRect(0, 0, w, h);
        g.dispose();
        return new ImageIcon(bi);
    }

    public static void message(String msg)
    {
        try { JOptionPane.showMessageDialog(null, msg); }
        catch(Throwable t) { System.err.println(msg); }
    }
}
