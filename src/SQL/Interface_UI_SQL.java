package SQL;

import DAO.Alarm_DAO;
import DAO.File_DAO;
import DAO.Question_DAO;
import DAO.Student_DAO;
import Function.Announcement.GetAnnouncement;
import Function.Announcement.Publish;
import Function.Download.Download_File;
import Function.Download.Upload_File;
import Function.Login.Login;
import Function.Login.Register;
import Function.Login.Update_Stu_Info;
import Function.Question.Question_Answer;
import Function.Question.Question_Ask;
import INFO.Question;
import INFO.Student;
import Function.*;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class Interface_UI_SQL {

    private static String type="";

/******************************************************************************
 *
 *                          账号判断，数据匹配等工作函数接口
 *
 ******************************************************************************/
    ////////////////////////////////////////////////////////////////////////////
    //  @input      :   username    ->  输入的账号
    //                  password    ->  输入的密码
    //
    //  @output     :   String      ->  查询情况
    //                      flag[0]     //该账号和密码是否相匹配
    //                      flag[1]     //如果匹配，则该账号属于admin、老师还是学生
    //
    //  @function   :   将输入的密码和账号相匹配，查询是否有该用户并判断用户类型
    ////////////////////////////////////////////////////////////////////////////
    public static String SQLLoginCheck(String username, String password) throws SQLException {
        Login login = new Login();
        String[] flag = login.check(username,password);
        type = flag[1];
        return flag[0];
    }


    //////////////////////////////////////////////////////////////
    //  @input      :   NULL
    //
    //  @output     :   String  ->  返回账号类型
    //
    //  @function   :   返回用户类型，用于UI界面选择进入相应界面
    /////////////////////////////////////////////////////////////
    public static String SQLTypeCheck(){
        return type;
    }




    ///////////////////////////////////////////////////////////////////////
    //  @input      :   username    ->  学生登录的账号
    //
    //  @output     :   String[]    ->  返回账号对应信息
    //
    //                      result[0]   //学号/账号
    //                      result[1]   //密码
    //                      result[2]   //姓名
    //                      result[3]   //性别
    //                      result[4]   //班级
    //                      result[5]   //邮件地址
    //                      result[6]   //密保
    //
    //  @function   :   将学生账号指定的所有信息返回到数组中用于UI显示
    //////////////////////////////////////////////////////////////////////
    public static String[] SQLgetStuInfo(String username) throws SQLException {
        Student_DAO stu = new Student_DAO();
        return stu.show_one_student(username);
    }


    ///////////////////////////////////////////////////////////////////////
    //  @input      :   username    ->  老师登录的账号
    //
    //  @output     :   String[]    ->  返回账号对应信息
    //
    //                      result[0]   //学号/账号
    //                      result[1]   //密码
    //                      result[2]   //姓名
    //                      result[3]   //性别
    //                      result[4]   //班级
    //                      result[5]   //邮件地址
    //                      result[6]   //密保
    //
    //  @function   :   将老师账号指定的所有信息返回到数组中用于UI显示
    //////////////////////////////////////////////////////////////////////
    public static String[] SQLgetTeaInfo(String username) throws SQLException {
        Student_DAO stu = new Student_DAO();
        return stu.show_one_student(username);
    }




    ///////////////////////////////////////////////////////////////////////
    //  @input      :   username    ->  登录的账号
    //
    //  @output     :   String[][]  ->  如果username是admin = 显示老师和学生列表
    //                                  如果username是老师 = 显示老师对应班级的学生列表
    //
    //                      result[0]   //学号/账号
    //                      result[1]   //姓名
    //                      result[2]   //性别
    //                      result[3]   //班级
    //
    //  @function   :   将学生账号指定的所有信息返回到数组中用于UI显示
    //////////////////////////////////////////////////////////////////////
    public static String[][] SQLgetStudList( String username) throws SQLException {
        String[][] list =null ;

        Student_DAO student_dao = new Student_DAO();

        //如果是管理员使用该函数，则老师学生一起显示
        if(username.equals("admin")){
            list = student_dao.show_student_admin();
        }
        //如果是老师使用该函数，则只显示属于同一个班的学生信息
        else{
            String[] result = student_dao.Search_Class(username);
            list = student_dao.show_student(result[0]);
        }

        return list;
    }


    ////////////////////////////////////////////////////////////////
    //  @input      :   student  ->  要添加的学生的详细信息
    //
    //                  student[0]  //学号
    //                  student[1]  //密码
    //                  student[2]  //姓名
    //                  student[3]  //性别
    //                  student[4]  //班级
    //                  student[5]  //邮件地址
    //                  student[6]  //密保
    //
    //  @output     :   int     ->  0:  未注册过
    //                              1:  已注册过
    //
    //  @function   :   将学生信息存入数据库中
    /////////////////////////////////////////////////////////////////
    public static int SQLStudentAdd(String[] student) throws SQLException {
        Register reg = new Register();
        return reg.register(student);
    }



    ////////////////////////////////////////////////////////////////
    //  @input      :   student  ->  要修改的学生的详细信息
    //
    //                  student[0]  //学号
    //                  student[1]  //密码
    //                  student[2]  //姓名
    //                  student[3]  //性别
    //                  student[4]  //班级
    //                  student[5]  //邮件地址
    //                  student[6]  //密保
    //
    //  @output     :   NULL
    //
    //  @function   :   将学生信息进行更新
    /////////////////////////////////////////////////////////////////
    public static void SQLStudentUpd(String[] student) throws SQLException {
        Update_Stu_Info update_stu_info = new Update_Stu_Info();
        update_stu_info.update(student);
    }



    ////////////////////////////////////////////////////////
    //  @input      :   username    ->  指定删除的学生的账号
    //
    //  @output     ：   NULL
    //
    //  @function   :   将指定的学生信息删除
    ////////////////////////////////////////////////////////
    public static void SQLStudentDel(String username) throws SQLException {
        Student_DAO stu = new Student_DAO();
        stu.Delete_stu(username);
    }







    ////////////////////////////////////////////////////////////////
    //  @input      :   teacher     ->  要添加的老师的详细信息
    //
    //                  teacher[0]  //学号
    //                  teacher[1]  //密码
    //                  teacher[2]  //姓名
    //                  teacher[3]  //性别
    //                  teacher[4]  //班级
    //                  teacher[5]  //邮件地址
    //                  teacher[6]  //密保
    //
    //  @output     :   int     ->  0:  未注册过
    //                              1:  已注册过
    //
    //  @function   :   将老师信息存入数据库中
    /////////////////////////////////////////////////////////////////
    public static int SQLTeacherAdd(String[] teacher) throws SQLException {
        Register reg = new Register();
        return reg.register(teacher);
    }


    ////////////////////////////////////////////////////////////////
    //  @input      :   teacher     ->  要修改的老师的详细信息
    //
    //                  teacher[0]  //学号
    //                  teacher[1]  //密码
    //                  teacher[2]  //姓名
    //                  teacher[3]  //性别
    //                  teacher[4]  //班级
    //                  teacher[5]  //邮件地址
    //                  teacher[6]  //密保
    //
    //  @output     :   NULL
    //
    //  @function   :   将指定老师的信息进行修改
    /////////////////////////////////////////////////////////////////
    public static void SQLTeacherUpd(String[] student) throws SQLException {
        Update_Stu_Info update_stu_info = new Update_Stu_Info();
        update_stu_info.update(student);
    }



    ////////////////////////////////////////////////////////
    //  @input      :   username    ->  指定删除的老师的账号
    //
    //  @output     ：   NULL
    //
    //  @function   :   将指定的老师信息删除
    ////////////////////////////////////////////////////////
    public static void SQLTeacherDel(String username) throws SQLException {
        Student_DAO stu = new Student_DAO();
        stu.Delete_stu(username);
    }



    ///////////////////////////////////////////////////////
    //  @input      :   username    ->  学生的账号
    //
    //  @output     :   String      ->  学生所处班级的老师账号
    //
    //  @function   :   将学生对应老师的信息提取出来
    ///////////////////////////////////////////////////////
    public static String SQLgetTeacherId(String username) throws SQLException {
        Student_DAO stu = new Student_DAO();
        String[] teacher = stu.Search_Class(username);
        return teacher[1];
    }




    ///////////////////////////////////////////////////////
    //  @input      :   null
    //
    //  @output     :   String[][]
    //
    //                      result[0]   ->  id
    //                      result[1]   ->  老师信息
    //                      result[2]   ->  当日问题数量
    //                      result[3]   ->  回答问题数量
    //                      result[4]   ->  是否收到提醒
    //                      result[5]   ->  回答率
    //
    //  @function   :   将老师的统计信息组成表格
    ///////////////////////////////////////////////////////
    public static String[][] SQLgetTeacList() throws SQLException {
        Alarm_DAO alarm_dao = new Alarm_DAO();
        return alarm_dao.show_alarm();
    }


    /////////////////////////////////////////////////////
    //  @input      :   id  ->  老师的标识
    //
    //  @output     :   NULL
    //
    //  @function   :   提醒某位老师及时回复问题
    /////////////////////////////////////////////////////
    public static void SQLAlarmSend(String id) throws SQLException {
        Alarm_DAO alarm_dao = new Alarm_DAO();
        alarm_dao.Alarm(id);
    }










    /******************************************************************************
     *
     *                          消息、公告类型函数接口
     *
     ******************************************************************************/
    ////////////////////////////////////////////////////////////////////////
    //  @input      :   username        ->  以username为接收方消息的简介
    //
    //  @output     :   String[][]      ->  简介情况
    //
    //                  list[][0]   //消息唯一id
    //                  list[][1]   //消息标题
    //                  list[][2]   //发送方
    //                  list[][3]   //发送时间
    //
    //  @function   :   根据登录用户情况，选择显示该用户接收的信息情况
    /////////////////////////////////////////////////////////////////////////
    public static String[][] SQLgetReceList( String username) throws SQLException {
        Question_DAO question_dao = new Question_DAO();
        return question_dao.show_question_receiver(username);
    }


    ////////////////////////////////////////////////////////////////////////
    //  @input      :   username        ->  以username为发送方消息的简介
    //
    //  @output     :   String[][]      ->  简介情况
    //
    //                  list[][0]   //消息唯一id
    //                  list[][1]   //消息标题
    //                  list[][2]   //接收方
    //                  list[][3]   //发送时间
    //
    //  @function   :   根据登录用户情况，选择显示该用户发送的信息情况
    /////////////////////////////////////////////////////////////////////////
    public static String[][] SQLgetSendList( String username) throws SQLException {
        Question_DAO question_dao = new Question_DAO();
        return question_dao.show_question_sender(username);
    }


    ///////////////////////////////////////////////////////////////////////////////////////////
    //  @input      :   username        ->  传入当前操作对象的账号，通过判断不同的对象实现不同的功能
    //
    //  @output     :   String[][]
    //
    //                  list[][0]   //id
    //                  list[][1]   //公告标题
    //                  list[][2]   //发送方
    //                  list[][3]   //发送时间
    //
    //  @function   :   根据当前用户不同，显示相应的公告
    ////////////////////////////////////////////////////////////////////////////////////////////
    public static String[][] SQLgetAnnoList( String username) throws IOException, SQLException {
        GetAnnouncement getAnnouncement = new GetAnnouncement();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

        if(username.equals("admin")) {
            return getAnnouncement.getAnnouncement(df.format(new Date()),"");
        }
        else if(username.substring(0,1).equals("T")){
            return getAnnouncement.getAnnouncement(df.format(new Date()),username);
        }
        else{
            String teacher = SQLgetTeacherId(username);
            return getAnnouncement.getAnnouncement(df.format(new Date()),teacher);
        }
    }


    /////////////////////////////////////////////////////////////
    //  @input      :   id          ->  指定的消息的主键
    //
    //  @output     :   String[]    ->  返回消息的内容
    //
    //              question[0]   //消息id
    //              question[1]   //内容
    //              question[2]   //标题
    //              question[3]   //回答内容 == null
    //              question[4]   //发信人
    //              question[5]   //收信人
    //              question[6]   //时间
    //
    //  @function   :   将某一条消息的详细信息展现出来
    /////////////////////////////////////////////////////////////
    public static String[] SQLgetMessInfo(String id) throws SQLException {
        Question_DAO question_dao = new Question_DAO();
        return question_dao.show_one_question(id);
    }


    /////////////////////////////////////////////////////////////////
    //  @input      :   id          ->  指定的公告的主键
    //
    //  @output     :   String[]    ->  返回特定内容
    //
    //  @function   :   将某一个公告的详情显示出来
    //                      result[0]:  id
    //                      result[1]:  content
    //                      result[2]:  title
    //                      result[3]:  sender
    //                      result[4]:  recevicer   = null
    //                      result[5]:  time
    //                      result[6]:  answer_time = null
    //                      result[7]:  flag        = null
    /////////////////////////////////////////////////////////////////
    public static String[] SQLgetAnnoInfo(String id) throws SQLException {
        GetAnnouncement getAnnouncement = new GetAnnouncement();
        return getAnnouncement.show_one_anno(id);
    }


    //////////////////////////////////////////////////////////////
    //  @input      :   message     ->  将某一条信息设置好存入SQL
    //
    //                  message[0]      //内容
    //                  message[1]      //标题
    //                  message[2]      //发信人
    //                  message[3]      //收信人
    //                  message[4]      //时间
    //
    //  @output     :   NULL
    //
    //  @function   :   将某一条信息存入数据库
    //////////////////////////////////////////////////////////////
    public static void SQLMessageSend(String[] message) throws SQLException {
        Question_Ask question_ask = new Question_Ask();
        question_ask.question_ask(message[1],message[0],message[2],message[3],message[4]);
    }



    ////////////////////////////////////////////////////////
    //  @input      :   message     ->  将公告存入SQL
    //
    //                  result[0]   //内容
    //                  result[1]   //标题
    //                  result[2]   //发信人
    //                  result[3]   //时间
    //
    //  @output     :   NULL
    //
    //  @function   :   将公告存入SQL中
    ///////////////////////////////////////////////////////
    public static void SQLAnnounceSend(String[] message){
        Publish publish = new Publish();
        publish.publish_announce(message[0],message[1],message[2],message[4]);
    }


    ///////////////////////////////////////////////////////////////////////////
    //  将问题添加至SQL
    //  输入：  String[] result：消息信息
    //
    //                  result[0]   //内容
    //                  result[1]   //标题
    //                  result[2]   //回复
    //                  result[3]   //发信人
    //                  result[4]   //收信人
    //                  result[5]   //发送时间
    //                  result[6]   //回复时间
    //                  result[7]   //是否回复
    ///////////////////////////////////////////////////////////////////////////
    public static void SQLQuestionSend(String[] question) throws SQLException {
        Question_Ask question_ask = new Question_Ask();
        question_ask.question_ask(question[1],question[0],question[3],question[4],question[5]);
    }


    /////////////////////////////////////////////////////////////////////
    //  @input      :   question    ->  输入问题List的各个数据
    //
    //  @output     :   NULL
    //
    //  @function   :   回复一个指定的问题
    ////////////////////////////////////////////////////////////////////
    public static void SQLQuestionAnswer(String[] question) throws SQLException {
        Question_Answer question_answer = new Question_Answer();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        question_answer.question_answer(question[5],question[2],df.format(new Date()));
    }


    /////////////////////////////////////////////////////////////////////////////
    //  根据id获取该条问答
    //
    //  输出：  String[] result：消息信息
    //
    //              result[0]   //id
    //              result[1]   //内容
    //              result[2]   //标题
    //              result[3]   //回复
    //              result[4]   //发信人
    //              result[5]   //收信人
    //              result[6]   //发送时间
    //              result[7]   //回复时间
    //              result[8]   //是否回复
    //////////////////////////////////////////////////////////////////////////
    public static String[] SQLgetQuesInfo(String id) throws SQLException {
        Question_DAO question_dao = new Question_DAO();
        return question_dao.show_one_question(id);
    }



    //////////////////////////////////////////////////////////////////////////////////
    //根据账号（老师和学生都可以）获得问题的部分信息
    //
    //  当username为老师时：
    //  output:           String[] result：消息信息
    //
    //                  result[0]    //id
    //                  result[1]    //标题
    //                  result[2]    //发信人
    //                  result[3]    //发送时间
    //                  result[4]    //是否回复
    //
    //  当username为学生时：
    //  output:           String[] result：消息信息
    //
    //                  result[0]    //id
    //                  result[1]    //标题
    //                  result[2]    //发送时间
    //                  result[3]    //是否回复
    /////////////////////////////////////////////////////////////////////////////////
    public static String[][] SQLgetQuesList(String username) throws SQLException {
        Question_DAO question_dao = new Question_DAO();
        String[][] res = null;
        if(username.substring(0,1).equals("T")){
            res = question_dao.show_question_receiver(username);
        }
        else{
            res = question_dao.show_question_sender(username);
        }
        return res;
    }






/********************************************************************************************
 *
 *                          文件类型的函数接口，用于上传下载和显示文件
 *
*********************************************************************************************/
    /////////////////////////////////////////////////////////////////////////
    //  @input      :   username    ->  文件的名称
    //
    //  @output     :   String[][]  ->  将选中的文件的各种信息返回到String[][]中
    //                      result[j][0]        //主键id
    //                      result[j][1]        //文件名
    //                      result[j][2]        //发送人
    //                      result[j][3]        //时间
    //
    //  @function   :   将UI界面中的某个文件的所有信息在数据库中提取出来并保存在String[][]中
    //////////////////////////////////////////////////////////////////////////
    public static String[][] SQLgetFileList( String username) throws SQLException {
        String[][] result = null;
        File_DAO file_dao = new File_DAO();
        result = file_dao.show_file();
        return result;
    }


    ////////////////////////////////////////////////////////
    //  @input      :   username    ->  文件的名称
    //                  path        ->  下载的源路径
    //
    //  @output     :   NULL
    //
    //  @function   :   将文件的名称、上传时间、路径保存到数据库
    ////////////////////////////////////////////////////////
    public static void SQLFileUpload( String username, String path) throws SQLException {
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = form.format(new Date());        //time为当前时间

        Upload_File upload = new Upload_File();
        upload.upload(path,username,time);
    }


    /////////////////////////////////////////////////////
    //  @input      :   id      ->  文件的唯一标识
    //                  path    ->  下载的目标路径
    //
    //  @output     :   NULL
    //
    //  @function   :   将指定的文件下载到指定的路径
    /////////////////////////////////////////////////////
    public static void SQLFileDownload( String id, String path) throws IOException, SQLException {
            Download_File download_file = new Download_File();
            download_file.download(Integer.parseInt(id), path);             //将主键为id的文件下载
            //提示信息
            JOptionPane.showMessageDialog(null, "下载成功", "Success", 1);

    }




}