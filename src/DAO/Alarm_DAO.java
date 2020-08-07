package DAO;

import Function.BaseDB;
import INFO.Alarm;
import Function.Question.Calculate_Rate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Alarm_DAO extends BaseDB {


    ////////////////////////////////////////////
    //  @input      :   teacher ->  提醒哪位老师
    //
    //  @output     :   NULL
    //
    //  @function   :   admin提醒某位老师要及时答疑
    ////////////////////////////////////////////
    public void Alarm(String id) throws SQLException {
        conn = db.getConn();
        String sql = "UPDATE admin_alarm_teacher SET flag=1 WHERE id=?";
        ps = conn.prepareStatement(sql);
        ps.setString(1,id);
        ps.executeUpdate();
        db.close();
    }


    ///////////////////////////////////////////////////////////////////
    //  @input      :   teacher ->  某位老师登录，获取登录账号
    //
    //  @output     :   boolean ->  0:未收到管理员提醒
    //                              1:收到管理员提醒
    //
    //  @function   :   某位老师登录时检查其是否有收到管理员提醒
    ///////////////////////////////////////////////////////////////////
    public boolean Check_Alarm(String teacher) throws SQLException {

        conn = db.getConn();
        String sql = "SELECT flag FROM admin_alarm_teacher WHERE teacher=?";    //检查某位老师是否收到提醒
        ps = conn.prepareStatement(sql);
        ps.setString(1,teacher);
        rs = ps.executeQuery();

        if(rs.next()){
            //如果收到了提醒
            if(rs.getInt(1) == 1){
                db.close();
                conn = db.getConn();
                String sql_update = "UPDATE admin_alarm_teacher SET flag=0 WHERE teacher=?";    //将提醒标记置回原样，方便下次提醒
                ps = conn.prepareStatement(sql_update);
                ps.setString(1,teacher);
                ps.executeUpdate();
                db.close();
                return true;
            }
            else
                return false;
        }
        return false;
    }





    /////////////////////////////////////////////////////////////////////////
    //  @input      :   NULL
    //
    //  @output     :   String[][]      ->  返回老师及对应问题量的信息
    //
    //                      result[0]   ->  id
    //                      result[1]   ->  老师信息
    //                      result[2]   ->  当日问题数量
    //                      result[3]   ->  回答问题数量
    //                      result[4]   ->  是否收到提醒
    //                      result[5]   ->  回答率
    //
    //  @function   :   将所有老师当日的回答率等显示出来
    //////////////////////////////////////////////////////////////////////////
    public String[][] show_alarm() throws SQLException {

        conn = db.getConn();

        String[][] result = null;
        List<Alarm> alarm = new ArrayList<Alarm>();    //创建file列表
        int fieldnum = 6;                                   //fieldnum是数据库的column数目
        int i=0,N,M;

        Calculate_Rate calculate_rate = new Calculate_Rate();


        //计算一共多少老师信息
        //String sql_num = "select count(id) from admin_alarm_teacher";
        String sql_num = "select count(id) from user_info WHERE login LIKE ?";
        ps = conn.prepareStatement(sql_num);
        ps.setString( 1,"T%");
        rs = ps.executeQuery();
        if(rs.next()) {
            N = rs.getInt(1);
        }
        else{
            N = 0;
        }


        String[] teachers = new String[N];      //存储老师账号
        int[][] num= new int[N][];              //存储每个老师的问题量
                                                //num[0]:总问题数
                                                //num[1]:回答问题数

        //先执行查询，求出每个老师
        //String sql_teacher = "select teacher from admin_alarm_teacher";
        String sql_teacher = "select login from user_info WHERE login LIKE ?";
        ps = conn.prepareStatement(sql_teacher);
        ps.setString(1, "T%");
        rs = ps.executeQuery();
        try{
            while (rs.next()){
                teachers[i] = rs.getString(1);
                i++;
            }
        }catch (SQLException se) {
            se.printStackTrace();
        }
        db.close();


        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
        String time = form.format(new Date());


        for(i=0;i<N;i++){
            num[i] = calculate_rate.daily(teachers[i], time);  //获取老师姓名并查询“问题”表格，获取问题回答率等
        }


        //根据老师信息及其对应的问题个数等更新表
        String sql_update = "INSERT INTO admin_alarm_teacher SET question_num=?,answer_num=?,rate=?,teacher=?" +
                " ON DUPLICATE KEY UPDATE question_num=?,answer_num=?,rate=?";
        for(i=0;i<N;i++) {
            conn = db.getConn();
            ps = conn.prepareStatement(sql_update);
            if(num[i][0] == 0){
                ps.setInt(1,0);
                ps.setInt(2,0);
                ps.setString(3,"0.00%");
                ps.setString(4,teachers[i]);
                ps.setInt(5,0);
                ps.setInt(6,0);
                ps.setString(7,"0.00%");
            }
            else{
                ps.setInt(1,num[i][0]);
                ps.setInt(2,num[i][1]);
                ps.setString(3,String.valueOf(((float)num[i][1]/num[i][0])*100)+"%");
                ps.setString(4,teachers[i]);
                ps.setInt(5,num[i][0]);
                ps.setInt(6,num[i][1]);
                ps.setString(7,String.valueOf(((float)num[i][1]/num[i][0])*100)+"%");
            }
            ps.executeUpdate();
        }

        //获取更新后的数据，并送至UI显示
        conn = db.getConn();
        String sql = "select * from admin_alarm_teacher";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();
        try {
            //将sql中数据转入list中存储
            while (rs.next()) {
                buildList(rs, alarm);
            }
            //将list中指定序号的信息保存到String[][] result
            if (alarm.size() > 0) {
                result = new String[alarm.size()][fieldnum];
                for (int j = 0; j < alarm.size(); j++) {
                    buildResult(result, alarm, j);
                }
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            destroy();
        }

        return result;
    }



    ///////////////////////////////////////////////////////////////////////////
    //  @input      :   ResultSet       ->      为数据库查找后的返回结果
    //                  List            ->      将数据库内容建立一个列表，不同的数据库的表需要不同的list
    //
    //  @output     :   NULL
    //
    //  @function   ：  将数据库查询到的结果保存到对应的List，再返回到UI函数中进行显示和选择
    ////////////////////////////////////////////////////////////////////////////
    private void buildList(ResultSet rs, List<Alarm> list) throws SQLException {
        Alarm alarm = new Alarm();
        alarm.setId(rs.getInt("id"));
        alarm.setLogin(rs.getString("teacher"));
        alarm.setQuestion_num(rs.getString("question_num"));
        alarm.setAnswer_num(rs.getString("answer_num"));
        alarm.setFlag(rs.getBoolean("flag"));
        alarm.setRate(rs.getString("rate"));
        list.add(alarm);
    }


    //////////////////////////////////////////////////////////////////////////////////////
    //  @input      :   String[][]      ->      存储List中某一条信息
    //                  List            ->      之前存入的数据库中的记录
    //                  int             ->      将int指定的List记录取出，存入String[][]
    //
    //  @output     :   Null
    //
    //  @function   :   将List的某一条取出，并将所需内容存入String[][]进行返回
    //////////////////////////////////////////////////////////////////////////////////////
    private void buildResult(String[][] result, List<Alarm> files, int j) {
        Alarm alarm = files.get(j);

        result[j][0] = String.valueOf(alarm.getId());
        result[j][1] = alarm.getLogin();
        result[j][2] = alarm.getQuestion_num();
        result[j][3] = alarm.getAnswer_num();
        result[j][4] = String.valueOf(alarm.getFlag());
        result[j][5] = alarm.getRate();
    }
}
