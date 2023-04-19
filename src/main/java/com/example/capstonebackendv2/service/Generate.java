package com.example.capstonebackendv2.service;

import org.jetbrains.annotations.NotNull;

public interface Generate {
    /**
     * Follow the prefix and suffix to avoid bugs
     * @param prefix -> TR- or NP- or IP-
     * @return TR-0000000000001-A0
     */
    default String generate(String prefix) {
        long id = (long) (Math.random() * 1000000000000L);
        String formatId = String.format("%013d", id);
        return prefix + formatId + "-A0";
    }

    /**
     * @param id -> TR-0000000000001-A1
     * @return TR-0000000000001-A0
     */
    default String reverseId(@NotNull String id) {
        StringBuilder start = new StringBuilder(id.substring(0,17));
        String end = id.substring(17);
        int num = Integer.parseInt(end) - 1;
        num = Math.max(num, 0);
        return start.append(num).toString();
    }

    /**
     * @param id -> TR-0000000000001-A0
     * @return TR-0000000000001-A1
     */
    default String forwardId(@NotNull String id) {
        int num = Character.getNumericValue(id.charAt(id.length() - 1));
        return id.substring(0, id.length()-1) + ++num;
    }
}
