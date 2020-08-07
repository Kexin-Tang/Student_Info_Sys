/*
 * 文件名：JButton_Image.java
 * 作者：高占恒
 * 描述：按钮制作：将背景图片切割后打印在按钮上，然后添加按钮文字
 * 修改人: 高占恒
 * 修改时间：2019-12-13
 * 修改内容：添加注释
 */
package UI_Part;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
// 外部引用

public class JButton_Image extends JButton {

    private ImageIcon image;    // 定义按钮的背景图片
    private JLabel jlabel;      // 定义按钮上的文字标签


    /* 初始化方式一*///======================================================================================================
    //
    //  @input      ：  x：按钮横坐标  y：按钮纵坐标     width：容器宽度   height：容器高度     path：背景图片路径
    //  @function   :   根据传入参数数量，初始化对象中的各项，并调用不同的构造方法。
    //                  这个方式中只将按钮上打印背景图片
    //
    public JButton_Image (int x, int y, int width, int height, String path){
        Rectangle rect = new Rectangle(x, y, width, height);
        PutImage(rect,path);
        // 调用图片打印函数
    }

    /* 初始化方式二*///======================================================================================================
    //
    //  @input      ：  x：按钮横坐标  y：按钮纵坐标     width：容器宽度   height：容器高度
    //                  text：按钮上文字字符串   path：背景图片路径     jpanel：按钮所在的中间容器
    //  @function   :   根据传入参数数量，初始化对象中的各项，并调用不同的构造方法。
    //                  这个方式中将按钮上打印背景图片，并加文字标签，并添加到中间容器
    //
    public JButton_Image (int x, int y, int width, int height, String text, String path,JPanel jpanel){
        int py,px;
        if (jpanel == null) {
            py = 0;
            px = 0;
        }else {
            py = jpanel.getY();
            px = jpanel.getX();
        }
        // 获取中间容器的相对位置以确定其图片切割位置

        Rectangle rect = new Rectangle(px+x, py+y, width, height);
        PutImage(rect,path);
        // 调用图片打印函数

        this.setBounds(x, y, width, height);
        // 设置按钮大小

        jlabel = new JLabel(text,JLabel.CENTER);
        jlabel.setBounds(x, y, width, height);
        jlabel.setFont(new Font("逼格青春体简2.0", Font.BOLD, height/2));
        jlabel.setForeground(new Color(84, 130, 168));
        // 添加一个文字标签

        if (jpanel == null) return;
        jpanel.add(jlabel);
        jpanel.add(this);
        // 将足见添加到中间容器
    }


    /* 属性函数*///======================================================================================================
    //
    //  @input      ：  color：需要改变的颜色
    //  @function   :   改变标签字体颜色
    //
    public void addColor(Color color){
        jlabel.setForeground(color);
    }


    /* 属性函数*///======================================================================================================
    //
    //  @input      ：  text：需要改变的内容
    //  @function   :   改变标签文字内容
    //
    public void addText(String text){
        jlabel.setText(text);
    }


    /* 属性函数*///======================================================================================================
    //
    //  @input      ：  Is：是否可见
    //  @function   :   设置按钮是否可见
    //
    public void setDiscover(boolean Is){
        if (Is){
            jlabel.setVisible(true);
            this.setVisible(true);
        }
        else {
            jlabel.setVisible(false);
            this.setVisible(false);
        }
    }


    /* 属性函数*///======================================================================================================
    //
    //  @input      ：  jpanel：需要添加到的中间容器
    //  @function   :   重新打印按钮
    //
    public void repaint(JPanel jpanel){
        jpanel.add(jlabel);
        jpanel.add(this);
    }


    /* 属性函数*///======================================================================================================
    //
    //  @input      ：  x：横坐标        y：纵坐标
    //  @function   :   改变按钮位置
    //
    public void setPos(int x,int y){
        this.setLocation(x,y);
        jlabel.setLocation(x,y);
    }


    /* 属性函数*///======================================================================================================
    //
    //  @input      ：  font：需要改变的字体
    //  @function   :   改变标签字体
    //
    public void addFont(Font font){
        jlabel.setFont(font);
    }




    /* 打印图片函数*///======================================================================================================
    //
    //  @input      ：  rect：显示的矩形位置和大小      path：背景图片路径
    //  @function   :   根据位置和路径将图片打印到按钮上
    //
    private void PutImage(Rectangle rect,String path){
        try {
            FileInputStream fis = new FileInputStream(path);                 // 根据路径获取文件输入流
            ImageInputStream iis = ImageIO.createImageInputStream(fis);      // 根据文件流获取图片流
            String suffix = path.substring(path.lastIndexOf(".") + 1);   // 读取文件后缀
            ImageReader reader = ImageIO.getImageReadersBySuffix(suffix).next();
            reader.setInput(iis,true);
            ImageReadParam param = reader.getDefaultReadParam();             // 转换为ImageReadParam格式
            param.setSourceRegion(rect);                                     // 切割图片
            Image i = reader.read(0, param);                     // 转换为Image格式
            image = new ImageIcon(i);                                        // 转换为ImageIcon格式
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setContentAreaFilled(false);
        this.setMargin(new Insets(0, 0, 0, 0));
        this.setBorderPainted(false);
        this.setIcon(image);
        // 设置按钮不显示框并且打印图片
    }

}
