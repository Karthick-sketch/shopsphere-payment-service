package com.shopsphere.paymentservice.kafka.producer;

import com.shopsphere.paymentservice.kafka.events.PaymentResponseEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

  private final String topic;
  private final KafkaTemplate<String, PaymentResponseEvent> kafkaTemplate;

  public KafkaProducerService(
    @Value("${kafka.topic.payment-status}") String topic,
    KafkaTemplate<String, PaymentResponseEvent> kafkaTemplate
  ) {
    this.topic = topic;
    this.kafkaTemplate = kafkaTemplate;
  }

  public void sendPaymentResponseEvent(PaymentResponseEvent event) {
    kafkaTemplate.send(topic, event);
  }
}
