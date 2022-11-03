package be.thebeehive.kata.api.repository;

import be.thebeehive.kata.api.entities.BowlingGameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BowlingGameRepositoryV2 extends JpaRepository<BowlingGameEntity, String> {
    BowlingGameEntity findByGameId(String gameId);
}
