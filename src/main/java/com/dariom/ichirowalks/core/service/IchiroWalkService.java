package com.dariom.ichirowalks.core.service;

import com.dariom.ichirowalks.core.domain.IchiroWalk;
import com.dariom.ichirowalks.core.domain.Result;
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
     * Get all walks created in the given date, sorted by "left at" date, in descending order.
     *
     * @param date date for which to fetch the walks. If empty, returns all walks.
     * @return a {@link List} of {@link IchiroWalk}
     */
    public List<IchiroWalk> getWalks(Optional<LocalDate> date) {
        return date.map(ichiroWalkRepository::findByDate)
                .orElseGet(ichiroWalkRepository::findAll);
    }

    /**
     * Creates a new walk in the DB, with the given "left at" date-time.
     * <p>
     * If a walk with null "back at" for the given date exists, no record is created.
     *
     * @param leftAt date-time at which you leave the house with the dog
     * @return {@link Result} containing if the operation succeeded, and if not, the reason of the failure
     */
    public Result createWalk(LocalDateTime leftAt) {
        var todayWalks = ichiroWalkRepository.findByDate(leftAt.toLocalDate());
        var walkWithNullBackAtExists = todayWalks.stream()
                .anyMatch(walk -> walk.getBackAt() == null);

        if (walkWithNullBackAtExists) {
            log.info("Walk with no 'back at' exists for date [{}], will not create a new one", leftAt.toLocalDate());
            return Result.builder()
                    .success(false)
                    .reason("A walk already exists")
                    .build();
        }

        ichiroWalkRepository.save(IchiroWalk.builder().leftAt(leftAt).build());
        return Result.builder().success(true).build();
    }

    /**
     * Updates the "back at" date-time for a walk.
     * <p>
     * The walk to assign the "back at" is picked with the following criteria, which are evaluated in order:
     * <ol>
     *     <li>The earliest walk matching the given date, that has no "back at" set.</li>
     *     <li>The latest walk from the day before, that has no "back at" set.</li>
     * </ol>
     * If no walk matching the above criteria is found, the save is not performed.
     *
     * @param backAt "back at" date
     * @return {@link Result} containing if the operation succeeded, and if not, the reason of the failure
     */
    public Result updateBackAt(LocalDateTime backAt) {
        var earliestTodayWalk = ichiroWalkRepository.findEarliestByDateWithNullBackAt(backAt.toLocalDate());
        if (earliestTodayWalk.isPresent()) {
            var walk = earliestTodayWalk.get();
            walk.setBackAt(backAt);
            ichiroWalkRepository.save(walk);

            return Result.builder().success(true).build();
        }

        var latestYesterdayWalk = ichiroWalkRepository.findLatestByDateWithNullBackAt(backAt.toLocalDate().minusDays(1));
        if (latestYesterdayWalk.isPresent()) {
            var walk = latestYesterdayWalk.get();
            walk.setBackAt(backAt);
            ichiroWalkRepository.save(walk);

            return Result.builder().success(true).build();
        }

        return Result.builder()
                .success(false)
                .reason("No walk exists")
                .build();
    }

    public void save(IchiroWalk ichiroWalk) {
        ichiroWalkRepository.save(ichiroWalk);
    }

    public void delete(long walkId) {
        ichiroWalkRepository.delete(walkId);
    }
}
