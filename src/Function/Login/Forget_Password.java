package Function.Login;

import Function.BaseDB;

import java.sql.SQLException;

public class Forget_Password extends BaseDB {

    //////////////////////////////////////////////////////////////////////
    //  @input      :   login   ->  用户的账号
    //                  protect ->  用户输入的密保
    //
    //  @output     :   int     ->  用于标记信息是否匹配
    //
    //  @function   :   判断用户输入密保和存储密保是否一致，如果一致则返回1
    /////////////////////////////////////////////////////////////////////
    public int check_protect(String login,String protect) throws SQLException {
        conn = db.getConn();
        int flag = 0;   //账号和保存的密保是否匹配

        String sql = "SELECT * FROM login_info WHERE login=?";
        ps = conn.prepareStatement(sql);
        ps.setString(1,login);
        rs = ps.executeQuery();

        if(rs.next()){
            if(rs.getString("protect").equals(protect)){
                flag = 1;
            }
        }
        db.close();
        return flag;
    }


    ////////////////////////////////////////////////////////////////////
    //  @input      :   password    ->  用户新设置的密码
    //                  login       ->  用户登录账号
    //
    //  @output     :   NULL
    //
    //  @function   :   将新设置的密码更新到该用户表中
    ///////////////////////////////////////////////////////////////////
    public void password_revise(String password, String login) throws SQLException {
        conn = db.getConn();

        String sql = "UPDATE login_info SET password=? WHERE login=?";
        ps = conn.prepareStatement(sql);
        ps.setString(1,password);
        ps.setString(2,login);
        ps.executeUpdate();
        db.close();

        conn = db.getConn();
        sql = "UPDATE user_info SET password=? WHERE login=?";
        ps = conn.prepareStatement(sql);
        ps.setString(1,password);
        ps.setString(2,login);
        ps.executeUpdate();
        db.close();
    }
}
