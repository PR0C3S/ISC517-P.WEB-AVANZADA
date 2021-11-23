package com.pucmm.practica5.configuracion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component //indicando que es un componente y será injectado por defecto.
@Order(1) // indicando el orden.
public class LogRequestFilter implements Filter {

    Logger logger = LoggerFactory.getLogger(LogRequestFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        logger.info("Registro Log del Request  {} : {} : {}", req.getMethod(),req.getRequestURI(), req.getSession().getId());
        Cookie[] cookies = req.getCookies();
        if(cookies!=null) {
            for (Cookie c : cookies) {
                logger.info("Cookie[{}] = {}", c.getName(), c.getValue());
            }
        }
        chain.doFilter(request, response);
        logger.info("Registro log del Response :{}",res.getContentType());
    }

    @Override
    public void destroy() {

    }
}
