package com.foood.register.order.kafka;

import com.foood.commons.dto.Order;
import order.events.CartItem;
import order.events.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
public class KafkaProducer {

    private static final Logger log = LoggerFactory.getLogger(
            KafkaProducer.class);
    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderEvent(Order order) {
        var cartItems = order.orderItems().stream().map(cartitem -> CartItem.newBuilder()
                .setProductId(cartitem.productId())
                .setQuantity(cartitem.quantity())
                .setTotalPrice(cartitem.totalPrice().longValue())
                .build()).toList();
        var event = OrderEvent.newBuilder()
                .addAllOrderItems(cartItems)
                .build();

        try {
            kafkaTemplate.send("register.order", event.toByteArray());
            log.info("Success sending event to Kafka: {}", event);
        } catch (Exception e) {
            log.error("Error sending order create event: {}", event);
        }
    }
}