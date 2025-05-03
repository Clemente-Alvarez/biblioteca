package SOS.biblioteca.cliente;

import SOS.biblioteca.model.*;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;


public class BibliotecaService {



    private WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080/api/v1").build();

    public void getUsuario(int usuarioId) {

        // Realizamos la petición GET y deserializamos la respuesta en
        // Usuario
        Usuario Usuario = webClient.get()
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

        if (Usuario != null) {
                String selfLink = Usuario.get_links().getSelf().getHref();
                if (selfLink != null) {
                        System.out.println("El Usuario con id: " + Usuario.getId() + " y nombre: "
                                        + Usuario.getNombre()
                                        + " se encuentra disponible en el enlace: " + selfLink);
                } else {
                        System.out.println("Enlace 'self' no encontrado.");
                }
        }

}

public void postUsuario(String nombre) {
        Usuario Usuario = new Usuario();
        Usuario.setNombre(nombre);

        try {
                String referencia = webClient.post()
                                .uri("/Usuarios")
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(Mono.just(Usuario), Usuario.class)
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

public void putUsuario(int UsuarioId, String nombre) {
        Usuario Usuario = new Usuario();
        Usuario.setId(UsuarioId);
        Usuario.setNombre(nombre);

        webClient.put()
                        .uri("/Usuarios/{id}", UsuarioId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(Usuario), Usuario.class)
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
