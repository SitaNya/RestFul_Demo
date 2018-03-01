package ofo.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NowString {
    static public String getNowString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

//      set date format
        return df.format(new Date());

//      new date is get system time,now;
    }
}
