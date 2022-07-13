package com.eden.enforcementService.security.Jwt;

import com.eden.enforcementService.util.Constants;
import com.eden.enforcementService.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtility jwtUtility;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        String token = null;
        String userName = null;


        if (!Utils.isApiSecured(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (authorization != null && authorization.startsWith(Constants.TOKEN_TYPE)) {
            token = authorization.substring(7);
            userName = jwtUtility.getUsernameFromToken(token);
        }
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = new User(userName, "", jwtUtility.getAuthorities(token));

            if (jwtUtility.validateToken(token)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                        = new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());

                HashMap<String, Object> basicUserinfo = new HashMap<String, Object>();
                basicUserinfo.put("request", request);
                jwtUtility.setBasicUserInfo(token, basicUserinfo);

                usernamePasswordAuthenticationToken.setDetails(basicUserinfo);

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }

        }
        filterChain.doFilter(request, response);

    }
}
