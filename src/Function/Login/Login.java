package Function.Login;

import Function.BaseDB;

import javax.swing.*;
import java.sql.SQLException;

public class Login extends BaseDB {

    public String[] check(String login, String password) throws SQLException {
        String[] flag = {"0",""};       //flag[0]   ->  用于标记是否有该用户
                                        //flag[1]   ->  用于标记用户是管理员还是老师还是学生

        conn = db.getConn();

        String sql = "SELECT * FROM login_info WHERE login = ?";
        ps = conn.prepareStatement(sql);
        ps.setString(1,login);
        rs = ps.executeQuery();

        if(rs.next()){
            if(password.equals(rs.getString("password"))){
                flag[0] = "1";
                if(login.equals("admin")){
                    flag[1] = "admin";
                }
                else if(login.substring(0,1).equals("T")){
                    flag[1] = "teacher";
                }
                else
                    flag[1] = "student";
            }
            else{
                JOptionPane.showMessageDialog(null, "密码错误", "登陆失败", 2);
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "无此用户", "登陆失败", 2);
        }

        db.close();
        return flag;
    }


}
