package com.shopsphere.paymentservice.service;

import com.shopsphere.paymentservice.dto.PaymentRequest;
import com.shopsphere.paymentservice.entity.Payment;
import com.shopsphere.paymentservice.enums.*;
import com.shopsphere.paymentservice.repository.PaymentRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

  private final PaymentRepository paymentRepository;

  public List<Payment> findAll() {
    return paymentRepository.findAll();
  }

  public Payment findById(Long id) {
    return paymentRepository
      .findById(id)
      .orElseThrow(() ->
        new RuntimeException("Payment not found with id: " + id)
      );
  }

  public Payment findByOrderId(Long orderId) {
    return paymentRepository
      .findByOrderId(orderId)
      .orElseThrow(() ->
        new RuntimeException("Payment not found for order id: " + orderId)
      );
  }

  public List<Payment> findByStatus(PaymentStatus status) {
    return paymentRepository.findByStatus(status);
  }

  public Payment create(PaymentRequest request) throws InterruptedException {
    Payment payment = toPayment(request);

    // mock 3s payment gateway delay
    if (PaymentMethod.CARD.equals(payment.getMethod())) {
      Thread.sleep(3000);
      payment.setStatus(PaymentStatus.PAID);
      payment.setPaidAt(LocalDateTime.now());
    }

    return paymentRepository.save(payment);
  }

  public Payment updateStatus(Long id, PaymentStatus status) {
    Payment existing = findById(id);
    existing.setStatus(status);
    return paymentRepository.save(existing);
  }

  public void delete(Long id) {
    findById(id);
    paymentRepository.deleteById(id);
  }

  private Payment toPayment(PaymentRequest request) {
    return Payment.builder()
      .orderId(request.getOrderId())
      .amount(request.getAmount())
      .status(request.getStatus())
      .method(request.getMethod())
      .initiatedAt(request.getInitiatedAt())
      .build();
  }
}
