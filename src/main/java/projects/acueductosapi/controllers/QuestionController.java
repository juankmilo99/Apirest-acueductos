package projects.acueductosapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projects.acueductosapi.entities.Question;
import projects.acueductosapi.response.QuestionResponseRest;
import projects.acueductosapi.services.QuestionService;
import projects.acueductosapi.services.ReviewService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    public QuestionService questionService;

    @GetMapping("")
    public ResponseEntity<QuestionResponseRest> buscarQuestions() {
        return questionService.buscarQuestions();
    }

    @PostMapping("")
    public ResponseEntity<QuestionResponseRest> crearQuestion(@RequestBody Question question) {
        return questionService.crearQuestion(question);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<QuestionResponseRest> buscarQuestionsPorProducto(@PathVariable Integer productId) {
        return questionService.buscarQuestionsPorProducto(productId);
    }


}
