package nl.miwnn.ch16.bas.tcglibrary.repositories;

import nl.miwnn.ch16.bas.tcglibrary.model.Deck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface DeckRepository extends JpaRepository<Deck, Long> {
}
