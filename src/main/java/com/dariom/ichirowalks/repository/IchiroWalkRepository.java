package com.dariom.ichirowalks.repository;

import com.dariom.ichirowalks.core.domain.IchiroWalk;
import com.dariom.ichirowalks.repository.jpa.IchiroWalkJpaRepository;
import com.dariom.ichirowalks.repository.jpa.entity.IchiroWalkEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class IchiroWalkRepository {

    private final IchiroWalkJpaRepository jpaRepository;

    public List<IchiroWalk> findAll() {
        return jpaRepository.findAllByOrderByLeftAtDesc().stream()
                .map(this::toDomain)
                .toList();
    }

    public void save(IchiroWalkEntity entity) {
        jpaRepository.save(entity);
    }

    private IchiroWalk toDomain(IchiroWalkEntity entity) {
        return IchiroWalk.builder()
                .id(entity.getId())
                .leftAt(entity.getLeftAt())
                .backAt(entity.getBackAt())
                .build();
    }
}
