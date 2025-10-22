package com.foood.register.order.service;

import com.foood.commons.dto.Order;
import com.foood.register.order.kafka.KafkaProducer;
import org.springframework.stereotype.Service;

@Service
public class RegisterOrderService {
    private final KafkaProducer kafkaProducer;

    public RegisterOrderService( KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    public void sendOrderEvent(Order order) {
        kafkaProducer.sendOrderEvent(order);
    }
}
