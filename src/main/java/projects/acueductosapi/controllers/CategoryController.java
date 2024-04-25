package projects.acueductosapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projects.acueductosapi.response.CategoryResponse;
import projects.acueductosapi.response.CategoryResponseRest;
import projects.acueductosapi.response.OrderResponseRest;
import projects.acueductosapi.services.CategoryService;
import projects.acueductosapi.services.OrderService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    public CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<CategoryResponseRest> consultarCategories() {
        ResponseEntity<CategoryResponseRest> response = categoryService.buscarCategories();
        return response;
    }
}
