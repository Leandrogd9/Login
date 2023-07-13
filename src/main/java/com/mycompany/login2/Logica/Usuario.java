package com.mycompany.login2.Logica;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Usuario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID_usuario")
    private long idUsuario;
    @Column(name = "nombre_de_usuario")
    private String nombreUser;
    @Column(name = "contraseña_de_usuario")
    private String contrasenaUser;
    @ManyToOne
    @JoinColumn(name = "ID_rol")
    private Rol unRol;

    public Usuario() {
    }

    public Usuario(long idUsuario, String nombreUser, String contrasenaUser, Rol unRol) {
        this.idUsuario = idUsuario;
        this.nombreUser = nombreUser;
        this.contrasenaUser = contrasenaUser;
        this.unRol = unRol;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUser() {
        return nombreUser;
    }

    public void setNombreUser(String nombreUser) {
        this.nombreUser = nombreUser;
    }

    public String getContrasenaUser() {
        return contrasenaUser;
    }

    public void setContrasenaUser(String contrasenaUser) {
        this.contrasenaUser = contrasenaUser;
    }

    public Rol getUnRol() {
        return unRol;
    }

    public void setUnRol(Rol unRol) {
        this.unRol = unRol;
    }

}
