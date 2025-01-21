package aluraLatam.literAlura.repository;

import aluraLatam.literAlura.model.Author;
import aluraLatam.literAlura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByTitleIgnoreCase (String Title);

    @Query("SELECT b FROM Book b WHERE b.idioma = :idioma")
    List<Book> findByIdioma(@Param("idioma") String idioma);

//    List<Book> findByAuthors(@Param("authors") Author author);
}
