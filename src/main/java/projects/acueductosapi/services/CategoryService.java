package projects.acueductosapi.services;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import projects.acueductosapi.response.CategoryResponseRest;

public interface CategoryService {

    public ResponseEntity<CategoryResponseRest> buscarCategories();
}
