package com.xxy.stock.web.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.xxy.stock.web.util.SqliteUtil;

public class SqliteUtilTest {
    public static void main(String[] args) {
        testDemo();
        
        //testQueryDemo();
    }

    public static void testDemo() {
        try {
            Connection conn = SqliteUtil.getConnection();
            
            Statement stat = conn.createStatement();
            
            stat.executeUpdate("drop table if exists tbl1;");
            stat.executeUpdate("create table if not exists tbl1(name varchar(20), salary int);");// 创建一个表，两列
            stat.executeUpdate("insert into tbl1 values('ZhangSan',8000);"); // 插入数据
            stat.executeUpdate("insert into tbl1 values('LiSi',7800);");
            stat.executeUpdate("insert into tbl1 values('WangWu',5800);");
            stat.executeUpdate("insert into tbl1 values('ZhaoLiu',9100);");
            ResultSet rs = stat.executeQuery("select * from tbl1;"); // 查询数据
            System.out.println("创建表结构录入数据操作演示：");
            while (rs.next()) { // 将查询到的数据打印出来
                System.out.print("name = " + rs.getString("name") + ", "); // 列属性一
                System.out.println("salary = " + rs.getString("salary")); // 列属性二
            }
            SqliteUtil.close(null, null, rs);
            
            
            stat.executeUpdate("alter table tbl1 add column address varchar(20) not null default 'changsha'; ");// 创建一个表，两列
            stat.executeUpdate("insert into tbl1 values('HongQi',9000,'tianjing');"); // 插入数据
            stat.executeUpdate("insert into tbl1(name,salary) values('HongQi',9000);"); // 插入数据
            rs = stat.executeQuery("select * from tbl1 limit 0,10;"); // 查询数据
            System.out.println("表结构变更操作演示：");
            while (rs.next()) { // 将查询到的数据打印出来
                System.out.print("name = " + rs.getString("name") + ", "); // 列属性一
                System.out.print("name = " + rs.getString("salary") + ", "); // 列属性二
                System.out.println("address = " + rs.getString("address")); // 列属性三
            }
            SqliteUtil.close(null, null, rs);
            
            SqliteUtil.close(conn, stat, rs); // 结束数据库的连接
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void testQueryDemo() {
        try {
            Connection conn = SqliteUtil.getConnection();
            
            Statement stat = conn.createStatement();
            
            ResultSet rs = stat.executeQuery("select * from tbl1;"); // 查询数据
            System.out.println("创建表结构录入数据操作演示：");
            while (rs.next()) { // 将查询到的数据打印出来
                System.out.print("name = " + rs.getString("name") + ", "); // 列属性一
                System.out.println("salary = " + rs.getString("salary")); // 列属性二
            }
            SqliteUtil.close(null, null, rs);
            
            rs = stat.executeQuery("select * from tbl1 limit 0,10;"); // 查询数据
            System.out.println("表结构变更操作演示：");
            while (rs.next()) { // 将查询到的数据打印出来
                System.out.print("name = " + rs.getString("name") + ", "); // 列属性一
                System.out.print("salary = " + rs.getString("salary") + ", "); // 列属性二
                System.out.println("address = " + rs.getString("address")); // 列属性三
            }
            SqliteUtil.close(null, null, rs);
            
            SqliteUtil.close(conn, stat, rs); // 结束数据库的连接
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
