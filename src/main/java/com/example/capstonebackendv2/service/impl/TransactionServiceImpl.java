package com.example.capstonebackendv2.service.impl;

import com.example.capstonebackendv2.dto.ProductReportDTO;
import com.example.capstonebackendv2.entity.TransactionReport;
import com.example.capstonebackendv2.entity.TransactionReportItem;
import com.example.capstonebackendv2.repository.TransactionItemRepository;
import com.example.capstonebackendv2.repository.TransactionReportRepository;
import com.example.capstonebackendv2.service.Generate;
import com.example.capstonebackendv2.service.TransactionService;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService, Generate {
    private final TransactionReportRepository reportRepository;
    private final TransactionItemRepository itemRepository;

    public TransactionServiceImpl(TransactionReportRepository reportRepository, TransactionItemRepository itemRepository) {
        this.reportRepository = reportRepository;
        this.itemRepository = itemRepository;
    }

    public String generateId(String id) {
        if(!reportRepository.existsById(id)) return id;
        String prefix = "TR";
        while (reportRepository.existsById(id)) id = generate(prefix);
        return id;
    }

    @Override
    public void save(List<TransactionReportItem> itemList, TransactionReport transactionReport) {
        reportRepository.save(transactionReport);
        itemRepository.saveAll(itemList);
    }

    @Override @Transactional
    public void invalidate(String id) {
        reportRepository.invalidate(id);
        reportRepository.archive(id);
    }

    @Override
    public int countActiveReportInBetween(String start, String end) {
        return reportRepository.
                findAllByIsValidAndTimestampGreaterThanEqualAndTimestampLessThanEqualOrderByTimestampDesc
                        (true, start,end).size();
    }

    @Override
    public BigDecimal getAnnualBreakDown(@NotNull List<ProductReportDTO> reports) {
        BigDecimal total = BigDecimal.ZERO;
        for (ProductReportDTO report : reports) {
            total = total.add(report.getTotal());
        }
        return total;
    }

    @Override
    public List<TransactionReport> findAllReportByCriteria(Boolean valid, Boolean archived, String search, String start, String end,int size) {
        Pageable pageable = PageRequest.of(0,size);
        return reportRepository.
                findAllByIsValidAndIsArchivedAndIdContainingIgnoreCaseAndTimestampGreaterThanEqualAndTimestampLessThanEqualOrderByTimestampDesc
                        (valid,archived,search,start,end, pageable);
    }

    @Override
    public TransactionReport findReportById(String id) {
        return reportRepository.findById(id).orElseGet(TransactionReport::new);
    }

    @Override
    public List<TransactionReportItem> findItems(String id) {
        return itemRepository.findAllByUniqueId(id);
    }
}
