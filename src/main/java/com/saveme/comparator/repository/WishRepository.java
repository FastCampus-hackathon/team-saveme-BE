package com.saveme.comparator.repository;

import com.saveme.comparator.domain.User;
import com.saveme.comparator.domain.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WishRepository extends JpaRepository<Wish, Long> {

    boolean existsByUserAndRecruition_RecruitmentId(User user, Long recruitmentId);

    void deleteByUserAndRecruition_RecruitmentId(User user, Long recruitmentId);

    @Query("select w from Wish w join fetch w.recruition where w.user.userId=:userId")
    List<Wish> findAllByUser_UserId(@Param("userId")Long userId);
}
