package com.shopsphere.paymentservice.controller;

import com.shopsphere.paymentservice.dto.*;
import com.shopsphere.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

  private final PaymentService paymentService;

  @PostMapping
  public ResponseEntity<PaymentResponse> processPayment(
    @RequestBody PaymentRequest request
  ) throws InterruptedException {
    return ResponseEntity.status(HttpStatus.CREATED).body(
      paymentService.create(request)
    );
  }
}
