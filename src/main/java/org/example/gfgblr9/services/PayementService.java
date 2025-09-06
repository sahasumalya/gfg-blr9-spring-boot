package org.example.gfgblr9.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PayementService implements PaymentInterface {
    public PayementService() {
        log.info("Payment getting initiated");
    }
    public void payement() {

    }
}
