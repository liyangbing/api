package com.domain.dao.impl;


import com.domain.common.Page;
import com.domain.dao.BaseDAO;
import org.apache.commons.beanutils.PropertyUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.text.html.parser.Entity;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: ybli
 * Date: 13-12-2
 * Description: Ibatis BaseDAO实现
 */
public class BaseDAOMyBatis<E, PK extends Serializable> implements BaseDAO<E, PK> {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;


    public SqlSessionTemplate getSqlSessionTemplate() {
        return sqlSessionTemplate;
    }

    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    // 基本增删改查
    public static final String POSTFIX_INSERT = ".insert";
    public static final String POSTFIX_INSERT_SELECTIVE = ".insertSelective";
    public static final String POSTFIX_DELETEBYID = ".deleteByPrimaryKey";
    public static final String POSTFIX_UPDATE= ".updateByPrimaryKey";
    public static final String POSTFIX_UPDATE_SELECTIVE = ".updateByPrimaryKeySelective";
    public static final String POSTFIX_SELECTBYID = ".selectByPrimaryKey";


    // 列表
    public static final String POSTFIX_SELECTLIST = ".selectAll";

    // 分页
    public static final String TOTAL_COUNT_END_STR = ".TotalCount";
    public static final String START_ROW = "startRow";
    public static final String MAX_ROW_NUM = "maxRowNum";
    public static final String POSTFIX_SELECTPAGE = ".selectPage";

    public static final String POSTFIX_NAMESPACE = "com.domain.entity.xml.";
    public static final String PREFIX_NAMESPACE = "Mapper";

    /**
     * DAO所管理的Entity类型
     */
    protected Class<E> clazz;
    protected String clazzName;
    protected String nameSpace; // 命名空间

    public BaseDAOMyBatis() {
        clazz = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        clazzName = clazz.getSimpleName();
        nameSpace = POSTFIX_NAMESPACE + clazzName + PREFIX_NAMESPACE;
    }

    @Override
    public E get(PK id) {
        E e = sqlSessionTemplate.selectOne(nameSpace + POSTFIX_SELECTBYID, id);
        return e;
    }

    @Override
    public Long saveSelective(E entity) {
        sqlSessionTemplate.insert(nameSpace + POSTFIX_INSERT_SELECTIVE, entity);
        Long id = null;
        try {
            id = (Long) entity.getClass().getDeclaredMethod("getId").invoke(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (id == null) {
            return id;
        }
        return Long.valueOf(id);
    }

    @Override
    public void updateSelective(E entity) {
        sqlSessionTemplate.update(nameSpace + POSTFIX_UPDATE_SELECTIVE, entity);
    }

    @Override
    public void delete(PK id) {
        sqlSessionTemplate.delete(nameSpace + POSTFIX_DELETEBYID, id);
    }

    @Override
    public Long save(E entity) {
        sqlSessionTemplate.insert(nameSpace + POSTFIX_INSERT, entity);
        Long id = null;
        try {
            id = (Long) entity.getClass().getDeclaredMethod("getId").invoke(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (id == null) {
            return id;
        }
        return Long.valueOf(id);
    }

    @Override
    public void update(E entity) {
        sqlSessionTemplate.update(nameSpace + POSTFIX_UPDATE, entity);
    }

    @Override
    public List<E> selectAll(E entity) {
        Map parameterMap = this.convertBean(entity);
        return sqlSessionTemplate.selectList(nameSpace + POSTFIX_SELECTLIST, parameterMap);
    }

    // =========================================================================
    public Page<E> search(Page<E> page, E e) {
        Map parameterMap = this.convertBean(e);
        return searchByMap(page, parameterMap);
    }

    public Page<E> searchByMap(Page<E> page, Map parameterMap) {

        // 统计并设置结果集记录总数
        if (page.isAutoCount()) {
            Object totalCount = sqlSessionTemplate.selectOne(
                    nameSpace + BaseDAOMyBatis.TOTAL_COUNT_END_STR,
                    parameterMap);
            page.setTotalCount((Long) totalCount);
        }

        // 获得page中的参数并设置到ParameterMap
        // 获取记录的开始行数,从0开始
        int startRow = page.getFirst();

        // 步长,一共获取几行
        int maxRowNum = page.getPageSize();

        // 获取记录的结束行数,Oracle数据库用到,使用"startRow"和"endRow"参数
        //int endRow = (maxRowNum > 0) ? (startRow + maxRowNum ) : startRow;

        // 参数并设置到ParameterMap
        parameterMap.put(BaseDAOMyBatis.START_ROW, startRow);
        parameterMap.put(BaseDAOMyBatis.MAX_ROW_NUM, maxRowNum);

        //parameterMap.put(BaseDAOIbatis.END_ROW, endRow);  //oracle分页用到
        // 根据statementName和查询条件parameterObject查询
        List resultList = sqlSessionTemplate.selectList(nameSpace + BaseDAOMyBatis.POSTFIX_SELECTPAGE,
                parameterMap);

        // 查询出的结果集合设置到page对象
        page.setResult(resultList);
        return page;
    }

    /**
     * 将一个 JavaBean 对象转化为一个  Map
     * @param bean 要转化的JavaBean 对象
     * @return 转化出来的  Map 对象
     * @throws java.beans.IntrospectionException 如果分析类属性失败
     * @throws IllegalAccessException 如果实例化 JavaBean 失败
     * @throws java.lang.reflect.InvocationTargetException 如果调用属性的 setter 方法失败
     */
    public static Map convertBean(Object bean) {
        Class type = bean.getClass();
        Map returnMap = new HashMap();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(type);
            PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
            for (int i = 0; i< propertyDescriptors.length; i++) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                String propertyName = descriptor.getName();
                if (!propertyName.equals("class")) {
                    Method readMethod = descriptor.getReadMethod();
                    Object result = readMethod.invoke(bean, new Object[0]);
                    if (result != null) {
                        returnMap.put(propertyName, result);
                    } else {
                        returnMap.put(propertyName, null);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnMap;
    }
}