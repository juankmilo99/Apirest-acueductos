package projects.acueductosapi.services.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projects.acueductosapi.entities.Question;
import projects.acueductosapi.repository.QuestionRepository;
import projects.acueductosapi.response.QuestionResponseRest;
import projects.acueductosapi.services.QuestionService;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    private static final Logger log = LoggerFactory.getLogger(QuestionServiceImpl.class);

    @Autowired
    private QuestionRepository questionRepository;
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<QuestionResponseRest> buscarQuestions() {
        log.info("Inicio metodo buscarQuestions");

        QuestionResponseRest response = new QuestionResponseRest();

        try {
            List<Question> questions = questionRepository.findAll();

            if(questions != null && !questions.isEmpty()) {
                response.getQuestionResponse().setQuestions(questions);
                response.setMetadata("Respuesta ok", "00", "Questions encontradas");
            } else {
                log.error("No se encontraron questions");
                response.setMetadata("Respuesta nok", "-1", "No se encontraron questions");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

        } catch(Exception e) {
            log.error("Error al buscar questions ", e);
            response.setMetadata("Respuesta nok", "-1", "Error al buscar questions");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.OK); //devuelve 200
    }

    @Override
    @Transactional
    public ResponseEntity<QuestionResponseRest> crearQuestion(Question question) {
        log.info("Inicio metodo crear Question");

        QuestionResponseRest response = new QuestionResponseRest();
        List<Question> list = new ArrayList<>();

        try {
            Question savedQuestion = questionRepository.save(question);

            if(savedQuestion != null) {
                list.add(savedQuestion);
                response.getQuestionResponse().setQuestions(list);
                response.setMetadata("Respuesta ok", "00", "Question creada");
            } else {
                log.error("Error en grabar Question");
                response.setMetadata("Respuesta nok", "-1", "Question no guardada");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

        } catch(Exception e) {
            log.error("Error en grabar Question ");
            response.setMetadata("Respuesta nok", "-1", "Error al grabar Question");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.OK); //devuelve 200
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<QuestionResponseRest> buscarQuestionsPorProducto(Integer productId) {
        log.info("Inicio metodo buscarQuestionsPorProducto");

        QuestionResponseRest response = new QuestionResponseRest();

        try {
            List<Question> questions = questionRepository.findByProductId(productId);

            if(questions != null && !questions.isEmpty()) {
                response.getQuestionResponse().setQuestions(questions);
                response.setMetadata("Respuesta ok", "00", "Questions encontradas");
            } else {
                log.error("No se encontraron questions para el producto con id: " + productId);
                response.setMetadata("Respuesta nok", "-1", "No se encontraron questions");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

        } catch(Exception e) {
            log.error("Error al buscar questions por product_id ", e);
            response.setMetadata("Respuesta nok", "-1", "Error al buscar questions");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.OK); //devuelve 200
    }

}
