package projects.acueductosapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projects.acueductosapi.entities.Quote;

public interface QuoteRepository extends JpaRepository<Quote, Integer> {
}