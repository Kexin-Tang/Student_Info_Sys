package Function.Login;

import Function.BaseDB;

import java.sql.SQLException;

public class Update_Stu_Info extends BaseDB {

    public void update(String[] stu) throws SQLException {

        conn = db.getConn();
        String sql_find = "SELECT * FROM user_info WHERE login=?";
        ps = conn.prepareStatement(sql_find);
        ps.setString(1,stu[0]);
        rs = ps.executeQuery();
        if(rs.next()) {
            String login = rs.getString("login");

            String sql = "UPDATE user_info SET login=?,password=?,name=?,sex=?,class=?,email=?,protect=? WHERE login=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, stu[0]);
            ps.setString(2, stu[1]);
            ps.setString(3, stu[2]);
            ps.setString(4, stu[3]);
            ps.setString(5, stu[4]);
            ps.setString(6, stu[5]);
            ps.setString(7, stu[6]);
            ps.setString(8, login);
            ps.executeUpdate();
            db.close();

            conn = db.getConn();
            sql = "UPDATE login_info SET login=?,password=?,protect=? WHERE login =?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, stu[0]);
            ps.setString(2, stu[1]);
            ps.setString(3, stu[6]);
            ps.setString(4, login);
            ps.executeUpdate();

            db.close();
        }
    }
}
