package projects.acueductosapi.response;

import projects.acueductosapi.entities.User;


import java.util.List;

public class UsuarioResponse {
    private List<User> usuario;

    public List<User> getUsers() {
        return usuario;
    }

    public void setUsers(List<User> users) {
        this.usuario = users;
    }
}
