package com.mycompany.login.Logica;
import com.mycompany.login.IGU.PantallaAdmin;
import com.mycompany.login.IGU.PantallaUser;
import com.mycompany.login.Persistencia.ControlPersis;
import java.util.ArrayList;
import java.util.List;

public class ControlLogica {
    ControlPersis controlP = new ControlPersis();
            
    public String verificarExistencia(String username, String contra) {
        List<Usuario> listaUsuario = this.traerUsuarios();
        String mensaje;
                
        for(Usuario user : listaUsuario){
            if(user.getNombreUser().equals(username)){
                if(user.getContrasenaUser().equals(contra)){
                    if(user.getUnRol().getTipoRol().equalsIgnoreCase("Administrador")){ 
                        PantallaAdmin panta = new PantallaAdmin(username);
                        panta.setVisible(true);
                        panta.setLocationRelativeTo(null);
                    }else{
                        PantallaUser panta = new PantallaUser(username);
                        panta.setVisible(true);
                        panta.setLocationRelativeTo(null);
                    }
                    return mensaje = " ";
                }else{
                    return mensaje = "Contraseña incorrecta.";
                } 
            }
        }
        return mensaje = "Usuario no encontrado.";
    }

    public List<Usuario> traerUsuarios() {
        return controlP.traerUsuarios();
    }

    public List<Usuario> verUsurios() {
        List<Usuario> listaUsuario = this.traerUsuarios();
        ArrayList<Usuario> listaUsuario2 = new ArrayList();
        
        for(Usuario user : listaUsuario){
            if(user.getUnRol().getTipoRol().equalsIgnoreCase("Usuario")){
                listaUsuario2.add(user);
            }
        }

        return listaUsuario2;
    }

    public boolean guardarUsuario(String usuario, String contrasena, String rol) {
        Usuario newUser = new Usuario();
        List<Rol> listaRol = this.traerRoles();
        List<Usuario> listaUser = this.traerUsuarios();
        
        for(Usuario user : listaUser){
            if(user.getNombreUser().equals(usuario)){
                return false;
            }
        }
        
        newUser.setNombreUser(usuario);
        newUser.setContrasenaUser(contrasena);
                
        for(Rol unRol : listaRol){
            if(unRol.getTipoRol().equalsIgnoreCase(rol)){
                newUser.setUnRol(unRol);
            }
        }
        
        controlP.crearUsuario(newUser);
        
        return true;
    }

    public List<Rol> traerRoles() {
        return controlP.trerRoles();
    }

    public Usuario traerAuto(String user) {
        List<Usuario> listaUsuario = this.traerUsuarios();
        long id = 0;
                
        for(Usuario usuario : listaUsuario){
            if(usuario.getNombreUser().equals(user)){
                id = usuario.getIdUsuario();
            }
        }
        return controlP.traerAuto(id);
    }

    public boolean editarUsuario(String nombreUser, String contrasena, String rol, Usuario usuario) {
        List<Rol> listaRol = this.traerRoles();
        List<Usuario> listaUser = this.traerUsuarios();
        
        for(Usuario user : listaUser){
            if(user.getNombreUser().equals(nombreUser) && !user.getNombreUser().equals(usuario.getNombreUser())){
                return false;
            }
        }
        
        usuario.setNombreUser(nombreUser);
        usuario.setContrasenaUser(contrasena);
                
        for(Rol unRol : listaRol){
            if(unRol.getTipoRol().equalsIgnoreCase(rol)){
                usuario.setUnRol(unRol);
            }
        }
        
        controlP.editarUsuario(usuario);
        
        return true;
    }

    public void borrarUsuario(String user) {
        List<Usuario> listaUser = this.traerUsuarios();
        
        for(Usuario usuario : listaUser){
            if(usuario.getNombreUser().equals(user)){
                controlP.borrarUsuario(usuario.getIdUsuario());
            }
        }
    }
}
