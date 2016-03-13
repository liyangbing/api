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

	/**
	 * 保存实体对象<br>
	 * @param entity 实体对象
	 */
	public Long save(E entity);

    /**
     * 保存实体对象<br>
     * @param entity 实体对象
     */
    public Long saveSelective(E entity);

	/**
	 * 更新实体，<br>
	 * @param entity 更新实体对象
	 */
	public void update(E entity);

    /**
     * 根据参数Map更新
     * @param entity
     */
    public void updateSelective(E entity);

    /**
     *
     * @return
     */
    public List<E> selectAll(E entity);

    /**
     * 分页
     * @param page
     * @return
     */
    public Page<E> search(Page<E> page, E entity);
}