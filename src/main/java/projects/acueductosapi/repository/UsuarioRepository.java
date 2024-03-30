package projects.acueductosapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projects.acueductosapi.entities.User;

public interface UsuarioRepository extends JpaRepository<User, Integer> {
}