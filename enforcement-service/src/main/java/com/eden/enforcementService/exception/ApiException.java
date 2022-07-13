package com.eden.enforcementService.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ApiException {
    private String message;
    private String ar_message;
    private HttpStatus httpStatus;
    private String zonedDateTime;

//    public Map<String,Object> toJson(){
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("message",this.message) ;
//        map.put("ar_message",this.ar_message) ;
//        map.put("httpStatus",this.httpStatus.value()) ;
//        map.put("updatedAt",this.zonedDateTime.toString()) ;
//        System.out.println(map);
//    return map;
//    }
}
