package com.example.capstonebackendv2.service.impl;

import com.example.capstonebackendv2.dto.InventoryOption;
import com.example.capstonebackendv2.entity.GoodsReceiptItem;
import com.example.capstonebackendv2.entity.GoodsReceiptReport;
import com.example.capstonebackendv2.repository.GoodsReceiptItemRepository;
import com.example.capstonebackendv2.repository.GoodsReceiptReportRepository;
import com.example.capstonebackendv2.service.Generate;
import com.example.capstonebackendv2.service.GoodsReceiptService;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GoodsReceiptServiceImpl implements GoodsReceiptService, Generate {

    private final GoodsReceiptReportRepository reportRepository;
    private final GoodsReceiptItemRepository itemRepository;

    public GoodsReceiptServiceImpl(GoodsReceiptReportRepository reportRepository, GoodsReceiptItemRepository itemRepository) {
        this.reportRepository = reportRepository;
        this.itemRepository = itemRepository;
    }

    public String generateId(String id) {
        if(!reportRepository.existsById(id)) return id;
        String prefix = "IP";
        while (reportRepository.existsById(id)) id = generate(prefix);
        return id;
    }

    @Override @Transactional
    public void saveReportAndItems(GoodsReceiptReport report, List<GoodsReceiptItem> items) {
        reportRepository.save(report);
        itemRepository.saveAll(items);
    }

    @Override
    public List<GoodsReceiptReport> findAllReports(@NotNull InventoryOption option) {
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
    public List<GoodsReceiptItem> findAllItems(String id) {
        return itemRepository.findAllByUniqueId(id);
    }

    @Override
    public GoodsReceiptReport findReportById(String id) {
        return reportRepository.findById(id).orElse(null);
    }

    @Override
    public void invalidate(String id) {
        reportRepository.setInactive(id);
    }

    @Override
    public List<GoodsReceiptReport> findAllValidReportByEnd(String end) {
        return reportRepository.findAllByIsValidAndTimestampLessThanEqualOrderByTimestampDesc(true, end);
    }

    @Override
    public void archive(String id) {
        reportRepository.archive(id);
    }
}
