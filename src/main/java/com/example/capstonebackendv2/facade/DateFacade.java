package com.capstone.backend.facade;

import com.capstone.backend.service.DateService;
import org.springframework.stereotype.Service;

@Service
public class DateFacade {
    private final DateService service;

    public DateFacade(DateService service) {
        this.service = service;
    }
    public String getDate() {
        return service.getDate();
    }

    public String getTime() {
        return service.getTime();
    }

    public String getDateAsOf(String option, String start) {
        return service.getDateAsOf(start,option);
    }
}
