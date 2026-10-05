package com.shopsphere.paymentservice.kafka.events;

import com.shopsphere.paymentservice.dto.PaymentResponseData;
import com.shopsphere.paymentservice.kafka.KafkaConstants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentResponseEvent extends KafkaEventBase {

  private PaymentResponseData data;

  public PaymentResponseEvent(PaymentResponseData data) {
    super(KafkaConstants.PAYMENT_RESPONSE_EVENT_TYPE);
    this.data = data;
  }
}
