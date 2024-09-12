package com.dariom.ichirowalks.core.domain;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record IchiroWalk(
        Long id,
        LocalDateTime leftAt,
        LocalDateTime backAt
) {
}
