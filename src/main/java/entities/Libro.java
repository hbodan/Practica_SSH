package entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "libros")
@Getter @Setter
public class Libro {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo", length = 100, nullable = false)
    private String titulo;

    @Column(name = "anio_publicacion", length = 100, nullable = false)
    private String anioPublicacion;

    @ManyToOne
    @JoinColumn(name = "autor")
    private Autor autor;

    @ManyToMany
    @JoinTable(name = "libro_categoria", joinColumns = @JoinColumn(name = "libro_id"), inverseJoinColumns = @JoinColumn(name = "categoria_id"))
    private List<Categoria> categorias;

    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", anioPublicacion='" + anioPublicacion + '\'' +
                ", autor=" + autor +
                ", categorias=" + categorias +
                '}';
    }
}
