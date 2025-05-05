package SOS.biblioteca.cliente;

import SOS.biblioteca.model.*;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.hateoas.*;

import reactor.core.publisher.Mono;

public class BibliotecaService {

    private WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080/api/v1").build();

    // /usuarios

    public void getUsuarios() {
        Usuario usuario = webClient.get()
                .uri("/usuarios")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> response
                        .bodyToMono(String.class)
                        .doOnNext(body -> System.err.println("Error 4xx: " + body))
                        .then(Mono.empty()) // Permite continuar la ejecución
                )
                .onStatus(HttpStatusCode::is5xxServerError, response -> response
                        .bodyToMono(String.class)
                        .doOnNext(body -> System.err.println("Error 5xx: " + body))
                        .then(Mono.empty()))
                .bodyToMono(Usuario.class)
                .block(); // Usamos block() para obtener la respuesta de forma síncrona

        if (usuario != null) {
            Links selfLink = usuario.getLinks();
            System.out.println("El Usuario con matricula: " + usuario.getMatricula() + " y nombre: "
                    + usuario.getNombre()
                    + " se encuentra disponible en el enlace: " + selfLink);
        }
    }

    public void postUsuario(String nombre, String fecha_nac, String correo, String penalizacion) {
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setFechaNacimiento(fecha_nac);
        usuario.setCorreo(correo);
        usuario.setPenalizacion(penalizacion);
        try {
            String referencia = webClient.post()
                    .uri("/usuarios")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(usuario), Usuario.class)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, response -> response
                            .bodyToMono(String.class)
                            .doOnNext(body -> System.err.println("Error 4xx: " + body))
                            .then(Mono.empty()) // Permite continuar la ejecución
                    )
                    .onStatus(HttpStatusCode::is5xxServerError, response -> response
                            .bodyToMono(String.class)
                            .doOnNext(body -> System.err.println("Error 5xx: " + body))
                            .then(Mono.empty()))
                    .toBodilessEntity() // Obtiene solo la respuesta HTTP sin cuerpo
                    .map(response -> {
                        if (response.getHeaders().getLocation() != null) {
                            return response.getHeaders().getLocation().toString();
                        } else {
                            throw new RuntimeException(
                                    "No se recibió una URL en la cabecera Location");
                        }
                    })
                    .block();// Bloquea para obtener el resultado sincrónicamente
            if (referencia != null) {
                System.out.println(referencia);
            }
        } catch (RuntimeException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // /usuarios/{matricula}
    public void getUsuario(int usuarioId) {

        // Realizamos la petición GET y deserializamos la respuesta en
        // Usuario
        Usuario usuario = webClient.get()
                .uri("/usuarios/" + usuarioId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> response
                        .bodyToMono(String.class)
                        .doOnNext(body -> System.err.println("Error 4xx: " + body))
                        .then(Mono.empty()) // Permite continuar la ejecución
                )
                .onStatus(HttpStatusCode::is5xxServerError, response -> response
                        .bodyToMono(String.class)
                        .doOnNext(body -> System.err.println("Error 5xx: " + body))
                        .then(Mono.empty()))
                .bodyToMono(Usuario.class)
                .block(); // Usamos block() para obtener la respuesta de forma síncrona

        if (usuario != null) {
            Links selfLink = usuario.getLinks();
            System.out.println("El Usuario con matricula: " + usuario.getMatricula() + " y nombre: "
                    + usuario.getNombre()
                    + " se encuentra disponible en el enlace: " + selfLink);
        }
    }

    public void putUsuario(int UsuarioId, String nombre, String fecha_nac, String correo, String penalizacion) {
        Usuario usuario = new Usuario();
        usuario.setMatricula(UsuarioId);
        usuario.setNombre(nombre);
        usuario.setCorreo(correo);
        usuario.setFechaNacimiento(fecha_nac);

        webClient.put()
                .uri("/usuarios/{matricula}", UsuarioId)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(usuario), Usuario.class)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> response
                        .bodyToMono(String.class)
                        .doOnNext(body -> System.err.println("Error 4xx: " + body))
                        .then(Mono.empty()) // Permite continuar la ejecución
                )
                .onStatus(HttpStatusCode::is5xxServerError, response -> response
                        .bodyToMono(String.class)
                        .doOnNext(body -> System.err.println("Error 5xx: " + body))
                        .then(Mono.empty()))
                .toBodilessEntity() // Obtiene solo la respuesta HTTP sin cuerpo
                .block(); // Bloquea hasta recibir la respuesta
    }

    public void deleteUsuario(int matricula) {
        webClient.delete()
                .uri("/usuarios/{matricula}", matricula)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> response
                        .bodyToMono(String.class)
                        .doOnNext(body -> System.err.println("Error 4xx: " + body))
                        .then(Mono.empty()) // Permite continuar la ejecución
                )
                .onStatus(HttpStatusCode::is5xxServerError, response -> response
                        .bodyToMono(String.class)
                        .doOnNext(body -> System.err.println("Error 5xx: " + body))
                        .then(Mono.empty()))
                .toBodilessEntity() // Obtiene solo la respuesta HTTP sin cuerpo
                .block();// Bloquea para obtener el resultado sincrónicamente
    }

    // /usuarios/{matricula}/prestamo -GET POST

    public void postPrestamo(int matricula, String fechaInicio, String fechaDevolucion, Boolean devuelto) {

        Prestamo prestamo = new Prestamo();
        prestamo.setFechaInicio(fechaInicio);
        prestamo.setFechaDevolucion(fechaDevolucion);
        prestamo.setDevuelto(devuelto);

        try {
            String referencia = webClient.post()
                    .uri("/usuarios/{matricula}/prestamo", matricula)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(prestamo), Prestamo.class)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, response -> response
                            .bodyToMono(String.class)
                            .doOnNext(body -> System.err.println("Error 4xx: " + body))
                            .then(Mono.empty()) // Permite continuar la ejecución
                    )
                    .onStatus(HttpStatusCode::is5xxServerError, response -> response
                            .bodyToMono(String.class)
                            .doOnNext(body -> System.err.println("Error 5xx: " + body))
                            .then(Mono.empty()))
                    .toBodilessEntity() // Obtiene solo la respuesta HTTP sin cuerpo
                    .map(response -> {
                        if (response.getHeaders().getLocation() != null) {
                            return response.getHeaders().getLocation().toString();
                        } else {
                            throw new RuntimeException(
                                    "No se recibió una URL en la cabecera Location");
                        }
                    })
                    .block();// Bloquea para obtener el resultado sincrónicamente
            if (referencia != null) {
                System.out.println(referencia);
            }
        } catch (RuntimeException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void getPrestamos(int matricula) {

        // Realizamos la petición GET y deserializamos la respuesta en
        // Prestamo
        Prestamo prestamo = webClient.get()
                .uri("/usuarios/{matricula}/prestamo", matricula)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> response
                        .bodyToMono(String.class)
                        .doOnNext(body -> System.err.println("Error 4xx: " + body))
                        .then(Mono.empty()) // Permite continuar la ejecución
                )
                .onStatus(HttpStatusCode::is5xxServerError, response -> response
                        .bodyToMono(String.class)
                        .doOnNext(body -> System.err.println("Error 5xx: " + body))
                        .then(Mono.empty()))
                .bodyToMono(Prestamo.class)
                .block(); // Usamos block() para obtener la respuesta de forma síncrona

        if (prestamo != null) {
            Links selfLink = prestamo.getLinks();
            System.out.println("El Prestamo con id: " + prestamo.getPrestamoId() + " y fechas: "
                    + prestamo.getFechaInicio() + " " + prestamo.getFechaDevolucion()
                    + " se encuentra disponible en el enlace: " + selfLink);

        }

    }

    // /usuarios/{matricula}/prestamo/{prestamo-id} -PUT

    public void putPrestamo(int matricula, int prestamo_id,String fechaInicio, String fechaDevolucion, Boolean devuelto) {
        Prestamo prestamo = new Prestamo();


        prestamo.setFechaInicio(fechaInicio);
        prestamo.setFechaDevolucion(fechaDevolucion);
        prestamo.setDevuelto(devuelto);

        webClient.put()
                .uri("/usuarios/{matricula}/prestamo/{prestamo-id}", matricula, prestamo_id )
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(prestamo), Prestamo.class)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> response
                        .bodyToMono(String.class)
                        .doOnNext(body -> System.err.println("Error 4xx: " + body))
                        .then(Mono.empty()) // Permite continuar la ejecución
                )
                .onStatus(HttpStatusCode::is5xxServerError, response -> response
                        .bodyToMono(String.class)
                        .doOnNext(body -> System.err.println("Error 5xx: " + body))
                        .then(Mono.empty()))
                .toBodilessEntity() // Obtiene solo la respuesta HTTP sin cuerpo
                .block(); // Bloquea hasta recibir la respuesta
    }

    // /libro
    public void getLibros() {
        Libro libro = webClient.get()
                .uri("/Libros")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> response
                        .bodyToMono(String.class)
                        .doOnNext(body -> System.err.println("Error 4xx: " + body))
                        .then(Mono.empty()) // Permite continuar la ejecución
                )
                .onStatus(HttpStatusCode::is5xxServerError, response -> response
                        .bodyToMono(String.class)
                        .doOnNext(body -> System.err.println("Error 5xx: " + body))
                        .then(Mono.empty()))
                .bodyToMono(Libro.class)
                .block();

        if (libro != null) {
            Links selfLink = libro.getLinks();
            System.out.println("El Usuario con matricula: " + libro.getId() + " y nombre: "
                    + libro.getTitulo()
                    + " se encuentra disponible en el enlace: " + selfLink);
        }
    }

    public void postLibro(String isbn, String titulo, String autor, String edicion, String editorial) {
        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAutor(autor);
        libro.setEdicion(edicion);
        libro.setEditorial(editorial);
        try {
            String referencia = webClient.post()
                    .uri("/libros")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(libro), Usuario.class)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, response -> response
                            .bodyToMono(String.class)
                            .doOnNext(body -> System.err.println("Error 4xx: " + body))
                            .then(Mono.empty())
                    )
                    .onStatus(HttpStatusCode::is5xxServerError, response -> response
                            .bodyToMono(String.class)
                            .doOnNext(body -> System.err.println("Error 5xx: " + body))
                            .then(Mono.empty()))
                    .toBodilessEntity()
                    .map(response -> {
                        if (response.getHeaders().getLocation() != null) {
                            return response.getHeaders().getLocation().toString();
                        } else {
                            throw new RuntimeException(
                                    "No se recibió una URL en la cabecera Location");
                        }
                    })
                    .block();
            if (referencia != null) {
                System.out.println(referencia);
            }
        } catch (RuntimeException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // /libros/{libro-id}

    public void getLibro(int libroId) {

        Libro libro = webClient.get()
                .uri("/libros/" + libroId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> response
                        .bodyToMono(String.class)
                        .doOnNext(body -> System.err.println("Error 4xx: " + body))
                        .then(Mono.empty()) // Permite continuar la ejecución
                )
                .onStatus(HttpStatusCode::is5xxServerError, response -> response
                        .bodyToMono(String.class)
                        .doOnNext(body -> System.err.println("Error 5xx: " + body))
                        .then(Mono.empty()))
                .bodyToMono(Libro.class)
                .block(); // Usamos block() para obtener la respuesta de forma síncrona

        if (libro != null) {
            Links selfLink = libro.getLinks();
            System.out.println("El libro con Id: " + libro.getId() + " y titulo: "
                    + libro.getTitulo()
                    + " se encuentra disponible en el enlace: " + selfLink);
        }
    }

    public void putLibro(int libroId, String isbn, String titulo, String autor, String edicion, String editorial) {
        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAutor(autor);
        libro.setEdicion(edicion);
        libro.setEditorial(editorial);

        webClient.put()
                .uri("/libro/{libro-id}", libroId)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(libro), Libro.class)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> response
                        .bodyToMono(String.class)
                        .doOnNext(body -> System.err.println("Error 4xx: " + body))
                        .then(Mono.empty()) // Permite continuar la ejecución
                )
                .onStatus(HttpStatusCode::is5xxServerError, response -> response
                        .bodyToMono(String.class)
                        .doOnNext(body -> System.err.println("Error 5xx: " + body))
                        .then(Mono.empty()))
                .toBodilessEntity() // Obtiene solo la respuesta HTTP sin cuerpo
                .block(); // Bloquea hasta recibir la respuesta
    }

    public void deleteLibro(int libroId) {
        webClient.delete()
                .uri("/libros/{libro-id}", libroId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> response
                        .bodyToMono(String.class)
                        .doOnNext(body -> System.err.println("Error 4xx: " + body))
                        .then(Mono.empty()) // Permite continuar la ejecución
                )
                .onStatus(HttpStatusCode::is5xxServerError, response -> response
                        .bodyToMono(String.class)
                        .doOnNext(body -> System.err.println("Error 5xx: " + body))
                        .then(Mono.empty()))
                .toBodilessEntity() // Obtiene solo la respuesta HTTP sin cuerpo
                .block();// Bloquea para obtener el resultado sincrónicamente
    }

    // /libro//{libro-id}/ejemplares

    public void postEjemplar(int ejemplarId, int libroId, String Estado) {

        Ejemplar ejemplar = new Ejemplar();
        ejemplar.setId(ejemplarId);
        ejemplar.setLibroId(libroId);
        ejemplar.setEstado(Estado);

        try {
            String referencia = webClient.post()
                    .uri("/libros/{libro-id}/ejemplares", ejemplarId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(ejemplar), Ejemplar.class)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, response -> response
                            .bodyToMono(String.class)
                            .doOnNext(body -> System.err.println("Error 4xx: " + body))
                            .then(Mono.empty()) // Permite continuar la ejecución
                    )
                    .onStatus(HttpStatusCode::is5xxServerError, response -> response
                            .bodyToMono(String.class)
                            .doOnNext(body -> System.err.println("Error 5xx: " + body))
                            .then(Mono.empty()))
                    .toBodilessEntity() // Obtiene solo la respuesta HTTP sin cuerpo
                    .map(response -> {
                        if (response.getHeaders().getLocation() != null) {
                            return response.getHeaders().getLocation().toString();
                        } else {
                            throw new RuntimeException(
                                    "No se recibió una URL en la cabecera Location");
                        }
                    })
                    .block();// Bloquea para obtener el resultado sincrónicamente
            if (referencia != null) {
                System.out.println(referencia);
            }
        } catch (RuntimeException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void getEjemplares(int ejemplarId) {

        Ejemplar ejemplar = webClient.get()
                .uri("/libros/{libro-id}/ejemplares", ejemplarId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> response
                        .bodyToMono(String.class)
                        .doOnNext(body -> System.err.println("Error 4xx: " + body))
                        .then(Mono.empty()) // Permite continuar la ejecución
                )
                .onStatus(HttpStatusCode::is5xxServerError, response -> response
                        .bodyToMono(String.class)
                        .doOnNext(body -> System.err.println("Error 5xx: " + body))
                        .then(Mono.empty()))
                .bodyToMono(Ejemplar.class)
                .block(); // Usamos block() para obtener la respuesta de forma síncrona

        if (ejemplar != null) {
            Links selfLink = ejemplar.getLinks();
            System.out.println("El Ejemplar con id: " + ejemplar.getId() + ", libroId: "
                    + ejemplar.getLibroId() + " y estado: " + ejemplar.getEstado()
                    + " se encuentra disponible en el enlace: " + selfLink);

        }

    }

    // /libro//{libro-id}/ejemplares/{ejemplar-id}

    public void putEjemplar(int ejemplarId, int libroId, String Estado) {
        Ejemplar ejemplar = new Ejemplar();

        ejemplar.setId(ejemplarId);
        ejemplar.setLibroId(libroId);
        ejemplar.setEstado(Estado);

        webClient.put()
                .uri("/libro//{libro-id}/ejemplares/{ejemplar-id}", libroId, ejemplarId )
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(ejemplar), Ejemplar.class)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> response
                        .bodyToMono(String.class)
                        .doOnNext(body -> System.err.println("Error 4xx: " + body))
                        .then(Mono.empty()) // Permite continuar la ejecución
                )
                .onStatus(HttpStatusCode::is5xxServerError, response -> response
                        .bodyToMono(String.class)
                        .doOnNext(body -> System.err.println("Error 5xx: " + body))
                        .then(Mono.empty()))
                .toBodilessEntity() // Obtiene solo la respuesta HTTP sin cuerpo
                .block(); // Bloquea hasta recibir la respuesta
    }

    public void getEjemplar(int libroId, int ejemplarId) {

        Ejemplar ejemplar = webClient.get()
                .uri("/libro//{libro-id}/ejemplares/" + ejemplarId, libroId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> response
                        .bodyToMono(String.class)
                        .doOnNext(body -> System.err.println("Error 4xx: " + body))
                        .then(Mono.empty()) // Permite continuar la ejecución
                )
                .onStatus(HttpStatusCode::is5xxServerError, response -> response
                        .bodyToMono(String.class)
                        .doOnNext(body -> System.err.println("Error 5xx: " + body))
                        .then(Mono.empty()))
                .bodyToMono(Ejemplar.class)
                .block(); // Usamos block() para obtener la respuesta de forma síncrona

        if (ejemplar != null) {
            Links selfLink = ejemplar.getLinks();
            System.out.println("El Ejemplar con Id: " + ejemplar.getId() + " y libroId: "
                    + ejemplar.getLibroId()
                    + " se encuentra disponible en el enlace: " + selfLink);
        }
    }

    public void deleteEjemplar(int libroId, int ejemplarId) {
        webClient.delete()
                .uri("/libro//{libro-id}/ejemplares/", libroId, ejemplarId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> response
                        .bodyToMono(String.class)
                        .doOnNext(body -> System.err.println("Error 4xx: " + body))
                        .then(Mono.empty()) // Permite continuar la ejecución
                )
                .onStatus(HttpStatusCode::is5xxServerError, response -> response
                        .bodyToMono(String.class)
                        .doOnNext(body -> System.err.println("Error 5xx: " + body))
                        .then(Mono.empty()))
                .toBodilessEntity() // Obtiene solo la respuesta HTTP sin cuerpo
                .block();// Bloquea para obtener el resultado sincrónicamente
    }

}



