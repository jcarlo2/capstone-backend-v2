package com.example.capstonebackendv2.service.impl;

import com.example.capstonebackendv2.dto.InventoryOption;
import com.example.capstonebackendv2.entity.InventoryLossItem;
import com.example.capstonebackendv2.entity.InventoryLossReport;
import com.example.capstonebackendv2.repository.InventoryLossItemRepository;
import com.example.capstonebackendv2.repository.InventoryLossReportRepository;
import com.example.capstonebackendv2.service.Generate;
import com.example.capstonebackendv2.service.InventoryLossService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InventoryLossServiceImpl implements InventoryLossService, Generate {
    private final InventoryLossReportRepository reportRepository;
    private final InventoryLossItemRepository itemRepository;

    public InventoryLossServiceImpl(InventoryLossReportRepository reportRepository, InventoryLossItemRepository itemRepository) {
        this.reportRepository = reportRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public String generateId(String id) {
        if(!reportRepository.existsById(id)) return id;
        String prefix = "NP";
        while (reportRepository.existsById(id)) id = generate(prefix);
        return id;
    }

    @Override @Transactional
    public void  saveReportAndItems(InventoryLossReport report, List<InventoryLossItem> items) {
        itemRepository.saveAll(items);
        reportRepository.save(report);
    }

    @Override
    public List<InventoryLossReport> findAllReports(InventoryOption option) {
        Pageable pageable = PageRequest.of(0,option.getSize());
        return reportRepository.
                findAllByIsValidAndIsArchivedAndIdContainingIgnoreCaseAndTimestampGreaterThanEqualAndTimestampLessThanEqualAndReasonContainingIgnoreCaseOrderByTimestampDesc(
                        option.getIsValid(),
                        option.getIsArchived(),
                        option.getSearch(),
                        option.getStart() + "T00:00:00",
                        option.getEnd() + "T23:59:59",
                        option.getCategory(),
                        pageable);
    }

    @Override
    public List<InventoryLossItem> findAllItems(String id) {
        return itemRepository.findAllByReportId(id);
    }

    @Override
    public InventoryLossReport findReportById(String id) {
        return reportRepository.findById(id).orElse(null);
    }

    @Override
    public boolean invalidate(String id) {
        reportRepository.invalidate(id);
        reportRepository.archive(id);
        return true;
    }

    @Override
    public List<InventoryLossReport> findAllValidReportByEnd(String end) {
        return reportRepository.findAllByIsValidAndTimestampLessThanEqualOrderByTimestampDesc(true,end);
    }

    @Override
    public void archive(String id) {
        reportRepository.archive(id);
    }
}
