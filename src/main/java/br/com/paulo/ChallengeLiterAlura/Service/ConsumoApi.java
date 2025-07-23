package br.com.paulo.ChallengeLiterAlura.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoApi {

    public String obterDados(String endereco) {
        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endereco))
                .build();

        try {
//            System.out.println("URL chamada: " + endereco); // Log da URL

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            int statusCode = response.statusCode();
            System.out.println("Status HTTP: " + statusCode); // Log do status HTTP

            if (statusCode != 200) {
                throw new RuntimeException("Erro ao chamar API. Código HTTP: " + statusCode);
            }

            String json = response.body();
//            System.out.println("JSON retornado: " + json); // Log do JSON retornado

            if (json == null || json.isBlank()) {
                throw new RuntimeException("Resposta da API está vazia.");
            }

            return json;

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Erro ao fazer requisição para a API", e);
        }
    }
}
