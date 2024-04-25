package projects.acueductosapi.services;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import projects.acueductosapi.entities.Question;
import projects.acueductosapi.response.QuestionResponseRest;

public interface QuestionService {

    public ResponseEntity<QuestionResponseRest> buscarQuestions();


    public ResponseEntity<QuestionResponseRest> crearQuestion(Question question);


    public ResponseEntity<QuestionResponseRest> buscarQuestionsPorProducto(Integer productId);
}
