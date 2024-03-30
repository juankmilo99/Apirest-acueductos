package projects.acueductosapi.services;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import projects.acueductosapi.entities.Quote;
import projects.acueductosapi.response.QuoteResponseRest;

public interface QuoteService {

    public ResponseEntity<QuoteResponseRest> buscarQuotes();


    public ResponseEntity<QuoteResponseRest> crear(Quote quote);
}
