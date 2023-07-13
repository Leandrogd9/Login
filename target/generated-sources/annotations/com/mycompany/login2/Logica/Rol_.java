package com.mycompany.login2.Logica;

import com.mycompany.login2.Logica.Usuario;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-07-13T00:05:00", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Rol.class)
public class Rol_ { 

    public static volatile SingularAttribute<Rol, Long> idRol;
    public static volatile ListAttribute<Rol, Usuario> listaUsuario;
    public static volatile SingularAttribute<Rol, String> tipoRol;

}