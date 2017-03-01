package com.bank.web.filters;

import com.bank.web.model.entity.Users;
import com.bank.web.model.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserInfoInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        //UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
        //if (userInfo == null) {
            SecurityContext securityContext = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
            if (securityContext != null) {
                UserDetails principal = (UserDetails)(securityContext.getAuthentication().getPrincipal());
                String name = principal.getUsername();
                Users user = usersRepository.findByLogin(name);
                if (user != null) {
                    //userInfo = new UserInfo(user, principal.getAuthorities());
                    session.setAttribute("userInfo", user);
                }
            }
        //}
        return super.preHandle(request, response, handler);
    }
}
