package UI_Part;

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;


public class JButton_time_Select extends JButton {

    private DateChooser dateChooser = null;
    private String preLabel = "";
    private String originalText = null;
    private SimpleDateFormat sdf = null;
    static int newDay;
    private JButton_Image button;

    public JButton_Image getButton(){
        return button;
    }



    public JButton_time_Select() {
        this(getNowDate());
    }



    public JButton_time_Select(String dateString) {
        this();
        setText(getDefaultDateFormat(), dateString);
        initOriginalText(dateString);
    }



    public JButton_time_Select(SimpleDateFormat df, String dateString) {
        this();
        setText(df, dateString);
        this.sdf = df;
        Date originalDate = null;
        try {
            originalDate = df.parse(dateString);
        } catch (ParseException ex) {
            originalDate = getNowDate();
        }
        initOriginalText(originalDate);
    }



    public JButton_time_Select(Date date) {
        this("", date);
        initOriginalText(date);
    }



    public JButton_time_Select(String preLabel, Date date) {
        if (preLabel != null) {
            this.preLabel = preLabel;
        }
        button = new JButton_Image(410,273,110,40,getDefaultDateFormat().format(date),"photo/Message_down.png",null);
        button.addFont(new Font("方正非凡体简体", Font.BOLD, 20));
        setDate(date);
        initOriginalText(date);
        setBorder(null);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        super.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (dateChooser == null) {
                    dateChooser = new DateChooser(sdf);
                }
                Point p = getLocationOnScreen();
                p.y = p.y + 30;
                dateChooser.showDateChooser(p);
            }
        });

        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (dateChooser == null) {
                    dateChooser = new DateChooser(sdf);
                }
                dateChooser.showDateChooser(new Point(710,510));
            }
        });
    }


    private static Date getNowDate() {
        return Calendar.getInstance().getTime();
    }



    private static SimpleDateFormat getDefaultDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd");
    }



    public SimpleDateFormat getCurrentSimpleDateFormat(){
        if(this.sdf != null){
            return sdf;
        }else{
            return getDefaultDateFormat();
        }
    }



    //保存原始是日期时间
    private void initOriginalText(String dateString) {
        this.originalText = dateString;
    }


    //保存原始是日期时间
    private void initOriginalText(Date date) {
        this.originalText = preLabel + getDefaultDateFormat().format(date);
    }



    public String getOriginalText() {
        return originalText;
    }



    // 覆盖父类的方法
    @Override
    public void setText(String s) {
        Date date;
        try {
            date = getDefaultDateFormat().parse(s);
        } catch (ParseException e) {
            date = getNowDate();
        }
        setDate(date);
    }



    public void setText(SimpleDateFormat df, String s) {
        Date date;
        try {
            date = df.parse(s);
        } catch (ParseException e) {
            date = getNowDate();
        }
        setDate(date);
    }



    public void setDate(Date date) {
        super.setText(preLabel + getDefaultDateFormat().format(date));
    }



    public Date getDate() {
        String dateString = getText().substring(preLabel.length());
        try {
            SimpleDateFormat currentSdf = getCurrentSimpleDateFormat();
            return currentSdf.parse(dateString);
        } catch (ParseException e) {
            return getNowDate();
        }
    }


    @Override
    public void addActionListener(ActionListener listener) {
    }



    //返回时间选择后的确切日期，如"yyyy-mm-dd"
    public String Get_Select_Time(){
        if (dateChooser == null) return sdf.format(new Date());
        String time = dateChooser.getSelectedYear() + "-" + dateChooser.getSelectedMonth() + "-" + newDay;
        return time;
    }




    //创建一个时间选择控件的class
    private class DateChooser extends JPanel implements ActionListener, ChangeListener {

        int startYear = 1980;       //最小年份
        int lastYear = 2050;        //最大年份
        int width = 390;            //界面宽度
        int height = 210;           //界面高度
        Color backGroundColor = Color.gray; // 底色
        // 月历表格配色//
        Color palletTableColor = Color.white;   // 日历表底色
        Color todayBackColor = Color.orange;    // 今天背景色
        Color weekFontColor = Color.blue;       // 星期文字色
        Color dateFontColor = Color.black;      // 日期文字色
        Color weekendFontColor = Color.red;     // 周末文字色
        // 控制条配色//
        Color controlLineColor = new Color(116,217,255);    // 控制条底色
        Color controlTextColor = Color.white;   // 控制条标签文字色
        Color rbFontColor = Color.white;        // RoundBox文字色
        Color rbBorderColor = Color.red;        // RoundBox边框色
        Color rbButtonColor = Color.pink;       // RoundBox按钮色
        Color rbBtFontColor = Color.red;        // RoundBox按钮文字色
        JDialog dialog;
        JSpinner yearSpin;
        JSpinner monthSpin;
        JSpinner daySpin;
        SimpleDateFormat df;
        JButton[][] daysButton = new JButton[6][7];

        DateChooser(SimpleDateFormat df) {
            this.df=df;
            setLayout(new BorderLayout());
            setBorder(new LineBorder(backGroundColor, 2));
            setBackground(backGroundColor);

            JPanel topYearAndMonth = createYearAndMonthPanal(df);
            add(topYearAndMonth, BorderLayout.NORTH);
            JPanel centerWeekAndDay = createWeekAndDayPanal();
            add(centerWeekAndDay, BorderLayout.CENTER);
            JPanel buttonBarPanel = createButtonBarPanel();
            this.add(buttonBarPanel, java.awt.BorderLayout.SOUTH);
        }



        private JPanel createYearAndMonthPanal(SimpleDateFormat df) {
            Calendar c = getCalendar();
            int currentYear = c.get(Calendar.YEAR);
            int currentMonth = c.get(Calendar.MONTH) + 1;
            int currentHour = c.get(Calendar.HOUR_OF_DAY);

            JPanel result = new JPanel();
            result.setLayout(new FlowLayout());
            result.setBackground(controlLineColor);

            yearSpin = new JSpinner(new SpinnerNumberModel(currentYear, startYear, lastYear, 1));
            yearSpin.setPreferredSize(new Dimension(48, 20));
            yearSpin.setName("Year");
            yearSpin.setEditor(new JSpinner.NumberEditor(yearSpin, "####"));
            yearSpin.addChangeListener(this);
            result.add(yearSpin);
            JLabel yearLabel = new JLabel("年");
            yearLabel.setForeground(controlTextColor);
            result.add(yearLabel);
            monthSpin = new JSpinner(new SpinnerNumberModel(currentMonth, 1, 12, 1));
            monthSpin.setPreferredSize(new Dimension(35, 20));
            monthSpin.setName("Month");
            monthSpin.addChangeListener(this);
            result.add(monthSpin);
            JLabel monthLabel = new JLabel("月");
            monthLabel.setForeground(controlTextColor);
            result.add(monthLabel);
            daySpin = new JSpinner(new SpinnerNumberModel(currentMonth, 1, 31, 1));
            daySpin.setPreferredSize(new Dimension(35, 20));
            daySpin.setName("Day");
            daySpin.addChangeListener(this);
            daySpin.setEnabled(false);
            daySpin.setToolTipText("请下下面的日历面板中进行选择哪一天！");
            result.add(daySpin);

            JLabel dayLabel = new JLabel("日");
            dayLabel.setForeground(controlTextColor);
            result.add(dayLabel);

            return result;
        }



        //设置时间选择控件的样式
        private JPanel createWeekAndDayPanal() {
            String colname[] = {"日", "一", "二", "三", "四", "五", "六"};
            JPanel result = new JPanel();
            result.setFont(new Font("宋体", Font.PLAIN, 12));
            result.setLayout(new GridLayout(7, 7));
            result.setBackground(Color.white);
            JLabel cell;

            for (int i = 0; i < 7; i++) {
                cell = new JLabel(colname[i]);
                cell.setHorizontalAlignment(JLabel.RIGHT);
                if (i == 0 || i == 6) {
                    cell.setForeground(weekendFontColor);
                } else {
                    cell.setForeground(weekFontColor);
                }
                result.add(cell);
            }

            int actionCommandId = 0;
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 7; j++) {
                    JButton numberButton = new JButton();
                    numberButton.setBorder(null);
                    numberButton.setHorizontalAlignment(SwingConstants.RIGHT);
                    numberButton.setActionCommand(String.valueOf(actionCommandId));
                    numberButton.addActionListener(this);
                    numberButton.setBackground(palletTableColor);
                    numberButton.setForeground(dateFontColor);
                    if (j == 0 || j == 6) {
                        numberButton.setForeground(weekendFontColor);
                    } else {
                        numberButton.setForeground(dateFontColor);
                    }
                    daysButton[i][j] = numberButton;
                    result.add(numberButton);
                    actionCommandId++;
                }
            }

            return result;
        }




        public String getTextOfDateChooserButton() {
            return getText();
        }




        public void restoreTheOriginalDate() {
            String originalText = getOriginalText();
            setText(originalText);
        }




        private JPanel createButtonBarPanel() {
            JPanel panel = new JPanel();
            panel.setLayout(new java.awt.GridLayout(1, 2));

            JButton ok = new JButton("确定");
            ok.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    initOriginalText(getTextOfDateChooserButton());
                    dialog.setVisible(false);
                    button.addText(Get_Select_Time());
                }
            });
            panel.add(ok);
            JButton cancel = new JButton("取消");
            cancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    restoreTheOriginalDate();
                    dialog.setVisible(false);
                }
            });
            panel.add(cancel);
            return panel;
        }




        private JDialog createDialog(Frame owner) {
            JDialog result = new JDialog(owner, "日期时间选择", true);
            result.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
            result.getContentPane().add(this, BorderLayout.CENTER);
            result.pack();
            result.setSize(width, height);
            return result;
        }



        //显示选择时间的控件
        void showDateChooser(Point position) {
            Frame owner = (Frame) SwingUtilities.getWindowAncestor(JButton_time_Select.this);
            if (dialog == null || dialog.getOwner() != owner) {
                dialog = createDialog(owner);
            }
            dialog.setLocation(position);
            flushWeekAndDay();
            dialog.setVisible(true);
        }



        //将窗口摆放在正确的位置上
        Point getAppropriateLocation(Frame owner, Point position) {
            Point result = new Point(position);
            Point p = owner.getLocation();
            int offsetX = (position.x + width) - (p.x + owner.getWidth());
            int offsetY = (position.y + height) - (p.y + owner.getHeight());
            if (offsetX > 0) {
                result.x -= offsetX;
            }
            if (offsetY > 0) {
                result.y -= offsetY;
            }
            return result;
        }



        private Calendar getCalendar() {
            Calendar result = Calendar.getInstance();
            result.setTime(getDate());
            return result;
        }




        //用于获取设置之后的年份
        public int getSelectedYear() {
            return ((Integer) yearSpin.getValue()).intValue();
        }



        //用于获取设置之后的月份
        public int getSelectedMonth() {
            return ((Integer) monthSpin.getValue()).intValue();
        }




        //颜色变化
        private void dayColorUpdate(boolean isOldDay) {
            Calendar c = getCalendar();
            int day = c.get(Calendar.DAY_OF_MONTH);
            c.set(Calendar.DAY_OF_MONTH, 1);
            int actionCommandId = day - 2 + c.get(Calendar.DAY_OF_WEEK);
            int i = actionCommandId / 7;
            int j = actionCommandId % 7;
            if (isOldDay) {
                daysButton[i][j].setForeground(dateFontColor);
            } else {
                daysButton[i][j].setForeground(todayBackColor);
            }
        }




        private void flushWeekAndDay() {
            Calendar c = getCalendar();
            c.set(Calendar.DAY_OF_MONTH, 1);
            int maxDayNo = c.getActualMaximum(Calendar.DAY_OF_MONTH);
            int dayNo = 2 - c.get(Calendar.DAY_OF_WEEK);
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 7; j++) {
                    String s = "";
                    if (dayNo >= 1 && dayNo <= maxDayNo) {
                        s = String.valueOf(dayNo);
                    }
                    daysButton[i][j].setText(s);
                    dayNo++;
                }
            }
            dayColorUpdate(false);
        }



        //点击"年"和"月"时进行变换
        @Override
        public void stateChanged(ChangeEvent e) {
            JSpinner source = (JSpinner) e.getSource();
            Calendar c = getCalendar();

            dayColorUpdate(true);

            if (source.getName().equals("Year")) {
                c.set(Calendar.YEAR, getSelectedYear());
            } else if (source.getName().equals("Month")) {
                c.set(Calendar.MONTH, getSelectedMonth());
            }
            setDate(c.getTime());
            flushWeekAndDay();
        }



        //点击"日"时出现颜色变换的动画并更改相应的日期
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            if (source.getText().length() == 0) {
                return;
            }
            dayColorUpdate(true);
            source.setForeground(todayBackColor);
            newDay = Integer.parseInt(source.getText());
            Calendar c = getCalendar();
            c.set(Calendar.DAY_OF_MONTH, newDay);
            setDate(c.getTime());
            daySpin.setValue(Integer.valueOf(newDay));
        }
    }
}
