package com.domain.service;

import com.domain.common.Page;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 
 * 业务逻辑基类接口
 * @param <E>
 * @param <PK>
 */
public interface BaseService<E, PK extends Serializable> {

	/**
	 * 根据主键获得实体对象
	 * @param id  实体主键
	 * @return 实体对象
	 */
	public E get(PK id);

	/**
	 * 根据主键删除实体
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

    // ======================  list 查找 ========================
	public E findUniqueBy(String propertyName, Object value);
    public List<E> findListByEntity(E e);
    public List<E> findListByMap(Map<String, Object> map);
    public List<Map> findListMapByMap(Map<String, Object> map);

    //=================== 分页 ==================================
    public Page<E> search(Page<E> page, E e);
    public Page<E> search(Page<E> page, Map map);
    public Page<Map> searchMap(Page<Map> page, Map map);
}