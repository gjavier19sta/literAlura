package aluraLatam.literAlura.service;

import aluraLatam.literAlura.model.Book;
import aluraLatam.literAlura.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> listaDeLibros (){
        return bookRepository.findAll();
    }

    public List<Book> listaDeLibrosPorIdioma (String idioma){
        return bookRepository.findByIdioma( idioma);
    }

    public Book creatBook (Book book){
        return bookRepository.save(book);
    }

    public Optional<Book> buscaPorId (long  id){
        return bookRepository.findById(id);
    }

    public Optional<Book> listaDeLibrosPorTitulo (String title){
        return bookRepository.findByTitleIgnoreCase(title);
    }

    public Book updateBook (Long id, Book libroActualizar){
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("libro no encontrado"));
        book.setTitle(libroActualizar.getTitle());
        book.setIdioma(libroActualizar.getIdioma());
        book.setAuthors(libroActualizar.getAuthors());
        book.setCantidadDescaras(libroActualizar.getCantidadDescaras());
        return bookRepository.save(book);
    }

    public void deleteBook (Long id){
        bookRepository.deleteById(id);
    }



}

