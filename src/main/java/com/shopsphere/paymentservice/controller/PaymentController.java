package com.shopsphere.paymentservice.controller;

import com.shopsphere.paymentservice.dto.PaymentRequest;
import com.shopsphere.paymentservice.entity.Payment;
import com.shopsphere.paymentservice.enums.PaymentStatus;
import com.shopsphere.paymentservice.service.PaymentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

  private final PaymentService paymentService;

  @GetMapping
  public ResponseEntity<List<Payment>> getAll() {
    return ResponseEntity.ok(paymentService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Payment> getById(@PathVariable Long id) {
    return ResponseEntity.ok(paymentService.findById(id));
  }

  @GetMapping("/order/{orderId}")
  public ResponseEntity<Payment> getByOrderId(@PathVariable Long orderId) {
    return ResponseEntity.ok(paymentService.findByOrderId(orderId));
  }

  @GetMapping("/status/{status}")
  public ResponseEntity<List<Payment>> getByStatus(
    @PathVariable PaymentStatus status
  ) {
    return ResponseEntity.ok(paymentService.findByStatus(status));
  }

  @PostMapping
  public ResponseEntity<Payment> create(@RequestBody PaymentRequest request)
    throws InterruptedException {
    return ResponseEntity.status(HttpStatus.CREATED).body(
      paymentService.create(request)
    );
  }

  @PatchMapping("/{id}/status")
  public ResponseEntity<Payment> updateStatus(
    @PathVariable Long id,
    @RequestParam PaymentStatus status
  ) {
    return ResponseEntity.ok(paymentService.updateStatus(id, status));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    paymentService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
