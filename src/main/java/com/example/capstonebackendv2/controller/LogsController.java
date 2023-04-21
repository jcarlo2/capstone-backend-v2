package com.example.capstonebackendv2.controller;

import com.example.capstonebackendv2.dto.LogDTO;
import com.example.capstonebackendv2.facade.LogsFacade;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/logs")
public class LogsController {
    private final LogsFacade facade;

    public LogsController(LogsFacade facade) {
        this.facade = facade;
    }

    @GetMapping("/show")
    public List<LogDTO> show() {
        return facade.show();
    }

    @PostMapping("/submit")
    public void submit(@RequestBody LogDTO dto) {
        facade.submit(dto);
    }
}
