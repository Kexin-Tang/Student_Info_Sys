package DAO;

import Function.BaseDB;
import INFO.Question;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Question_DAO extends BaseDB {



    /////////////////////////////////////////////////////////////////////
    //  @input      :   username    ->  问题接收人
    //
    //  @output     :   String[][]  ->  接收者是receiver的所有问题详细信息
    //
    //  @function   :   根据问题接收者，显示相关问题详细信息
    ////////////////////////////////////////////////////////////////////
    public String[][] show_question_receiver(String username) throws SQLException {

        conn = db.getConn();

        String[][] result = null;
        List<Question> file = new ArrayList<Question>();    //创建file列表
        int fieldnum = 9;                                   //fieldnum是数据库的column数目

        String sql = "SELECT * FROM question WHERE receiver=? ORDER BY send_time DESC";     //sql语句
        ps = conn.prepareStatement(sql);
        ps.setString(1,username);
        rs = ps.executeQuery();

        try {
            //将sql中数据转入list中存储
            while (rs.next()) {
                buildList(rs, file);
            }
            //将list中指定序号的信息保存到String[][] result
            if (file.size() > 0) {
                result = new String[file.size()][fieldnum];
                for (int j = 0; j < file.size(); j++) {
                    buildResult(result, file, j);
                }
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            destroy();
        }
        return result;
    }



    /////////////////////////////////////////////////////////////////////
    //  @input      :   sender      ->  问题发起者
    //
    //  @output     :   String[][]  ->  发起者是sender的所有问题详细信息
    //
    //  @function   :   根据问题发起者，显示相关问题详细信息
    ////////////////////////////////////////////////////////////////////
    public String[][] show_question_sender(String sender) throws SQLException {

        conn = db.getConn();

        String[][] result = null;
        List<Question> file = new ArrayList<Question>();    //创建file列表
        int fieldnum = 9;                            //fieldnum是数据库的column数目

        String sql = "SELECT * FROM question WHERE sender=? ORDER BY send_time DESC";     //sql语句
        ps = conn.prepareStatement(sql);
        ps.setString(1,sender);
        rs = ps.executeQuery();                      //执行sql，返回结果到rs

        try {
            //将sql中数据转入list中存储
            while (rs.next()) {
                buildList(rs, file);
            }
            //将list中指定序号的信息保存到String[][] result
            if (file.size() > 0) {
                result = new String[file.size()][fieldnum];
                for (int j = 0; j < file.size(); j++) {
                    buildResult(result, file, j);
                }
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            destroy();
        }
        return result;
    }




    /////////////////////////////////////////////////////////////////////
    //  @input      :   id          ->  问题的主键
    //
    //  @output     :   String[]    ->  问题的内容
    //
    //  @function   :   根据所选择问题，显示详细信息
    ////////////////////////////////////////////////////////////////////
    public String[] show_one_question(String id) throws SQLException {
        conn = db.getConn();
        String[] result = new String[9];

        String sql = "SELECT * FROM question WHERE id=?";     //sql语句
        ps = conn.prepareStatement(sql);
        ps.setInt(1,Integer.parseInt(id));
        rs = ps.executeQuery();                      //执行sql，返回结果到rs

        if(rs.next()){
            result[0] = String.valueOf(rs.getInt("id"));
            result[1] = rs.getString("question_content");
            result[2] = rs.getString("question_title");
            result[3] = rs.getString("question_answer");
            result[4] = rs.getString("sender");
            result[5] = rs.getString("receiver");
            result[6] = rs.getString("send_time");
            result[7] = rs.getString("answer_time");
            result[8] = rs.getString("flag");
        }
        return result;
    }




    private void buildList(ResultSet rs, List<Question> list) throws SQLException {
        Question ques = new Question();                     //新建一条List内容
        ques.setAnswer(rs.getString("question_answer"));    //设置其问题的答案
        ques.setContent(rs.getString("question_content"));  //设置其问题内容
        ques.setTitle(rs.getString("question_title"));      //设置其问题标题
        ques.setId(String.valueOf(rs.getInt("id")));        //设置其主键
        ques.setReceiver(rs.getString("receiver"));         //设置其接收者
        ques.setSender(rs.getString("sender"));             //设置其发送者
        ques.setSend_time(rs.getString("send_time"));       //设置其发送时间
        ques.setAnswer_time(rs.getString("answer_time"));   //设置其回答时间
        ques.setFlag(String.valueOf(rs.getBoolean("flag")));//设置其是否回答
        list.add(ques);                                                 //添加到list中
    }


    ///////////////////////////////////////////////////////////////////////////////////
    //  @input      :   String[][]      ->      存储List中某一条信息
    //                  List            ->      之前存入的数据库中的记录
    //                  int             ->      将int指定的List记录取出，存入String[][]
    //
    //  @output     :   Null
    //
    //  @function   :   将List的某一条取出，并将所需内容存入String[][]进行返回
    ////////////////////////////////////////////////////////////////////////////////////
    private void buildResult(String[][] result, List<Question> files, int j) {
        Question ques = files.get(j);

        result[j][0] = String.valueOf(ques.getId());        //将数组的第0位设置为主键id，用于增删查改
        result[j][1] = ques.getTitle();                     //将数组的第1位设置为标题
        result[j][2] = ques.getSender();                    //将数组的第2为设置为发送人
        result[j][3] = ques.getSend_time();                 //将数组的第3位设置为发送时间
        result[j][4] = ques.getReceiver();                  //将数组的第4位设置为接收人
        result[j][5] = ques.getAnswer_time();               //将数组的第5位设置为回复时间
        result[j][6] = ques.getAnswer();                    //将数组的第6位设置为回答内容
        result[j][7] = ques.getContent();                   //将数组的第7位设置为提问内容
        result[j][8] = ques.getFlag().equals("false")? "未回复": "已回复";      //将数组的第8位设置为是否回答
    }
}
