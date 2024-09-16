package com.dariom.ichirowalks.core.service;

import com.dariom.ichirowalks.core.domain.IchiroWalk;
import com.dariom.ichirowalks.repository.IchiroWalkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class IchiroWalkService {

    private final IchiroWalkRepository ichiroWalkRepository;

    /**
     * Get all walks created in the given date, sorted in descending order by "left at" date.
     *
     * @param date date for which to fetch the walks. If empty, returns all walks.
     * @return a {@link List} of {@link IchiroWalk}
     */
    public List<IchiroWalk> getWalks(Optional<LocalDate> date) {
        return date.map(ichiroWalkRepository::findByDate)
                .orElseGet(ichiroWalkRepository::findAll);
    }

    /**
     * Updates the "back at" date for a walk.
     * <p>
     * The walk to assign the "back at" date is picked with the following criteria, which are evaluated in order:
     * <ol>
     *     <li>The earliest walk matching the given date, that has no "back at" date set.</li>
     *     <li>The latest walk from the day before, that has no "back at" date set.</li>
     * </ol>
     * If no walk matching the above criteria is found, the save is not performed.
     *
     * @param backAt "back at" date
     */
    public void updateBackAt(LocalDateTime backAt) {
        var earliestTodayWalk = ichiroWalkRepository.findEarliestByDateWithNullBackAt(backAt.toLocalDate());
        if (earliestTodayWalk.isPresent()) {
            var walk = earliestTodayWalk.get();
            walk.setBackAt(backAt);
            ichiroWalkRepository.save(walk);
            return;
        }

        var latestYesterdayWalk = ichiroWalkRepository.findLatestByDateWithNullBackAt(backAt.toLocalDate().minusDays(1));
        latestYesterdayWalk.ifPresentOrElse(walk -> {
            walk.setBackAt(backAt);
            ichiroWalkRepository.save(walk);
        }, () -> log.warn("No walk found to set 'back at' for date: {}", backAt));
    }

    public void save(IchiroWalk ichiroWalk) {
        ichiroWalkRepository.save(ichiroWalk);
    }

    public void delete(long walkId) {
        ichiroWalkRepository.delete(walkId);
    }
}
