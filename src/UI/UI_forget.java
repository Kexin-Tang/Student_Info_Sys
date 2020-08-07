/*
 * 文件名：UI_forget.java
 * 作者：高占恒
 * 描述：忘记密码界面：用于验证密保信息并设置新密码
 * 修改人: 高占恒
 * 修改时间：2019-12-08
 * 修改内容：添加注释
 */
package UI;

import Function.Login.Forget_Password;
import UI_Part.JButton_Image;
import UI_Part.JPanel_image;
// 内部引用

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
// 外部引用

public class UI_forget extends JFrame{

    private static String path = "photo/Forget.png";
    // 定义背景图片路径

    private static Forget_Password SQL;
    // 定义修改密码时与SQL连接的对象

    private JTextField jtext_username;
    private JPasswordField jtext_passprot;
    private JPasswordField jtext_password1;
    private JPasswordField jtext_password2;
    // 定义输入账号、密保、密码的文本框

    private JLabel jlabel_pas1;
    private JLabel jlabel_pas2;
    private JLabel jlabel_tip2;
    // 定义显示提示的文本框

    private JButton_Image jbutton_check;
    private JButton_Image jbutton_sure;
    // 定义两个确定按钮

    private JPanel jpanel_forget;
    // 定义中间容器

    public UI_forget(){ forget_init(); }

    /* 忘记密码界面*///======================================================================================================
    //
    //  @function   :   初始化忘记密码界面；修改密码分两步进行，第一步检查账号密保是否匹配；第二步修改密码。
    //
    private void forget_init(){
        SQL = new Forget_Password();
        // 定义修改密码时与SQL连接的对象

        jtext_username = new JTextField("");
        jtext_passprot = new JPasswordField("");
        jtext_passprot.setFont(new Font("Arial", Font.BOLD, 13));
        jtext_username.setBounds(80, 190, 150, 26);
        jtext_passprot.setBounds(80, 270, 150, 26);
        // 设置第一步输入账号、密保的文本框的各参数
        JLabel jlabel_name = new JLabel("请输入您的账号：",SwingConstants.LEFT);
        JLabel jlabel_prot = new JLabel("请输入您的身份证后6位：",SwingConstants.LEFT);
        jlabel_name.setBounds(80,160,150,30);
        jlabel_prot.setBounds(80,240,150,30);;
        JLabel jlabel_tip1 = new JLabel("第一步",SwingConstants.LEFT);
        jlabel_tip1.setBounds(60,120,150,40);
        jlabel_tip1.setFont(new Font("逼格青春体简2.0", Font.BOLD, 20));
        // 设置第一步文本提示信息的各参数


        jtext_password1 = new JPasswordField("");
        jtext_password2 = new JPasswordField("");
        jtext_password1.setFont(new Font("Arial", Font.BOLD, 13));
        jtext_password2.setFont(new Font("Arial", Font.BOLD, 13));
        jtext_password1.setBounds(310, 190, 150, 26);
        jtext_password2.setBounds(310, 270, 150, 26);
        // 设置第二步输入两次密码的文本框的各参数
        jlabel_pas1 = new JLabel("请输入您的新密码：",SwingConstants.LEFT);
        jlabel_pas2 = new JLabel("请再次输入您的新密码：",SwingConstants.LEFT);
        jlabel_pas1.setBounds(310,160,150,30);
        jlabel_pas2.setBounds(310,240,150,30);;
        jlabel_tip2 = new JLabel("第二步",SwingConstants.LEFT);
        jlabel_tip2.setBounds(290,120,150,40);
        jlabel_tip2.setFont(new Font("逼格青春体简2.0", Font.BOLD, 20));
        // 设置第二步文本提示信息的各参数

        jpanel_forget = new JPanel();
        jpanel_forget.setLayout(null);
        jpanel_forget.setBackground(new Color(0,0,0,0));
        jpanel_forget.setBounds(0,0,540,360);
        jpanel_forget.add(jtext_username);
        jpanel_forget.add(jtext_passprot);
        jpanel_forget.add(jlabel_name);
        jpanel_forget.add(jlabel_prot);
        jpanel_forget.add(jlabel_tip1);
        // 将第一步组件添加至中间容器

        jbutton_check = new JButton_Image(30,32,80,40,"确  定", path, jpanel_forget);
        jbutton_check.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    check();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        jbutton_check.addColor(new Color(172, 252, 255));
        jbutton_sure = new JButton_Image(430,32,80,40,"修  改", path, jpanel_forget);
        jbutton_sure.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    sure();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        jbutton_sure.addColor(new Color(172, 252, 255));
        jbutton_sure.setDiscover(false);
        // 设置两个步骤的按钮，分别为其添加回调函数，暂时令第二步的按钮遮挡


        /* 底层框架*///==================================================================================================
        JPanel_image jpanel_base = new JPanel_image(540,360,path);
        // 添加背景图片
        this.add(jpanel_forget);
        this.add(jpanel_base);
        this.setTitle("Forget");
        this.setDefaultLookAndFeelDecorated(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setBounds(545, 50, 550, 358);
        this.setResizable(true);
        this.setVisible(true);
        // 设置主窗体的各参数，使其显示
    }


    /* 第一步检查按钮回调函数*///======================================================================================================
    //
    //  @function   :   检查输入的账号和密保信息是否匹配，若匹配则进行第二步
    //
    private void check() throws SQLException {
        String username = jtext_username.getText();
        String passport = String.valueOf(jtext_passprot.getPassword());
        // 获取账号和密保文本框内容

        if (SQL.check_protect(username,passport) == 1){  // 在信息表中查找，匹配时进入
            jbutton_sure.setDiscover(true);
            jbutton_check.setDiscover(false);
            // 使第一步的按钮消失，第二步的给予显示

            jpanel_forget.add(jtext_password1);
            jpanel_forget.add(jtext_password2);
            jpanel_forget.add(jlabel_pas1);
            jpanel_forget.add(jlabel_pas2);
            jpanel_forget.add(jlabel_tip2);
            // 将第二步组件添加至中间容器

            jtext_passprot.setEnabled(false);
            jtext_username.setEnabled(false);
            // 将第二步组件给予显示

            JOptionPane.showMessageDialog(null, "验证成功，请修改密码！", "Congratulation！", 1);
            // 弹出验证成功消息
        }else {  // 在信息表中查找，不匹配时进入
            JOptionPane.showMessageDialog(null, "密保信息错误！", "Error！", 2);
            // 弹出验证失败消息
        }
    }

    /* 第二步确认按钮回调函数*///======================================================================================================
    //
    //  @function   :   检查输入的两次新密码是否一致，若一致则修改用户密码
    //
    private void sure() throws SQLException {
        String username  = jtext_username.getText();
        String password1 = String.valueOf(jtext_password1.getPassword());
        String password2 = String.valueOf(jtext_password2.getPassword());
        // 获取账号和密保文本框内容

        if (password1.equals(password2)){  // 在两次输入一致时进入
            SQL.password_revise(password1,username);
            // 修改用户密码
            JOptionPane.showMessageDialog(null, "密码修改成功，请登录！", "Congratulation！", 1);
            // 弹出修改成功的消息
            dispose();
        }else {  // 在两次输入不一致时进入
            JOptionPane.showMessageDialog(null, "两次密码输入不一致，请检查！", "Error！", 2);
            // 弹出修改失败的消息
        }
    }
}
