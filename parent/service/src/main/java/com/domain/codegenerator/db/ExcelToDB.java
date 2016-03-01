package com.domain.codegenerator.db;

import jxl.Sheet;
import jxl.Workbook;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ybli on 2015/5/19.
 */
public class ExcelToDB {

    private static Log log = LogFactory.getLog(ExcelToDB.class);
    private List<String> createTableSqlList = new ArrayList<String>(); // 创建数据库表sql数组
    private StringBuilder createTableSqlBuilder = new StringBuilder(); // 创建数据库表sql

    /**
     * 导出Excel到DB
     */
    public void generate(String excelFile, JdbcTemplate jdbcTemplate) {
        // 产生sql脚本
        generateSQL(excelFile);

        // 执行sql脚本
        for (String sql : createTableSqlList) {
            log.info(sql);
            jdbcTemplate.execute(sql);
        }
    }

    /**
     * 表名	测试表	T_HS_TEST	默认值	必填
     * ID	ID	bigint
     * 从Excel产生sql脚本
     *
     */
    private void generateSQL(String excelFile) {
        Workbook workbook = null;
        try {
            workbook = Workbook.getWorkbook(new File(excelFile));
            Sheet dbSheet = workbook.getSheet("数据库");

            StringBuilder createTableSql = null;
            String tableComment = "";
            for (int i = 0; i < dbSheet.getRows(); i++) {
                String fieldName = dbSheet.getCell(0, i).getContents();
                String fieldChineseName = dbSheet.getCell(1, i).getContents();
                String fieldType = dbSheet.getCell(2, i).getContents();
                String defaultValue = dbSheet.getCell(3, i).getContents();
                String required = dbSheet.getCell(4, i).getContents();

                // 空行，不处理
                if (fieldName == null || fieldName.equals("")) continue;

                // 表名行的处理
                if (fieldName != null && fieldName.equals("表名")) {

                    // 完成一个表sql
                    if (createTableSql != null && createTableSql.length() > 0) {
                        createTableSql.deleteCharAt(createTableSql.length() - 2) // 擅长最后一个字段后的逗号
                                .append(")comment\t\t'")
                                .append(tableComment)
                                .append("'");
                        createTableSqlList.add(createTableSql.toString());
                        createTableSqlBuilder.append(createTableSql)
                                .append("\n\r");
                    }

                    // 新启一个表sql
                    createTableSql = new StringBuilder();
                    createTableSql.append("create table if not EXISTS ")
                            .append(fieldType)
                            .append("(");
                    tableComment = fieldChineseName;

                    // 表字段行的处理
                } else {
                    createTableSql.append("\t\t")
                            .append(fieldName)
                            .append("\t\t")
                            .append(fieldType)
                            .append("\t\t");

                    // ID字段
                    if (fieldName.equals("ID")) {
                        createTableSql.append("not null AUTO_INCREMENT PRIMARY KEY");

                        // 非ID字段
                    } else {
                        if (required != null && "是".equals(required)) {
                            createTableSql.append(" not null");

                            // 非必填指定默认值
                        } else {
                            if (StringUtils.isNotEmpty(defaultValue)) {
                                createTableSql.append("default")
                                        .append("\t\t");
                                if ("空串".equals(defaultValue)) {
                                    createTableSql.append("'")
                                            .append("'");
                                } else if ("datetime".equals(fieldType) || "date".equals(fieldType)) {
                                    createTableSql.append("'")
                                            .append("0000-00-00")
                                            .append("'");
                                } else if ("time".equals(fieldType)) {
                                    createTableSql.append("'")
                                            .append("00:00:00")
                                            .append("'");
                                }
                                else {
                                    createTableSql.append(defaultValue);
                                }
                            }
                        }
                    }

                    // 字段comment
                    createTableSql.append("\t\t")
                            .append("comment")
                            .append("\t\t")
                            .append("'")
                            .append(fieldChineseName)
                            .append("'");


                    // 字段结尾逗号隔开
                    createTableSql.append(",");
                }
                createTableSql.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }
    }

}
