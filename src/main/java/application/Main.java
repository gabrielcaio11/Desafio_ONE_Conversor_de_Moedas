package application;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final String API_KEY = "9008998656bb2aff2761b971"; // Substitua pela sua chave da API
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";

    public static void main(String[] args) {
        mensagemBoasVindas();
        escolherOpcao();
    }

    private static void escolherOpcao() {
        Scanner scanner = new Scanner(System.in);
        int opcao = scanner.nextInt();
        
        while (!isOpcaovalida(opcao)) {
            System.out.println("Opção invalida ");
            escolherOpcao();
        }
        switch(opcao) {
            case 1: coverteDolarReal(); break;
            case 2: converterRealDolar(); break;
            case 3: converterEuroReal(); break;
            case 4: converterRealEuro(); break;
            case 5: converterRealPeso(); break;
            case 6: converterPesoReal(); break;
        }

        
    }

    private static void converterPesoReal() {
        Double valor = obterValorParaConversao();
        String siglaMonetaria = "ARS"; // Exemplo de busca
        try {
            ExchangeRateResponse exchangeRateResponse = getData(siglaMonetaria);
            Map<String, Double> map = exchangeRateResponse.getConversionRates();
            Double brl = map.get("BRL");
            Double ars = map.get("ARS");
            System.out.println("Valor em Real: " + valor);
            System.out.println("ARS: " + brl*ars);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void converterRealPeso() {
        Double valor = obterValorParaConversao();
        String siglaMonetaria = "BRL"; // Exemplo de busca
        try {
            ExchangeRateResponse exchangeRateResponse = getData(siglaMonetaria);
            Map<String, Double> map = exchangeRateResponse.getConversionRates();
            Double ars = map.get("ARS");
            System.out.println("Valor em Peso: " + valor);
            System.out.println("BRL: " + valor*ars);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void converterRealEuro() {
        Double valor = obterValorParaConversao();
        String siglaMonetaria = "BRL"; // Exemplo de busca
        try {
            ExchangeRateResponse exchangeRateResponse = getData(siglaMonetaria);
            Map<String, Double> map = exchangeRateResponse.getConversionRates();
            Double eur = map.get("EUR");
            System.out.println("Valor em Real: " + valor);
            System.out.println("EUR: " + valor*eur);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void converterEuroReal() {
        Double valor = obterValorParaConversao();
        String siglaMonetaria = "EUR"; // Exemplo de busca
        try {
            ExchangeRateResponse exchangeRateResponse = getData(siglaMonetaria);
            Map<String, Double> map = exchangeRateResponse.getConversionRates();
            Double brl = map.get("BRL");
            System.out.println("Valor em Euro: " + valor);
            System.out.println("BRL: " +valor*brl);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void converterRealDolar() {
        Double valor = obterValorParaConversao();
        String siglaMonetaria = "BRL"; // Exemplo de busca
        try {
            ExchangeRateResponse exchangeRateResponse = getData(siglaMonetaria);
            Map<String, Double> map = exchangeRateResponse.getConversionRates();
            Double usd = map.get("USD");
            System.out.println("Valor em Real: " + valor);
            System.out.println("USD: " + valor*usd);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void coverteDolarReal() {
        Double valor = obterValorParaConversao();
        String siglaMonetaria = "USD"; // Exemplo de busca
        try {
            ExchangeRateResponse exchangeRateResponse = getData(siglaMonetaria);
            Map<String, Double> map = exchangeRateResponse.getConversionRates();
            Double brl = map.get("BRL");
            System.out.println("Valor em dolar: " + valor);
            System.out.println("Brl: " + valor*brl);


        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    private static Double obterValorParaConversao() {
        System.out.println("Digite um valor a ser convertido: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextDouble();
    }

    private static boolean isOpcaovalida(int opcao) {
        return opcao > 0 && opcao < 7;
    }

    private static void escolherQuantidade() {
    }

    private static void mensagemBoasVindas() {
        System.out.println("\nConverso de Moeda\n");
        System.out.println("Dolar => Real[1]");
        System.out.println("Real => Dolar[2]");
        System.out.println("Euro => Real[3]");
        System.out.println("Real => Euro[4]");
        System.out.println("Real => Peso[5]");
        System.out.println("Peso => Real[6]");

    }
    public static ExchangeRateResponse getData(String siglaMonetaria) throws IOException, InterruptedException {
        // Monta a URL para fazer a requisição
        String url = BASE_URL + API_KEY +"/latest/"+ siglaMonetaria;

        // Cria o cliente HTTP
        HttpClient client = HttpClient.newHttpClient();

        // Monta a requisição
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        // Faz a requisição e captura a resposta
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        return gson.fromJson( response.body(), ExchangeRateResponse.class);
    }
}
