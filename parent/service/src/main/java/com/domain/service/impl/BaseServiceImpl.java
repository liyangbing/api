package com.domain.service.impl;


import com.domain.common.Page;
import com.domain.dao.BaseDAO;
import com.domain.service.BaseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 默认的事务传播特性PROPAGATION_REQUIRED
 * 如果当前没有事务，就新建一个事务，如果已经存在
 * 一个事务中，加入到这个事务中，这是最常见的选择
 *
 * @param <E>
 * @param <PK>
 */
@Transactional
public abstract class BaseServiceImpl<E,PK extends Serializable> implements BaseService<E, PK> {
	
	protected final Log log = LogFactory.getLog(getClass().getName());

	/**
	 *   数据访问接口
	 */
	protected abstract BaseDAO<E, PK> getBaseDao();

    @Transactional(readOnly=true, propagation= Propagation.NOT_SUPPORTED)
    public E get(PK id) {
        return getBaseDao().get(id);
    }

	public void delete(PK id) {
		getBaseDao().delete(id);
    }

    public void delete(PK[] ids) {
        getBaseDao().delete(ids);
    }

	public Long save(E entity) {
        Long id = getBaseDao().save(entity);
        return id;
	}

	public void update(E entity) {
		getBaseDao().update(entity);
	}

    public void update(Map map) {
        getBaseDao().update(map);
    }

    // =====================================================================================

    @Transactional(readOnly=true, propagation= Propagation.NOT_SUPPORTED)
    public List<E> findListByEntity(E e) {
        return getBaseDao().findListByEnity(e);
    }

    @Transactional(readOnly=true, propagation= Propagation.NOT_SUPPORTED)
    public List<E> findListByMap(Map<String,Object> map) {
        return getBaseDao().findListByMap(map);
    }

    @Transactional(readOnly=true, propagation= Propagation.NOT_SUPPORTED)
    public List<Map> findListMapByMap(Map<String,Object> map) {
        return getBaseDao().findListMapByMap(map);
    }

    @Transactional(readOnly=true, propagation= Propagation.NOT_SUPPORTED)
    public E findUniqueBy(String propertyName, Object value) {
        return getBaseDao().findUniqueBy(propertyName, value);
    }


    // ======================== 分页 =========================================================
    @Transactional(readOnly=true, propagation= Propagation.NOT_SUPPORTED)
    public Page<E> search(Page<E> page, E e) {
        return getBaseDao().search(page,e);
    }

    @Transactional(readOnly=true, propagation= Propagation.NOT_SUPPORTED)
    public Page<E> search(Page<E> page, Map map) {
        return getBaseDao().searchByMap(page,map);
    }

    @Transactional(readOnly=true, propagation= Propagation.NOT_SUPPORTED)
    public Page<Map> searchMap(Page<Map> page, Map map) {
        return getBaseDao().searchMapByMap(page,map);
    }

}
