/*
 * 文件名：JFrame_message.java
 * 作者：高占恒
 * 描述：消息界面：用于编辑/查看相应的消息/公告
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
import java.text.SimpleDateFormat;
import java.util.Date;
// 外部引用

public class JFrame_message extends JFrame {
    private static String path_u = "photo/Message_up.png";
    private static String path_d = "photo/Message_down.png";
    // 定义背景图片路径

    public Message message;         // 定义消息结构体

    private JLabel message_titl;    // 定义标题标签
    private JLabel message_detl;    // 定义内容标签

    public JTextField jtext_titl;    // 定义标题编辑文本框
    private JTextArea jtext_detl;    // 定义内容编辑文本框
    private JScrollPane jpanel_detail;        // 定义内容编辑为可滑动窗口

    private JButton_Image jbutton_send;       // 发送按钮
    private JButton_time_Select date_select;  // 时间选择按钮
    private JPanel jpanel_message;            // 消息编辑中间容器

    private JFrame_stu_Select jframe_sect;    // 学生选择界面




    /* 初始化方式一*///======================================================================================================
    //
    //  @function   :   根据传入参数数量，初始化对象中的各项，并调用不同的构造方法。
    //                  这个方式中将模式设置为编辑模式
    //
    public JFrame_message() throws SQLException {
        message = new Message();
        message_init();
        // 初始化消息界面

        this.setWritable(true);
        // 设置可编辑
    }
    /* 初始化方式二*///======================================================================================================
    //
    //  @input      ：  news：消息详情
    //  @function   :   根据传入参数数量，初始化对象中的各项，并调用不同的构造方法。
    //                  这个方式中将模式设置为查看
    //
    public JFrame_message(Message news) {
        message = news;
        message_init();
        // 初始化消息界面

        this.setWritable(false);
        // 设置为不可编辑
    }


    /* 消息界面*///======================================================================================================
    //
    //  @function   :   初始化消息界面
    //
    private void message_init(){
        /* 基本组件*///==================================================================================================
        message_titl = new JLabel("标题");
        message_detl = new JLabel("内容");
        message_titl.setBounds(85,15,50,30);
        message_detl.setBounds(85,46,50,30);
        message_titl.setFont(new Font("逼格青春体简2.0", Font.BOLD, 18));
        message_detl.setFont(new Font("逼格青春体简2.0", Font.BOLD, 18));
        // 定义标题和内容标签，设置其参数

        jtext_titl = new JTextField("");
        jtext_detl = new JTextArea ("");
        jtext_titl.setBounds(150,15,330,25);
        jtext_detl.setBounds(0,0,400,140);
        jtext_detl.setAutoscrolls(true);
        // 定义标题和内容文本框，设置其参数

        jbutton_send = new JButton_Image(20,273,110,40,"发  送",path_d,null);
        jbutton_send.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    message_send();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        // 设置发送按钮并添加回调函数


        /* 中间容器*///==================================================================================================
        jpanel_detail = new JScrollPane(jtext_detl);
        jpanel_detail.setBounds(80,84,400,140);
        jpanel_detail.setBackground(new Color(255, 244, 120,255));
        jpanel_detail.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        // 定义内容窗口的可滑动性

        jpanel_message = new JPanel();
        jpanel_message.setLayout(null);
        jpanel_message.setBounds(0,0,540,360);
        jpanel_message.setBackground(new Color(255, 244, 120,0));
        jpanel_message.add(jpanel_detail);
        jpanel_message.add(jtext_titl);
        jpanel_message.add(message_detl);
        jpanel_message.add(message_titl);
        // 定义中间容器，并添加组件




        /* 底层框架*///==================================================================================================
        this.add(jpanel_message);
        this.setTitle("Message");
        this.setDefaultLookAndFeelDecorated(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setBounds(540, 0, 540, 360);
        this.setResizable(true);
        this.setVisible(true);
        // 设置主窗体的各参数，添加所有界面，使其显示
    }



    /* 模式设置函数*///==================================================================================================
    //
    //  @input      ：  sender：发信人       receicer：收信人
    //  @function   :   设置消息的接收方和发送方
    //
    public void setSendRecer(String sender, String receicer) {
        message.sender = sender;
        message.recevicer = receicer;
    }
    /* 模式设置函数*///==================================================================================================
    //
    //  @input      ：  isanno：是否为公告
    //  @function   :   设置是不是为公告模式
    //
    public void setIsAnno(boolean isanno) throws SQLException {
        message.IsAnno = isanno;
        if (!isanno) jframe_sect = new JFrame_stu_Select(message.sender);
    }

    /* 模式设置函数*///==================================================================================================
    //
    //  @input      ：  iswrite：是否可编辑
    //  @function   :   设置界面是否可编辑
    //
    public void setWritable(boolean iswrite) {
        if(iswrite){
            JPanel jpanel_base = new JPanel_image(540,360,path_d);
            // 设置背景图片
            this.add(jpanel_base);
            this.setBounds(545, 408, 550, 358);
            // 设置JFrame界面

            jbutton_send.repaint(jpanel_message);
            jtext_detl.setText("");
            jtext_titl.setText("");
            // 设置文本框为空

            SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
            date_select = new JButton_time_Select(new SimpleDateFormat("yyyy-MM-dd"),form.format(new Date()));
            date_select.setBounds(175, 270,200,30);
            date_select.getButton().repaint(jpanel_message);
            // 添加时间选择按钮
        }
        else{
            JPanel jpanel_base = new JPanel_image(540,360,path_u);
            // 设置背景图片
            this.add(jpanel_base);
            this.setBounds(545,50,550,358);
            // 设置JFrame界面

            message_titl.setLocation(85,35);
            message_detl.setLocation(85,66);
            jtext_titl.setLocation(150,35);
            jpanel_detail.setBounds(80,135,400,170);
            jtext_titl.setText(message.title );
            jtext_detl.setText(message.detail);
            jtext_titl.setEditable(false);
            jtext_detl.setEditable(false);
            // 填充文本框内容，并令其不可编辑

            JLabel jlabel_time = new JLabel(
                    message.sender + " to " + message.recevicer + "    " + message.settime,SwingConstants.RIGHT);
            jlabel_time.setBounds(200,85,280,30);
            jlabel_time.setFont(new Font("方正非凡体简体", Font.BOLD, 15));
            jpanel_message.add(jlabel_time);
            // 添加消息详情标签
        }
    }



    /* 添加发送按钮回调函数*///======================================================================================================
    //
    //  @function   :   将消息信息更新到SQL
    //
    private void message_send() throws SQLException {
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        message.time = form.format(new Date());        //获取当前时间

        message.settime = date_select.Get_Select_Time();
        message.detail = jtext_detl.getText();
        message.title  = jtext_titl.getText();
        // 获取信息对象的发送时间、定时时间、内容、标题
        if (message.IsAnno) {  // 如果为公告模式进入
            Interface_UI_SQL.SQLAnnounceSend(message.toStrings());
            // 将消息作为公告上传至SQL
            dispose();
        }
        else {  // 如果不是公告模式进入
            jframe_sect.mess_send(message);
            // 将消息作为消息上传至SQL
        }

        JOptionPane.showMessageDialog(null, "发送成功！", "Over！", 1);
    }

}

