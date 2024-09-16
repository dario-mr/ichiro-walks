package com.dariom.ichirowalks.repository;

import com.dariom.ichirowalks.core.domain.IchiroWalk;
import com.dariom.ichirowalks.repository.jpa.IchiroWalkJpaRepository;
import com.dariom.ichirowalks.repository.jpa.entity.IchiroWalkEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.time.LocalTime.MAX;

@Repository
@RequiredArgsConstructor
public class IchiroWalkRepository {

    private final IchiroWalkJpaRepository jpaRepository;

    public List<IchiroWalk> findAll() {
        return jpaRepository.findAllByOrderByLeftAtDesc().stream()
                .map(this::toDomain)
                .toList();
    }

    public List<IchiroWalk> findByDate(LocalDate date) {
        var startOfDay = date.atStartOfDay();
        var endOfDay = date.atTime(MAX);

        return jpaRepository.findAllByLeftAtBetweenOrderByLeftAtDesc(startOfDay, endOfDay).stream()
                .map(this::toDomain)
                .toList();
    }

    public Optional<IchiroWalk> findEarliestByDateWithNullBackAt(LocalDate date) {
        var startOfDay = date.atStartOfDay();
        var endOfDay = date.atTime(MAX);

        return jpaRepository.findFirstByLeftAtBetweenAndBackAtNullOrderByLeftAtAsc(startOfDay, endOfDay)
                .map(this::toDomain);
    }

    public Optional<IchiroWalk> findLatestByDateWithNullBackAt(LocalDate date) {
        var startOfDay = date.atStartOfDay();
        var endOfDay = date.atTime(MAX);

        return jpaRepository.findFirstByLeftAtBetweenAndBackAtNullOrderByLeftAtDesc(startOfDay, endOfDay)
                .map(this::toDomain);
    }

    public void save(IchiroWalk ichiroWalk) {
        jpaRepository.save(toEntity(ichiroWalk));
    }

    public void delete(Long walkId) {
        jpaRepository.deleteById(walkId);
    }

    private IchiroWalk toDomain(IchiroWalkEntity entity) {
        return IchiroWalk.builder()
                .id(entity.getId())
                .leftAt(entity.getLeftAt())
                .backAt(entity.getBackAt())
                .build();
    }

    private IchiroWalkEntity toEntity(IchiroWalk domain) {
        return IchiroWalkEntity.builder()
                .id(domain.getId())
                .leftAt(domain.getLeftAt())
                .backAt(domain.getBackAt())
                .build();
    }
}
