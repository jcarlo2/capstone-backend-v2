package com.example.capstonebackendv2.facade;

import com.example.capstonebackendv2.service.impl.DateServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DateFacade {
    private final DateServiceImpl service;

    public DateFacade(DateServiceImpl service) {
        this.service = service;
    }

    public String getDate() {
        return service.getDate();
    }

    public String getTime() {
        return service.getTime();
    }

    public String getDateAsOf(String start, String option) {
        return service.getDateAsOf(start,option);
    }

    public List<Integer> getDateAhead() {
        return service.getDateAhead();
    }
}
