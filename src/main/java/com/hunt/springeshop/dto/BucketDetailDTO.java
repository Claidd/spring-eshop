package com.hunt.springeshop.dto;

import com.hunt.springeshop.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
/*Вью с детальной информацией по выбранной корзине*/
public class BucketDetailDTO {
    private String title;
    private Long productId;
    private BigDecimal price;
    private BigDecimal amount;
    private Double sum;

    /*Вставляем во вью данные из выбранного-переданного продукта*/
    public BucketDetailDTO(Product product) {
        this.title = product.getTitle();
        this.productId = product.getId();
        this.price = product.getPrice();
        this.amount = new BigDecimal(1.0);
        this.sum = Double.valueOf(product.getPrice().toString());
    }
}
