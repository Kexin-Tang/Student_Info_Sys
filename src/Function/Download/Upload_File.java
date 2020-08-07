/*******************************************************
 *
 * @function:       将一个文件存入Mysql
 *
 * @date:           12/21/2019
 *
 * @class:          Update_File
 *
 * @content:        public void update(String path)
 *
 *******************************************************/
package Function.Download;

import Function.BaseDB;
import org.apache.commons.io.IOUtils;

import javax.sql.rowset.serial.SerialBlob;
import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;

public class Upload_File extends BaseDB {

    //
    //  @input      :   path -> 为文件路径，类似于C:\xxx\xxx.yyy
    //  @output     :   NULL
    //  @function   :   将path路径的文件存入MYSQL
    //
    public void upload(String path,String name,String time) throws SQLException
    {
        if(path == null) {
            ;
        }
        else {
            conn = db.getConn();      //连接MYSQL

            try {
                File file = new File(path);       //将文件提取出
                FileInputStream inputStream = new FileInputStream(file);              //将文件转换成输入流
                String file_name = path.substring(path.lastIndexOf("\\") + 1);     //将输入文件路径的文件名提取出来

                //利用JDBC将数据写入
                String sql = "INSERT INTO download (content,file_name,upload_time,sender) VALUE(?,?,?,?)";   //根据MYSQL表名和COLUMN名进行修改设置
                ps = conn.prepareStatement(sql);
                ps.setString(2, file_name);
                byte[] bytes = IOUtils.toByteArray(inputStream);
                Blob blob = new SerialBlob(bytes);
                ps.setBlob(1, blob);
                ps.setString(3, time);
                ps.setString(4, name);
                ps.executeUpdate();

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            db.close();     //清除临时变量
        }
    }
}
