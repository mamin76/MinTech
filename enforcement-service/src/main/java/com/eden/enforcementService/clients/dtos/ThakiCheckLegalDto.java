package com.eden.enforcementService.clients.dtos;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ThakiCheckLegalDto {

   private boolean success;
   private  String message;
}
