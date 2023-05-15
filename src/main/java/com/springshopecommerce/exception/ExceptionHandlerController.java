package com.springshopecommerce.exception;

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
        model.addAttribute("errorMessage", "A database error occurred: " + ex.getMessage());
        return "admin/errors/error";
    }

    @ExceptionHandler(CloudinaryException.class)
    public ModelAndView handleCloudinaryException(CloudinaryException ex) {
        ModelAndView mav = new ModelAndView("/admin/errors/uploadError");
        mav.addObject("errorMessage", "failed to load to Cloudinary the image file: " + ex.getMessage());
        mav.addObject("backUrl", "javascript:history.go(-1)");
        return mav;
    }

    @ExceptionHandler(DataAccessException.class)
    public String handleDataAccessException(Model model, DataAccessException ex) {
        model.addAttribute("errorMsg", "A data access error occurred: " + ex.getMessage());
        return "admin/errors/error";
    }
    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        ModelAndView modelAndView = new ModelAndView("admin/errors/error");
        modelAndView.addObject("errorMessage", ex.getMessage());
        return modelAndView;
    }

}
