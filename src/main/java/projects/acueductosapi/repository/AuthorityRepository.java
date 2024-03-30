package projects.acueductosapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projects.acueductosapi.entities.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}