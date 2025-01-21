package aluraLatam.literAlura.service;


import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class ConsumoAPI {
    public static String obtenerDatosApi(String url) {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }
        catch (IOException e) {
            throw new RuntimeException("Error al obtener el respuesta: " + e.getMessage()  );
        }
        catch (InterruptedException e) {
            throw new RuntimeException("Se interrumpio la solicitud: " + e.getMessage()  );
        }

        String json = response.body();
        return json;
    }

}
