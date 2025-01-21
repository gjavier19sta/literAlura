package aluraLatam.literAlura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Book")
public class Book {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String idioma;
    private int cantidadDescaras;

    @ManyToOne
    @JoinColumn(name = "authorId")
    private Author authors;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String language) {
        this.idioma = language;
    }

    public int getCantidadDescaras() {
        return cantidadDescaras;
    }

    public void setCantidadDescaras(int cantidadDescaras) {
        this.cantidadDescaras = cantidadDescaras;
    }

    public Author getAuthors() {
        return authors;
    }

    public void setAuthors(Author authors) {
        this.authors = authors;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", language='" + idioma + '\'' +
                ", cantidadDescaras=" + cantidadDescaras +
                ", authors=" + authors +
                '}';
    }
}
