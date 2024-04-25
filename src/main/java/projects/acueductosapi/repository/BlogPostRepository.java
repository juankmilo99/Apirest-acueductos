package projects.acueductosapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projects.acueductosapi.entities.BlogPost;

public interface BlogPostRepository extends JpaRepository<BlogPost, Integer> {
}