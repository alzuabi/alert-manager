package com.alertmanager.demo.Repository;

import com.alertmanager.demo.Domin.Alert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;


@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {
//    Page<Alert> findByAction_Id(Long postId, Pageable pageable);
//    Optional<Alert> findByIdAndAction_Id(Long id, Long ActionId);
Page<Alert> findAll(Pageable pageable);
    Optional<List<Alert>> findAllByOnBetween(Timestamp after, Timestamp before);
    OnTimestamp findFirstByOrderByOn();
    Optional<List<Alert>> findAllByOnBetweenAndAlertTypeEquals(Timestamp after, Timestamp before, String alertType);
}
