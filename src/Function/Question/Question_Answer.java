/***********************************************************
 *
 * @function:       老师对问题进行回复和修改
 *
 * @date:           2019-12-26
 *
 * @Class:          Question_Answer
 *
 * @content:
 *
 ***********************************************************/
package Function.Question;

import java.sql.SQLException;
import Function.BaseDB;

public class Question_Answer extends BaseDB {



    ////////////////////////////////////////////////////////////////////////////////////
    //  @input      :   sendtime    ->  发送问题的时间用于确定问题
    //                  answer      ->  为老师回复的内容
    //                  time        ->  为回复的时间
    //
    //  @output     :   NULL
    //
    //  @function   :   老师回答某一条提问，并提交回复内容和回复时间，可用于回答问题和修改问题
    ////////////////////////////////////////////////////////////////////////////////////
    public void question_answer(String sendtime, String answer,String time) throws SQLException {
        conn = db.getConn();

        String sql = "UPDATE question SET question_answer=?, flag=1, answer_time=? WHERE send_time=?";
        ps = conn.prepareStatement(sql);
        ps.setString(1,answer);
        ps.setString(2,time);
        ps.setString(3,sendtime);
        ps.executeUpdate();
        db.close();
    }



    ////////////////////////////////////////////////////////////////////////////////////
    //  @input      :   id          ->  指定主键的信息
    //
    //  @output     :   String[]    ->  用于返回查询到的某个已回答问题的标题、内容、回答等
    //
    //  @function   :   老师点开某个已回答问题，可以看见学生问题和自己的回复，有必要时进行修改
    ////////////////////////////////////////////////////////////////////////////////////
    public String[] answer_revise(int id) throws SQLException {
        conn = db.getConn();

        String[] msg = new String[3];

        String sql = "SELECT * FROM question WHERE id=?";
        ps = conn.prepareStatement(sql);
        ps.setInt(1,id);
        rs = ps.executeQuery();

        if(rs.next()){
            msg[0] = rs.getString("question_title");
            msg[1] = rs.getString("question_content");
            msg[2] = rs.getString("question_answer");
        }

        db.close();

        return msg;
    }

}

