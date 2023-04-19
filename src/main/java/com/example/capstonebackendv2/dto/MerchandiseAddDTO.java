package com.example.capstonebackendv2.dto;

import com.example.capstonebackendv2.helper.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter @ToString
@AllArgsConstructor
public class MerchandiseAddDTO {
    private MerchandiseDTO merchandise;
    private List<Category> categoryList;
}
