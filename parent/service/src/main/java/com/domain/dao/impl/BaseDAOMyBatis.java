package com.domain.dao.impl;


import com.domain.common.Page;
import com.domain.dao.BaseDAO;
import org.apache.commons.beanutils.PropertyUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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
public class BaseDAOMyBatis<T, PK extends Serializable> implements BaseDAO<T, PK> {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;


    public SqlSessionTemplate getSqlSessionTemplate() {
        return sqlSessionTemplate;
    }

    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public static final String POSTFIX_INSERT = ".insertSelective";
    public static final String POSTFIX_DELETEBYID = ".deleteByPrimaryKey";
    public static final String POSTFIX_DELETEBYIDS = ".deleteByIds";
    public static final String POSTFIX_UPDATE = ".updateByPrimaryKeySelective";
    public static final String POSTFIX_UPDATEBYMAP = ".updateByMap";

    //
    public static final String POSTFIX_SELECTBYID = ".selectByPrimaryKey";
    public static final String POSTFIX_SELECTLISTBYMAP = ".selectListByMap";
    public static final String POSTFIX_SELECTLISTMAPBYMAP = ".selectListMapByMap";

    //
    public static final String TOTAL_COUNT_END_STR = ".TotalCount";
    public static final String TOTAL_COUNT_END_STR_PAGEMAP = ".PageMapTotalCount";

    public static final String START_ROW = "startRow";
    public static final String MAX_ROW_NUM = "maxRowNum";
    public static final String POSTFIX_SELECTPAGEBYMAP = ".selectPageByMap";
    public static final String POSTFIX_SELECTPAGEMAPBYMAP = ".selectPageMapByMap";

    public static final String NAMESPACE = "com.domain.entity.xml.";

    /**
     * DAO所管理的Entity类型
     */
    protected Class<T> clazz;
    protected String clazzName;

    public BaseDAOMyBatis() {
        clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        clazzName = clazz.getSimpleName();
        clazzName = NAMESPACE + clazzName + "Mapper";
    }

    @Override
    public T get(PK id) {
        T t = (T) sqlSessionTemplate.selectOne(clazzName + POSTFIX_SELECTBYID, id);
        return t;
    }

    @Override
    public void delete(T t) {
        // todo
    }

    @Override
    public void delete(PK id) {
        sqlSessionTemplate.delete(clazzName + POSTFIX_DELETEBYID, id);
    }

    public void delete(PK[] ids) {
        if (ids == null || ids.length == 0) return;
        List<PK> idList = new ArrayList<PK>();
        for (PK id : ids) {
            idList.add(id);
        }
        sqlSessionTemplate.delete(clazzName + POSTFIX_DELETEBYIDS, idList);
    }

    @Override
    public Long save(T entity) {
        sqlSessionTemplate.insert(clazzName + POSTFIX_INSERT, entity);
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
    public void update(T entity) {
        sqlSessionTemplate.update(clazzName + POSTFIX_UPDATE, entity);
    }

    @Override
    public void update(Map map) {
        sqlSessionTemplate.update(clazzName + POSTFIX_UPDATEBYMAP, map);
    }

    @Override
    public List<T> findListByEnity(T entity) {
        Map parameterMap = this.convertBean(entity);
        return sqlSessionTemplate.selectList(clazzName + POSTFIX_SELECTLISTBYMAP, parameterMap);
    }

    @Override
    public List<T> findListByMap(Map map) {
        return sqlSessionTemplate.selectList(clazzName + POSTFIX_SELECTLISTBYMAP, map);
    }

    @Override
    public List<Map> findListMapByMap(Map map) {
        return sqlSessionTemplate.selectList(clazzName + POSTFIX_SELECTLISTMAPBYMAP, map);
    }

    @Override
    public T findUniqueBy(String propertyName, Object value) {
        try {
            T t = clazz.newInstance();
            PropertyUtils.setProperty(t, propertyName, value);
            List<T> list = findListByEnity(t);
            if (list != null && list.size() > 0) {
                return list.get(0);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new RuntimeException(exception);
        }
        return null;
    }

    // =========================================================================
    public Page<T> search(Page<T> page, T e) {
        Map parameterMap = this.convertBean(e);
        return searchByMap(page, parameterMap);
    }

    public Page<T> searchByMap(Page<T> page, Map parameterMap) {

        // 统计并设置结果集记录总数
        if (page.isAutoCount()) {
            Object totalCount = sqlSessionTemplate.selectOne(
                    clazzName + BaseDAOMyBatis.TOTAL_COUNT_END_STR,
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
        List resultList = sqlSessionTemplate.selectList(clazzName + BaseDAOMyBatis.POSTFIX_SELECTPAGEBYMAP,
                parameterMap);

        // 查询出的结果集合设置到page对象
        page.setResult(resultList);
        return page;
    }

    @Override
    public Page<Map> searchMapByMap(Page<Map> page, Map parameterMap) {
        // 统计并设置结果集记录总数
        if (page.isAutoCount()) {
            Object totalCount = sqlSessionTemplate.selectOne(
                    clazzName + BaseDAOMyBatis.TOTAL_COUNT_END_STR_PAGEMAP,
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
        List resultList = sqlSessionTemplate.selectList(clazzName + BaseDAOMyBatis.POSTFIX_SELECTPAGEMAPBYMAP,
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