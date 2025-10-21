package repository;

import entities.Autor;
import java.util.List;

public interface IAutor {
    Autor guardar(Autor autor);
    Boolean eliminar(Autor autor);
    Autor actualizar(Autor autor);
    List<Autor> listar();
    Autor buscarPorId(Long id);
}