package com.shopsphere.paymentservice.service;

import com.shopsphere.paymentservice.dto.*;
import com.shopsphere.paymentservice.entity.Payment;
import com.shopsphere.paymentservice.enums.*;
import com.shopsphere.paymentservice.repository.PaymentRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

  private final PaymentRepository paymentRepository;

  public PaymentResponse create(PaymentRequest request)
    throws InterruptedException {
    Payment payment = toPayment(request);

    // mock 3s payment gateway delay
    if (PaymentMethod.CARD.equals(payment.getPaymentMethod())) {
      Thread.sleep(3000);
      payment.setStatus(PaymentStatus.PAID);
      payment.setPaidAt(LocalDateTime.now());
    }

    return toPaymentResponse(paymentRepository.save(payment));
  }

  private Payment toPayment(PaymentRequest request) {
    return Payment.builder()
      .orderId(request.getOrderId())
      .amount(request.getAmount())
      .paymentMethod(request.getPaymentMethod())
      .initiatedAt(LocalDateTime.now())
      .status(PaymentStatus.PENDING)
      .build();
  }

  private PaymentResponse toPaymentResponse(Payment payment) {
    return PaymentResponse.builder()
      .id(payment.getId())
      .amount(payment.getAmount())
      .status(payment.getStatus())
      .paymentMethod(payment.getPaymentMethod())
      .paidAt(payment.getPaidAt())
      .build();
  }
}
