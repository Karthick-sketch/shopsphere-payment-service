package com.shopsphere.paymentservice.dto;

import com.shopsphere.paymentservice.enums.PaymentMethod;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {

  private Long orderId;
  private BigDecimal amount;
  private PaymentMethod paymentMethod;
  private String cardName;
  private String cardNumber;
  private String expiryMonth;
  private String expiryYear;
  private String cvv;
}
