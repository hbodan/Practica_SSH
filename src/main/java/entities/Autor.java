package entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "autores")
@Getter @Setter
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "nacionalidad", length = 100, nullable = false)
    private String nacionalidad;

    @Temporal(TemporalType.DATE)
    @Column (name = "fecha_nacimiento",  nullable = false)
    private Date fechaNacimiento;

    @OneToMany (mappedBy = "autor")
    List<Libro> libros;

    @Override
    public String toString() {
        return "Autor{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", nacionalidad='" + nacionalidad + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", libros=" + libros +
                '}';
    }
}
