package aluraLatam.literAlura.principal;

import aluraLatam.literAlura.dto.AuthorDto;
import aluraLatam.literAlura.dto.BookDto;
import aluraLatam.literAlura.dto.RespuestaBookDto;
import aluraLatam.literAlura.model.Author;
import aluraLatam.literAlura.model.Book;
import aluraLatam.literAlura.service.AuthorService;
import aluraLatam.literAlura.service.BookService;
import aluraLatam.literAlura.service.ConsumoAPI;
import aluraLatam.literAlura.service.ConvertData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

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

    private void mostrarDetallesLibro(BookDto libroDTO) {
        System.out.println("------LIBRO--------");
        System.out.println("Título: " + libroDTO.getTitle());
        System.out.println("Autor: " + (libroDTO.getAuthors().isEmpty() ? "Desconocido" : libroDTO.getAuthors().get(0).getName()));
        System.out.println("Idioma: " + libroDTO.getIdioma().get(0));
        System.out.println("Número de descargas: " + libroDTO.getCantidadDescaras());
    }
    public void mostrarMenu(){
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
                    String encoderTitulo = URLEncoder.encode(titulo, StandardCharsets.UTF_8);
                    String json = ConsumoAPI.obtenerDatosApi(URL_PRINCIPAL + "?search=" + encoderTitulo);
                    RespuestaBookDto respuestaBookDto = convertData.obtenerDatos(json, RespuestaBookDto.class);
                    List<BookDto> librosDto = respuestaBookDto.getBooks();
                    boolean libroRegistrado = false;
                    if (librosDto.isEmpty()) {
                        System.out.printf("No se encontro el libro en la base de datos");
                    } else {
                        libroRegistrado = false;
                        for (BookDto bookDto : librosDto) {
                            Optional<Book> libroExistente = bookService.listaDeLibrosPorTitulo(titulo);
                            if (libroExistente.isPresent()) {
                                System.out.println("No se puede registrar el mismo libro más de una vez");
                                libroRegistrado = true;
                                break;
                            } else {
                                Book libro = new Book();
                                libro.setTitle(bookDto.getTitle());
                                libro.setIdioma(bookDto.getIdioma().get(0));
                                libro.setCantidadDescaras(bookDto.getCantidadDescaras());

                                // Buscar o crear el Autor
                                List<AuthorDto> primerAutorDTO = bookDto.getAuthors();
                                Author autor = authorService.encontrarAuthorByName(primerAutorDTO.get(0).getName())
                                        .orElseGet(() -> {
                                            Author nuevoAutor = new Author();
                                            nuevoAutor.setName(primerAutorDTO.get(0).getName());
                                            nuevoAutor.setNacimiento(primerAutorDTO.get(0).getNacimiento());
                                            nuevoAutor.setFallecimiento(primerAutorDTO.get(0).getFallecimiento());
                                            return authorService.createAuthor(nuevoAutor);
                                        });

                                // Asociar el Autor al Libro
                                libro.setAuthors(autor);

                                // Guardar el libro en la base de datos
                                bookService.creatBook(libro);
                                System.out.println("Libro registrado: " + libro.getTitle());
                                mostrarDetallesLibro(bookDto);
                                libroRegistrado = true;
                                break;
                            }
                        }
                    }
                    if (!libroRegistrado) {
                        System.out.println("No se encontró un libro exactamente con el título '" + titulo + "' en la API");
                    }
                } catch (Exception e) {
                    System.out.printf("Error al Obterner los datos de la API: %s", e.getMessage());
                }
                break;
            case 2:
                bookService.listaDeLibros().forEach(libro -> {
                    System.out.println("------LIBRO--------");
                    System.out.println("Título: " + libro.getTitle());
                    System.out.println("Autor: " + (libro.getAuthors().getName()));
                    System.out.println("Idioma: " + libro.getIdioma());
                    System.out.println("Número de descargas: " + libro.getCantidadDescaras());

                });
                break;
            case 3:
                authorService.todosLosAutores().forEach(autor -> {
                    System.out.println("-------AUTOR-------");
                    System.out.println("Autor: " + autor.getName());
                    System.out.println("Fecha de nacimiento: " + autor.getNacimiento());
                    System.out.println("Fecha de fallecimiento: " + (autor.getFallecimiento() != null ? autor.getFallecimiento() : "Desconocido"));
                    String libros = autor.getBooks().stream()
                            .map(Book::getTitle)
                            .collect(Collectors.joining(", "));
                    System.out.println("Libros: [ " + libros + " ]");
                });
                break;
            case 4:
                System.out.print("Ingrese el año vivo de autor(es) que desea buscar: ");
                int ano = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea
                List<Author> autoresVivos = authorService.authoreVVivosFecha(ano);
                if (autoresVivos.isEmpty()) {
                    System.out.println("No se encontraron autores vivos en el año " + ano);
                } else {
                    autoresVivos.forEach(autor -> {
                        System.out.println("-------AUTOR-------");
                        System.out.println("Autor: " + autor.getName());
                        System.out.println("Fecha de nacimiento: " + autor.getNacimiento());
                        System.out.println("Fecha de fallecimiento: " + (autor.getFallecimiento() != null ? autor.getFallecimiento() : "Desconocido"));
                        System.out.println("Libros: " + autor.getBooks().size());
                    });
                }
                break;
            case 5:
                System.out.println("Ingrese el idioma:");
                System.out.println("es");
                System.out.println("en");
                System.out.println("fr");
                System.out.println("pt");
                String idioma = scanner.nextLine();
                if ("es".equalsIgnoreCase(idioma) || "en".equalsIgnoreCase(idioma) || "fr".equalsIgnoreCase(idioma) || "pt".equalsIgnoreCase(idioma)) {
                    bookService.listaDeLibrosPorIdioma(idioma).forEach(libro -> {
                        System.out.println("------LIBRO--------");
                        System.out.println("Título: " + libro.getTitle());
                        System.out.println("Autor: " + (libro.getAuthors() != null ? libro.getAuthors().getName() : "Desconocido"));
                        System.out.println("Idioma: " + libro.getIdioma());
                        System.out.println("Número de descargas: " + libro.getCantidadDescaras());
                    });
                } else {
                    System.out.println("Idioma no válido. Intente de nuevo.");
                }
                break;
            case 0:
                System.out.println("Saliendo...");
                break;
            default:
                System.out.println("Opción no válida. Intente de nuevo.");

        }


    }
}