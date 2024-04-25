package projects.acueductosapi.response;

public class QuestionResponseRest extends ResponseRest{

        private QuestionResponse questionResponse = new QuestionResponse();

        public QuestionResponse getQuestionResponse() {
            return questionResponse;
        }

        public void setQuestionResponse(QuestionResponse questionResponse) {
            this.questionResponse = questionResponse;
        }
}
