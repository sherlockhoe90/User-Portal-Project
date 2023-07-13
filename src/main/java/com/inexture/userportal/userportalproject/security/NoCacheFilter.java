package com.inexture.userportal.userportalproject.security;

import javax.servlet.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;


@WebFilter(filterName = "NoCacheFilter")

public class NoCacheFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Set the Cache-Control and Pragma headers to control caching behavior
        httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        httpResponse.setHeader("Pragma", "no-cache");

        chain.doFilter(request, response);
    }

}
