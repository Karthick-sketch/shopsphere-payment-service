package com.shopsphere.paymentservice.repository;

import com.shopsphere.paymentservice.entity.Payment;
import com.shopsphere.paymentservice.enums.PaymentStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
  Optional<Payment> findByOrderId(Long orderId);
  List<Payment> findByStatus(PaymentStatus status);
}
