/*
 * 文件名：UI_login.java
 * 作者：高占恒
 * 描述：登录界面：用于登录、注册、忘记密码，是主函数直接并唯一调用的函数
 * 修改人: 高占恒
 * 修改时间：2019-12-05
 * 修改内容：添加注释
 */
package UI;

import UI_Part.JButton_Image;
import UI_Part.JFrame_setInfo;
import UI_Part.JPanel_image;
import SQL.Interface_UI_SQL;
// 内部引用

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
// 外部引用

public class UI_login extends JFrame {

    private JTextField jtext_username;
    private JPasswordField jtext_password;
    // 定义姓名、密码文本框

    private JButton jbutton_login;
    private JButton jbutton_regis;
    private JButton jbutton_forgt;
    // 定义登录、注册、忘记密码按钮

    private JPanel jpanel_login;
    // 定义中间容器

    private static String path = "photo/Login.png";
    // 定义背景图片路径

    private JFrame dur;
    // 定义登录过渡窗体

    public UI_login(){ login_init(); }


    /* 登录界面*///======================================================================================================
    //
    //  @function   :   初始化登录界面
    //
    private void login_init(){
        /* 基本组件*///--------------------------------------------------------------------
        jtext_username = new JTextField("");
        jtext_password = new JPasswordField("");
        jtext_password.setFont(new Font("Arial", Font.BOLD, 13));
        jtext_username.setBounds(260, 348, 140, 26);
        jtext_password.setBounds(260, 395, 140, 26);
        // 设置姓名、密码文本框

        jpanel_login = new JPanel();
        jpanel_login.setLayout(null);
        jpanel_login.setBounds(0,0,540,720);
        jpanel_login.setBackground(new Color(255, 216, 57,0));
        jpanel_login.add(jtext_username);
        jpanel_login.add(jtext_password);
        // 设置中间容器

        jbutton_login = new JButton_Image(210,480,120,40,"登  录", path, jpanel_login);
        jbutton_login.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    login();
                } catch (SQLException | IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        jbutton_regis = new JButton_Image(210,530,120,40,"注  册", path, jpanel_login);
        jbutton_regis.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) { register(); }
        });
        jbutton_forgt = new JButton_Image(210,583,120,40,"忘记密码", path, jpanel_login);
        jbutton_forgt.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) { forget(); }
        });
        // 设置登录、注册、忘记密码按钮，分别为其添加回调函数

        jpanel_login.add(jbutton_login);
        jpanel_login.add(jbutton_regis);
        jpanel_login.add(jbutton_forgt);
        // 添加登录、注册、忘记密码按钮到中间容器


        /* 底层框架*///-----------------------------------------------------------------
        JPanel_image jpanel_base = new JPanel_image(540,720,path);
        // 添加背景图片
        this.add(jpanel_login);
        this.add(jpanel_base);
        this.setTitle("Login");
        this.setDefaultLookAndFeelDecorated(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setBounds(0, 50, 555, 720);
        this.setResizable(true);
        this.setVisible(true);
        // 设置主窗体的各参数，使其显示
    }

    /* 登录按钮*///======================================================================================================
    //
    //  @function   :   登录按钮的回调函数，检查用户名密码是否正确，并弹出对应用户类型的窗口
    //
    private void login() throws SQLException, IOException {

        String username = jtext_username.getText();
        String password = String.valueOf(jtext_password.getPassword());
        // 获取文本框的内容

        String WE = Interface_UI_SQL.SQLLoginCheck( username, password);
        // 检查用户名密码是否正确

        if(WE.equals("1")){     // 如果账号密码匹配则进入
            if(Interface_UI_SQL.SQLTypeCheck().equals("admin")){
                new UI_admin( username);
                dispose();
            }    // 管理员界面入口
            else if(Interface_UI_SQL.SQLTypeCheck().equals("teacher")){
                new UI_teacher( username);
                dispose();
            }    // 老师界面入口
            else if(Interface_UI_SQL.SQLTypeCheck().equals("student")){
                new UI_student( username);
                dispose();
            }    // 学生界面入口
            else
                dispose();

        }
        else {    //如果账号密码不匹配则进入
            jtext_password.setText("");
            //令密码栏为空，重新输入
        }
    }

    /* 注册按钮*///======================================================================================================
    //
    //  @function   :   注册按钮的回调函数，并弹出注册的新窗口
    //
    private void register(){
        JFrame_setInfo window = new JFrame_setInfo(false);
        window.setEmpty();
        //弹出新的注册窗口，设置模式为添加学生
    }


    /* 忘记密码按钮*///==================================================================================================
    //
    //  @function   :   忘记密码按钮的回调函数，并弹出忘记密码的新窗口
    //
    private void forget(){
        UI_forget window = new UI_forget();
    }

}
