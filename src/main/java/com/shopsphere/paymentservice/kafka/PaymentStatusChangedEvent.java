package com.shopsphere.paymentservice.kafka;

import com.shopsphere.paymentservice.dto.PaymentStatusChangedData;
import java.time.Instant;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentStatusChangedEvent {

  private UUID eventId;
  private String eventType;
  private Instant initiatedAt;
  private PaymentStatusChangedData data;

  public PaymentStatusChangedEvent(PaymentStatusChangedData data) {
    this.data = data;
    this.eventId = UUID.randomUUID();
    this.initiatedAt = Instant.now();
    this.eventType = KafkaConstants.PAYMENT_STATUS_CHANGED_EVENT_TYPE;
  }
}
