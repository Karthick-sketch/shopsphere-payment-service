package com.shopsphere.paymentservice.kafka.events;

import com.shopsphere.paymentservice.dto.PaymentRequestData;
import com.shopsphere.paymentservice.kafka.KafkaConstants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentRequestEvent extends KafkaEventBase {

  private PaymentRequestData data;

  public PaymentRequestEvent(PaymentRequestData data) {
    super(KafkaConstants.PAYMENT_REQUEST_EVENT_TYPE);
    this.data = data;
  }
}
