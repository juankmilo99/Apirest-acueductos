package projects.acueductosapi.services.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projects.acueductosapi.entities.Category;
import projects.acueductosapi.repository.CategoryRepository;
import projects.acueductosapi.response.CategoryResponseRest;
import projects.acueductosapi.services.CategoryService;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<CategoryResponseRest> buscarCategories() {

        CategoryResponseRest response = new CategoryResponseRest();

        try {
            List<Category> categories = categoryRepository.findAll();
            response.getCategoryResponse().setCategories(categories);
            response.setMetadata("Respuesta ok", "200", "Respuesta exitosa");
        } catch (Exception e) {

            response.setMetadata("Respuesta nok", "-1", "Error al consultar Categories");
            log.error("error al consultar Categories: ", e.getMessage());
            e.getStackTrace();
            return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK); //devuelve 200
    }
}
