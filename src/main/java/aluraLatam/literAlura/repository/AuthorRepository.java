package aluraLatam.literAlura.repository;

import aluraLatam.literAlura.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findAuthorByName(String name);


    @Query("SELECT a FROM authors a LEFT  JOIN  FETCH  a.books")
    List<Author> findAllConBooks();

    @Query("SELECT  a FROM  authors a LEFT  JOIN  fetch a.books WHERE (a.fallecimiento IS NULL OR a.nacimiento > :year) AND a.nacimiento <= :year ")
    List<Author> findAuthorVivosEnElYear(@Param( "year") int year);


}
