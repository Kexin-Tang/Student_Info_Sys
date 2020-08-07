/*****************************************************
 *
 * @function:       返回特定日期或总体的问题数、问题解答数
 *
 * @date:           2019-12-25
 *
 * @Class:          Calculate_Rate
 *
 * @content:        public int[] daily(String teacher, String time)
 *                  public int[] whole(String teacher)
 *
 ****************************************************/
package Function.Question;

import java.sql.SQLException;
import Function.BaseDB;

public class Calculate_Rate extends BaseDB {

    //////////////////////////////////////////////////////////////////////////////////
    //  @input      :   teacher     ->  查询特定的老师回答情况
    //                  time        ->  查询特定的日期
    //
    //  @output     :   int[]       ->  返回 当天问题总数 int[0] 和 当天问题回答数 int[1]
    //
    //  @function   :   将特定日期，指定老师的问题情况返回
    //////////////////////////////////////////////////////////////////////////////////
    public int[] daily(String teacher, String time) throws SQLException {
        conn = db.getConn();
        int[] num = new int[2];     //num[0] -> 当天问题数
        //num[1] -> 当天已回答问题


        String sql = "SELECT * FROM question WHERE receiver=? AND send_time LIKE ?";
        //String sql = "SELECT * FROM question WHERE receiver=?";
        ps = conn.prepareStatement(sql);
        ps.setString(1,teacher);
        ps.setString(2,time+"%");
        rs = ps.executeQuery();

        while(rs.next()){
            if(rs.getBoolean("flag")){
                num[1]++;
            }
            num[0]++;
        }
        db.close();
        return num;
    }



    /////////////////////////////////////////////////////////////////////////////
    //  @input      :   teacher     ->  查询特定的老师回答情况
    //
    //  @output     :   int[]       ->  返回 问题总数 int[0] 和 问题回答数 int[1]
    //
    //  @function   :   将指定老师的总问题情况返回
    /////////////////////////////////////////////////////////////////////////////
    public int[] whole(String teacher) throws SQLException {
        conn = db.getConn();
        int[] num = new int[2];     //num[0] -> 总问题数
        //num[1] -> 总已回答问题
        String sql = "SELECT * FROM question WHERE receiver=?";

        ps = conn.prepareStatement(sql);
        ps.setString(1,teacher);
        rs = ps.executeQuery();

        while(rs.next()){
            num[0]++;
            if(rs.getBoolean("flag")){
                num[1]++;
            }
        }
        db.close();

        return num;
    }
}
