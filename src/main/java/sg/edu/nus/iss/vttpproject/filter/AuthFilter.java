package sg.edu.nus.iss.vttpproject.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        System.out.printf(">>>> url: %s\n", req.getRequestURI().toString());

        HttpSession sess = req.getSession();
        String username = (String) sess.getAttribute("username");
        System.out.println("Username: " + username);
        if ((null == username) || (username.trim().length() <= 0)) {
            System.out.println("Redirecting to index");
            resp.sendRedirect("/");
            return;
        }

        // Forwards the request
        System.out.println("Forwarding...");
        chain.doFilter(req, resp);
    }
}
