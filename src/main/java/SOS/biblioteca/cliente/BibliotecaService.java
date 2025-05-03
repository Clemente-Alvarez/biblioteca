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
                .body(Mono.just(prestamo), Usuario.class)
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



}
