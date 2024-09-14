package com.dariom.ichirowalks.core.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@EqualsAndHashCode(of = "id")
public class IchiroWalk implements Serializable {
    private Long id;
    private LocalDateTime leftAt;
    private LocalDateTime backAt;
}
