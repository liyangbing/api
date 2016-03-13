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

	public Long save(E entity) {
        Long id = getBaseDao().save(entity);
        return id;
	}

    @Override
    public Long saveSelective(E entity) {
        return getBaseDao().save(entity);
    }

    public void update(E entity) {
		getBaseDao().update(entity);
	}

    public void updateSelective(E entity) {
        getBaseDao().updateSelective(entity);
    }

    // list
    @Transactional(readOnly=true, propagation= Propagation.NOT_SUPPORTED)
    public List<E> selectAll(E e) {
        return getBaseDao().selectAll(e);
    }

    // 分页
    @Transactional(readOnly=true, propagation= Propagation.NOT_SUPPORTED)
    public Page<E> search(Page<E> page, E e) {
        return getBaseDao().search(page,e);
    }
}
