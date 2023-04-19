package com.example.capstonebackendv2.controller;

import com.example.capstonebackendv2.facade.DateFacade;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/date")
public class DateController {
    private final DateFacade facade;

    public DateController(DateFacade facade) {
        this.facade = facade;
    }

    @GetMapping("/get-date")
    public String getDate() {
        return facade.getDate();
    }

    @GetMapping("/get-time")
    public String getTime() {
        return facade.getTime();
    }

    @GetMapping("/get-date-asOf")
    public String getDateAsOf(@RequestParam String start, @RequestParam String option) {
        return facade.getDateAsOf(start,option);
    }
}
