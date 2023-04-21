package com.example.capstonebackendv2.dto;

import com.example.capstonebackendv2.entity.MerchandiseDiscountHistory;
import com.example.capstonebackendv2.entity.MerchandiseHistory;

import java.util.List;

public record MerchandiseHistoriesDTO(List<MerchandiseHistory> historyList,
                                      List<MerchandiseDiscountHistory> discountHistoryList) {
}
