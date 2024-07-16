package modulos;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConectApi {
    HttpClient cliente;
    HttpRequest request;
    HttpResponse<String> response;

    public ConectApi(){
        this.cliente = HttpClient.newHttpClient();
    }

    public String getRespuesta(String url) throws IOException, InterruptedException {
        this.request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        this.response = cliente.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

}