package projects.acueductosapi.response;

import projects.acueductosapi.entities.Quote;
import projects.acueductosapi.entities.User;

import java.util.List;

public class QuoteResponse {
    private List<Quote> quotes;

    public List<Quote> getQuotes() {
        return quotes;
    }

    public void setQuotes(List<Quote> quotes) {
        this.quotes = quotes;
    }
}
