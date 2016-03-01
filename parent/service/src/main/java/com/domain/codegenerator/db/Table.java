package com.domain.codegenerator.db;

/**
 *  数据库表
 */
public class Table {

    private String tableName; // 表名
    private String tableChinseName; // 表中文名
    private String commnent; // 表备注
    private String entityName; // 实体名

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableChinseName() {
        return tableChinseName;
    }

    public void setTableChinseName(String tableChinseName) {
        this.tableChinseName = tableChinseName;
    }

    public String getCommnent() {
        return commnent;
    }

    public void setCommnent(String commnent) {
        this.commnent = commnent;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {

        this.entityName = entityName;
    }
}
