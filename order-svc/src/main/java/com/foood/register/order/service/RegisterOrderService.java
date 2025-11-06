package com.foood.register.order.service;

import com.foood.commons_svc.dto.Order;
import com.foood.register.order.kafka.KafkaProducer;
import com.foood.register.order.repository.OrderRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

@Service
public class RegisterOrderService {

    private final KafkaProducer kafkaProducer;

    private final Dto2EntityTranslator dto2EntityTranslator;
    private final OrderRepository orderRepository;

    public RegisterOrderService(KafkaProducer kafkaProducer, Dto2EntityTranslator dto2EntityTranslator, OrderRepository orderRepository) {
        this.kafkaProducer = kafkaProducer;
        this.dto2EntityTranslator = dto2EntityTranslator;
        this.orderRepository = orderRepository;
    }

    private void publishOrder(com.foood.register.order.model.Order order) {
        kafkaProducer.sendOrderEvent(order);
    }

    private void persistOrder(com.foood.register.order.model.Order order) {
        orderRepository.save(order);
    }

    public void registerOrder(@NotNull Order order) {
        var orderModel = dto2EntityTranslator.translateOrder(order);
        persistOrder(orderModel);
        publishOrder(orderModel);
    }
}
