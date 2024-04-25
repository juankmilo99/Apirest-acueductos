package projects.acueductosapi.services;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import projects.acueductosapi.entities.Review;
import projects.acueductosapi.response.ReviewResponseRest;

public interface ReviewService {

   public ResponseEntity<ReviewResponseRest> buscarReviews();


   public ResponseEntity<ReviewResponseRest> crearReview(Review review);


   public ResponseEntity<ReviewResponseRest> buscarReviewsPorProducto(Integer productId);
}
