package repository;

import entities.Libro;
import java.util.List;

public interface ILibro {
    Libro guardar(Libro libro);
    Boolean eliminar(Libro libro);
    Libro actualizar(Libro libro);
    List<Libro> listar();
    Libro buscarPorId(Long id);

}
