package com.api.nba.seasons.repository;

import com.api.nba.seasons.model.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeasonRepository extends JpaRepository<Season, Long> {

    Season findBySeason(String season);

}
