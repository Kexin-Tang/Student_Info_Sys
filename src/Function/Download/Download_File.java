/***********************************************
 *
 * @function:       从数据库下载一个文件
 *
 * @date:           21/12/2019
 *
 * @class:          Download_File
 *
 * @content:        public void download()
 *
 ***********************************************/
package Function.Download;

import Function.BaseDB;
import org.apache.commons.io.*;

import java.io.*;
import java.sql.*;

public class Download_File extends BaseDB {

    //
    //  @input      :   id  ->  数据库的主键，用于确定某一个文件
    //  @output     :   NULL
    //  @function   :   按主键搜索文件内容，然后保存到指定位置
    //
    public void download(int id, String path) throws SQLException, IOException {
        conn = db.getConn();    //连接数据库

        //如果id=0，即不进行条件搜索，则执行if
        if(id == 0) {
            String sql = "select * from download";
            ps= conn.prepareStatement(sql);
        }
        //如果id不为0，则按条件搜索一条信息
        else{
            String sql = "select * from download where id=?";
            ps= conn.prepareStatement(sql);
            ps.setInt(1,id);
        }

        rs = ps.executeQuery();     //获取信息的结果
        if (rs.next()){
            Blob blob = rs.getBlob("content");                          //获取文件内容
            String file_name = rs.getString("file_name");               //获取文件名
            InputStream in = blob.getBinaryStream();
            FileOutputStream out = new FileOutputStream(path+file_name);    //按名字保存到默认位置
            IOUtils.copy(in,out);
        }
        db.close();
    }
}
