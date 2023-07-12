package com.mycompany.login.Persistencia;

import com.mycompany.login.Logica.Rol;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.login.Logica.Usuario;
import com.mycompany.login.Persistencia.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class RolJpaController implements Serializable {

    public RolJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public RolJpaController(){
        emf = Persistence.createEntityManagerFactory("loginPU");
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Rol rol) {
        if (rol.getListaUsuario() == null) {
            rol.setListaUsuario(new ArrayList<Usuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Usuario> attachedListaUsuario = new ArrayList<Usuario>();
            for (Usuario listaUsuarioUsuarioToAttach : rol.getListaUsuario()) {
                listaUsuarioUsuarioToAttach = em.getReference(listaUsuarioUsuarioToAttach.getClass(), listaUsuarioUsuarioToAttach.getIdUsuario());
                attachedListaUsuario.add(listaUsuarioUsuarioToAttach);
            }
            rol.setListaUsuario(attachedListaUsuario);
            em.persist(rol);
            for (Usuario listaUsuarioUsuario : rol.getListaUsuario()) {
                Rol oldUnRolOfListaUsuarioUsuario = listaUsuarioUsuario.getUnRol();
                listaUsuarioUsuario.setUnRol(rol);
                listaUsuarioUsuario = em.merge(listaUsuarioUsuario);
                if (oldUnRolOfListaUsuarioUsuario != null) {
                    oldUnRolOfListaUsuarioUsuario.getListaUsuario().remove(listaUsuarioUsuario);
                    oldUnRolOfListaUsuarioUsuario = em.merge(oldUnRolOfListaUsuarioUsuario);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Rol rol) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rol persistentRol = em.find(Rol.class, rol.getIdRol());
            List<Usuario> listaUsuarioOld = persistentRol.getListaUsuario();
            List<Usuario> listaUsuarioNew = rol.getListaUsuario();
            List<Usuario> attachedListaUsuarioNew = new ArrayList<Usuario>();
            for (Usuario listaUsuarioNewUsuarioToAttach : listaUsuarioNew) {
                listaUsuarioNewUsuarioToAttach = em.getReference(listaUsuarioNewUsuarioToAttach.getClass(), listaUsuarioNewUsuarioToAttach.getIdUsuario());
                attachedListaUsuarioNew.add(listaUsuarioNewUsuarioToAttach);
            }
            listaUsuarioNew = attachedListaUsuarioNew;
            rol.setListaUsuario(listaUsuarioNew);
            rol = em.merge(rol);
            for (Usuario listaUsuarioOldUsuario : listaUsuarioOld) {
                if (!listaUsuarioNew.contains(listaUsuarioOldUsuario)) {
                    listaUsuarioOldUsuario.setUnRol(null);
                    listaUsuarioOldUsuario = em.merge(listaUsuarioOldUsuario);
                }
            }
            for (Usuario listaUsuarioNewUsuario : listaUsuarioNew) {
                if (!listaUsuarioOld.contains(listaUsuarioNewUsuario)) {
                    Rol oldUnRolOfListaUsuarioNewUsuario = listaUsuarioNewUsuario.getUnRol();
                    listaUsuarioNewUsuario.setUnRol(rol);
                    listaUsuarioNewUsuario = em.merge(listaUsuarioNewUsuario);
                    if (oldUnRolOfListaUsuarioNewUsuario != null && !oldUnRolOfListaUsuarioNewUsuario.equals(rol)) {
                        oldUnRolOfListaUsuarioNewUsuario.getListaUsuario().remove(listaUsuarioNewUsuario);
                        oldUnRolOfListaUsuarioNewUsuario = em.merge(oldUnRolOfListaUsuarioNewUsuario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                long id = rol.getIdRol();
                if (findRol(id) == null) {
                    throw new NonexistentEntityException("The rol with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rol rol;
            try {
                rol = em.getReference(Rol.class, id);
                rol.getIdRol();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rol with id " + id + " no longer exists.", enfe);
            }
            List<Usuario> listaUsuario = rol.getListaUsuario();
            for (Usuario listaUsuarioUsuario : listaUsuario) {
                listaUsuarioUsuario.setUnRol(null);
                listaUsuarioUsuario = em.merge(listaUsuarioUsuario);
            }
            em.remove(rol);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Rol> findRolEntities() {
        return findRolEntities(true, -1, -1);
    }

    public List<Rol> findRolEntities(int maxResults, int firstResult) {
        return findRolEntities(false, maxResults, firstResult);
    }

    private List<Rol> findRolEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Rol.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Rol findRol(long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Rol.class, id);
        } finally {
            em.close();
        }
    }

    public int getRolCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Rol> rt = cq.from(Rol.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
