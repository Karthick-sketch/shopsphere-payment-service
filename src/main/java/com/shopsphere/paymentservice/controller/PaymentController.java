package com.shopsphere.paymentservice.controller;

import com.shopsphere.paymentservice.dto.*;
import com.shopsphere.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

  private final PaymentService paymentService;

  @PostMapping("/cod")
  public ResponseEntity<PaymentResponse> processCodPayment(
    @RequestBody PaymentRequest request,
    @AuthenticationPrincipal Jwt jwt
  ) {
    Long authUserId = Long.valueOf(jwt.getClaim("sub"));
    return ResponseEntity.status(HttpStatus.CREATED).body(
      paymentService.processCodPayment(authUserId, request)
    );
  }
}
