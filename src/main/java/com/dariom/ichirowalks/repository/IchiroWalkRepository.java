package com.dariom.ichirowalks.repository;

import com.dariom.ichirowalks.repository.jpa.IchiroWalkJpaRepository;
import com.dariom.ichirowalks.repository.jpa.entity.IchiroWalkEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class IchiroWalkRepository {

    private final IchiroWalkJpaRepository jpaRepository;

    public List<IchiroWalkEntity> findAll() {
        return jpaRepository.findAll();
    }

    public void save(IchiroWalkEntity entity) {
        jpaRepository.save(entity);
    }
}
