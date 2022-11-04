package com.wcq.reggie.filter;


import com.alibaba.fastjson.JSON;
import com.wcq.reggie.common.BaseContext;
import com.wcq.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "LoginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();//专门用来进行路径匹配 支持通配符
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        //获取本次请求URI
        String requestURI = request.getRequestURI();
        log.info("拦截到请求:{}",requestURI);
        //不用处理的URL
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
//                "/employee/page",
                "/backend/**",
                "/front/**",
                "/common/download",
                "/user/sendMsg",
                "/user/login"
        };
        //判断本次请求是否需要处理
        boolean check = check(urls, requestURI);
        if(check){
            log.info("本次请求{}不需要处理",requestURI);
            filterChain.doFilter(request,response);//true 放行
            return;
        }
        //4.1判断登录状态 如果已登录 则直接放行
        if(request.getSession().getAttribute("employee") != null){
            log.info("用户已登录，用户id为{}",request.getSession().getAttribute("employee"));

            Long empId=(Long)request.getSession().getAttribute("employee");

//            long id = Thread.currentThread().getId();
//            log.info("线程id为:{}",id);

            BaseContext.setCurrentId(empId);
            filterChain.doFilter(request,response);// 放行
            return;
        }

        if(request.getSession().getAttribute("user") != null){
            log.info("用户已登录，用户id为{}",request.getSession().getAttribute("user"));

            Long userId=(Long)request.getSession().getAttribute("user");

//            long id = Thread.currentThread().getId();
//            log.info("线程id为:{}",id);

            BaseContext.setCurrentId(userId);
            filterChain.doFilter(request,response);// 放行
            return;
        }
        //如果未登录 则返回登录结果 通过输出流方式向客户端页面响应数据
        log.info("用户未登录");
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;


    }

    //路径匹配 检查本次请求是否放行
    public boolean check(String[] urls,String requestURI){
        for (String url : urls) {
           boolean match= PATH_MATCHER.match(url,requestURI);
           if (match){
               return true;//匹配成功
           }
        }
        return false;//没有匹配成功
    }
}
