package com.virtual.infrastucture.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.virtual.infrastucture.entity.RateEntity;

@Repository
public interface RateRepository extends JpaRepository<RateEntity, Long> {
	Optional<RateEntity> findById(Long id);
	Optional<RateEntity> findByStartDateLessThanEqualAndEndDateGreaterThanEqualAndProductIdAndBrandId(LocalDate from, 
			LocalDate to, Long productId, Long brandId);
}
