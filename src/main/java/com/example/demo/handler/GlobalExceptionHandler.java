package com.example.demo.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

// @ControllerAdvice // 集中處裡所有Conteoller
public class GlobalExceptionHandler {

    // @ResponseBody
    // @ExceptionHandler(Exception.class)
    // public String handlerExceptionHandler(Exception e) {

    // return "hello" + e.getMessage();
    // }

}
