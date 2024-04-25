package projects.acueductosapi.response;

import projects.acueductosapi.entities.BlogPost;
import projects.acueductosapi.entities.Review;

import java.util.List;

public class ReviewResponse {
    private List<Review> reviews;

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }


}
