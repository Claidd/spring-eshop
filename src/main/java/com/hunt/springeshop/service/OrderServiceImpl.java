package com.hunt.springeshop.service;

import com.hunt.springeshop.config.OrderIntegrationConfig;
import com.hunt.springeshop.dao.OrderRepository;
import com.hunt.springeshop.domain.Order;
import com.hunt.springeshop.dto.OrderIntegrationDto;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final OrderIntegrationConfig integrationConfig;

    public OrderServiceImpl(OrderRepository orderRepository, OrderIntegrationConfig integrationConfig) {
        this.orderRepository = orderRepository;
        this.integrationConfig = integrationConfig;
    }

    /*Сохраняем заказ*/
    @Override
    @Transactional
    public void saveOrder(Order order) {

        Order savedOrder = orderRepository.save(order);
        sendIntegrationNotify(savedOrder);
    }

    private void sendIntegrationNotify(Order order){
        OrderIntegrationDto dto = new OrderIntegrationDto();
        dto.setUsername(order.getUser().getName());
        dto.setAddress(order.getAddress());
        dto.setOrderId(order.getId());

        List<OrderIntegrationDto.OrderDetailsDto> details = order.getDetails().stream()
                .map(OrderIntegrationDto.OrderDetailsDto::new).collect(Collectors.toList());

        Message<OrderIntegrationDto> message = MessageBuilder.withPayload(dto)
                .setHeader("Content-type", "application/json").build();

        integrationConfig.getOrdersChannel().send(message);
    }

    @Override
    public Order getOrder(Long id) {

        return orderRepository.findById(id).orElse(null);
    }
}
