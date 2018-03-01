package ofo.dao.Cache;

import java.sql.Connection;

class DbUtil {
    /**
     * @return 将从连接池中取连接的动作封装一层
     */
    static Connection getConnection() {
        DBPool pool = DBPool.getInstance();

        return pool.getConnection();
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
