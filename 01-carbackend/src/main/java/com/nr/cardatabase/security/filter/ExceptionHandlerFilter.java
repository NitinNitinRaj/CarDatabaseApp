package com.nr.cardatabase.security.filter;

import java.io.IOException;
import javax.persistence.EntityNotFoundException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

public class ExceptionHandlerFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain
  ) throws ServletException, IOException {
    try {
      filterChain.doFilter(request, response);
    } catch (EntityNotFoundException e) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      response.getWriter().write("User Name does not exist");
      response.getWriter().flush();
    } catch (Exception e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      response.getWriter().write("BAD REQUEST");
      response.getWriter().flush();
    }
  }
}
