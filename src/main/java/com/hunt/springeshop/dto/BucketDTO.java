package com.hunt.springeshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BucketDTO {
    private int amountProducts;
    private Double sum;
    private List<BucketDetailDTO> bucketDetails = new ArrayList<>();


    /*Метод, который будет агрегировать в себе кол-во определенного товара, чтобы мы выводили сумму по принципу сколько
    * товаров мы ввели(в том числе одинковых, чтобы увидеть общую сумму)*/
    public void aggregate(){
        this.amountProducts = bucketDetails.size();
        this.sum = bucketDetails.stream()
                .map(BucketDetailDTO::getSum)
                .mapToDouble(Double::doubleValue)
                .sum();
    }
}
