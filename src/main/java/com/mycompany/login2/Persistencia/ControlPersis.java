package com.mycompany.login2.Persistencia;
import com.mycompany.login2.Logica.Rol;
import com.mycompany.login2.Logica.Usuario;
import com.mycompany.login2.Persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControlPersis {
    UsuarioJpaController userJPA = new UsuarioJpaController();
    RolJpaController rolJPA = new RolJpaController();

    public List<Usuario> traerUsuarios() {
        return userJPA.findUsuarioEntities();
    }

    public List<Rol> trerRoles() {
        return rolJPA.findRolEntities();
    }

    public void crearUsuario(Usuario newUser) {
        userJPA.create(newUser);
    }

    public Usuario traerAuto(long id) {
        return userJPA.findUsuario(id);
    }

    public void editarUsuario(Usuario usuario) {
        try {
            userJPA.edit(usuario);
        } catch (Exception ex) {
            Logger.getLogger(ControlPersis.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void borrarUsuario(long idUsuario) {
        try {
            userJPA.destroy(idUsuario);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControlPersis.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
