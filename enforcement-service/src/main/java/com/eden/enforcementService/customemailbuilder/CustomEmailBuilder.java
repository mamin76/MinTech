package com.eden.enforcementService.customemailbuilder;

public interface CustomEmailBuilder<T> {

    String buildBody(T t);
}
