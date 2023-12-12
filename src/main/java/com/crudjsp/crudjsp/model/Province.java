package com.crudjsp.crudjsp.model;

import lombok.Data;

@Data
public class Province {
    private int provinceId;
    private Department department;
    private String provinceName;
}
