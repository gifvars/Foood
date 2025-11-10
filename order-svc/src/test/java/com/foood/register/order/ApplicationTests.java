package com.foood.register.order;

import com.foood.commons_svc.dto.CartItem;
import com.foood.commons_svc.enums.OrderStatus;
import com.foood.register.order.service.Dto2EntityTranslator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.Assert;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;

@SpringBootTest
// @EnableWebMvc
// @AutoConfiguration
class ApplicationTests {

    @Autowired
    Dto2EntityTranslator dto2EntityTranslator;

	@Test
	void contextLoads() {
	}

    @Test
    public void testCalculate(){

        Assert.assertTrue(true);

    }

    @Test
    public void testTransform(){

        var orderRec = new com.foood.commons_svc.dto.Order(
                666,
                Collections.singletonList(new CartItem(222, 1, BigDecimal.valueOf(125))),
                        BigDecimal.valueOf(230),
                        BigDecimal.valueOf(999),
                        BigDecimal.valueOf(10),
                OrderStatus.ACCEPTED_BY_RESTAURANT,
                777,
                888,
                LocalDateTime.parse("2025-11-06T14:32:00"),
                        LocalDateTime.parse("2025-11-06T14:32:00"));

        var order = dto2EntityTranslator.translateOrder(orderRec);
        Assert.assertTrue(order.getOrderItems().size() == 1);
        Assert.assertTrue(order.getDriverId() == 888);

    }
}
