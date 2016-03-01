package com.domain.codegenerator.db;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ybli on 2015/5/19.
 */
public class DBUtil {

    /**
     *
     * @return
     */
    public static JdbcTemplate getJdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(getDataSource());
        return jdbcTemplate;
    }

    /**
     *
     * @return
     */
    public static DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(PropertyUtil.getProperties("database.driverClassName"));
        dataSource.setUrl(PropertyUtil.getProperties("database.url"));
        dataSource.setUsername(PropertyUtil.getProperties("database.username"));
        dataSource.setPassword(PropertyUtil.getProperties("database.password"));
        return dataSource;
    }

    /**
     * 关闭连接
     * @param rs
     */
    public static void close(ResultSet rs) {
            close(rs, null,null);
    }

    /**
     * 根据数据库名，返回数据库中所有表
     */
    public static List<String> getTablesByDBName(String dbname) {
        final List<String> tableList = new ArrayList<String>();

        // sql
        String tableSql = "SELECT TABLE_NAME \n" +
                "  FROM information_schema.TABLES  \n" +
                " WHERE TABLE_SCHEMA = '" + dbname + "' order by TABLE_NAME asc";
        JdbcTemplate jdbcTemplate = getJdbcTemplate();

        // 执行查询
        jdbcTemplate.query(tableSql, new RowCallbackHandler() {
            public void processRow(ResultSet rs) throws SQLException {
                String tableName = rs.getString(1);
                tableList.add(tableName);
            }
        });
        return tableList;
    }

    /**
     * 根据数据库名，返回数据库中所有的表结构
     * @param dbname
     * @return
     */
    public static Map<String, List<TableField>> getTableFieldsByDBName(String dbname) {
        final Map<String, List<TableField>> tableFieldListMap = new HashMap<String, List<TableField>>();
        List<String> tableList = getTablesByDBName(dbname);

        for (String tableName : tableList) {
            tableFieldListMap.put(tableName,getFieldListByTableName(dbname, tableName));
        }
        return tableFieldListMap;
    }

    /**
     * 返回 TableField Map结构
     * @param dbname
     * @param tableName
     * @return
     */
    public static Map<String, TableField> getTableFieldMap(String dbname,String tableName) {
        List<TableField> tableFieldList = getFieldListByTableName(dbname,tableName);
        Map<String,TableField> tableFieldMap = new HashMap<String,TableField>();

        for (TableField tableField : tableFieldList) {
            tableFieldMap.put(tableField.getFieldName(),tableField);
        }
        return tableFieldMap;
    }

    /**
     * 根据表名获取表结构信息
     * @param tableName
     */
    public static List<TableField> getFieldListByTableName(String dbname,String tableName) {
        final List<TableField> tableFieldList = new ArrayList<TableField>();

        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        String sql = "SELECT TABLE_NAME, COLUMN_NAME, COLUMN_TYPE,COLUMN_COMMENT\n" +
                "  FROM information_schema.COLUMNS \n" +
                " WHERE TABLE_SCHEMA = '" + dbname + "'" +
                " and TABLE_NAME='" + tableName +"'"+ " order by TABLE_NAME asc ";

        // 执行查询
        jdbcTemplate.query(sql, new RowCallbackHandler() {
            public void processRow(ResultSet rs) throws SQLException {
                TableField field = new TableField();

                //
                field.setFieldName(rs.getString(2));
                field.setFieldType(rs.getString(3));
                field.setComment(rs.getString(4));
                field.setFieldChineseName(field.getComment());

                //
                tableFieldList.add(field);
            }
        });
        return tableFieldList;
    }

    /**
     * 关闭连接
     * @param rs
     * @param psmt
     * @param conn
     */
    public static void close(ResultSet rs, PreparedStatement psmt, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (psmt != null) {
                psmt.close();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                conn.close();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
