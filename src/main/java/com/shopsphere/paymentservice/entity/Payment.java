package com.shopsphere.paymentservice.entity;

import com.shopsphere.paymentservice.enums.PaymentMethod;
import com.shopsphere.paymentservice.enums.PaymentStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long orderId;

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal amount;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  @Builder.Default
  private PaymentStatus status = PaymentStatus.PENDING;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  @Builder.Default
  private PaymentMethod method = PaymentMethod.COD;

  @Column(name = "payment_date", nullable = false)
  @Builder.Default
  private LocalDateTime paymentDate = LocalDateTime.now();
}
