/******************************************************
 *
 * @function:       实现向全体发送公告，可定时
 *
 * @date:           2019-12-24
 *
 * @Class:          Publish
 *
 * @content:        public  void    publish_settime     (String time, String text, String sender)
 *                  public  void    publish_announce    (String text, String sender             )
 *
 *****************************************************/
package Function.Announcement;

import Function.BaseDB;

import javax.sql.rowset.serial.SerialException;
import java.sql.SQLException;

public class Publish extends BaseDB {


    //
    //  @input      :   time    ->  定时的时间为多少，格式为yyyy-mm-dd
    //                  text    ->  发送的消息是什么
    //                  sender  ->  发送者是谁
    //  @output     :   NULL
    //  @function   :   设置提醒时间，将题型内容存入数据库
    //
    public void publish_settime(String time, String text, String sender){
        conn=db.getConn();

        String sql = "INSERT INTO message (message,message_time,sender) VALUE(?,?,?)";

        try{
            ps= conn.prepareStatement(sql);
            ps.setString(1,text);
            ps.setString(2,time);
            ps.setString(3,sender);
            ps.executeUpdate();
        } catch (SerialException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    ////////////////////////////////////////////////////////////////
    //  @input      :   text    ->  发送的公告是什么
    //                  title   ->  发送公告的标题
    //                  sender  ->  发送者是谁
    //                  time    ->  发送时间，为当时的系统时间
    //
    //  @output     :   NULL
    //
    //  @function   :   将一条公告存入数据库，保存发送的时间
    ////////////////////////////////////////////////////////////////
    public void publish_announce(String text, String title, String sender,String time){
        conn=db.getConn();


        String sql = "INSERT INTO message (message,title,message_time,sender) VALUE(?,?,?,?)";

        try{
            ps= conn.prepareStatement(sql);
            ps.setString(1,text);
            ps.setString(2,title);
            ps.setString(3,time);
            ps.setString(4,sender);
            ps.executeUpdate();
        } catch (SerialException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

