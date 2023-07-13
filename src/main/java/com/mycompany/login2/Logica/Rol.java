package com.mycompany.login2.Logica;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Rol implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_rol")
    private long idRol;
    @Column(name = "Tipo_de_rol")
    private String tipoRol;
    @OneToMany(mappedBy = "unRol")
    private List<Usuario> listaUsuario;

    public Rol() {
    }

    public Rol(long idRol, String tipoRol, List<Usuario> listaUsuario) {
        this.idRol = idRol;
        this.tipoRol = tipoRol;
        this.listaUsuario = listaUsuario;
    }

    public long getIdRol() {
        return idRol;
    }

    public void setIdRol(long idRol) {
        this.idRol = idRol;
    }

    public String getTipoRol() {
        return tipoRol;
    }

    public void setTipoRol(String tipoRol) {
        this.tipoRol = tipoRol;
    }

    public List<Usuario> getListaUsuario() {
        return listaUsuario;
    }

    public void setListaUsuario(List<Usuario> listaUsuario) {
        this.listaUsuario = listaUsuario;
    }
    
}
