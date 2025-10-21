package repository.dao;

import entities.Autor;
import jakarta.persistence.EntityManager;
import repository.IAutor;

import java.util.List;

public class AutorDao implements IAutor {
    private final EntityManager em;

    public AutorDao(EntityManager em) {
        this.em = em;
    }

    @Override
    public Autor guardar(Autor autor) {
        try{
            em.getTransaction().begin();
            Autor guardado;
            if(autor.getId() == null){
                em.persist(autor);
                guardado = autor;
            }else{
                guardado = em.merge(autor);
            }

            em.getTransaction().commit();
            return guardado;
        }catch (RuntimeException e){
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            throw e;
        }
    }

    @Override
    public Autor actualizar(Autor autor) {
        if (autor.getId() == null) {
            throw new IllegalArgumentException("No se puede actualizar el autor debido a que no se encuentra el id");
        }

        try {
            em.getTransaction().begin();
            Autor editado = em.find(Autor.class, autor.getId());

            if (editado == null) {
                em.getTransaction().commit();
                return null;
            }

            editado = em.merge(autor);
            em.getTransaction().commit();
            return editado;

        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }

    @Override
    public Boolean eliminar (Autor autor){
        if (autor.getId() == null) {
            throw new IllegalArgumentException("No se puede actualizar el autor debido a que no se encuentra el id");
        }

        try {
            em.getTransaction().begin();
            Autor eliminado = em.find(Autor.class, autor.getId());

            if (eliminado == null) {
                em.getTransaction().commit();
                return null;
            }

            em.remove(eliminado);
            em.getTransaction().commit();
            return true;

        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }

    @Override
    public Autor buscarPorId(Long id){
        return (id==null) ? null : em.find(Autor.class, id);
    }

    @Override
    public List<Autor> listar() {
        List<Autor> lista = em.createQuery("from Autor", Autor.class).getResultList();
        return lista;
    }
}

