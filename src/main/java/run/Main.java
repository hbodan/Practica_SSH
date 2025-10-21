package run;

import config.JPAUtil;
import entities.Autor;
import entities.Categoria;
import entities.Libro;
import jakarta.persistence.EntityManager;
import repository.dao.AutorDao;
import repository.dao.CategoriaDao;
import repository.dao.LibroDao;

import java.sql.Date;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        EntityManager em = JPAUtil.getEntityManager();

        AutorDao autorDao = new AutorDao(em);
        CategoriaDao categoriaDao = new CategoriaDao(em);
        LibroDao libroDao = new LibroDao(em);
        try {
            // 1) Crear y guardar Autor
            Autor a = new Autor();
            a.setNombre("Gabriel García Márquez");
            a.setNacionalidad("Colombiana");
            a.setFechaNacimiento(Date.valueOf("1927-03-06"));
            a = autorDao.guardar(a);
            System.out.println("Autor guardado: id=" + a.getId() + ", nombre=" + a.getNombre());

            // 2) Crear y guardar Categorías
            Categoria c1 = new Categoria();
            c1.setNombre("Realismo mágico");
            c1 = categoriaDao.guardar(c1);

            Categoria c2 = new Categoria();
            c2.setNombre("Novela");
            c2 = categoriaDao.guardar(c2);

            System.out.println("Categorías: " + c1.getId() + " y " + c2.getId());

            // 3) Crear y guardar Libro con autor y categorías
            Libro l = new Libro();
            l.setTitulo("Cien años de soledad");
            l.setAnioPublicacion("1967");
            l.setAutor(a);
            l.setCategorias(new ArrayList<>());
            l.getCategorias().add(c1);
            l.getCategorias().add(c2);
            l = libroDao.guardar(l);
            System.out.println("Libro guardado: id=" + l.getId() + ", titulo=" + l.getTitulo());

            // 4) Listar entidades
            System.out.println("\n=== Listar Libros ===");
            libroDao.listar().forEach(lib ->
                    System.out.println("Libro{id=" + lib.getId() + ", titulo=" + lib.getTitulo() + "}"));

            System.out.println("\n=== Listar Autores ===");
            autorDao.listar().forEach(au ->
                    System.out.println("Autor{id=" + au.getId() + ", nombre=" + au.getNombre() + "}"));

            System.out.println("\n=== Listar Categorías ===");
            categoriaDao.listar().forEach(cat ->
                    System.out.println("Categoria{id=" + cat.getId() + ", nombre=" + cat.getNombre() + "}"));

            // 5) Buscar por id
            Libro l2 = libroDao.buscarPorId(l.getId());
            System.out.println("\nLibro por id: " + l2.getId() + " -> " + l2.getTitulo());

            // 6) Actualizar libro
            l2.setTitulo("Cien años de soledad (Ed. 50 aniversario)");
            l2 = libroDao.actualizar(l2);
            System.out.println("Libro actualizado: " + l2.getTitulo());

            // 7) Actualizar autor
            a.setNombre("G. G. Márquez");
            a = autorDao.actualizar(a);
            System.out.println("Autor actualizado: " + a.getNombre());

            // 8) Romper relación Libro–Categoría y eliminar una categoría
            l2.getCategorias().remove(c1);
            l2 = libroDao.actualizar(l2);
            boolean catEliminada = categoriaDao.eliminar(c1);
            System.out.println("¿Categoría c1 eliminada? " + catEliminada);

            // 9) Eliminar libro
            boolean libroEliminado = libroDao.eliminar(l2);
            System.out.println("¿Libro eliminado? " + libroEliminado);

            // 10) Eliminar autor
            boolean autorEliminado = autorDao.eliminar(a);
            System.out.println("¿Autor eliminado? " + autorEliminado);


            System.out.println("Hola esto es una prueba");

        } finally {
            em.close();
        }
    }

}

