package com.api.nba.leaders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReboundsRepository extends JpaRepository<Rebounds, Long> {
}
