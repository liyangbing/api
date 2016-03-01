package com.domain.dao;


import com.domain.common.Page;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *  BaseDAO接口类
 * @param <E>
 * @param <PK>
 */
public interface BaseDAO<E, PK extends Serializable> {

    /**
	 * 根据主键ID获取对象
	 * @param id  实体主键
	 * @return 实体对象
	 */
	public E get(PK id) ;

	/**
	 * 删除实体
	 * @param entity 实体对象
	 */
	public void delete(E entity);

	/**
	 * 根据主键Id删除实体
	 * @param id 实体主键
	 */
	public void delete(PK id);

    public void delete(PK[] ids);

	/**
	 * 保存实体对象<br>
	 * @param entity 实体对象
	 */
	public Long save(E entity);

	/**
	 * 更新实体，<br>
	 * @param entity 更新实体对象
	 */
	public void update(E entity);

    /**
     * 根据参数Map更新
     * @param map
     */
    public void update(Map map);



    // ===================== List ============================
	/**
	 * 根据属性名和属性值查询唯一对象. 
	 * @param propertyName
	 * @param value
	 * @return 符合条件的唯一对象， 或者是空，或者不存在.
	 */
	public E findUniqueBy(String propertyName, Object value);

    public List<E> findListByEnity(E entity);
    public List<E> findListByMap(Map map);
    public List<Map> findListMapByMap(Map map);

    // =========================== 分页 ========================
    public Page<E> search(Page<E> page, E e);
    public Page<E> searchByMap(Page<E> page, Map parameterMap);
    public Page<Map> searchMapByMap(Page<Map> page, Map e);
}