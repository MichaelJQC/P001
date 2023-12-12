package com.crudjsp.crudjsp.model;

import lombok.Data;

@Data
public class District {
    private int districtId;
    private Province province;
    private String districtName;
}
