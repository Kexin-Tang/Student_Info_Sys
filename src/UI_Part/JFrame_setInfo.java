/*
 * 文件名：JFrame_setInfo.java
 * 作者：高占恒
 * 描述：学生信息界面：用于添加/修改学生信息
 * 修改人: 高占恒
 * 修改时间：2019-12-15
 * 修改内容：添加注释
 */
package UI_Part;

import INFO.Person;
import SQL.Interface_UI_SQL;
// 内部引用

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
// 外部引用


public class JFrame_setInfo extends JFrame{
    public boolean Teacher_info;     // 是否为老师信息编辑

    private JTextField jtext_user;   // 定义账号输入文本框
    private JTextField jtext_pswd;   // 定义密码输入文本框
    private JTextField jtext_name;   // 定义姓名输入文本框
    private JTextField jtext_sex ;   // 定义性别输入文本框
    private JTextField jtext_clas;   // 定义班级输入文本框
    private JTextField jtext_mail;   // 定义邮箱输入文本框
    private JTextField jtext_prot;   // 定义密保输入文本框

    private JButton_Image jbutton_add;  // 定义添加按钮
    private JButton_Image jbutton_del;  // 定义删除按钮
    private JButton_Image jbutton_upd;  // 定义修改按钮

    private JPanel jpanel_setinfo;   // 定义中间容器

    private static String path_add = "photo/Register_add.png";
    private static String path_upd = "photo/Register_upd.png";
    // 定义背景图片


    /* 初始化方式一*///======================================================================================================
    //
    //  @input      ：  student：学生信息      IsAdmin：是否为管理员
    //  @function   :   根据传入参数数量，初始化对象中的各项，并调用不同的构造方法。
    //                  这个方式中将模式设置为修改/删除模式
    //
    public JFrame_setInfo(Person student,boolean IsAdmin) throws SQLException {
        if(student.getUsername().startsWith("T")) Teacher_info = true;
        else Teacher_info = false;
        // 判断是否为老师的信息

        setinfo_init();
        setInfo(student);
        // 界面初始化，将文本框填充个人信息

        jbutton_add.setDiscover(false);
        // 添加按钮不可用

        jtext_user.setEditable(false);
        // 账号不可编辑
        if( !IsAdmin){   // 如果不是管理员模式进入
            jtext_pswd.setVisible(false);
            jtext_prot.setVisible(false);
            // 其他人不可以编辑密码和密保
            JPanel jpanel_base = new JPanel_image(540,720,path_upd);
            this.add(jpanel_base);
            // 设置背景图片

        }else{   // 如果为管理员模式进入
            jtext_pswd.setEditable(true);
            jtext_prot.setEditable(true);
            // 管理员可以编辑密码和密保
            JPanel jpanel_base = new JPanel_image(540,720,path_upd);
            this.add(jpanel_base);
            // 设置背景图片

            JLabel jlabel_patc = new JLabel(new ImageIcon(path_add));
            jlabel_patc.setBounds(100,0,340,720);
            jpanel_base.setLayout(null);
            jpanel_base.add(jlabel_patc);
            // 修复一些没背景显示到的地方
        }
    }
    /* 初始化方式二*///======================================================================================================
    //
    //  @input      ：  IsAddTeacher：是否添加老师
    //  @function   :   根据传入参数数量，初始化对象中的各项，并调用不同的构造方法。
    //                  这个方式中将模式设置为添加
    //
    public JFrame_setInfo(boolean IsAddTeacher){
        if(IsAddTeacher) Teacher_info = true;
        else Teacher_info = false;
        // 判断是否为老师的信息

        setinfo_init();
        setEmpty();
        // 界面初始化，将文本框置空

        jbutton_del.setDiscover(false);
        jbutton_upd.setDiscover(false);
        // 修改和删除按钮不可用

        JPanel jpanel_base = new JPanel_image(540,720,path_add);
        this.add(jpanel_base);
        // 设置背景图片
    }



    /* 学生信息界面*///======================================================================================================
    //
    //  @function   :   初始化学生信息界面
    //
    private void setinfo_init(){
        /* 基本组件*///==================================================================================================
        jtext_user = new JTextField("");
        jtext_pswd = new JTextField("");
        jtext_name = new JTextField("");
        jtext_sex  = new JTextField("");
        jtext_clas = new JTextField("");
        jtext_mail = new JTextField("");
        jtext_prot = new JTextField("");
        jtext_user.setBounds(205,178,150,25);
        jtext_pswd.setBounds(205,239,150,25);
        jtext_name.setBounds(205,300,150,25);
        jtext_sex .setBounds(205,361,150,25);
        jtext_clas.setBounds(205,422,150,25);
        jtext_mail.setBounds(205,486,150,25);
        jtext_prot.setBounds(205,546,150,25);
        // 定义各输入文本框，设置其参数

        jpanel_setinfo = new JPanel();
        jpanel_setinfo.setLayout(null);
        // 定义中间容器

        jbutton_add = new JButton_Image(477, 340, 60, 47,"add",path_add,jpanel_setinfo);
        jbutton_add.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    stu_add();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        jbutton_add.addColor(new Color(87, 117, 152));
        jbutton_del = new JButton_Image(477, 440, 60, 47,"del",path_upd,jpanel_setinfo);
        jbutton_del.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    stu_del();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        jbutton_del.addColor(new Color(87, 117, 152));
        jbutton_upd = new JButton_Image(477, 253, 60, 47,"upd",path_upd,jpanel_setinfo);
        jbutton_upd.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    stu_upd();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        jbutton_upd.addColor(new Color(87, 117, 152));
        // 定义添加、删除、修改按钮并添加回调函数

        jbutton_add.addText("确定");
        jbutton_del.addText("注销");
        jbutton_upd.addText("保存");
        // 设置按钮文字显示


        /* 中间容器*///==================================================================================================
        jpanel_setinfo.setBounds(0,0,540,720);
        jpanel_setinfo.setBackground(new Color(255, 244, 120,0));
        jpanel_setinfo.add(jtext_user);
        jpanel_setinfo.add(jtext_pswd);
        jpanel_setinfo.add(jtext_name);
        jpanel_setinfo.add(jtext_sex );
        jpanel_setinfo.add(jtext_clas);
        jpanel_setinfo.add(jtext_mail);
        jpanel_setinfo.add(jtext_prot);
        // 设置中间容器，并添加组件



        /* 底层框架*///==================================================================================================
        this.add(jpanel_setinfo);
        this.setTitle("Login");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setBounds(545, 50, 550, 716);
        this.setDefaultLookAndFeelDecorated(true);
        this.setResizable(true);
        this.setVisible(true);
        // 设置主窗体的各参数，添加所有界面，使其显示
    }


    /* 显示函数*///==================================================================================================
    //
    //  @function   :   获取文本框内容并存在学生对象中
    //
    public Person getStuInfo(){
        if(jtext_user.getText().equals("")) return null;
        if(jtext_pswd.getText().equals("")) return null;
        if(jtext_name.getText().equals("")) return null;
        if(jtext_sex .getText().equals("")) return null;
        if(jtext_clas.getText().equals("")) return null;
        if(jtext_mail.getText().equals("")) return null;
        if(jtext_prot.getText().equals("")) return null;
        // 判断是否有未输入的文本框

        String[] infomation = new String[7];

        infomation[0] = jtext_user.getText();
        infomation[1] = jtext_pswd.getText();
        infomation[2] = jtext_name.getText();
        infomation[3] = jtext_sex .getText();
        infomation[4] = jtext_clas.getText();
        infomation[5] = jtext_mail.getText();
        infomation[6] = jtext_prot.getText();
        // 读取文本框内容存入字符串数组

        Person student = new Person(infomation);
        // 将字符串数组存入学生对象

        return student;
    }


    /* 显示函数*///==================================================================================================
    //
    //  @input      ：  student：学生信息
    //  @function   :   将账号信息显示在文本框中
    //
    public void setInfo(Person student){
        if(student == null) return;

        jtext_user.setText(student.getUsername ());
        jtext_pswd.setText(student.getPwd      ());
        jtext_name.setText(student.getName     ());
        jtext_sex .setText(student.getSex      ());
        jtext_clas.setText(student.getClas     ());
        jtext_mail.setText(student.getEmail    ());
        jtext_prot.setText(student.getProtect  ());
        // 将账号信息显示在文本框中
    }


    /* 显示函数*///==================================================================================================
    //
    //  @function   :   将所有文本框置空
    //
    public void setEmpty() {
        jtext_user.setText("");
        jtext_pswd.setText("");
        jtext_name.setText("");
        jtext_sex .setText("");
        jtext_clas.setText("");
        jtext_mail.setText("");
        jtext_prot.setText("");
        // 将所有文本框置空
    }


    /* 添加按钮回调函数*///======================================================================================================
    //
    //  @function   :   将学生信息添加到SQL
    //
    private void stu_add() throws SQLException {
        //获取信息并检查
        Person student = getStuInfo();
        if ( student == null) {
            JOptionPane.showMessageDialog(null, "注册信息不足，请检查!", "Error!", 2);
            return;
        }

        //进一步详细检查
        if ( !check() ) return;


        //注册并提示结果
        if( Interface_UI_SQL.SQLStudentAdd(student.toStrings()) == 1){
            JOptionPane.showMessageDialog(null, "该账号已被注册！", "Error！", 2);
        }
        else{
            dispose();
            JOptionPane.showMessageDialog(null, "账号信息添加成功！", "Congratulation！", 1);
        }
    }

    /* 删除按钮回调函数*///======================================================================================================
    //
    //  @function   :   将学生信息从SQL中删除
    //
    private void stu_del() throws SQLException {
        String user_id = jtext_user.getText();
        // 读取账户名

        Interface_UI_SQL.SQLStudentDel(user_id);
        // 将学生信息从SQL中删除

        dispose();
        JOptionPane.showMessageDialog(null, "账号信息删除成功！", "Congratulation！", 1);
    }


    /* 修改按钮回调函数*///======================================================================================================
    //
    //  @function   :   将学生信息修改到SQL中
    //
    private void stu_upd() throws SQLException {
        Person student = getStuInfo();
        // 获取文本框内容

        if(student == null){
            JOptionPane.showMessageDialog(null, "学生信息不足，请检查!", "Error", 2);
            return;
        }
        // 检查信息是否足够

        Interface_UI_SQL.SQLStudentUpd(student.toStrings());
        // 将学生信息修改到SQL中

        dispose();
        JOptionPane.showMessageDialog(null, "学生信息修改成功！", "Congratulation！", 1);
    }



    /* 检查函数*///======================================================================================================
    //
    //  @function   :   检查学生信息是否格式合法
    //
    private boolean check(){
        Person student = getStuInfo();
        String temp;
        temp = student.getUsername();         //账户名检查
        if ( !( Teacher_info? temp.matches("T\\d{9}"): temp.matches("U\\d{9}"))  ){
            JOptionPane.showMessageDialog(null, "账号格式有误，请检查!", "Error!", 2);
            return false;
        }
        temp = student.getSex();         //性别检查
        if ( !( temp.matches("\\u7537") || temp.matches("\\u5973"))  ){
            JOptionPane.showMessageDialog(null, "性别格式有误，请检查!", "Error!", 2);
            return false;
        }
        temp = student.getClas();         //班级检查
        if ( !( temp.matches("[\\u4e00-\\u9fa5]{2,3}\\d{4}"))  ){
            JOptionPane.showMessageDialog(null, "班级格式有误，请检查!", "Error!", 2);
            return false;
        }
        temp = student.getProtect();         //密保检查
        if ( !( temp.matches("\\d{6}"))  ){
            JOptionPane.showMessageDialog(null, "密保格式有误，请检查!", "Error!", 2);
            return false;
        }
        return true;
    }
}

