package com.shopsphere.paymentservice.service;

import com.shopsphere.paymentservice.dto.*;
import com.shopsphere.paymentservice.entity.Payment;
import com.shopsphere.paymentservice.enums.*;
import com.shopsphere.paymentservice.kafka.events.PaymentResponseEvent;
import com.shopsphere.paymentservice.kafka.producer.KafkaProducerService;
import com.shopsphere.paymentservice.repository.PaymentRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

  private final PaymentRepository paymentRepository;

  private final KafkaProducerService kafkaProducerService;

  public PaymentResponse processCardPayment(
    Long authUserId,
    PaymentRequest request
  ) {
    if (!PaymentMethod.CARD.equals(request.getPaymentMethod())) {
      throw new RuntimeException(
        "Invalid payment method : " + request.getPaymentMethod()
      );
    }

    Payment payment = paymentRepository.save(toCardPayment(request));
    try {
      // mock 3s payment gateway delay
      Thread.sleep(3000);
      // set payment status to paid and save it to the database
      payment.setStatus(PaymentStatus.SUCCESS);
      payment.setPaidAt(LocalDateTime.now());
      payment = paymentRepository.save(payment);

      // publish payment status changed event to kafka
      kafkaProducerService.sendPaymentResponseEvent(
        mapPaymentResponseEventSuccess(authUserId, payment)
      );

      return toPaymentResponse(payment);
    } catch (Exception e) {
      kafkaProducerService.sendPaymentResponseEvent(
        mapPaymentResponseEventFailed(authUserId, payment)
      );
      throw new RuntimeException("Failed to process payment");
    }
  }

  public PaymentResponse processCodPayment(
    Long authUserId,
    PaymentRequest request
  ) {
    if (!PaymentMethod.COD.equals(request.getPaymentMethod())) {
      throw new RuntimeException(
        "Invalid payment method : " + request.getPaymentMethod()
      );
    }

    Payment payment = paymentRepository.save(toCodPayment(request));

    // publish payment status changed event to kafka
    kafkaProducerService.sendPaymentResponseEvent(
      mapPaymentResponseEventSuccess(authUserId, payment)
    );

    return toPaymentResponse(payment);
  }

  public void handlePaymentRequestEvent(PaymentRequestData data) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException(
      "Unimplemented method 'handlePaymentRequestEvent'"
    );
  }

  private PaymentResponseEvent mapPaymentResponseEventSuccess(
    Long authUserId,
    Payment payment
  ) {
    return new PaymentResponseEvent(
      new PaymentResponseData(
        authUserId,
        payment.getOrderId(),
        payment.getId(),
        PaymentStatus.SUCCESS,
        payment.getPaidAt()
      )
    );
  }

  private PaymentResponseEvent mapPaymentResponseEventFailed(
    Long authUserId,
    Payment payment
  ) {
    return new PaymentResponseEvent(
      new PaymentResponseData(
        authUserId,
        payment.getOrderId(),
        payment.getId(),
        PaymentStatus.FAILED,
        null
      )
    );
  }

  private Payment toCardPayment(PaymentRequest request) {
    return Payment.builder()
      .orderId(request.getOrderId())
      .amount(request.getAmount())
      .paymentMethod(request.getPaymentMethod())
      .initiatedAt(LocalDateTime.now())
      .status(PaymentStatus.PENDING)
      .build();
  }

  private Payment toCodPayment(PaymentRequest request) {
    return Payment.builder()
      .orderId(request.getOrderId())
      .amount(request.getAmount())
      .paymentMethod(request.getPaymentMethod())
      .status(PaymentStatus.SUCCESS)
      .initiatedAt(LocalDateTime.now())
      .paidAt(LocalDateTime.now())
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
