/*
 * 文件名：JPanel_image.java
 * 作者：高占恒
 * 描述：容器制作：将背景图片打印在容器中上
 * 修改人: 高占恒
 * 修改时间：2019-12-15
 * 修改内容：添加注释
 */
package UI_Part;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class JPanel_image extends JPanel {
    int width;
    int height;
    Image image;


    /* 主界面初始化*///======================================================================================================
    //
    //  @input      ：  width：容器宽度   height：容器高度     path：背景图片路径
    //  @function   :   获取个人信息，并显示在个人信息界面
    //
    public JPanel_image(int width,int height, String path) {
        this.height = height;
        this.width = width;

        try {
            // 该方法会将图像加载到内存，从而拿到图像的详细信息。
            image = ImageIO.read(new FileInputStream(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 获取路径对应的图片

        this.setBackground(new Color(116,217,255,255));
    }

    public JPanel getJPanel(){
        return this;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, width, height, this);
    }
}
