package com.domain.webadmin.controller;

import com.domain.entity.${entityName};
import com.domain.service.${entityName}Manager;
import com.domain.common.Page;
import com.domain.webadmin.controller.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;

/**
*
* ${entityName} 控制类
* Description:
*
*/
@Controller
@Scope("prototype")
public class ${entityName}Controller extends BaseController{

    @Autowired
    private ${entityName}Manager ${entityNameLower}Manager;

    /**
     * 获取 ${entityName} 信息列表
     */
    @RequestMapping(value="/jsp/${entityNameLower}", method = RequestMethod.GET)
    public ModelAndView list(Model model,Page<${entityName}> page, ${entityName} ${entityNameLower}) {
        page=${entityNameLower}Manager.search(page, ${entityNameLower});
        model.addAttribute("page", page);
        return new ModelAndView("jsp/${entityNameLower}/${entityNameLower}_list", model.asMap());
    }

    /**
     * 获取 ${entityName} 信息
     */
    @RequestMapping(value = "/jsp/${entityNameLower}/{id}", method = RequestMethod.GET)
    public ModelAndView get(@PathVariable Long id, Model model) {
        // 新增
        if (id == 0) {
            return new ModelAndView("jsp/${entityNameLower}/${entityNameLower}_add", model.asMap());
        }

        // 更新
        ${entityName} ${entityNameLower} = ${entityNameLower}Manager.get(id);
        model.addAttribute("${entityNameLower}", ${entityNameLower});
        return new ModelAndView("jsp/${entityNameLower}/${entityNameLower}_view", model.asMap());
    }

    /**
     *
     * 保存、更新 ${entityName} 信息
     */
    @RequestMapping(value = "/jsp/${entityNameLower}", method = RequestMethod.POST)
    public ModelAndView save(${entityName} ${entityNameLower})  throws Exception {
        if (${entityNameLower}.getId() == null || ${entityNameLower}.getId() == 0)  {
            ${entityNameLower}Manager.save(${entityNameLower});
        } else {
            ${entityNameLower}Manager.update(${entityNameLower});
        }
        return new ModelAndView(ADD_SUCCESS_PAGE);
    }

    /**
    * 删除 ${entityName} 信息
    */
    @RequestMapping(value = "/jsp/${entityNameLower}/{id}", method = RequestMethod.DELETE)
    public ModelAndView delete(@PathVariable Long id) {
        ${entityNameLower}Manager.delete(id);
        return new ModelAndView(DELETE_SUCCESS_PAGE);
    }


    @RequestMapping(value = "/jsp/${entityNameLower}", method = RequestMethod.DELETE)
    public ModelAndView delete(Long[] ids) {
        ${entityNameLower}Manager.delete(ids);
        return new ModelAndView(DELETE_SUCCESS_PAGE);
    }

}
