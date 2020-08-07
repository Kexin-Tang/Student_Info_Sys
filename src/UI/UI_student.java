/*
 * 文件名：UI_student.java
 * 作者：高占恒
 * 描述：学生界面：用于实现学生的各种功能
 * 修改人: 高占恒
 * 修改时间：2019-12-12
 * 修改内容：添加注释
 */
package UI;

import INFO.Person;
import SQL.Interface_UI_SQL;
import UI_Part.*;
import UI_Part.JList;
// 内部引用

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
// 外部引用

public class UI_student extends JFrame {
    /* 主框架控件*///===========================================
    private static String path_m = "photo/Student_m.png";
    private static String path_w = "photo/Student_w.png";
    private static String path_button = "photo/UI_button.png";
    // 定义背景图片

    // 个人信息部分----------------------------
    private String user_id;                // 学生账号
    private Person user_info;              // 学生信息对象
    private JPanel jpanel_info;            // 信息部分中间容器

    private JButton_Image jbutton_setu;
    private JButton_Image jbutton_refr;
    // 定义个人信息部分的两个按钮：修改信息和刷新

    // 界面切换部分-------------------------------
    private JPanel jpanel_swit;            // 界面切换部分中间容器

    private JButton jbutton_rece;
    private JButton jbutton_send;
    private JButton jbutton_anno;
    private JButton jbutton_file;
    // 定义界面切换部分的按钮：分别代表一个界面的显示


    /* 子框架控件*///===========================================
    // 收信箱------------------------------------
    private JPanel jpanel_rece;            // 收信箱栏目中间容器
    private JList jlist_rece;              // 收信箱栏目列表

    //问题状态
    private JPanel jpanel_send;            // 问题状态栏目中间容器
    private JList jlist_send;              // 问题状态栏目列表
    private JButton jbutton_messsend;      // 问题状态栏目提问按钮

    //公告栏
    private JPanel jpanel_anno;            // 公告栏目中间容器
    private JList jlist_anno;              // 公告栏目列表

    //文件管理
    private JPanel jpanel_file;            // 文件管理栏目中间容器
    private JList jlist_file;              // 文件管理栏目列表
    private JButton jbutton_filechoose;    // 文件管理栏目上传文件按钮
    //------------------------------------------------------------------------------------------------------------------



    public UI_student(String username) throws SQLException, IOException {
        user_id = username;
        stu_init();
    }


    /* 学生界面*///======================================================================================================
    //
    //  @function   :   初始化学生界面，包括更新各列表、界面。
    //
    private void stu_init() throws SQLException, IOException {
        /* 主框架控件*///================================================================
        // 个人信息部分-------------------------------------------------------------
        {
            jpanel_info = new JPanel();
            jpanel_info.setLayout(null);
            jpanel_info.setBounds(0,0,540,170);
            // 定义个人信息部分中间容器

            jbutton_setu = new JButton_Image(331,65,60,40,"编辑",path_m,jpanel_info);
            jbutton_setu.addColor(new Color(188, 248, 255));
            jbutton_setu.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        info_change();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            jbutton_refr = new JButton_Image(443,65,60,40,"刷新",path_m,jpanel_info);
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

        // 界面切换部分----------------------------------------------------------------------------------------------------
        {
            jpanel_swit = new JPanel();
            jpanel_swit.setLayout(null);
            jpanel_swit.setBounds(0, 250, 150, 470);
            jpanel_swit.setBackground(new Color(197, 255, 0, 0));
            // 定义界面切换部分中间容器

            jbutton_rece = new JButton_Image(20, 15, 110, 40,"收信箱",path_m,jpanel_swit);
            jbutton_rece.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    swit_rece();
                }
            });
            jbutton_send = new JButton_Image(20, 68, 110, 40,"问题状态",path_m,jpanel_swit);
            jbutton_send.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    swit_send();
                }
            });
            jbutton_anno = new JButton_Image(20, 118, 110, 40,"公告通知",path_m,jpanel_swit);
            jbutton_anno.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    swit_anno();
                }
            });
            jbutton_file = new JButton_Image(20, 168, 110, 40,"文件管理",path_m,jpanel_swit);
            jbutton_file.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    swit_file();
                }
            });
            // 设置四个界面切换按钮并添加回调函数
        }


        /* 子框架控件*///================================================================================================
        // 收信箱栏目-------------------------------------------------------------------------------------------------------
        {
            jlist_rece = new JList(user_id,1);   // 添加列表 模式为学生收信箱（1）
            jlist_rece.jbutton_fileproP.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jlist_rece.Page--;
                    try {
                        jlist_rece.list_init();
                    } catch (SQLException | IOException ex) {
                        ex.printStackTrace();
                    }
                    UI_student.super.repaint();
                }
            });
            jlist_rece.jbutton_filenextP.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jlist_rece.Page++;
                    try {
                        jlist_rece.list_init();
                    } catch (SQLException | IOException ex) {
                        ex.printStackTrace();
                    }
                    UI_student.super.repaint();
                }
            });
            // 设置列表并添加翻页按钮并添加回调函数

            jpanel_rece = new JPanel();
            jpanel_rece.setLayout(null);
            jpanel_rece.setBounds(150, 157, 370, 600);
            jpanel_rece.setBackground(new Color(156, 250, 255, 0));
            jpanel_rece.add(jlist_rece.getJPanel());
            // 定义收信箱部分中间容器并添加各组件
        }

        //问题状态栏目----------------------------------------------------------------------------------------------------------
        {
            jlist_send = new JList(user_id,2);   // 添加列表 模式为学生问题状态（2）
            jlist_send.jbutton_fileproP.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jlist_send.Page--;
                    try {
                        jlist_send.list_init();
                    } catch (SQLException | IOException ex) {
                        ex.printStackTrace();
                    }
                    UI_student.super.repaint();
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
                    UI_student.super.repaint();
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
                    } catch (SQLException | IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            JLabel jlabel_send = new JLabel("提 问",SwingConstants.CENTER);
            jlabel_send.setBounds(208, 0, 171, 41);
            jlabel_send.setFont(new Font("逼格青春体简2.0", Font.BOLD, 22));
            // 设置提问按钮并添加回调函数

            jpanel_send = new JPanel();
            jpanel_send.setLayout(null);
            jpanel_send.setBounds(150, 157, 370, 600);
            jpanel_send.setBackground(new Color(167, 209, 255, 0));
            jpanel_send.add(jlist_send.getJPanel());
            jpanel_send.add(jlabel_send);
            jpanel_send.add(jbutton_messsend);
            // 定义提问状态栏目中间容器并添加各组件
        }

        //公告栏栏目------------------------------------------------------------------------------------------------------
        {
            jlist_anno = new JList(user_id,3);  // 添加列表 模式为学生公告栏（3）
            jlist_anno.jbutton_fileproP.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jlist_anno.Page--;
                    try {
                        jlist_anno.list_init();
                    } catch (SQLException | IOException ex) {
                        ex.printStackTrace();
                    }
                    UI_student.super.repaint();
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
                    UI_student.super.repaint();
                }
            });
            // 设置列表并添加翻页按钮并添加回调函数

            jpanel_anno = new JPanel();
            jpanel_anno.setLayout(null);
            jpanel_anno.setBounds(150, 157, 370, 600);
            jpanel_anno.setBackground(new Color(174, 170, 255, 0));
            jpanel_anno.add(jlist_anno.getJPanel());
            // 定义公告栏栏目中间容器并添加各组件
        }

        // 文件管理栏目--------------------------------------------------------------------------------------------------------
        {
            jlist_file = new JList(user_id,4);   // 添加列表 模式为学生文件管理（4）
            jlist_file.jbutton_fileproP.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jlist_file.Page--;
                    try {
                        jlist_file.list_init();
                    } catch (SQLException | IOException ex) {
                        ex.printStackTrace();
                    }
                    UI_student.super.repaint();
                }
            });
            jlist_file.jbutton_filenextP.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jlist_file.Page++;
                    try {
                        jlist_file.list_init();
                    } catch (SQLException | IOException ex) {
                        ex.printStackTrace();
                    }
                    UI_student.super.repaint();
                }
            });
            // 设置列表并添加翻页按钮并添加回调函数

            jbutton_filechoose = new JButton(new ImageIcon(path_button));
            jbutton_filechoose.setBounds(201, 0, 171, 41);
            jbutton_filechoose.setContentAreaFilled(false);
            jbutton_filechoose.setBorderPainted(false);
            jbutton_filechoose.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        file_upload();
                    } catch (SQLException | IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            JLabel jlabel_file = new JLabel("上传文件",SwingConstants.CENTER);
            jlabel_file.setBounds(208, 0, 171, 35);
            jlabel_file.setFont(new Font("逼格青春体简2.0", Font.BOLD, 18));
            // 设置上传文件按钮并添加回调函数

            jpanel_file = new JPanel();
            jpanel_file.setLayout(null);
            jpanel_file.setBounds(150, 157, 370, 600);
            jpanel_file.setBackground(new Color(218, 146, 255, 0));
            jpanel_file.add(jlist_file.getJPanel());
            jpanel_file.add(jlabel_file);
            jpanel_file.add(jbutton_filechoose);
            // 定义文件管理栏目中间容器并添加各组件
        }

        /* 底层框架*///==================================================================================================
        {
            JPanel jpanel_base = null;
            if (user_info.getSex().equals("男"))
                jpanel_base = new JPanel_image(540,720,path_m);
            else
                jpanel_base = new JPanel_image(540,720,path_w);
            // 根据性别添加背景图片

            this.add(jpanel_info);
            this.add(jpanel_swit);
            this.add(jpanel_rece);
            this.add(jpanel_send);
            this.add(jpanel_anno);
            this.add(jpanel_file);
            this.add(jpanel_base);
            this.setTitle("Student");
            this.setDefaultLookAndFeelDecorated(true);
            this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            this.setBounds(5, 50, 550, 716);
            this.setResizable(true);
            this.setVisible(true);
            swit_rece();
            // 设置主窗体的各参数，添加所有界面，使其显示
        }
    }


    /* 主界面功能函数*///=================================================================================================
    /* 主界面初始化*///======================================================================================================
    //
    //  @function   :   获取个人信息，并显示在个人信息界面
    //
    private void info_init() throws SQLException {
        user_info = new Person(Interface_UI_SQL.SQLgetStuInfo(user_id));
        // 根据学生账号获取学生所有信息

        jpanel_info.removeAll();
        jpanel_info.setLayout(null);
        jpanel_info.setBounds(0,0,540,170);
        jpanel_info.setBackground(new Color(255, 216, 57,0));
        // 重置个人信息界面中间容器

        JLabel info_name = new JLabel(user_info.getName());
        JLabel info_id   = new JLabel(user_info.getUsername());
        JLabel info_clas = new JLabel(user_info.getClas());
        info_name.setBounds(160,37,120,30);
        info_id  .setBounds(170,73,120,30);
        info_clas.setBounds(160,110,120,30);
        info_name.setForeground(new Color(128,226,251));
        info_id  .setForeground(new Color(128,226,251));
        info_clas.setForeground(new Color(128,226,251));
        info_name.setFont(new Font("逼格青春体简2.0", Font.BOLD, 15));
        info_id  .setFont(new Font("方正非凡体简体", Font.BOLD, 20));
        info_clas.setFont(new Font("逼格青春体简2.0", Font.BOLD, 15));
        // 设置标签，分别显示学生的姓名、学号、班级

        jpanel_info.add(info_name);
        jpanel_info.add(info_id);
        jpanel_info.add(info_clas);
        jbutton_refr.repaint(jpanel_info);
        jbutton_setu.repaint(jpanel_info);
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
        jlist_rece.list_init();
        jlist_send.list_init();
        jlist_anno.list_init();
        jlist_file.list_init();
        // 刷新每个模块的表格
        this.repaint();
    }

    /* 修改信息按钮回调函数*///======================================================================================================
    //
    //  @function   :   弹出新的修改信息窗口
    //
    private void info_change() throws SQLException {
        JFrame_setInfo window = new JFrame_setInfo(user_info,false);
        // 弹出新的信息设置窗口，设置模式为学生信息、修改模式
    }


    /* 子界面切换函数*///==================================================================================================
    /* 界面切换按钮回调函数*///======================================================================================================
    //
    //  @function   :   切换至对应界面，四个函数结构相同，均为令其他模块遮挡，对应模块显示
    //
    private void swit_rece(){
        jpanel_rece.setVisible(true);
        jpanel_send.setVisible(false);
        jpanel_anno.setVisible(false);
        jpanel_file.setVisible(false);
    }
    private void swit_send(){
        jpanel_rece.setVisible(false);
        jpanel_send.setVisible(true);
        jpanel_anno.setVisible(false);
        jpanel_file.setVisible(false);
    }
    private void swit_anno(){
        jpanel_rece.setVisible(false);
        jpanel_send.setVisible(false);
        jpanel_anno.setVisible(true);
        jpanel_file.setVisible(false);
    }
    private void swit_file(){
        jpanel_rece.setVisible(false);
        jpanel_send.setVisible(false);
        jpanel_anno.setVisible(false);
        jpanel_file.setVisible(true);
    }


    /* 问题功能函数*///==================================================================================================
    /* 提问按钮回调函数*///======================================================================================================
    //
    //  @function   :   弹出新的编辑问题窗口
    //
    private void mess_send() throws SQLException, IOException {
        JFrame_question window = new JFrame_question();
        window.setSendRecer(user_id,Interface_UI_SQL.SQLgetTeacherId(user_id));
        refresh();
        // 弹出新的编辑问题函数，设置模式为学生提问、可编辑。
    }


    /* 上传文件功能函数*///==================================================================================================
    /* 上传文件按钮回调函数*///======================================================================================================
    //
    //  @function   :   选择文件路径并上传，由以下两个函数共同实现
    //
    private void file_upload() throws SQLException, IOException {
        File f = choosefile();  //选择文件
        if (f != null) {
            Interface_UI_SQL.SQLFileUpload(user_id, f.getPath());
            //上传文件
            JOptionPane.showMessageDialog(null, "文件上传成功!", "Congratulation!", 1);
        }
        refresh();
    }
    private File choosefile(){
        //打开新的窗口选择文件并选择路径
        JFileChooser chooser=new JFileChooser();
        chooser.showOpenDialog(new JFrame());
        File f = chooser.getSelectedFile();
        return f;
    }



    /* 浏览模式设置函数*///======================================================================================================
    //
    //  @function   :   将学生界面变为浏览模式，用于老师浏览学生成长档案
    //
    public void setViewMode(){
        jbutton_anno.setVisible(false);
        jbutton_setu.setVisible(false);
        jbutton_rece.setVisible(false);
        jbutton_file.setVisible(false);
        jbutton_messsend.setVisible(false);
        // 将各不需要的按键遮挡

        jlist_rece.setViewMode(true);
        jlist_send.setViewMode(true);
        // 将列表设置为浏览模式

        this.setBounds(545, 50, 545, 716);
        // 改变窗体位置
    }
}
