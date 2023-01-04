package ru.job4j.cinema.filter;

import org.springframework.stereotype.Component;
import ru.job4j.cinema.model.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthFilter implements Filter {
    private boolean isImage(String uri) {
        int res = 0;
        try {
            int index = uri.lastIndexOf("images/images");
            res = (index >= 0 &&  uri.endsWith(".jpg"))
                ? Integer.valueOf(uri.substring(index + 13, uri.length() - 4)) : 0;

        } catch (Exception e) {
            res = 0;
        }
        return  res > 0;
    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        if (uri.endsWith("formLogin")
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
        User user = (User) req.getSession().getAttribute("user");
        if (user == null || user.getUsername() == "Гость") {
            res.sendRedirect(req.getContextPath() + "/formLogin");
            return;
        }
        chain.doFilter(req, res);
    }
}
