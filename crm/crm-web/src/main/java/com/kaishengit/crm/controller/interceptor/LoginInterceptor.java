package com.kaishengit.crm.controller.interceptor;

import com.kaishengit.crm.entity.Account;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor extends HandlerInterceptorAdapter  {


    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        //获取用户请求的路径
        String requestUrl = request.getRequestURI();

        //如果是静态路径，则放行
        if(requestUrl.startsWith("/static/")) {
            return true;
        }

        //如果是登录页面，则方行
        if("".equals(requestUrl) || "/login".equals(requestUrl)) {
            return true;
        }

        //判断用户是否登录
        HttpSession httpSession = request.getSession();
        Account account = (Account) httpSession.getAttribute("account");
        if(account != null) {
            return true;
        }
        response.sendRedirect("/login");
        return false;
    }


}
