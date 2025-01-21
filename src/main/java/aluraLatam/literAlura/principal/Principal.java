package aluraLatam.literAlura.principal;

import aluraLatam.literAlura.dto.BookDto;
import aluraLatam.literAlura.dto.RespuestaBookDto;
import aluraLatam.literAlura.model.Book;
import aluraLatam.literAlura.service.AuthorService;
import aluraLatam.literAlura.service.BookService;
import aluraLatam.literAlura.service.ConsumoAPI;
import aluraLatam.literAlura.service.ConvertData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class Principal {

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private ConsumoAPI consumoAPI;

    @Autowired
    private ConvertData convertData;

    private static final String URL_PRINCIPAL = "http://gutendex.com/books/";

    public void menu() {
        Scanner scanner = new Scanner(System.in);
        int option = 0;

        final String MENU = """
            
            **********************************************************
            ----LiterAlura---------
            
            1) Buscar libro por Titulo
            2) Buscar libros Registrados
            3) Buscar Autores Registrados
            4) Buscar Autores vivos en un año
            5) Buscar libros por idioma
            0) Salir
            
            Elija una opción válida
            **********************************************************
            """;

        option = scanner.nextInt();
        scanner.nextLine(); //no se si hace falta probar sin esto

        switch (option) {
            case 1:
                System.out.printf("Ingrese el titulo del libro que desea buscar");
                String titulo = scanner.nextLine();
                try {
                    String encoderTitulo = URLEncoder.encode(titulo, "UTF-8");
                    String json = ConsumoAPI.obtenerDatosApi(URL_PRINCIPAL + "?search=" + encoderTitulo);
                    RespuestaBookDto respuestaBookDto = convertData.obtenerDatos(json, RespuestaBookDto.class);
                    List<BookDto> librosDto = respuestaBookDto.getBooks();
                    if (librosDto.isEmpty()) {
                        System.out.printf("No se encontro el libro en la base de datos");
                    }
                    else {
                        boolean bookFound = false;
                        for (BookDto bookDto : librosDto) {
                            Optional<Book> librosEncontrados = bookService.listaDeLibrosPorTitulo(titulo);
                            if (librosEncontrados.isPresent()) {
                                System.out.printf("Detalle clave (titulo)" );
                            }

                        }
                    }


                }catch (Exception e){
                    System.out.printf("Error al Obterner los datos de la API: %s",e.getMessage());
                }
                break;










        }








    }
}
