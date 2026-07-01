package com.tinasheGomo.EventManagementSystem.repository.counter;

import com.tinasheGomo.EventManagementSystem.entity.counter.CounterEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CounterRepository extends JpaRepository<CounterEntity, String> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<CounterEntity> findById(String id);
}
