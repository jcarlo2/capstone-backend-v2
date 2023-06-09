package com.example.capstonebackendv2.controller;

import com.example.capstonebackendv2.dto.TransactionDTO;
import com.example.capstonebackendv2.dto.TransactionItemDTO;
import com.example.capstonebackendv2.dto.TransactionRefundDTO;
import com.example.capstonebackendv2.dto.TransactionReportDTO;
import com.example.capstonebackendv2.entity.TransactionReport;
import com.example.capstonebackendv2.facade.TransactionFacade;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
    private final TransactionFacade facade;

    public TransactionController(TransactionFacade facade) {
        this.facade = facade;
    }

    @GetMapping("/generate")
    public String generate(@RequestParam String id) {
        return facade.generate(id);
    }

    @GetMapping("/forward-id")
    public String forwardId(@RequestParam String id) {
        return facade.forwardId(id);
    }

    @GetMapping("/find-items")
    public List<TransactionItemDTO> findItems(@RequestParam String id) {
        return facade.findItems(id);
    }

    @GetMapping("/find-report-id")
    public TransactionReportDTO findReportById(@RequestParam String id) {
        return facade.findReportById(id);
    }

    @GetMapping("/find-report")
    public List<TransactionReport> findAllReportByCriteria(
            @RequestParam boolean isValid,
            @RequestParam boolean isArchived,
            @RequestParam String search,
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam int size
    ) {
        return facade.findAllReportByCriteria(isValid,isArchived,search,start,end,size);
    }

    @PostMapping("/save")
    public List<String> save(@RequestBody TransactionDTO dto) {
        return facade.save(dto);
    }

    @PostMapping("/return-refund")
    public void returnRefund(@RequestBody TransactionRefundDTO dto) {
        facade.returnRefund(dto);
    }

    @GetMapping("count-report")
    public int countActiveReportInBetween(String start, String option) {
        return facade.countActiveReportInBetween(start,option);
    }

    @GetMapping("get-annual-break")
    public List<BigDecimal> getAnnualBreakDown(@RequestParam String start) {
        return facade.getAnnualBreakDown(start);
    }
}
