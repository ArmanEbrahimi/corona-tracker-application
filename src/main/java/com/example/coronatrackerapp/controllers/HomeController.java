package com.example.coronatrackerapp.controllers;

import com.example.coronatrackerapp.services.CoronaDatas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired // autowiring my spring service
    CoronaDatas coronaDatas;

    @GetMapping("/") // mapping root url to homeEditor method
    public String homeEditor(Model model){
        //setting an attribute for my model
        model.addAttribute("CoronaDataTrackerService",coronaDatas.getAllStat());
        //setting an attribute for my model
        model.addAttribute("totalCases",coronaDatas.getTotalCases());
        return "home";
    }
}
