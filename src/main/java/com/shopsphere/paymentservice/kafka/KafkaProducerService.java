package com.shopsphere.paymentservice.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

  private final String topic;
  private final KafkaTemplate<String, PaymentStatusChangedEvent> kafkaTemplate;

  public KafkaProducerService(
    @Value("${kafka.topic.payment-status}") String topic,
    KafkaTemplate<String, PaymentStatusChangedEvent> kafkaTemplate
  ) {
    this.topic = topic;
    this.kafkaTemplate = kafkaTemplate;
  }

  public void sendPaymentStatusChangedEvent(PaymentStatusChangedEvent event) {
    kafkaTemplate.send(topic, event);
  }
}
