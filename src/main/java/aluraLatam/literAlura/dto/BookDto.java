package aluraLatam.literAlura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookDto {

    @JsonAlias("id")
    private long id;

    private String title;

    @JsonAlias ("authors")
    private List<AuthorDto> authors;

    @JsonAlias("languages")
    private List<String> idioma;

    @JsonAlias("download_count")
    private int cantidadDescaras;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<AuthorDto> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorDto> authors) {
        this.authors = authors;
    }

    public List<String> getIdioma() {
        return idioma;
    }

    public void setIdioma(List<String> idioma) {
        this.idioma = idioma;
    }

    public int getCantidadDescaras() {
        return cantidadDescaras;
    }

    public void setCantidadDescaras(int cantidadDescaras) {
        this.cantidadDescaras = cantidadDescaras;
    }
}
