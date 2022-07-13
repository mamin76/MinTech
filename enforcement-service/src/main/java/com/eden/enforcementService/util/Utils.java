package com.eden.enforcementService.util;

import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

public class Utils {


    public static HashMap<String, Object> getBasicUserInfoMap() {
        return (HashMap<String, Object>) SecurityContextHolder.getContext().getAuthentication().getDetails();
    }


    public static Object getHeaderFromRequest(String headerName) {
        HttpServletRequest request = (HttpServletRequest) getBasicUserInfoMap().get("request");
        return request.getHeader(headerName);

    }

    public static boolean isApiSecured(HttpServletRequest request) {
        final List<String> apiEndpoints = List.of( "/enforcements/actuator");

        Predicate<HttpServletRequest> isApiSecured = r -> apiEndpoints.stream()
                .noneMatch(uri -> r.getRequestURI().contains(uri));
        return isApiSecured.test(request);
    }
}
