/******************************************************************
 *
 * @function:       学生将问题发送给指定的人，包括了问题标题和问题内容
 *
 * @date:           2019-12-25
 *
 * @Class:          Question_Ask
 *
 * @content:        public void question_ask(String title, String content, String sender, String receiver, String time)
 *
 ******************************************************************/
package Function.Question;

import Function.BaseDB;
import java.sql.SQLException;

public class Question_Ask extends BaseDB {


    ////////////////////////////////////////////////////////////////////////////
    //  @input      :   title       ->  输入问题的标题
    //                  content     ->  输入问题的内容
    //                  sender      ->  发送的学生
    //                  receiver    ->  接收的老师
    //                  time        ->  发布的时间，为yyyy-mm-dd hh:mm:ss
    //
    //  @output     :   NULL
    //
    //  @function   :   将学生问的问题提交到数据库中
    /////////////////////////////////////////////////////////////////////////
    public void question_ask(String title, String content, String sender, String receiver, String time) throws SQLException {
        conn = db.getConn();

        String sql="INSERT INTO question (question_title,question_content,sender,receiver,send_time) VALUE(?,?,?,?,?)";   //根据MYSQL表名和COLUMN名进行修改设置
        ps = conn.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, content);
        ps.setString(3, sender);
        ps.setString(4, receiver);
        ps.setString(5, time);
        ps.executeUpdate();
        db.close();     //清除临时变量
    }

}

