package repository.dao;

import entities.Categoria;
import jakarta.persistence.EntityManager;
import repository.ICategoria;

import java.util.List;

public class CategoriaDao implements ICategoria {
    private EntityManager em;

    public CategoriaDao(EntityManager em) {
        this.em = em;
    }


    @Override
    public Categoria guardar(Categoria categoria) {
        try{
            em.getTransaction().begin();
            Categoria guardado;
            if(categoria.getId() == null){
                em.persist(categoria);
                guardado = categoria;
            }else{
                guardado = em.merge(categoria);
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
    public Boolean eliminar(Categoria categoria) {
        if(categoria.getId() == null){
            throw new IllegalArgumentException("El id del categoria no puede ser null");
        }

        try {
            em.getTransaction().begin();
            Categoria eliminado = em.find(Categoria.class, categoria.getId());

            if(eliminado == null){
                em.getTransaction().commit();
                return false;
            }

            em.remove(eliminado);
            em.getTransaction().commit();
            return true;

        } catch (RuntimeException e) {
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            throw e;
        }
    }

    @Override
    public Categoria actualizar(Categoria categoria) {
        if(categoria.getId() == null){
            throw new IllegalArgumentException("El id del categoria no puede ser null");
        }

        try{
            em.getTransaction().begin();
            Categoria actualizado = em.find(Categoria.class, categoria.getId());

            if(actualizado == null){
                em.getTransaction().commit();
                return null;
            }

            actualizado = em.merge(categoria);
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
    public List<Categoria> listar() {
        List<Categoria> lista = em.createQuery("from Categoria", Categoria.class).getResultList();
        return lista;
    }

    @Override
    public Categoria buscarPorId(Long id) { return (id==null)?null:em.find(Categoria.class, id);}
}

