package com.vikas.TrackerAPI.security;

import com.vikas.TrackerAPI.util.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    String jwtToken = null;
    String username = null;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       String requestTokenheader= request.getHeader("Authorization");
       if(requestTokenheader!=null && requestTokenheader.startsWith("Bearer ")) {
          jwtToken = requestTokenheader.substring(7);
           try {
               username= jwtTokenUtil.getUsernameFromToken(jwtToken);
           }catch (IllegalArgumentException e) {
                throw new RuntimeException("unable to get JWT Token");
           }catch (ExpiredJwtException e){
               throw new RuntimeException("JWT Token expired");
           }
       }

       //once get the Token validate it
        if(username==null && SecurityContextHolder.getContext().getAuthentication()==null) {
           UserDetails user= customUserDetailsService.loadUserByUsername(username);
           if(jwtTokenUtil.validateToken(jwtToken,user)){
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
           }
        }
        filterChain.doFilter(request, response);
    }
}
