package util;

/**
 * 数据库操作工具类
 * Created by sunp on 2020/4/14.
 */

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtil {

    // 数据库连接地址
    public String Connection_URL;
    // 用户名
    public String USERNAME;
    // 密码
    public String PASSWORD;
    // mysql的驱动类
    public String DRIVER;

    QueryRunner qr = new QueryRunner();


    public DBUtil(String url, String username, String password) {
        Connection_URL = url;
        USERNAME = username;
        PASSWORD = password;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    // 定义一个获取数据库连接的方法
    public Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(Connection_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("获取连接失败");
        }
        return conn;
    }

    // 关闭数据库连接
    public void close(ResultSet rs, Statement stat, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stat != null) {
                stat.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(String sql, Object... objs) {
        try {
            qr.update(getConnection(), sql, objs);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void update(String sql, Object... objs) {
        try {
            qr.update(getConnection(), sql, objs);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public List getAll(String sql, Class clasName) {
        List<?> list = new ArrayList();
        try {
            list = (List) qr.query(getConnection(), sql, new BeanListHandler(clasName));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;

    }

    public List<?> findBy(String sql, Class clasName, Object... objs) {
        List<?> list = new ArrayList();
        try {
            list = (List) qr.query(getConnection(), sql, new BeanHandler(clasName), objs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;

    }

    public List<Map<String, Object>> queryMapListHandler(String sql, Object[] params) {
        Connection conn = getConnection();
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        try {
            mapList = (List<Map<String, Object>>) qr.query(conn, sql, new MapListHandler(), params);
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("mapList.size(): " + mapList.size());
        return mapList;
//        for(int i=0; i<mapList.size();i++){
//            for(Map.Entry<String , Object> entry : mapList.get(i).entrySet())
//            {
//                System.out.println(entry.getKey() + "=" + entry.getValue());
//            }
//        }

    }

    public List<Map<String, Object>> queryMapListHandler(String sql) {
        Connection conn = getConnection();
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        try {
            mapList = (List<Map<String, Object>>) qr.query(conn, sql, new MapListHandler());
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("mapList.size(): " + mapList.size());
        return mapList;

    }

    public Map<String, Object> queryMapHandler(String sql) {
        Map<String, Object> map = new HashMap<String, Object>();

        try {
            Connection conn = getConnection();
            map = (Map<String, Object>) qr.query(conn, sql, new MapHandler());
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        for(Map.Entry<String , Object> entry : map.entrySet())
//        {
//            System.out.println(entry.getKey() + "=" + entry.getValue());
//        }
        return map;
    }

    public Map<String, Object> queryMapHandler(String sql, String id) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Connection conn = getConnection();
            map = (Map<String, Object>) qr.query(conn, sql, new MapHandler(), id);
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        for(Map.Entry<String , Object> entry : map.entrySet())
//        {
//            System.out.println(entry.getKey() + "=" + entry.getValue());
//        }
        return map;
    }


    public List<?> queryArrayHandler(String sql) throws SQLException {
//        数据库连接
        Connection conn = getConnection();
        List<?> list = new ArrayList();
        //定义返回结果
        Object[] result = null;
        try {
            //执行SQL查询
            result = (Object[]) qr.query(conn, sql, new ArrayHandler());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        list = Arrays.asList(result);
        //关闭
        conn.close();
        return list;
    }

    public List<?> queryArrayHandler(String sql, List params) throws SQLException {
        Connection conn = getConnection();
        List<?> list = new ArrayList();
        Object[] result = null;
        try {
            result = (Object[]) qr.query(conn, sql, new ArrayHandler(), params);
            System.out.println("sql " + sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("queryArrayHandler: result.length== " + result.length);
        list = Arrays.asList(result);
        conn.close();
        return list;
    }

    public List<Object[]> queryArrayListHandler(String sql) {
        List<Object[]> list = new ArrayList<Object[]>();
        try {
            Connection conn = getConnection();
            list = (List<Object[]>) qr.query(conn, sql, new ArrayListHandler());

            for (Object[] obj : list)
                System.out.println(Arrays.asList(obj));
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;

    }

    public int queryResultNumber(String sql, String id) {

        List<Object[]> list = new ArrayList<Object[]>();
        try {
            Connection conn = getConnection();
            list = (List<Object[]>) qr.query(conn, sql, new ArrayListHandler(), id);

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list.size();

    }

    public Map<String, Object> queryMapHandler1(String sql) {
        Map<String, Object> map = new HashMap<String, Object>();

        try {
            Connection conn = getConnection();
            map = (Map<String, Object>) qr.query(conn, sql, new MapHandler());
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        for(Map.Entry<String , Object> entry : map.entrySet())
//        {
//            System.out.println(entry.getKey() + "=" + entry.getValue());
//        }
        return map;
    }


}