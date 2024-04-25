package projects.acueductosapi.response;

public class ReviewResponseRest extends ResponseRest{

    private ReviewResponse reviewResponse = new ReviewResponse();

    public ReviewResponse getReviewResponse() {
        return reviewResponse;
    }

    public void setReviewResponse(ReviewResponse reviewResponse) {
        this.reviewResponse = reviewResponse;
    }
}
