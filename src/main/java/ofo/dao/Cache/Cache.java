package ofo.dao.Cache;

import ofo.Enity.UserEnity;
import org.apache.log4j.Logger;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Properties;

import static ofo.impl.NowString.getNowString;

/**
 * Created by sitanya on 2017/7/4.
 * 进行Mysql数据库缓存和读取
 */
public class Cache {
    private static final Logger Log = Logger.getLogger(Cache.class);
    private String sql;

    public Cache() {
    }

    /**
     * @param timestampString 传入时间戳String
     * @return 返回格式化后的时间
     */
    private String TimeStamp2Date(String timestampString) {
        Long timestamp = Long.parseLong(timestampString);

        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(timestamp));
    }

    /**
     * @param time 传入格式化好的时间
     * @return 返回时间戳
     */
    private Timestamp get_time(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d = null;

        try {
            d = format.parse(time);
        } catch (Exception e) {
            Log.error(e.getMessage(), e);
        }

        return new Timestamp((d != null)
                ? d.getTime()
                : 0);
    }

    public String insert(UserEnity userEnity) {
        sql = "INSERT INTO user VALUES(?,?,?,?,?,?,?)";
        String state = "succeed";
        try (Connection conn = DbUtil.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setTimestamp(1, get_time(getNowString()));
                ps.setString(2, userEnity.getUser());
                ps.setString(3, userEnity.getPassword());
                ps.setString(4, userEnity.getGroup());
                ps.setString(5, userEnity.getLevel());
                ps.setInt(6, userEnity.getPhone());
                ps.setString(7, userEnity.getEmail());
                ps.executeUpdate();
                Log.debug("add User check info for: " + userEnity.getUser());
            }
        } catch (SQLException e) {
            Log.error("add User check info for: " + userEnity.getUser() + " Exception is: " + e.getMessage(), e);
            state = e.toString();
        }
        return state;
    }

    /**
     * @param id_value 传入application_id，查询这个id上一次检测有问题的时间
     * @return 返回一系列标志位，得出这个任务是第一次报警
     */
    public ArrayList<String> select(String id_value) {

//      传入任务id，查询这个id中上次报警的值
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ArrayList<String> list = new ArrayList<>(2);

        try (Connection conn = DbUtil.getConnection()) {
            sql = String.format(
                    "select firstAlarm,is_alarm from job_info_check where id ='%s' order by elapsedTime desc limit 1",
                    id_value);

            try (Statement stat = conn.createStatement()) {
                try (ResultSet set = stat.executeQuery(sql)) {

                    while (set.next()) {

//              返回一个列表，里面包含格式化好的时间和报警标志位
                        list.add(df.format(set.getTimestamp("firstAlarm")));
                        list.add(set.getString("is_alarm"));
                    }
                }
                stat.close();
            }
        } catch (Exception e) {
            Log.error("select firstAlarm info error: +" + e.getMessage(), e);
        }

        return list;
    }

    /**
     * @return 得到HDFS这个取值点和上一个取值点的存储容量差值
     */
    public Long selectLastHdfs() {
        Long LastHdfs = 0L;
        try (Connection conn = DbUtil.getConnection()) {
            sql = "SELECT true_size AS value FROM HDFS_state WHERE dir = 'ALL' AND `group` = '/' ORDER BY unix_timestamp(time) DESC LIMIT 1;";

            try (Statement stat = conn.createStatement()) {
                try (ResultSet set = stat.executeQuery(sql)) {

                    while (set.next()) {
                        LastHdfs = set.getLong("value");
                    }
                }
                stat.close();
            }
        } catch (Exception e) {
            Log.error("select firstAlarm info error: +" + e.getMessage(), e);
        }

        return LastHdfs;
    }

    /**
     * @param now_namenode 检查当前的active_namenode
     * @return 返回现在的active_namenode和上一个检查点的active_namenode是否改变，也就是HA是否切换了，返回布尔值
     */
    public Boolean check_active_namenode(String now_namenode) {
        String last_namenode = "";
        try (Connection conn = DbUtil.getConnection()) {
            sql = "SELECT active_namenode AS value FROM HA_state ORDER BY unix_timestamp(time) DESC LIMIT 1;";

            try (Statement stat = conn.createStatement()) {
                try (ResultSet set = stat.executeQuery(sql)) {

                    while (set.next()) {
                        last_namenode = set.getString("value");
                    }
                }
                stat.close();
            }
        } catch (Exception e) {
            Log.error("select firstAlarm info error: +" + e.getMessage(), e);
        }

        return last_namenode.equals(now_namenode);
    }

    /**
     * @param properties 配置文件实例
     * @return 返回表权限继承可能有问题的表的数量
     */
    public ArrayList<String> select_Permission_num(Properties properties) {
        int Error_num = 0;
        ArrayList<String> Error_Table_List = new ArrayList<>();
        try (Connection conn = DbUtil.getConnection()) {
            sql = "SELECT count(DataBaseDir) - sum(default_exist) - 2 AS value FROM Hive_Table_Permission WHERE time=(SELECT time FROM Hive_Table_Permission ORDER BY time DESC LIMIT 1) GROUP BY dbName,time ORDER BY time ASC;";

            try (Statement stat = conn.createStatement()) {
                stat.setQueryTimeout(10);
                try (ResultSet set = stat.executeQuery(sql)) {

                    while (set.next()) {
                        Error_num = set.getInt("value");
                    }
                }

                if (Error_num > 0) {
                    sql = "SELECT dbName, DataBaseDir FROM Hive_Table_Permission WHERE time = ( SELECT time FROM Hive_Table_Permission ORDER BY time DESC LIMIT 1) AND default_exist != TRUE;";
                    try (ResultSet set = stat.executeQuery(sql)) {
                        while (set.next()) {
                            Error_Table_List.add(set.getString("dbName") + "," + set.getString("DataBaseDir"));
                        }
                    }
                }
                stat.close();
            }
        } catch (SQLTimeoutException e) {
            Log.error(e.getMessage(), e);
        } catch (Exception e) {
            Log.error("select Hive Permission Error Table List info error: +" + e.getMessage(), e);
        }

        return Error_Table_List;
    }

}


//~ Formatted by Jindent --- http://www.jindent.com
