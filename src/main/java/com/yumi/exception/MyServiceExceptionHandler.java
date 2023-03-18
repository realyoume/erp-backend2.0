package com.yumi.exception;


import com.yumi.controller.Response;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = {"com.yumi.controller"})
@ResponseBody
public class MyServiceExceptionHandler {

    @ExceptionHandler(MyServiceException.class)
    private Response handleMyServiceException(MyServiceException e) {
        return new Response(e.getCode(), e.getMessage());
    }

}
