package com.example.capstonebackendv2.service.impl;

import com.example.capstonebackendv2.entity.Log;
import com.example.capstonebackendv2.repository.LogRepository;
import com.example.capstonebackendv2.service.LogsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogsServiceImpl implements LogsService {
    private final LogRepository logRepository;

    public LogsServiceImpl(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    public List<Log> show() {
        return logRepository.findAllByIsDeletableOrderByTimestampDesc(true);
    }

    @Override
    public void submit(Log log) {
        logRepository.save(log);
    }

    @Override
    public void archive() {
        logRepository.adminArchive();
        logRepository.save(new Log("","100000","Logs Archive","...","",true,false));
    }
}
