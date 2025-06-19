package nl.miwnn.ch16.bas.tcglibrary.repositories;

import nl.miwnn.ch16.bas.tcglibrary.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}
