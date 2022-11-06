package be.thebeehive.kata.api.repository;

import be.thebeehive.kata.api.entities.BowlingGameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BowlingGameRepository extends JpaRepository<BowlingGameEntity, String> { }
