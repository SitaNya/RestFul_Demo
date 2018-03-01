package ofo.dao.Cache;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 数据库连接池
 */
class DBPool {
    private static final Logger Log = Logger.getLogger(DBPool.class);
    private static DBPool instance;
    private Properties prop;

    static {
        instance = new DBPool();
    }

    private ComboPooledDataSource dataSource;

    /**
     * 根据本地db.properties文件中的内容创建连接池
     */
    private DBPool() {
        try {
            dataSource = new ComboPooledDataSource();

            this.prop = new Properties();
            InputStream in = DBPool.class.getClassLoader().getResourceAsStream("db.properties");

            this.prop.load(in);
            dataSource.setDriverClass(this.prop.getProperty("jdbcdriver"));
            dataSource.setJdbcUrl(this.prop.getProperty("url"));
            dataSource.setUser(this.prop.getProperty("username"));
            dataSource.setPassword(this.prop.getProperty("password"));
            Log.info("create DBPool");
        } catch (Exception e) {
            Log.error("create DBPool Error: " + e.getMessage(), e);
        }
    }

    /**
     * @return 返回连接
     */
    Connection getConnection() {
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            Log.debug("get Connection");
        } catch (SQLException e) {
            Log.error("get Connection error: \n" + dataSource.toString() + "\nuser is: " + this.prop.getProperty("username") + "pass is:" + this.prop.getProperty("password") + "\n" + e.getMessage(), e);
        }

        return conn;
    }

    /**
     * @return 返回连接池
     */
    static DBPool getInstance() {
        return instance;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
