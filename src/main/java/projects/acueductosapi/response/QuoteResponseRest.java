package projects.acueductosapi.response;

public class QuoteResponseRest extends ResponseRest {
    private QuoteResponse quoteResponse = new QuoteResponse();

    public QuoteResponse getQuoteResponse() {
        return quoteResponse;
    }

    public void setQuoteResponse(QuoteResponse quoteResponse) {
        this.quoteResponse = quoteResponse;
    }
}
