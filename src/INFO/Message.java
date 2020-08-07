package INFO;

import javax.swing.*;

public class Message {
    public boolean IsAnno   ;     //是否为公告
    public String id        ;     //消息id
    public String detail    ;     //内容
    public String title     ;     //标题
    public String sender    ;     //发信人
    public String recevicer ;     //收信人
    public String time      ;     //时间
    public String settime   ;     //定时时间
    public String flag      ;     //是否被读？

    public Message (){
        IsAnno = false;
        settime = null;
    };

    public Message (String[] s){
        if (s.length<7){
            JOptionPane.showMessageDialog(null, "输入字符串数过少！", "Error", 2);
            return;
        }
        id        = s[0];
        detail    = s[1];
        title     = s[2];
        sender    = s[3];
        recevicer = s[4];
        time      = s[5];
        settime   = s[6];
        flag      = "0" ;
    }

    public String[] toStrings(){
        String[] result;
        if (IsAnno) {
            result = new String[]{detail, title, sender, time, settime, flag};
        }
        else{
            result = new String[]{detail, title, sender, recevicer, time, settime, flag};
        }
        return result;
    }
}


