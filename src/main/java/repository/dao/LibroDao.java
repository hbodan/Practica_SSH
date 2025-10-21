package repository.dao;

import entities.Libro;
import jakarta.persistence.EntityManager;
import org.glassfish.expressly.util.ReflectionUtil;
import repository.ILibro;

import java.util.List;

public class LibroDao implements ILibro {

    private EntityManager em;

    public LibroDao(EntityManager em) {
        this.em = em;
    }

    @Override
    public Libro guardar(Libro libro) {
        try{
            em.getTransaction().begin();
            Libro guardado;

            if (libro.getId() == null){
                em.persist(libro);
                guardado = libro;
            }else{
                guardado = em.merge(libro);
            }

            em.getTransaction().commit();
            return guardado;
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }

    }

    @Override
    public Boolean eliminar(Libro libro) {
        if (libro.getId() == null){
            throw new IllegalArgumentException("No se puede eliminar el libro sin Id");
        }

        try{
            em.getTransaction().begin();
            Libro eliminado = em.find(Libro.class, libro.getId());

            if (eliminado == null){
                em.getTransaction().commit();
                return false;
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
    public Libro actualizar(Libro libro) {
        if (libro.getId() == null){
            throw new IllegalArgumentException("No se puede actualizar el libro sin Id");
        }

        try{
            em.getTransaction().begin();

            Libro actualizado = em.find(Libro.class, libro.getId());

            if (actualizado == null){
                em.getTransaction().commit();
                return null;
            }

            actualizado = em.merge(libro);
            em.getTransaction().commit();
            return actualizado;
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }

    @Override
    public List<Libro> listar() {
        List<Libro> lista = em.createQuery("from Libro", Libro.class).getResultList();
        return lista;
    }

    @Override
    public Libro buscarPorId(Long id) { return (id == null) ? null : em.find(Libro.class, id);}
}
