package pl.github.kolegasowa.insurtech_rate_aggregator.entity.nbp.client;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import pl.github.kolegasowa.insurtech_rate_aggregator.entity.nbp.dto.NbpResponse;

import java.math.BigDecimal;

@Component
public class NbpClient {

    private final RestClient restClient;

    public NbpClient(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("https://api.nbp.pl/api/exchangerates/rates/a/").build();
    }

    public BigDecimal getExchangeRate(String currencyCode) {
        NbpResponse response = restClient.get()
                .uri("{code}/?format=json", currencyCode)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, resp) -> {
                    throw new RuntimeException("Nie znaleziono kursu dla waluty: " + currencyCode);
                })
                .body(NbpResponse.class);

        if (response != null && !response.rates().isEmpty()) {
            return response.rates().get(0).mid();
        }

        throw new RuntimeException("Błąd komunikacji z API NBP");
    }
}
