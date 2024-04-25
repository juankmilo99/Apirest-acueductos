package projects.acueductosapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projects.acueductosapi.entities.Review;
import projects.acueductosapi.repository.UsuarioRepository;
import projects.acueductosapi.response.ReviewResponseRest;
import projects.acueductosapi.services.CategoryService;
import projects.acueductosapi.services.ReviewService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    public ReviewService reviewService;
    @Autowired
    private UsuarioRepository userRepository;

    @GetMapping("")
    public ResponseEntity<ReviewResponseRest> buscarReviews() {
        return reviewService.buscarReviews();
    }

    @PostMapping("")
    public ResponseEntity<ReviewResponseRest> crearReview(@RequestBody Review review) {
        return reviewService.crearReview(review);
    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<ReviewResponseRest> buscarReviewsPorProducto(@PathVariable Integer productId) {
        return reviewService.buscarReviewsPorProducto(productId);
    }


}
