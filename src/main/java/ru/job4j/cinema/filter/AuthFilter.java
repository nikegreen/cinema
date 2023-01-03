package ru.job4j.cinema.filter;

import org.springframework.stereotype.Component;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthFilter implements Filter {
    private boolean isImage(String uri) {
        int index = uri.lastIndexOf("images/images");
        String i = uri.substring(index + 13, uri.length() - 4);
        int res = Integer.valueOf(i);
        return uri.endsWith(".jpg") && index >= 0 && res > 0;
    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        if (uri.endsWith("loginPage")
                || uri.endsWith("login")
                || uri.endsWith("formLogin")
                || uri.endsWith("registration")
                || uri.endsWith("formRegistration")
                || uri.endsWith("success")
                || uri.endsWith("fail")
                || uri.endsWith("index")
                || uri.endsWith("bootstrap/css/bootstrap.min.css")
                || uri.endsWith("bootstrap/js/bootstrap.bundle.min.js")
                || uri.endsWith("login.css")
                || isImage(uri)) {
            chain.doFilter(req, res);
            return;
        }
        if (req.getSession().getAttribute("user") == null) {
            res.sendRedirect(req.getContextPath() + "/loginPage");
            return;
        }
        chain.doFilter(req, res);
    }
}
