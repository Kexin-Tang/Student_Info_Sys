package Function.Login;

import Function.BaseDB;

import javax.swing.*;
import java.sql.SQLException;

public class Register extends BaseDB {

    ///////////////////////////////////////////////////////////////
    //  @input      :   stu     ->  要注册的信息
    //
    //  @output     :   int     ->  用于返回是否注册过该账号
    //
    //  @function   :   查询账号是否使用过，如果没使用过则注册该信息
    ///////////////////////////////////////////////////////////////
    public int register(String[] stu) throws SQLException {

        conn = db.getConn();
        int flag = 0;       //用于标记是否已注册过

        String sql0 = "SELECT * FROM login_info WHERE login=?";
        ps = conn.prepareStatement(sql0);
        ps.setString(1,stu[0]);
        rs = ps.executeQuery();

        //如果没有注册过该账号，则可以继续注册
        if(!rs.next()) {
            //注册一条信息在用户表中
            String sql = "INSERT INTO user_info (login,password,name,sex,class,email,protect) VALUES(?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, stu[0]);
            ps.setString(2, stu[1]);
            ps.setString(3, stu[2]);
            ps.setString(4, stu[3]);
            ps.setString(5, stu[4]);
            ps.setString(6, stu[5]);
            ps.setString(7, stu[6]);
            ps.executeUpdate();
            db.close();


            //注册一条信息在登录表中
            conn = db.getConn();
            sql = "INSERT INTO login_info (login,password,protect) VALUES (?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, stu[0]);
            ps.setString(2, stu[1]);
            ps.setString(3, stu[6]);
            ps.executeUpdate();

            if(stu[0].substring(0,1).equals("T")){
                conn = db.getConn();
                sql = "INSERT INTO admin_alarm_teacher (teacher) VALUES (?)";
                ps = conn.prepareStatement(sql);
                ps.setString(1, stu[0]);
                ps.executeUpdate();
            }
        }
        //如果账号已使用，则提示错误并退出
        else{
            flag = 1;
        }
        db.close();
        return flag;
    }

}
