/*
 * 文件名：UI_admin.java
 * 作者：高占恒
 * 描述：管理员界面：用于实现管理员的各种功能
 * 修改人: 高占恒
 * 修改时间：2019-12-12
 * 修改内容：添加注释
 */
package UI;

import UI_Part.*;
import UI_Part.JList;
// 内部引用

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
// 外部引用

public class UI_admin extends JFrame {
    /*主框架控件*///=====================================================================================================
    private static String path = "photo/Admin.png";
    private static String path_button = "photo/UI_button.png";
    // 定义背景图片

    // 个人信息部分----------------------------
    public String user_id;                      // 管理员账号
    private JPanel jpanel_info;                 // 信息部分中间容器

    private JButton_Image jbutton_refr;
    // 定义个人信息部分的刷新按钮

    // 界面切换部分-------------------------------
    private JPanel jpanel_swit;                 // 界面切换部分中间容器

    private JButton jbutton_send;
    private JButton jbutton_anno;
    private JButton jbutton_teac;
    private JButton jbutton_stud;
    // 定义界面切换部分的按钮：分别代表一个界面的显示

    /* 子框架控件*///===========================================
    // 发信箱------------------------------------
    private JPanel jpanel_send;                 // 发信箱栏目中间容器
    private JList jlist_send;                   // 发信箱栏目列表
    private JButton jbutton_messsend;           // 发信箱栏目提问按钮

    //announce
    private JPanel jpanel_anno;                 // 公告栏目中间容器
    private JList jlist_anno;                   // 公告栏目列表
    private JButton jbutton_annosend;           // 公告栏栏目发公告按钮

    //teacher
    private JPanel jpanel_teac;                 // 老师信息栏目中间容器
    private JList jlist_teac;                   // 老师信息栏目列表

    //stud
    private JPanel jpanel_stud;                 // 账号信息栏目中间容器
    private JList jlist_stud;                   // 账号信息栏目列表
    private JButton jbutton_studadd;            // 账号信息栏目添加学生按钮
    private JButton jbutton_teacadd;            // 账号信息栏目添加老师按钮
    //------------------------------------------------------------------------------------------------------------------



    public UI_admin(String username) throws SQLException, IOException {
        user_id = username;
        adm_init();
    }


    /* 管理员界面*///======================================================================================================
    //
    //  @function   :   初始化管理员界面，包括更新各列表、界面。
    //
    private void adm_init() throws SQLException, IOException {
        /* 主框架控件*///================================================================================================
        // 个人信息部分---------------------------------------------------------------------------------------------------
        {
            jpanel_info = new JPanel();
            jpanel_info.setLayout(null);
            jpanel_info.setBounds(0,0,540,170);
            // 定义个人信息部分中间容器

            jbutton_refr = new JButton_Image(443,65,60,40,"刷新",path,jpanel_info);
            jbutton_refr.addColor(new Color(188, 248, 255));
            jbutton_refr.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        refresh();
                    } catch (SQLException | IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            // 设置修改信息和刷新按钮并添加回调函数

            info_init();
            // 运行个人信息初始化 包括获取个人信息并显示
        }

        //界面切换部分-----------------------------------------------------------------------------------------------------
        {
            jpanel_swit = new JPanel();
            jpanel_swit.setLayout(null);
            jpanel_swit.setBounds(0, 250, 150, 470);
            jpanel_swit.setBackground(new Color(197, 255, 0, 0));
            // 定义界面切换部分中间容器

            jbutton_send = new JButton_Image(20, 15, 110, 40,"发信箱",path,jpanel_swit);
            jbutton_send.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    swit_send();
                }
            });
            jbutton_anno = new JButton_Image(20, 67, 110, 40,"公告通知",path,jpanel_swit);
            jbutton_anno.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    swit_anno();
                }
            });
            jbutton_teac = new JButton_Image(20, 118, 110, 40,"教师管理",path,jpanel_swit);
            jbutton_teac.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    swit_teac();
                }
            });
            jbutton_stud = new JButton_Image(20, 168, 110, 40,"信息管理",path,jpanel_swit);
            jbutton_stud.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    swit_stud();
                }
            });
            // 设置四个界面切换按钮并添加回调函数
        }


        /* 子框架控件*///================================================================================================
        // 发信箱栏目----------------------------------------------------------------------------------------------------------
        {
            jlist_send = new JList(user_id,2);  // 添加列表 模式为管理员发信箱（2）
            jlist_send.jbutton_fileproP.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jlist_send.Page--;
                    try {
                        jlist_send.list_init();
                    } catch (SQLException | IOException ex) {
                        ex.printStackTrace();
                    }
                    UI_admin.super.repaint();
                }
            });
            jlist_send.jbutton_filenextP.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jlist_send.Page++;
                    try {
                        jlist_send.list_init();
                    } catch (SQLException | IOException ex) {
                        ex.printStackTrace();
                    }
                    UI_admin.super.repaint();
                }
            });
            // 设置列表并添加翻页按钮并添加回调函数

            jbutton_messsend = new JButton(new ImageIcon(path_button));
            jbutton_messsend.setBounds(201, 0, 171, 41);
            jbutton_messsend.setContentAreaFilled(false);
            jbutton_messsend.setBorderPainted(false);
            jbutton_messsend.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        mess_send();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            JLabel jlabel_send = new JLabel("群发消息",SwingConstants.CENTER);
            jlabel_send.setBounds(208, 0, 171, 41);
            jlabel_send.setFont(new Font("逼格青春体简2.0", Font.BOLD, 18));
            // 设置发信按钮并添加回调函数

            jpanel_send = new JPanel();
            jpanel_send.setLayout(null);
            jpanel_send.setBounds(150, 157, 370, 600);
            jpanel_send.setBackground(new Color(167, 209, 255, 0));
            jpanel_send.add(jlist_send.getJPanel());
            jpanel_send.add(jlabel_send);
            jpanel_send.add(jbutton_messsend);
            // 定义收信箱栏目中间容器并添加各组件
        }

        // 公告栏栏目------------------------------------------------------------------------------------------------------
        {
            jlist_anno = new JList(user_id,3);   // 添加列表 模式为管理员公告栏（3）
            jlist_anno.jbutton_fileproP.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jlist_anno.Page--;
                    try {
                        jlist_anno.list_init();
                    } catch (SQLException | IOException ex) {
                        ex.printStackTrace();
                    }
                    UI_admin.super.repaint();
                }
            });
            jlist_anno.jbutton_filenextP.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jlist_anno.Page++;
                    try {
                        jlist_anno.list_init();
                    } catch (SQLException | IOException ex) {
                        ex.printStackTrace();
                    }
                    UI_admin.super.repaint();
                }
            });
            // 设置列表并添加翻页按钮并添加回调函数

            jbutton_annosend = new JButton(new ImageIcon(path_button));
            jbutton_annosend.setBounds(201, 0, 171, 41);
            jbutton_annosend.setContentAreaFilled(false);
            jbutton_annosend.setBorderPainted(false);
            jbutton_annosend.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        anno_send();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            JLabel jlabel_anno = new JLabel("发布公告",SwingConstants.CENTER);
            jlabel_anno.setBounds(208, 0, 171, 41);
            jlabel_anno.setFont(new Font("逼格青春体简2.0", Font.BOLD, 18));
            // 设置发公告按钮并添加回调函数

            jpanel_anno = new JPanel();
            jpanel_anno.setLayout(null);
            jpanel_anno.setBounds(150, 157, 370, 600);
            jpanel_anno.setBackground(new Color(174, 170, 255, 0));
            jpanel_anno.add(jlist_anno.getJPanel());
            jpanel_anno.add(jlabel_anno);
            jpanel_anno.add(jbutton_annosend);
            // 定义公告栏栏目中间容器并添加各组件
        }

        // 老师信息----------------------------------------------------------------------------------------------------------
        {
            jlist_teac = new JList(user_id,9);    // 添加列表 模式为管理员 老师信息（9）
            jlist_teac.jbutton_fileproP.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jlist_stud.Page--;
                    try {
                        jlist_stud.list_init();
                    } catch (SQLException | IOException ex) {
                        ex.printStackTrace();
                    }
                    UI_admin.super.repaint();
                }
            });
            jlist_teac.jbutton_filenextP.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jlist_stud.Page++;
                    try {
                        jlist_stud.list_init();
                    } catch (SQLException | IOException ex) {
                        ex.printStackTrace();
                    }
                    UI_admin.super.repaint();
                }
            });
            // 设置列表并添加翻页按钮并添加回调函数

            jpanel_teac = new JPanel();
            jpanel_teac.setLayout(null);
            jpanel_teac.setBounds(150, 157, 370, 600);
            jpanel_teac.setBackground(new Color(218, 146, 255, 0));
            jpanel_teac.add(jlist_teac.getJPanel());
            // 定义老师信息栏目中间容器并添加各组件
        }

        // 账户管理栏目----------------------------------------------------------------------------------------------------------
        {
            jlist_stud = new JList(user_id,5);   // 添加列表 模式为管理员账号管理（5）
            jlist_stud.jbutton_fileproP.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jlist_stud.Page--;
                    try {
                        jlist_stud.list_init();
                    } catch (SQLException | IOException ex) {
                        ex.printStackTrace();
                    }
                    UI_admin.super.repaint();
                }
            });
            jlist_stud.jbutton_filenextP.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jlist_stud.Page++;
                    try {
                        jlist_stud.list_init();
                    } catch (SQLException | IOException ex) {
                        ex.printStackTrace();
                    }
                    UI_admin.super.repaint();
                }
            });
            // 设置列表并添加翻页按钮并添加回调函数

            jbutton_studadd = new JButton(new ImageIcon(path_button));
            jbutton_studadd.setBounds(201, 0, 171, 41);
            jbutton_studadd.setContentAreaFilled(false);
            jbutton_studadd.setBorderPainted(false);
            jbutton_studadd.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    stud_add();
                }
            });
            JLabel jlabel_add = new JLabel("添加学生",SwingConstants.CENTER);
            jlabel_add.setBounds(208, 0, 171, 35);
            jlabel_add.setFont(new Font("逼格青春体简2.0", Font.BOLD, 18));
            // 设置添加学生按钮并添加回调函数

            jbutton_teacadd = new JButton(new ImageIcon(path_button));
            jbutton_teacadd.setBounds(25, 0, 171, 41);
            jbutton_teacadd.setContentAreaFilled(false);
            jbutton_teacadd.setBorderPainted(false);
            jbutton_teacadd.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    teac_add();
                }
            });
            JLabel jlabel_adt = new JLabel("添加老师",SwingConstants.CENTER);
            jlabel_adt.setBounds(25, 0, 171, 35);
            jlabel_adt.setFont(new Font("逼格青春体简2.0", Font.BOLD, 18));
            // 设置添加老师按钮并添加回调函数

            jpanel_stud = new JPanel();
            jpanel_stud.setLayout(null);
            jpanel_stud.setBounds(150, 157, 370, 600);
            jpanel_stud.setBackground(new Color(218, 146, 255, 0));
            jpanel_stud.add(jlist_stud.getJPanel());
            jpanel_stud.add(jlabel_add);
            jpanel_stud.add(jbutton_studadd);
            jpanel_stud.add(jlabel_adt);
            jpanel_stud.add(jbutton_teacadd);
            // 定义账号管理栏目中间容器并添加各组件
        }

        /* 底层框架*///==================================================================================================
        {
            JPanel jpanel_base = new JPanel_image(540,720,path);
            // 添加背景图片

            this.add(jpanel_info);
            this.add(jpanel_swit);
            this.add(jpanel_send);
            this.add(jpanel_anno);
            this.add(jpanel_teac);
            this.add(jpanel_stud);
            this.add(jpanel_base);
            this.setTitle("Admin");
            this.setDefaultLookAndFeelDecorated(true);
            this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            this.setBounds(5, 50, 550, 716);
            this.setResizable(true);
            this.setVisible(true);
            swit_send();
            // 设置主窗体的各参数，添加所有界面，使其显示
        }
    }




    /* 主界面功能函数*///=================================================================================================
    /* 主界面初始化*///======================================================================================================
    //
    //  @function   :   获取个人信息，并显示在个人信息界面
    //
    private void info_init(){
        jpanel_info.removeAll();
        jpanel_info.setLayout(null);
        jpanel_info.setBounds(0,0,540,170);
        jpanel_info.setBackground(new Color(255, 216, 57,0));
        // 重置个人信息界面中间容器

        JLabel info_id   = new JLabel("Admin");
        info_id  .setBounds(170,73,120,30);
        info_id  .setForeground(new Color(128,226,251));
        info_id  .setFont(new Font("逼格青春体简2.0", Font.BOLD, 15));
        // 设置标签，分别显示Admin

        jpanel_info.add(info_id);
        jbutton_refr.repaint(jpanel_info);
        // 将各组件添加到个人信息界面
    }


    /* 刷新按钮回调函数*///======================================================================================================
    //
    //  @function   :   刷新
    //
    private void refresh() throws SQLException,IOException{
        info_init();
        // 刷新个人信息界面
        this.repaint();
        jlist_send.list_init();
        jlist_anno.list_init();
        jlist_teac.list_init();
        jlist_stud.list_init();
        // 刷新每个模块的表格
        this.repaint();
    }



    /* 子界面切换函数*///==================================================================================================
    /* 界面切换按钮回调函数*///======================================================================================================
    //
    //  @function   :   切换至对应界面，四个函数结构相同，均为令其他模块遮挡，对应模块显示
    //
    private void swit_send(){
        jpanel_send.setVisible(true);
        jpanel_anno.setVisible(false);
        jpanel_teac.setVisible(false);
        jpanel_stud.setVisible(false);
    }
    private void swit_anno(){
        jpanel_send.setVisible(false);
        jpanel_anno.setVisible(true);
        jpanel_teac.setVisible(false);
        jpanel_stud.setVisible(false);
    }
    private void swit_teac(){
        jpanel_send.setVisible(false);
        jpanel_anno.setVisible(false);
        jpanel_teac.setVisible(true);
        jpanel_stud.setVisible(false);
    }
    private void swit_stud(){
        jpanel_send.setVisible(false);
        jpanel_anno.setVisible(false);
        jpanel_teac.setVisible(false);
        jpanel_stud.setVisible(true);
    }


    /* 消息功能函数*///==================================================================================================
    /* 群发消息按钮回调函数*///======================================================================================================
    //
    //  @function   :   弹出新的编辑消息窗口
    //
    private void mess_send() throws SQLException {
        JFrame_message window = new JFrame_message();
        window.setSendRecer(user_id,null);
        window.setIsAnno(false);
        // 弹出新的公告编辑窗口，模式设置为消息编辑，可编辑模式
    }





    /* 发布公告功能函数*///==================================================================================================
    /* 发布公告按钮回调函数*///======================================================================================================
    //
    //  @function   :   弹出新的公告编辑窗口
    //
    private void anno_send() throws SQLException {
        JFrame_message window = new JFrame_message();
        window.setSendRecer(user_id,null);
        window.setIsAnno(true);
        // 弹出新的公告编辑窗口，模式设置为公告编辑，可编辑模式
    }



    /* 添加学生功能函数*///==================================================================================================
    /* 添加学生按钮回调函数*///======================================================================================================
    //
    //  @function   :   弹出新的学生注册窗口
    //
    private void stud_add(){
        JFrame_setInfo window = new JFrame_setInfo(false);
        // 弹出新的学生信息添加窗口，设置模式为学生添加
    }
    private void teac_add(){
        JFrame_setInfo window = new JFrame_setInfo(true);
        // 弹出新的老师信息添加窗口，设置模式为老师添加
    }
}

