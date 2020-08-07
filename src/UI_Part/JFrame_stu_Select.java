/*
 * 文件名：JFrame_stu_Select.java
 * 作者：高占恒
 * 描述：学生选择界面：用于群发时选择收信人
 * 修改人: 高占恒
 * 修改时间：2019-12-14
 * 修改内容：添加注释
 */
package UI_Part;

import INFO.Message;
import SQL.Interface_UI_SQL;
// 内部引用

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
// 外部引用

public class JFrame_stu_Select extends JFrame{
    private static String path = "photo/Select.png";
    private static String path_r = "photo/Select_red.png";
    private static String path_b = "photo/Select_blue.png";
    // 定义背景图片路径

    private String user_id;               // 定义使用者账号
    private String[][] stu_list;          // 定义学生列表
    private boolean[] IsSelect;           // 定义学生是否被选择

    private JScrollPane jspanel_stulist;        // 定义学生列表为可滑动窗口
    private JPanel jpanel_stulist;              // 定义学生列表中间容器
    private JButton_Image jbutton_all;          // 定义全部选取的按钮
    private JButton_Image jbutton_non;          // 定义全部取消的按钮



    public JFrame_stu_Select (String username) throws SQLException {
        user_id = username;
        select_init();
    }

    /* 学生选择界面*///======================================================================================================
    //
    //  @function   :   初始化学生选择界面
    //
    private void select_init() throws SQLException {
        stu_list = Interface_UI_SQL.SQLgetStudList(user_id);
        // 获取学生列表
        if (stu_list == null){
            JOptionPane.showMessageDialog(null, "您无学生！", "Error", 2);
            dispose();
            return;
        }
        // 学生列表为空时退出

        int stu_number = stu_list.length;
        IsSelect = new boolean[stu_number];
        for (int i=0; i<stu_number; i++) IsSelect[i] = false;
        // 根据学生列表初始化数组


        /* 基本组件*///==================================================================================================
        jpanel_stulist = new JPanel();
        jpanel_stulist.setLayout(new GridLayout(stu_number,5,20,5));
        jpanel_stulist.setPreferredSize(new Dimension(300,stu_number*30));
        jpanel_stulist.setBounds(0,0,300,stu_number*50);
        jpanel_stulist.setBackground(new Color(111, 255, 198,0));
        // 定义学生选择中间容器

        for( int i=0; i<stu_number; i++){
            JLabel col_1 = new JLabel(stu_list[i][0]);
            JLabel col_2 = new JLabel(stu_list[i][1]);
            JLabel col_3 = new JLabel(stu_list[i][2]);
            col_1.setBounds(10,10+i*50,100,30);
            col_2.setBounds(140,10+i*50,50,30);
            col_3.setBounds(200,10+i*50,60,30);
            // 选择4列显示 并设置显示内容
            JButton jbutton_stu = new JButton("选择");
            jbutton_stu.setBounds(280,10+i*50,60,30);
            jbutton_stu.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    int x = (stu_list.length<7)? (190/stu_list.length): 30;
                    button_callback(jbutton_stu.getY()/x);
                }
            });
            // 添加第4列的按钮回调函数

            JPanel unit = new JPanel_image(33,33,path_b);
            // 设置每行的背景图片

            jpanel_stulist.add(col_1);
            jpanel_stulist.add(col_2);
            jpanel_stulist.add(col_3);
            jpanel_stulist.add(jbutton_stu);
            jpanel_stulist.add(unit);
            // 将每列的组件添加到中间容器
        }


        /* 中间容器*///==================================================================================================
        jspanel_stulist = new JScrollPane(jpanel_stulist);
        jspanel_stulist.setBounds(80,120,400,190);
        jspanel_stulist.setBackground(new Color(116,217,255,255));
        jspanel_stulist.setWheelScrollingEnabled(true);
        jspanel_stulist.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        // 定义学生选择窗口的可滑动性

        jbutton_all = new JButton_Image(420,30,110,40,"选择全部",path,null);
        jbutton_all.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {selectall();}
        });
        jbutton_all.addColor(new Color(172, 252, 255));
        jbutton_non = new JButton_Image(15,30,110,40,"取消全部",path,null);
        jbutton_non.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {cancelall();}
        });
        jbutton_non.addColor(new Color(172, 252, 255));
        // 定义全部选择和全部去取消按钮并添加回调函数



        /* 底层框架*///==================================================================================================
        JPanel jpanel_base = new JPanel_image(540,360,path);
        jpanel_base.setLayout(null);
        jbutton_all.repaint(jpanel_base);
        jbutton_non.repaint(jpanel_base);
        //设置背景图片

        this.add(jspanel_stulist);
        this.add(jpanel_base);
        this.setTitle("Student");
        this.setDefaultLookAndFeelDecorated(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setBounds(545,50,550,362);
        this.setResizable(true);
        this.setVisible(true);
        // 设置主窗体的各参数，添加所有界面，使其显示
    }

    /* 学生选择按钮回调函数*///======================================================================================================
    //
    //  @input      ：  i：列表索引值
    //  @function   :   选择/取消选择相应学生
    //
    private void button_callback(int i){
        IsSelect[i] = !IsSelect[i];
        // 将该学生的标志为置反

        JPanel jPanel = new JPanel();
        if (IsSelect[i]) jPanel = new JPanel_image(33,33,path_r);
        else jPanel = new JPanel_image(33,33,path_b);
        // 根据是否被选择 新建一个是否被选择的图片容器

        jpanel_stulist.remove(i*5+4);
        jpanel_stulist.add(jPanel,i*5+4);
        // 更换该学生的选择图片容器

        jspanel_stulist.revalidate();
        // 界面显示刷新
    }

    /* 发送消息函数*///======================================================================================================
    //
    //  @input      ：  message：消息详情
    //  @function   :   给所有被选择的学生发送消息
    //
    public void mess_send(Message message) throws SQLException {
        for(int i=0; i<stu_list.length; i++){
            if (IsSelect[i]){
                message.recevicer = stu_list[i][0];
                Interface_UI_SQL.SQLMessageSend(message.toStrings());
            }
        }
        // 给所有被选择的学生发送消息
        cancelall();
        // 取消全选
    }

    /* 全部选择按钮回调函数*///======================================================================================================
    //
    //  @function   :   将所有学生选择
    //
    private void selectall() {
        for(int i=0; i<stu_list.length; i++){
            if (IsSelect[i] == false);
            IsSelect[i] = true;
            JPanel jPanel = new JPanel_image(33,33,path_r);

            jpanel_stulist.remove(i*5+4);
            jpanel_stulist.add(jPanel,i*5+4);
        }
        // 将所有学生的标志位置1；并且将学生对应的显示容器切换为被选择
        jspanel_stulist.revalidate();
    }

    /* 取消选择按钮回调函数*///======================================================================================================
    //
    //  @function   :   将所有学生取消选择
    //
    private void cancelall(){
        for(int i=0; i<stu_list.length; i++){
            if (IsSelect[i] == true);
            IsSelect[i] = false;
            JPanel jPanel = new JPanel_image(33,33,path_b);

            jpanel_stulist.remove(i*5+4);
            jpanel_stulist.add(jPanel,i*5+4);
        }
        // 将所有学生的标志位置0；并且将学生对应的显示容器切换为不被选择
        jspanel_stulist.revalidate();
    }


}
