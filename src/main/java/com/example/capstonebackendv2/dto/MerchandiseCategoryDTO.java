package com.example.capstonebackendv2.dto;

import com.example.capstonebackendv2.helper.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString
@AllArgsConstructor
public class MerchandiseCategoryDTO {
    private String id;
    private Category category;
}
