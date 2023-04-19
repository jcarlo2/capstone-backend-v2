package com.example.capstonebackendv2.enums;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@NoArgsConstructor
public enum Category {
    ALL,
    BAKING,
    BEVERAGES,
    BREAD_AND_BAKERY,
    CANNED_JARRED_GOODS,
    CONDIMENTS_AND_SPICES,
    DELI,
    FROZEN_FOODS,
    HOUSEHOLD_SUPPLIES,
    PASTA_RICE,
    PERSONAL_HEALTH_CARE,
    PRODUCE,
    SAUCES_AND_OIL,
    SNACKS,
    OTHER;

    public static Category fromString(@NotNull String str) {
        try {
            return Category.valueOf(str.toUpperCase());
        } catch (IllegalArgumentException e) {
            return Category.ALL;
        }
    }
}


