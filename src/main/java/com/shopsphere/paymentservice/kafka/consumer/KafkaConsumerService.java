package com.shopsphere.paymentservice.kafka.consumer;

import com.shopsphere.paymentservice.kafka.events.PaymentRequestEvent;
import com.shopsphere.paymentservice.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KafkaConsumerService {

  private final PaymentService paymentService;

  @KafkaListener(
    topics = "${kafka.topic.payment-request}",
    groupId = "${kafka.consumer.group-id}"
  )
  public void handlePaymentRequestEvent(PaymentRequestEvent event) {
    paymentService.handlePaymentRequestEvent(event.getData());
  }
}
