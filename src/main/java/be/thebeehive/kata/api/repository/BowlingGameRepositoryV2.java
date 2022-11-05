package be.thebeehive.kata.api.repository;

import be.thebeehive.kata.api.entities.BowlingGameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface BowlingGameRepositoryV2 extends JpaRepository<BowlingGameEntity, String> { }
