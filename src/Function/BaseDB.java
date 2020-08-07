package Function;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class BaseDB {
    protected DataBaseConnect db = DataBaseConnect.getDBUtil();
    protected ResultSet rs;
    protected Connection conn;
    protected PreparedStatement ps;
    private static BaseDB baseDB;

    public BaseDB() {
        init();
    }

    private void init() {
        // buildAbilityDAO();
    }

    protected void destroy() {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
}

