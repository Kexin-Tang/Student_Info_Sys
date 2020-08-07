/*
 * 文件名：JFrame_question.java
 * 作者：高占恒
 * 描述：问题界面：用于编辑/查看/回复相应的问题
 * 修改人: 高占恒
 * 修改时间：2019-12-14
 * 修改内容：添加注释
 */
package UI_Part;

import Function.Question.Question_Answer;
import INFO.Question;
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

public class JFrame_question extends JFrame {
    private static String path = "photo/Question.png";
    // 定义背景图片

    public Question question;         // 定义问题结构体

    public JTextField jtext_titl;     // 定义标题文本框
    private JTextArea jtext_detl;     // 定义内容文本框
    private JTextArea jtext_answ;     // 定义回复文本框

    private JButton_Image jbutton_send;// 发送按钮
    private JButton_Image jbutton_resp;// 回复按钮

    private JScrollPane jpanel_detail;        // 定义内容编辑为可滑动窗口
    private JScrollPane jpanel_answer;        // 定义回复编辑为可滑动窗口

    private JPanel jpanel_question;           // 问题编辑中间容器

    /* 初始化方式一*///======================================================================================================
    //
    //  @function   :   根据传入参数数量，初始化对象中的各项，并调用不同的构造方法。
    //                  这个方式中将模式设置为提问模式
    //
    public JFrame_question(){
        question = new Question();
        question_init();
        // 初始化问题界面

        jbutton_send.repaint(jpanel_question);
        jtext_detl.setText("");
        jtext_titl.setText("");
        jtext_answ.setText("");
        jtext_answ.setEnabled(false);
        // 所有文本框设置为空 且回复不可编辑
    }
    /* 初始化方式二*///======================================================================================================
    //
    //  @input      ：  news：消息详情       IsStudent：是否为学生
    //  @function   :   根据传入参数数量，初始化对象中的各项，并调用不同的构造方法。
    //                  这个方式中将模式设置为查看或者回复
    //
    public JFrame_question(Question news,boolean IsStudent){
        question = news;
        question_init();
        // 初始化问题界面

        if (IsStudent){    // 如果为学生则进入查看模式
            jtext_detl.setText(question.getContent());
            jtext_titl.setText(question.getTitle());
            jtext_answ.setText(question.getAnswer());
            jtext_detl.setEnabled(false);
            jtext_titl.setEnabled(false);
            jtext_answ.setEnabled(false);
            // 将问题内容、标题、回复添加至文本框并全部设置为不可编辑
            JLabel jlabel_sendtime = new JLabel(question.getSend_time(),SwingConstants.LEFT);
            jlabel_sendtime.setBounds(260,155,200,30);
            JLabel jlabel_answtime = new JLabel(question.getAnswer_time(),SwingConstants.LEFT);
            jlabel_answtime.setBounds(260,380,200,30);
            jpanel_question.add(jlabel_sendtime);
            if (question.getFlag().equals("已回复")) jpanel_question.add(jlabel_answtime);
            // 添加问题详情标签
        }
        else
        {       // 如果为老师则进入回复模式
            jbutton_resp.repaint(jpanel_question);
            jtext_detl.setText(question.getContent());
            jtext_titl.setText(question.getTitle());
            jtext_answ.setText(question.getAnswer());
            jtext_detl.setEnabled(false);
            jtext_titl.setEnabled(false);
            // 将问题内容、标题添加至文本框并设置为不可编辑
            JLabel jlabel_answtime = new JLabel(
                    question.getSender() + "    " + question.getSend_time(),SwingConstants.LEFT);
            jlabel_answtime.setBounds(260,155,200,30);
            jpanel_question.add(jlabel_answtime);
            // 添加问题详情标签
        }
    }

    /* 问题界面*///======================================================================================================
    //
    //  @function   :   初始化问题界面
    //
    private void question_init(){
        /* 基本组件*///==================================================================================================
        jpanel_question = new JPanel();
        jpanel_question.setLayout(null);
        jpanel_question.setBounds(0,0,540,720);
        jpanel_question.setBackground(new Color(255, 244, 120,0));
        // 定义中间容器

        JLabel message_titl = new JLabel("标题");
        JLabel message_detl = new JLabel("内容");
        JLabel message_answ = new JLabel("回复");
        message_titl.setBounds(86,48,50,30);
        message_detl.setBounds(86,150,50,30);
        message_answ.setBounds(86,375,50,30);
        message_titl.setFont(new Font("逼格青春体简2.0", Font.BOLD, 18));
        message_detl.setFont(new Font("逼格青春体简2.0", Font.BOLD, 18));
        message_answ.setFont(new Font("逼格青春体简2.0", Font.BOLD, 18));
        // 定义标题、内容、回复标签，设置其参数

        jtext_titl = new JTextField("");
        jtext_detl = new JTextArea ("");
        jtext_answ = new JTextArea ("");
        jtext_titl.setBounds(150,48,310,30);
        jtext_detl.setBounds(0,0,380,160);
        jtext_detl.setAutoscrolls(true);
        jtext_answ.setBounds(0,0,380,160);
        jtext_answ.setAutoscrolls(true);
        // 定义标题和、内容、回复文本框，设置其参数


        jbutton_send = new JButton_Image(470, 365, 60, 40,"发送",path,null);
        jbutton_send.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    question_send();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        jbutton_resp = new JButton_Image(470, 365, 60, 40,"回复",path,null);
        jbutton_resp.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    question_resp();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        // 设置发送按钮并添加回调函数


        /* 中间容器*///==================================================================================================
        jpanel_detail = new JScrollPane(jtext_detl);
        jpanel_detail.setBounds(80,190,380,160);
        jpanel_detail.setBackground(new Color(255, 244, 120,255));
        jpanel_detail.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jpanel_answer = new JScrollPane(jtext_answ);
        jpanel_answer.setBounds(80,416,380,160);
        jpanel_answer.setBackground(new Color(255, 244, 120,255));
        jpanel_answer.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        // 定义内容和回复窗口的可滑动性

        jpanel_question.add(jpanel_detail);
        jpanel_question.add(jpanel_answer);
        jpanel_question.add(jtext_titl);
        jpanel_question.add(message_detl);
        jpanel_question.add(message_titl);
        jpanel_question.add(message_answ);
        // 在中间容器中添加组件




        /* 底层框架*///==================================================================================================
        JPanel jpanel_base = new JPanel_image(540,720,path);
        // 设置背景图片
        this.add(jpanel_question);
        this.add(jpanel_base);
        this.setTitle("Question");
        this.setDefaultLookAndFeelDecorated(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setBounds(545, 50, 540, 716);
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
        question.setSender(sender);
        question.setReceiver(receicer);
    }



    /* 添加发送按钮回调函数*///======================================================================================================
    //
    //  @function   :   将问题信息更新到SQL
    //
    private void question_send() throws SQLException {
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        question.setSend_time(form.format(new Date()));        //获取当前时间
        question.setContent(jtext_detl.getText());
        question.setTitle(jtext_titl.getText());
        // 获取问题对象的发送时间、内容、标题

        Interface_UI_SQL.SQLQuestionSend(question.toStrings());
        // 将问题信息上传至SQL

        dispose();
        JOptionPane.showMessageDialog(null, "发送成功！", "Over！", 1);
        // 提示发送成功
    }

    /* 添加回复按钮回调函数*///======================================================================================================
    //
    //  @function   :   将问题信息更新到SQL
    //
    private void question_resp() throws SQLException {
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        question.setAnswer_time(form.format(new Date()));        //获取当前时间
        question.setAnswer(jtext_answ.getText());
        question.setFlag("已回复");
        // 获取问题对象的回复时间、回复内容

        Question_Answer SQL = new Question_Answer();
        SQL.question_answer(question.getSend_time(),question.getAnswer(),question.getAnswer_time());
        // 将问题信息上传至SQL

        dispose();
        JOptionPane.showMessageDialog(null, "回复成功！", "Over！", 1);
        // 提示发送成功
    }

}


