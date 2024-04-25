package projects.acueductosapi.services.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projects.acueductosapi.entities.Review;
import projects.acueductosapi.repository.ReviewRepository;
import projects.acueductosapi.response.ReviewResponseRest;
import projects.acueductosapi.services.ReviewService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private static final Logger log = LoggerFactory.getLogger(ReviewServiceImpl.class);

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ReviewResponseRest> buscarReviews() {

        ReviewResponseRest response = new ReviewResponseRest();

        try {
            List<Review> reviews = reviewRepository.findAll();

            response.getReviewResponse().setReviews(reviews);
            response.setMetadata("Respuesta ok", "200", "Respuesta exitosa");
        } catch (Exception e) {

            response.setMetadata("Respuesta nok", "-1", "Error al consultar Reviews");
            log.error("error al consultar reviews: ", e.getMessage());
            e.getStackTrace();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return new ResponseEntity<ReviewResponseRest>(response, HttpStatus.OK); //devuelve 200
    }

    @Override
    @Transactional
    public ResponseEntity<ReviewResponseRest> crearReview(Review review) {
        log.info("Inicio metodo crear Review");

        ReviewResponseRest response = new ReviewResponseRest();
        List<Review> list = new ArrayList<>();

        try {
            Review savedReview = reviewRepository.save(review);

            if(savedReview != null) {
                list.add(savedReview);
                response.getReviewResponse().setReviews(list);
            } else {
                log.error("Error en grabar Review");
                response.setMetadata("Respuesta nok", "-1", "Review no guardada");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

        } catch(Exception e) {
            log.error("Error en grabar Review ");
            response.setMetadata("Respuesta nok", "-1", "Error al grabar Review");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.setMetadata("Respuesta ok", "00", "Review creada");
        return new ResponseEntity<>(response, HttpStatus.OK); //devuelve 200
    }
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ReviewResponseRest> buscarReviewsPorProducto(Integer productId) {
        log.info("Inicio metodo buscarReviewsPorProducto");

        ReviewResponseRest response = new ReviewResponseRest();

        try {
            List<Review> reviews = reviewRepository.findByProductId(productId);

            if(reviews != null && !reviews.isEmpty()) {
                response.getReviewResponse().setReviews(reviews);
                response.setMetadata("Respuesta ok", "00", "Reviews encontradas");
            } else {
                log.error("No se encontraron reviews para el producto con id: " + productId);
                response.setMetadata("Respuesta nok", "-1", "No se encontraron reviews");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

        } catch(Exception e) {
            log.error("Error al buscar reviews por product_id ", e);
            response.setMetadata("Respuesta nok", "-1", "Error al buscar reviews");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.OK); //devuelve 200
    }


}
