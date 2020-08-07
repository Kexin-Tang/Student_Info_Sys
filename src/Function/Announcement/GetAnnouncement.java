package Function.Announcement;


import Function.BaseDB;
import INFO.Announcement;
import org.apache.commons.io.IOUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetAnnouncement extends BaseDB {

    //
    //  @input      :   id  ->  数据库的主键，用于确定某一个文件
    //  @output     :   String[] -> 返回查询到的所有内容
    //  @function   :   按主键搜索文件内容，然后保存到指定位置
    //
    public String[][] getAnnouncement(String time,String username) throws SQLException, IOException {
        conn = db.getConn();    //连接数据库

        String[][] msg = null;
        List<Announcement> anno = new ArrayList<Announcement>();      //创建file列表
        int fieldnum = 5;
        String sql;

        if(username.equals("")) {
            sql = "select * from message WHERE message_time<=? ORDER BY message_time DESC";
            ps = conn.prepareStatement(sql);
            ps.setString(1, time);
        }
        else {
            sql = "select * from message WHERE sender=? AND message_time<=? ORDER BY message_time DESC";
            ps = conn.prepareStatement(sql);
            ps.setString(1,username);
            ps.setString(2, time);
        }


        rs = ps.executeQuery();     //获取信息的结果
        try {
            //将sql中数据转入list中存储
            while (rs.next()) {
                buildList(rs, anno);
            }
            //将list中指定序号的信息保存到String[][] result
            if (anno.size() > 0) {
                msg = new String[anno.size()][fieldnum];
                for (int j = 0; j < anno.size(); j++) {
                    buildResult(msg, anno, j);
                }
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            destroy();
        }

        return msg;
    }




    public String[] show_one_anno(String id) throws SQLException {

        conn = db.getConn();

        String[] result = new String[8];

        String sql = "SELECT * FROM message WHERE id=?";     //sql语句
        ps = conn.prepareStatement(sql);
        ps.setInt(1,Integer.parseInt(id));
        rs = ps.executeQuery();

        if(rs.next()){
            result[0] = String.valueOf(rs.getInt("id"));
            result[1] = rs.getString("message");
            result[2] = rs.getString("title");
            result[3] = rs.getString("sender");
            result[4] = null;
            result[5] = rs.getString("message_time");
            result[6] = null;
            result[7] = null;
        }

        return result;
    }



    //
    //  @input      :   ResultSet       ->      为数据库查找后的返回结果
    //                  List            ->      将数据库内容建立一个列表，不同的数据库的表需要不同的list
    //  @output     :   NULL
    //  @function   ：  将数据库查询到的结果保存到对应的List，再返回到UI函数中进行显示和选择
    //
    private void buildList(ResultSet rs, List<Announcement> list) throws SQLException {
        Announcement anno = new Announcement();                     //新建一条List内容
        anno.setMessage_time(rs.getString("message_time"));     //设置其消息的时间
        anno.setMessage(rs.getString("message"));               //设置其内容
        anno.setId(rs.getInt("id"));                            //设置其主键
        anno.setSender(rs.getString("sender"));                 //设置其发布者
        anno.setTitle(rs.getString("title"));                   //设置其标题
        list.add(anno);                                                     //添加到list中
    }


    //
    //  @input      :   String[][]      ->      存储List中某一条信息
    //                  List            ->      之前存入的数据库中的记录
    //                  int             ->      将int指定的List记录取出，存入String[][]
    //  @output     :   Null
    //  @function   :   将List的某一条取出，并将所需内容存入String[][]进行返回
    //
    private void buildResult(String[][] result, List<Announcement> annos, int j) {
        Announcement anno = annos.get(j);

        result[j][0] = String.valueOf(anno.getId());        //将数组的第0位设置为主键id，用于增删查改
        result[j][1] = anno.getMessage();                   //将数组的第1位设置为内容
        result[j][2] = anno.getMessage_time();              //将数组的第2位设置为内容的时间
        result[j][3] = anno.getSender();                    //将数组的第3为设置为内容发布者
        result[j][4] = anno.getTitle();
    }
}
