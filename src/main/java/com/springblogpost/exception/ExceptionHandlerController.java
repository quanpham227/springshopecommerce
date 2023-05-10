package com.springblogpost.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(SQLException.class)
    public String handleSQLException(Model model, SQLException ex) {
        model.addAttribute("errorMsg", "A database error occurred: " + ex.getMessage());
        return "admin/errors/error";
    }

    @ExceptionHandler(DataAccessException.class)
    public String handleDataAccessException(Model model, DataAccessException ex) {
        model.addAttribute("errorMsg", "A data access error occurred: " + ex.getMessage());
        return "admin/errors/error";
    }
    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        ModelAndView modelAndView = new ModelAndView("admin/errors/404");
        modelAndView.addObject("errorMsg", ex.getMessage());
        return modelAndView;
    }

}
