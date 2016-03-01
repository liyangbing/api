package com.domain.service.impl;

import com.domain.dao.${entityName}DAO;
import com.domain.dao.BaseDAO;
import com.domain.entity.${entityName};
import com.domain.service.${entityName}Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
*
* ${entityName} 服务实现类
* Description:
*/
@Service
public class ${entityName}ServiceImpl extends BaseServiceImpl<${entityName}, Long>
        implements ${entityName}Service {

    @Autowired
    private ${entityName}DAO ${entityNameLower}DAO;

    @Override
    protected BaseDAO<${entityName}, Long> getBaseDao() {
        return ${entityNameLower}DAO;
    }
}
