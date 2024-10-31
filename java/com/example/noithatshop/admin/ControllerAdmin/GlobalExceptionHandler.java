package com.example.noithatshop.admin.ControllerAdmin;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice //định nghĩa là xửu lí ngoại lệ
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)// chỉ đinh xử lís toàn bộ ngoại lệ
    public String handleException(RuntimeException ex, Model model) {

        model.addAttribute("errorMessage", ex.getMessage());

        return "admin/error";
    }
}
