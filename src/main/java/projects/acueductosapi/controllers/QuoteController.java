package projects.acueductosapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projects.acueductosapi.entities.Quote;
import projects.acueductosapi.response.QuoteResponseRest;
import projects.acueductosapi.services.ProductoService;
import projects.acueductosapi.services.QuoteService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/quotes")
public class QuoteController {

    @Autowired
    public QuoteService quoteService;

    @GetMapping("")
    public ResponseEntity<QuoteResponseRest> consultarQuotes() {
        ResponseEntity<QuoteResponseRest> response = quoteService.buscarQuotes();
        return response;
    }

    @PostMapping("")
    public ResponseEntity<QuoteResponseRest> crearQuote(@RequestBody Quote request) {
        ResponseEntity<QuoteResponseRest> response = quoteService.crear(request);
        return response;
    }
}
