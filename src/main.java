import UI.*;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.Enumeration;

public class main {
    public static void main (String[] args) throws Exception {
        String root=System.getProperty("user.dir");
        InitGlobalFont(new Font("逼格青春体简2.0", Font.BOLD, 13));  //统一设置字体
        new UI_login();
    }
    private static void InitGlobalFont(Font font) {
        FontUIResource fontRes = new FontUIResource(font);
        for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, fontRes);
            }
        }
    }
}

