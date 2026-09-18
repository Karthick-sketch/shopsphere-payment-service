package com.shopsphere.paymentservice.dto;

import com.shopsphere.paymentservice.enums.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {

  private Long orderId;
  private BigDecimal amount;
  private PaymentStatus status;
  private PaymentMethod method;
  private LocalDateTime initiatedAt;
}
