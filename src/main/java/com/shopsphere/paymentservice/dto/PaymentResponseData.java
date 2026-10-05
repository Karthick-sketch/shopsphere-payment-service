package com.shopsphere.paymentservice.dto;

import com.shopsphere.paymentservice.enums.PaymentStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseData {

  private Long userId;
  private Long orderId;
  private Long paymentId;
  private PaymentStatus status;
  private LocalDateTime paidAt;
}
