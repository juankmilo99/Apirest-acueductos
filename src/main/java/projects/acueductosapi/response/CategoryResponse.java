package projects.acueductosapi.response;

import projects.acueductosapi.entities.Category;
import projects.acueductosapi.entities.Order;

import java.util.List;

public class CategoryResponse {
    private List<Category> categories;

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
