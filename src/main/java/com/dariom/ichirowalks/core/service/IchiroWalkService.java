package com.dariom.ichirowalks.core.service;

import com.dariom.ichirowalks.core.domain.IchiroWalk;
import com.dariom.ichirowalks.core.domain.Result;
import com.dariom.ichirowalks.repository.IchiroWalkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static java.time.LocalTime.MAX;
import static java.time.LocalTime.MIDNIGHT;

@Slf4j
@Service
@RequiredArgsConstructor
public class IchiroWalkService {

    private final IchiroWalkRepository ichiroWalkRepository;

    /**
     * Get the walks created on the given date, sorted by "left at" date, in descending order.
     * <p>
     * Since we often go out after midnight, if the given date is before 4:00 AM, it also returns yesterday's walks.
     * <p>
     * If no date is provided, returns all walks.
     *
     * @param optionalDateTime date for which to fetch the walks. If empty, returns all walks.
     * @return a {@link List} of {@link IchiroWalk}
     */
    public List<IchiroWalk> getWalksOfActiveDay(Optional<LocalDateTime> optionalDateTime) {
        if (optionalDateTime.isEmpty()) {
            return ichiroWalkRepository.findAll();
        }

        final LocalDateTime from, until;
        var dateTime = optionalDateTime.get();
        var fourAM = LocalTime.of(4, 0);

        // if dateTime is before 4 AM, load from yesterday at 4 AM until today at 4 AM
        if (dateTime.toLocalTime().isBefore(fourAM)) {
            from = LocalDateTime.of(dateTime.minusDays(1).toLocalDate(), fourAM);
            until = LocalDateTime.of(dateTime.toLocalDate(), fourAM);
        } else { // if dateTime is after 4 AM, load from today at 4 AM until the end of day
            from = LocalDateTime.of(dateTime.toLocalDate(), fourAM);
            until = LocalDateTime.of(dateTime.toLocalDate(), MAX);
        }

        return ichiroWalkRepository.findByDate(from, until);
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
        var leftAtDate = leftAt.toLocalDate();
        var startOfDay = LocalDateTime.of(leftAtDate, MIDNIGHT);
        var endOfDay = LocalDateTime.of(leftAtDate, MAX);

        var todayWalks = ichiroWalkRepository.findByDate(startOfDay, endOfDay);
        var walkWithNullBackAtExists = todayWalks.stream()
                .anyMatch(walk -> walk.getBackAt() == null);

        if (walkWithNullBackAtExists) {
            log.info("Walk with empty 'back at' exists for date [{}], will not create a new one", leftAtDate);
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
