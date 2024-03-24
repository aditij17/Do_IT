package com.example.ToDoApplication.controller;

import org.springframework.stereotype.Controller;
import com.example.ToDoApplication.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ToDoController {

    @Autowired
    private ToDoService todoService;

    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("todoItems", todoService.getAllToDoItems());
        return modelAndView;
    }

}