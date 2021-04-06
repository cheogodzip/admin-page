package com.example.study.model.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderDetailStatus {

    WAITING(0, "배송준비중", "배송 준비중"),
    SHIPPING(1, "배송중", "배송중"),
    COMPLETE(1, "배송완료", "배송 완료");

    private Integer id;
    private String title;
    private String description;
}
