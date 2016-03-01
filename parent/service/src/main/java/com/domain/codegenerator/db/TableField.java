package com.domain.codegenerator.db;

/**
 * 
 * <p>Title: TableField</p>
 * <p>Description: 表字段信息</p>
 * @createtime 2012-10-23 下午02:20:34
 *
 */
public class TableField {

	private String fieldName;   // 字段名
	private String fieldType;   // 字段类型
	private int fieldLength;    // 字段长度
	private int fieldScale;     // 字段精度
    private String comment; // 字段描述

    private String properyName; // 对应的实体属性名
    private String propertyDate; // 对应的属性类型
    private String fieldChineseName; // 字段中文名

    private String tableName; // 表名

	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public int getFieldLength() {
		return fieldLength;
	}
	public void setFieldLength(int fieldLength) {
		this.fieldLength = fieldLength;
	}
	public int getFieldScale() {
		return fieldScale;
	}
	public void setFieldScale(int fieldScale) {
		this.fieldScale = fieldScale;
	}

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getProperyName() {
        return properyName;
    }

    public void setProperyName(String properyName) {
        this.properyName = properyName;
    }

    public String getPropertyDate() {
        return propertyDate;
    }

    public void setPropertyDate(String propertyDate) {
        this.propertyDate = propertyDate;
    }

    public String getFieldChineseName() {
        return fieldChineseName;
    }

    public void setFieldChineseName(String fieldChineseName) {
        this.fieldChineseName = fieldChineseName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
