/*
 * 文件名：JList.java
 * 作者：高占恒
 * 描述：列表打印：用于根据不同的模式打印列表数据
 * 修改人: 高占恒
 * 修改时间：2019-12-12
 * 修改内容：添加注释
 */
package UI_Part;

import INFO.Message;
import INFO.Person;
import INFO.Question;
import SQL.Interface_UI_SQL;
import UI.UI_student;
// 内部注释

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
// 外部引用

public class JList {
    private static String path = "photo/UI_panel.png";
    private static String path_0 = "photo/Student_m.png";
    // 定义背景图片

    private String user_id;              // 用户账号
    public int Page;                     // 当前页数

    public JButton_Image jbutton_fileproP;
    public JButton_Image jbutton_filenextP;    // 翻页按钮

    private JPanel jlistpanel;           // 中间容器
    private String[][] list;             // 列表数据

    private int[] index;                 // 打印列表的条目及顺序设定
    private String string_button;        // 按钮上的字符串
    private boolean ViewMode;            // 是否为浏览模式

    private int Mode;                    // 列表模式
    /* 关于Mode：
     * Mode = 1：学生界面第一子界面 收信箱
     *                  回调函数为 查看可回复的信息
     * Mode = 2：学生界面第二子界面 问题状态
     *                  回调函数为 查看不可回复的信息
     * Mode = 3：学生界面第三子界面 公告栏
     *                  回调函数为 查看不可回复的公告
     * Mode = 4：学生界面第四子界面 文件管理
     *                  回调函数为 下载文件
     *
     * Mode = 7：老师界面第一子界面 收信箱
     *                  回调函数为 查看可回复的信息
     * Mode = 8：老师界面第二子界面 发信箱
     *                  回调函数为 查看不可回复的信息
     * Mode = 3：老师界面第三子界面 公告栏
     *                  回调函数为 查看不可回复的公告 与 Mode = 3复用
     * Mode = 4：老师界面第四子界面 文件管理
     *                  回调函数为 下载文件 与 Mode = 4复用
     * Mode = 5：老师界面第五子界面 学生管理
     *                  回调函数为 修改学生信息
     * Mode = 6：老师界面第六子界面 成长管理
     *                  回调函数为 查看学生信息* Mode = 7：老师界面第一子界面 收信箱
     *
     *
     *
     *
     * Mode = 8：管理员界面第一子界面 发信箱
     *                    回调函数为 查看不可回复的信息 与 Mode = 8复用
     * Mode = 3：管理员界面第二子界面 公告栏
     *                    回调函数为 查看不可回复的公告 与 Mode = 3复用
     * Mode = 5：管理员界面第三子界面 账号管理
     *                    回调函数为 修改学生信息 与 Mode = 5复用
     * Mode = 9：管理员界面第四子界面 老师信息
     *                    回调函数为 提醒老师答疑
     *
     */



    /* 列表初始化*///======================================================================================================
    //
    //  @function   :   根据列表模式，初始化对象中的各项。
    //
    public JList (String username, int mode) throws SQLException, IOException {
        ViewMode = false;
        user_id = username;
        Page = 1;
        Mode = mode;
        switch (Mode){
            case 1: string_button = "查看"; index = new int[]{1, 2, 3, 7}; break;
            case 2: string_button = "查看"; index = new int[]{1, 8, 3, 5}; break;
            case 3: string_button = "查看"; index = new int[]{4, 3, 2, 1}; break;
            case 4: string_button = "下载"; index = new int[]{1, 4, 2, 3}; break;
            case 5: string_button = "修改"; index = new int[]{2, 3, 0, 5}; break;
            case 6: string_button = "查看"; index = new int[]{2, 3, 0, 5}; break;
            case 7: string_button = "回复"; index = new int[]{1, 8, 2, 3}; break;
            case 8: string_button = "查看"; index = new int[]{1, 2, 3, 4}; break;
            case 9: string_button = "提醒"; index = new int[]{1, 2, 3, 5}; break;
            default: break;
        }
        // 根据模式对各参数进行初始化

        jlistpanel = new JPanel();
        jlistpanel.setBounds(2,112,380,450);
        jbutton_filenextP = new JButton_Image(400,517,80,30,"下一页",path_0,jlistpanel);
        jbutton_fileproP = new JButton_Image(215,517,80,30,"上一页",path_0,jlistpanel);
        jbutton_filenextP.setPos(250,360);
        jbutton_fileproP.setPos(65,360);
        // 初始化中间容器和添加翻页按钮

        list_init();
        // 开始列表的获取和打印
    }



    /* 列表获取*///======================================================================================================
    //
    //  @function   :   根据列表模式，获取列表信息。
    //
    private void getList() throws SQLException, IOException {
        switch (Mode){
            case 1: list = Interface_UI_SQL.SQLgetReceList(user_id); break;
            case 2:
            case 7: list = Interface_UI_SQL.SQLgetQuesList(user_id); break;
            case 8: list = Interface_UI_SQL.SQLgetSendList(user_id); break;
            case 3: list = Interface_UI_SQL.SQLgetAnnoList(user_id); break;
            case 4: list = Interface_UI_SQL.SQLgetFileList(user_id); break;
            case 5:
            case 6: list = Interface_UI_SQL.SQLgetStudList(user_id); break;
            case 9: list = Interface_UI_SQL.SQLgetTeacList(); break;
            default: break;
        }
        // 根据列表模式，获取列表信息，模式对应的信息在上边已说明
    }



    /* 按钮回调选择*///======================================================================================================
    //
    //  @function   :   根据列表模式，选择按钮回调。
    //
    private void button_callback(int i) throws IOException, SQLException {
        switch (Mode){
            case 1: rece_view(i);     break;
            case 2: ques_view(i,true);     break;
            case 7: ques_view(i,false);     break;
            case 8: send_view(i);     break;
            case 3: anno_view(i);     break;
            case 4: file_download(i); break;
            case 5: stud_chge(i);     break;
            case 6: grow_view(i);     break;
            case 9: teac_noti(i);     break;
            default: break;
        }
        // 根据列表模式，调用回调函数，模式对应的信息在上边已说明
    }

    /* 浏览模式设置*///======================================================================================================
    //
    //  @function   :   设置为浏览模式
    //
    public void setViewMode(boolean viewMode) {
        ViewMode = viewMode;
    }

    /* 获取JPanel*///======================================================================================================
    //
    //  @function   :   获取JPane
    //
    public JPanel getJPanel(){ return jlistpanel; }




    /* 列表打印*///======================================================================================================
    //
    //  @function   :   根据列表模式，获取信息并打印在JPanel中。
    //
    public void list_init() throws SQLException, IOException {
        jlistpanel.removeAll();
        jlistpanel.setLayout(null);
        jlistpanel.setBounds(2,112,380,450);
        jlistpanel.setBackground(new Color(255, 85, 49, 0));
        // 定义列表中间容器

        getList();
        if(list == null) return;
        // 获取列表

        int maxpage = list.length/8+1;
        int minpage = 1;
        int minindex = (Page-1)*8;
        int maxindex = (Page==maxpage)? (list.length): (minindex+8);
        // 根据获取的列表计算页数和索引

        for(int i=minindex,j=0; i<maxindex; i++,j++){   // 根据页数显示8条数据
            JLabel col_1 = new JLabel(list[i][index[0]]);
            JLabel col_2 = new JLabel(list[i][index[1]]);
            JLabel col_3 = new JLabel(list[i][index[2]]);
            JLabel col_4 = new JLabel(list[i][index[3]]);
            JLabel col_5 = new JLabel(string_button,SwingConstants.CENTER);
            // 选择5列显示 并设置显示内容
            JButton button = new JButton_Image(315,6,60,30,path);
            col_1.setFont(new Font("方正非凡体简体", Font.BOLD, 15));
            col_2.setFont(new Font("方正非凡体简体", Font.BOLD, 14));
            col_3.setFont(new Font("方正非凡体简体", Font.PLAIN, 15));
            col_4.setFont(new Font("方正非凡体简体", Font.PLAIN, 15));
            col_1.setBounds(7,7+j*45,60,30);
            col_2.setBounds(73,7+j*45,45,30);
            col_3.setBounds(127,8+j*45,90,30);
            col_4.setBounds(225,8+j*45,90,30);
            col_5.setBounds(310,7+j*45,60,30);
            button.setBounds(315,6+j*45,60,30);
            button.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        button_callback(button.getY()/45 + minindex);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            // 设置每列格式并添加第5列的按钮回调函数

            JPanel unit = new JPanel_image(375,45,path);
            unit.setBounds(0,j*45,375,45);
            // 设置每行的背景图片

            jlistpanel.add(col_1);
            jlistpanel.add(col_2);
            jlistpanel.add(col_3);
            jlistpanel.add(col_4);
            jlistpanel.add(col_5);
            jlistpanel.add(button);
            jlistpanel.add(unit);
            // 将每列的组件添加到中间容器
        }

        JLabel jlabel_page = new JLabel(Page + "/" + maxpage);
        jlabel_page.setBounds(185,360,100,30);
        jlabel_page.setForeground(new Color(84, 130, 168));
        // 添加页数标志

        if( Page!= minpage) jbutton_fileproP.repaint(jlistpanel);
        if( Page!= maxpage) jbutton_filenextP.repaint(jlistpanel);
        jlistpanel.add(jlabel_page);
        // 将翻页按钮和页数标志添加到中间容器
    }



    /* 按钮回调——收信箱*///======================================================================================================
    //
    //  @input      ：  i：列表索引值
    //  @function   :   查看相应消息
    //
    private void rece_view(int i) throws SQLException {
        if (ViewMode){
            JOptionPane.showMessageDialog(null, "无权查看！", "Warning！", 0);
            return;
        }
        // 当为浏览模式时不允许查看

        String[] s = Interface_UI_SQL.SQLgetMessInfo(list[i][0]);
        // 从SQL中获取该条消息

        Message m = new Message(s);
        JFrame_message window= new JFrame_message(m);
        // 打开新的消息窗口，设置模式为只读
    }

    /* 按钮回调——发信箱*///======================================================================================================
    //
    //  @input      ：  i：列表索引值
    //  @function   :   查看相应消息
    //
    private void send_view(int i) throws SQLException {
        String[] s = Interface_UI_SQL.SQLgetMessInfo(list[i][0]);
        // 从SQL中获取该条消息

        Message m = new Message(s);
        JFrame_message window= new JFrame_message(m);
        // 打开新的消息窗口，设置模式为只读
    }

    /* 按钮回调——问题情况*///======================================================================================================
    //
    //  @input      ：  i：列表索引值
    //  @function   :   查看相应问题
    //
    private void ques_view(int i,boolean IsStudent) throws SQLException {
        String[] s = Interface_UI_SQL.SQLgetQuesInfo(list[i][0]);
        // 从SQL中获取该条问题

        JFrame_question window= new JFrame_question(new Question(s),IsStudent);
        if (ViewMode) window.setBounds(0,50,545,716);
        // 打开新的问题窗口，根据模式选择是老师还是学生打开
    }


    /* 按钮回调——公告栏*///======================================================================================================
    //
    //  @input      ：  i：列表索引值
    //  @function   :   查看相应公告
    //
    private void anno_view(int i) throws SQLException {
        String[] s = Interface_UI_SQL.SQLgetAnnoInfo(list[i][0]);
        // 从SQL中获取该条公告

        Message m = new Message(s);
        JFrame_message window= new JFrame_message(m);
        // 打开新的消息窗口，设置模式为公告模式
    }


    /* 按钮回调——文件管理*///======================================================================================================
    //
    //  @input      ：  i：列表索引值
    //  @function   :   下载相应文件，有以下两个函数共同实现
    //
    private void file_download(int i) throws IOException, SQLException {
        String path = choosepath(i);
        // 选择下载路径
        Interface_UI_SQL.SQLFileDownload(list[i][0],path);
        // 将文件下载到该路径
    }
    private String choosepath(int i){
        //打开窗口选择下载路径
        JFileChooser chooser=new JFileChooser();
        chooser.setDialogTitle("另存为...");
        chooser.setApproveButtonText("保存");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setSelectedFile(new File(list[i][1]));
        chooser.setDialogType(JFileChooser.SAVE_DIALOG);
        chooser.showOpenDialog(new JFrame());
        File file = chooser.getSelectedFile();
        String path = file.getAbsolutePath();
        path = path.substring(0,path.lastIndexOf("\\")+1);
        return path;
    }


    /* 按钮回调——学生管理*///======================================================================================================
    //
    //  @input      ：  i：列表索引值
    //  @function   :   修改相应学生信息
    //
    private void stud_chge(int i) throws SQLException {
        Person student = new Person(Interface_UI_SQL.SQLgetStuInfo(list[i][0]));
        // 根据选择的学生信息 打开新的学生信息修改界面
        JFrame_setInfo window = new JFrame_setInfo(student,user_id.equals("admin"));
        // 设置模式为学生信息修改 根据用户名选择是否为管理员修改
    }


    /* 按钮回调——成长文档*///======================================================================================================
    //
    //  @input      ：  i：列表索引值
    //  @function   :   查看相应学生档案
    //
    private void grow_view(int i) throws SQLException, IOException {
        UI_student window = new UI_student(list[i][0]);
        // 弹出新的学生界面
        window.setViewMode();
        // 设置模式为浏览模式
    }


    /* 按钮回调——老师信息*///======================================================================================================
    //
    //  @input      ：  i：列表索引值
    //  @function   :   提醒相应老师注意答疑
    //
    private void teac_noti(int i) throws SQLException, IOException {
        Interface_UI_SQL.SQLAlarmSend(list[i][0]);
        // 将该消息传至SQL
        JOptionPane.showMessageDialog(null, "提示成功！", "Congratulation！", 1);
    }

}
