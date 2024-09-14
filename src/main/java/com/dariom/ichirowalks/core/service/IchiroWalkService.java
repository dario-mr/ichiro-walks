package com.dariom.ichirowalks.core.service;

import com.dariom.ichirowalks.core.domain.IchiroWalk;
import com.dariom.ichirowalks.repository.IchiroWalkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IchiroWalkService {

    private final IchiroWalkRepository ichiroWalkRepository;

    public List<IchiroWalk> getAllWalks() {
        return ichiroWalkRepository.findAll();
    }

    public void save(IchiroWalk ichiroWalk) {
        ichiroWalkRepository.save(ichiroWalk);
    }
}
