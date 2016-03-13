package com.domain.dao;


import com.domain.common.Page;

import javax.swing.text.html.parser.Entity;
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
	 * 根据主键id获取对象
	 * @param id  实体主键
	 * @return 实体对象
	 */
	public E get(PK id) ;

	/**
	 * 根据主键id删除实体
	 * @param id 实体主键
	 */
	public void delete(PK id);


	/**
	 * 保存实体对象，所有属性保存<br>
	 * @param entity 实体对象
	 */
	public Long save(E entity);

    /**
     * 根据属性是否为空，选择性保存实体
     * @param entity
     * @return
     */
    public Long saveSelective(E entity);

	/**
	 * 更新实体，<br>
	 * @param entity 更新实体对象
	 */
	public void update(E entity);

    /**
     * 根据属性是否为空，选择性更新实体
     * @param entity
     */
    public void updateSelective(E entity);

    /**
     * selectAll
     * @param entity 条件，非空字段
     * @return
     */
    public List<E> selectAll(E entity);
    /**
     * 分页查询
     * @param page
     * @param entity
     * @return
     */
    public Page<E> search(Page<E> page, E entity);
}