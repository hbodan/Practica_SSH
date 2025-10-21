package repository;

import entities.Categoria;
import java.util.List;

public interface ICategoria{
    Categoria guardar(Categoria categoria);

    Boolean eliminar(Categoria categoria);

    Categoria actualizar(Categoria categoria);

    List<Categoria> listar();

    Categoria buscarPorId(Long id);
}