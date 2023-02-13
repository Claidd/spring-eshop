package com.hunt.springeshop.service;

import com.hunt.springeshop.domain.Order;

public interface OrderService {

    void saveOrder(Order order);

    Order getOrder(Long id);
}
