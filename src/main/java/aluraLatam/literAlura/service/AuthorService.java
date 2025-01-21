package aluraLatam.literAlura.service;


import aluraLatam.literAlura.model.Author;
import aluraLatam.literAlura.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public List<Author> todosLosAutores (){
        return authorRepository.findAllConBooks();
    }

    public List<Author> authoreVVivosFecha(int year){
        return authorRepository.findAuthorVivosEnElYear(year);
    }

    public Author createAuthor (Author author){
        return authorRepository.save(author);
    }

    public Optional<Author> findAuthor (long id){
        return authorRepository.findById(id);
    }

    public Optional<Author> encontrarAuthorByName(String name){
        return authorRepository.findAuthorByName(name);
    }

    public Author updateAuthor (long id, Author author){
        Author old = authorRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Autor no encontrado"));
        old.setName(author.getName());
        old.setNacimiento(author.getNacimiento());
        old.setFallecimiento(author.getFallecimiento());
        return authorRepository.save(old);
    }

    public void deleteAuthor (long id){
        authorRepository.deleteById(id);
    }

}
