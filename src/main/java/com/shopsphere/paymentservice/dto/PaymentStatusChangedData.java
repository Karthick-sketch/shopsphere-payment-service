package com.shopsphere.paymentservice.dto;

import com.shopsphere.paymentservice.enums.PaymentMethod;
import com.shopsphere.paymentservice.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentStatusChangedData {

  private Long userId;
  private Long orderId;
  private Long paymentId;
  private PaymentStatus status;
  private PaymentMethod paymentMethod;
}
