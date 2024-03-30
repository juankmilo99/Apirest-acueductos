package projects.acueductosapi.response;

public class UsuarioResponseRest extends ResponseRest{

        private UsuarioResponse usuarioResponse = new UsuarioResponse();

        public UsuarioResponse getUserResponse() {
            return usuarioResponse;
        }

        public void setUserResponse(UsuarioResponse userResponse) {
            this.usuarioResponse = userResponse;
        }
}
