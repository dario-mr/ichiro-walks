package com.dariom.ichirowalks.core.domain;

import lombok.Builder;

@Builder
public record Result(
        boolean success,
        String reason
) {
}
