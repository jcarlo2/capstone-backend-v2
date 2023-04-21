package com.example.capstonebackendv2.facade;

import com.example.capstonebackendv2.dto.LogDTO;
import com.example.capstonebackendv2.helper.Mapper;
import com.example.capstonebackendv2.service.impl.LogsServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogsFacade {
    private final LogsServiceImpl service;
    private final Mapper mapper;

    public LogsFacade(LogsServiceImpl service, Mapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    public List<LogDTO> show() {
        return mapper.logsEntityToDTO(service.show());
    }

    public void submit(LogDTO dto) {
        service.submit(mapper.logDTOToEntity(dto));
    }
}
