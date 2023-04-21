package com.example.capstonebackendv2.service;

import com.example.capstonebackendv2.entity.Log;

import java.util.List;

public interface LogsService {
    List<Log> show();
    void submit(Log logDTOToEntity);
}
