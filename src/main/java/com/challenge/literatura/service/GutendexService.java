package com.challenge.literatura.service;

import com.challenge.literatura.dto.BookDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties; // Importa anotação para ignorar propriedades desconhecidas pelo Jackson
import com.fasterxml.jackson.databind.ObjectMapper; // Importa classe ObjectMapper do Jackson
import org.springframework.stereotype.Service; // Importa anotação de serviço do Spring

import java.net.URI; // Importa classe URI para manipulação de URI
import java.net.URLEncoder; // Importa classe URLEncoder para codificação de URL
import java.net.http.HttpClient; // Importa classe HttpClient para realizar chamadas HTTP
import java.net.http.HttpRequest; // Importa classe HttpRequest para construir requisições HTTP
import java.net.http.HttpResponse; // Importa classe HttpResponse para manipular respostas HTTP
import java.nio.charset.StandardCharsets; // Importa StandardCharsets para especificar codificação de caracteres
import java.util.List; // Importa List do Java.util
import java.util.Set; // Importa Set do Java.util
import java.util.stream.Collectors; // Importa Collectors do Java.util.stream

@Service // Marca a classe como um componente de serviço gerenciado pelo Spring
public class GutendexService {
    private static final String API_URL = "https://gutendex.com/books/"; // URL base da API Gutendex

    // Método para buscar livros na API Gutendex com base em um query
    public List<BookDTO> searchBooks(String query) throws Exception {
        HttpClient client = HttpClient.newHttpClient(); // Cria uma instância de HttpClient
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8); // Codifica a query com UTF-8
        HttpRequest request = HttpRequest.newBuilder().uri(new URI(API_URL + "?search=" + encodedQuery)) // Constrói a requisição HTTP
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString()); // Envia a requisição e recebe a resposta

        if (response.statusCode() == 200) { // Verifica se a resposta foi bem sucedida (código 200)
            String jsonResponse = response.body(); // Obtém o corpo da resposta em formato de String
            ObjectMapper objectMapper = new ObjectMapper(); // Cria uma instância de ObjectMapper do Jackson
            GutendexResponse gutendexResponse = objectMapper.readValue(jsonResponse, GutendexResponse.class); // Faz o mapeamento da resposta para a classe GutendexResponse
            return gutendexResponse.getResults(); // Retorna os resultados obtidos
        } else {
            throw new RuntimeException("Erro ao buscar livros: " + response.statusCode()); // Lança uma exceção se a resposta não for bem sucedida
        }
    }

    // Classe interna para mapear a resposta da API Gutendex
    @JsonIgnoreProperties(ignoreUnknown = true) // Ignora propriedades desconhecidas ao fazer o mapeamento com Jackson
    private static class GutendexResponse {
        private List<BookDTO> results; // Lista de resultados de livros

        public List<BookDTO> getResults() {
            return results; // Retorna os resultados obtidos
        }
    }
}
