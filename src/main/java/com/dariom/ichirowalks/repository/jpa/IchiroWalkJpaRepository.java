package com.dariom.ichirowalks.repository.jpa;

import com.dariom.ichirowalks.repository.jpa.entity.IchiroWalkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IchiroWalkJpaRepository extends JpaRepository<IchiroWalkEntity, Long> {

    List<IchiroWalkEntity> findAllByOrderByLeftAtDesc();
}
