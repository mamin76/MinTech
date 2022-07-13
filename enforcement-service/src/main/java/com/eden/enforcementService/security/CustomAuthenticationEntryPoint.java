package com.eden.enforcementService.security;

import com.eden.enforcementService.exception.ApiException;
import com.eden.enforcementService.payload.ApiResponse;
import com.eden.enforcementService.util.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    public @Override void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException , ServletException {
        ApiException apiException = new ApiException(
                Constants.ErrorKeys.ERROR_UNAUTHORIZED,
                Constants.ErrorKeys.ERROR_UNAUTHORIZED,
                HttpStatus.UNAUTHORIZED,
                ZonedDateTime.now(ZoneId.of("Z")).toString()
        );


        ObjectMapper mapper = new ObjectMapper();
        ApiResponse apiResponse =  ApiResponse.builder().error(apiException).success(false)
                .code(HttpStatus.UNAUTHORIZED.value()).build();
        String message = mapper.writeValueAsString(apiResponse);

//        String message = apiException.toJson().toString().replaceAll("=",":");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(APPLICATION_JSON_VALUE);
        response.getWriter().write(message);
    }
}
