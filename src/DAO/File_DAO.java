package DAO;

import INFO.*;
import Function.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class File_DAO extends BaseDB {


    //
    //  @input      :   NULL
    //  @output     :   String[][]      ->  将符合条件的内容全部返回，用于UI显示
    //  @function   :   将数据库-文件表中所有文件提取并显示
    //
    public String[][] show_file() throws SQLException {

        conn = db.getConn();

        String[][] result = null;
        List<File> file = new ArrayList<File>();    //创建file列表
        int fieldnum = 5;                                   //fieldnum是数据库的column数目

        String sql = "select * from download ORDER BY upload_time DESC";     //sql语句
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();

        try {
            //将sql中数据转入list中存储
            while (rs.next()) {
                buildList(rs, file);
            }
            //将list中指定序号的信息保存到String[][] result
            if (file.size() > 0) {
                result = new String[file.size()][fieldnum];
                for (int j = 0; j < file.size(); j++) {
                    buildResult(result, file, j);
                }
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            destroy();
        }
        return result;
    }



    //
    //  @input      :   ResultSet       ->      为数据库查找后的返回结果
    //                  List            ->      将数据库内容建立一个列表，不同的数据库的表需要不同的list
    //  @output     :   NULL
    //  @function   ：  将数据库查询到的结果保存到对应的List，再返回到UI函数中进行显示和选择
    //
    private void buildList(ResultSet rs, List<File> list) throws SQLException {
        File file = new File();                     //新建一条List内容
        file.setName(rs.getString("file_name"));            //设置其文件名称
        file.setContent(rs.getBytes("content"));            //设置其内容
        file.setId(rs.getInt("id"));                        //设置其主键
        file.setUploadDate(rs.getString("upload_time"));    //设置其上传时间
        file.setSender(rs.getString("sender"));             //设置其发送者
        list.add(file);                                                 //添加到list中
    }


    //
    //  @input      :   String[][]      ->      存储List中某一条信息
    //                  List            ->      之前存入的数据库中的记录
    //                  int             ->      将int指定的List记录取出，存入String[][]
    //  @output     :   Null
    //  @function   :   将List的某一条取出，并将所需内容存入String[][]进行返回
    //
    private void buildResult(String[][] result, List<File> files, int j) {
        File file = files.get(j);

        result[j][0] = String.valueOf(file.getId());        //将数组的第0位设置为主键id，用于增删查改
        result[j][1] = file.getName();                      //将数组的第1位设置为文件名
        result[j][2] = file.getSender();                    //将数组的第2为设置为发送人
        result[j][3] = file.getUploadDate();                //将数组的第3位设置为时间
        result[j][4] = result[j][1].substring(result[j][1].lastIndexOf("."));
    }
}
