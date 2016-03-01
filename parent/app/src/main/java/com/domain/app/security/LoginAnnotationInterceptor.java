package com.domain.app.security;


import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: ybli
 * Date: 13-12-17
 * Description:
 */
public class LoginAnnotationInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        HandlerMethod handler2 = (HandlerMethod) handler;

        // 后台接口 用户鉴权
        if (request.getRequestURI().contains("/jsp")) {
//            boolean pageValidRes = pageHandle(request, response, handler2);
//            if (pageValidRes) {
//                return true;
//            } else {
//                return false;
//            }
            return true;
        }


    // 前台接口 用户鉴权
    Login login = handler2.getMethodAnnotation(Login.class);

    // 资源没有声明权限, 放行
    if (null == login) {
        return true;
    }

    //取得session中的用户信息, 以便判断是否登录了系统
//    String ticket = request.getParameter("ticket");
//    if (ticket == null || "".equals(ticket.trim())) {
//        WebUtil.redirectLogin(request, response, login);
//        return false;
//    }
//
//    //
//    UserSessionRedisCache userSessionRedisCache=WebSpringContextUtil
//            .getBeanByType(UserSessionRedisCache.class);
//    TUser user= userSessionRedisCache.getUserByKey(ticket);
//    if (user == null) {
//        WebUtil.redirectLogin(request, response, login);
//        return false;
//    }

    // 放置用户信息
   // SystemContext.setUser(user);
    return true;
}


    //
    public boolean pageHandle(HttpServletRequest request, HttpServletResponse response,
                              Object handler) throws Exception {
        String path = request.getRequestURI();
        if (path.contains("user/member")) {
            return true;
        }

//        // 查看session中是否有用户信息
//        TUser user = (TUser) request.getSession().getAttribute("user");
//        if (user == null) {
//            //传统页面的登录
//            response.sendRedirect("/" + SystemConfig.getInstance().getProjectName() + "/jsp/TModule/");
//            return false;
//        }
//        SystemContext.setUser(user);
        return true;
    }


    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }


    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {

    }

}