package com.xxy.stock.web.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSource;
import com.xxy.stock.web.constants.StockWebsiteConstants;

public class SqliteUtil implements StockWebsiteConstants {
    
    private static final Map<String, DataSource> DATA_SOURCE_MAP = new ConcurrentHashMap<String, DataSource>();
    private static final String DATA_SOURCE_KEY = "data.source.key";
    
    private static final String     DATA_SOURCE_DRIVER_CLASSNAME = "org.sqlite.JDBC";
    private static final int        DATA_SOURCE_INITIAL_SIZE = 2;
    private static final int        DATA_SOURCE_MAX_ACTIVE = 30;
    private static final int        DATA_SOURCE_MIN_IDLE = 2;
    private static final long       DATA_SOURCE_MAX_WAIT = 60000;
    private static final long       DATA_SOURCE_TIME_BETWEEN_EVICTION_RUNS_MILLIS = 60000;
    private static final long       DATA_SOURCE_MIN_EVICTABLE_IDLETIME_MILLIS = 300000;
    private static final String     DATA_SOURCE_VALIDATION_QUERY = "SELECT 'x'";
    private static final boolean    DATA_SOURCE_TEST_WHILE_IDLE = true;
    private static final boolean    DATA_SOURCE_TEST_ON_BORROW = false;
    private static final boolean    DATA_SOURCE_TEST_ON_RETURN = false;
    
    static{
    	try {
			DataSource dataSource = initDataSource();
			DATA_SOURCE_MAP.put(DATA_SOURCE_KEY, dataSource);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    private static DruidDataSource initDataSource() throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        
        //dataSource.setUrl("jdbc:sqlite:D:\\data\\27d2d32939ff5f5df8d1d9114c3db31d\\property\\stock.db"); //数据库物理地址
        dataSource.setUrl("jdbc:sqlite:" + STCOK_DB_PATH); //数据库物理地址
        
        dataSource.setDriverClassName(DATA_SOURCE_DRIVER_CLASSNAME);
        dataSource.setInitialSize(DATA_SOURCE_INITIAL_SIZE); //初始化连接数量，默认值0
        dataSource.setMaxActive(DATA_SOURCE_MAX_ACTIVE); //最大连接数量，默认值8
        dataSource.setMinIdle(DATA_SOURCE_MIN_IDLE); //最小空闲连接数量
        dataSource.setMaxWait(DATA_SOURCE_MAX_WAIT); //获取连接时最大等待时间，单位毫秒
        dataSource.setTimeBetweenEvictionRunsMillis(DATA_SOURCE_TIME_BETWEEN_EVICTION_RUNS_MILLIS); //destroy线程会检测连接的间隔时间，单位毫秒
        dataSource.setMinEvictableIdleTimeMillis(DATA_SOURCE_MIN_EVICTABLE_IDLETIME_MILLIS); //连接在池中保持空闲而不被空闲连接回收器线程(如果有)回收的最小时间值，单位毫秒
        dataSource.setValidationQuery(DATA_SOURCE_VALIDATION_QUERY); //用来检测连接是否有效的sql，要求是一个查询语句。如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会起作用
        dataSource.setTestWhileIdle(DATA_SOURCE_TEST_WHILE_IDLE); //建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效
        dataSource.setTestOnBorrow(DATA_SOURCE_TEST_ON_BORROW); //申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能，建议false
        dataSource.setTestOnReturn(DATA_SOURCE_TEST_ON_RETURN); //归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能，建议false
        
        return dataSource;
    }
    
    public static Connection getConnection() throws SQLException {
        DataSource dataSource = DATA_SOURCE_MAP.get(DATA_SOURCE_KEY);
        if(dataSource == null){
            dataSource = initDataSource();
            DATA_SOURCE_MAP.put(DATA_SOURCE_KEY, dataSource);
        }
        return dataSource.getConnection();   
    }
    
    public static void close(Connection conn, Statement stmt, ResultSet rs) {    
        if (rs != null) {    
            try {    
                rs.close();    
            } catch (SQLException ex) {
                ex.printStackTrace();
            }    
            rs = null;    
        }    
        if (stmt != null) {    
            try {    
                stmt.close();    
            } catch (SQLException ex) {    
                ex.printStackTrace();    
            }    
            stmt = null;    
        }    
        if (conn != null) {    
            try {    
                conn.close();    
            } catch (SQLException ex) {    
                ex.printStackTrace();    
            }    
            conn = null;    
        }    
    }
    
}
