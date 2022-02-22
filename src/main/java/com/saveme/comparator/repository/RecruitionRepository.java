package com.saveme.comparator.repository;

import com.saveme.comparator.domain.Recruition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruitionRepository extends JpaRepository<Recruition,Long> {

    boolean existsByRecruitmentId(Long recruitmentId);
}
