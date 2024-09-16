package com.dariom.ichirowalks.repository.jpa;

import com.dariom.ichirowalks.repository.jpa.entity.IchiroWalkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IchiroWalkJpaRepository extends JpaRepository<IchiroWalkEntity, Long> {

    List<IchiroWalkEntity> findAllByOrderByLeftAtDesc();

    List<IchiroWalkEntity> findAllByLeftAtBetweenOrderByLeftAtDesc(@Param("startOfDay") LocalDateTime startOfDay,
                                                                   @Param("endOfDay") LocalDateTime endOfDay);

    Optional<IchiroWalkEntity> findFirstByLeftAtBetweenAndBackAtNullOrderByLeftAtAsc(@Param("startOfDay") LocalDateTime startOfDay,
                                                                                     @Param("endOfDay") LocalDateTime endOfDay);

    Optional<IchiroWalkEntity> findFirstByLeftAtBetweenAndBackAtNullOrderByLeftAtDesc(@Param("startOfDay") LocalDateTime startOfDay,
                                                                                      @Param("endOfDay") LocalDateTime endOfDay);

}
