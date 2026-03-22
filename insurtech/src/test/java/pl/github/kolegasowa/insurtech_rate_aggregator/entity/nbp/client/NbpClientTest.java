package pl.github.kolegasowa.insurtech_rate_aggregator.entity.nbp.client;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(NbpClient.class)
class NbpClientTest {

    @Autowired
    private NbpClient client;

    @Autowired
    private MockRestServiceServer server;

    @Test
    @DisplayName("Should return exchange rate from NBP when response is 200 OK")
    void shouldReturnExchangeRate() {
        String currencyCode = "USD";
        String nbpJsonResponse = """
                {
                    "table": "A",
                    "currency": "dolar amerykański",
                    "code": "USD",
                    "rates": [
                        {
                            "no": "055/A/NBP/2026",
                            "effectiveDate": "2026-03-21",
                            "mid": 4.1234
                        }
                    ]
                }
                """;

        server.expect(requestTo("https://api.nbp.pl/api/exchangerates/rates/a/USD/?format=json"))
                .andRespond(withSuccess(nbpJsonResponse, MediaType.APPLICATION_JSON));

        BigDecimal result = client.getExchangeRate(currencyCode);

        // THEN (Assert) - Sprawdzenie
        assertEquals(new BigDecimal("4.1234"), result);
    }

    @Test
    @DisplayName("Should throw RuntimeException when NBP returns 404 Not Found")
    void shouldThrowExceptionWhenCurrencyNotFound() {
        String wrongCode = "XYZ";
        server.expect(requestTo("https://api.nbp.pl/api/exchangerates/rates/a/XYZ/?format=json"))
                .andRespond(withStatus(HttpStatus.NOT_FOUND));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            client.getExchangeRate(wrongCode);
        });

        assertEquals("Nie znaleziono kursu dla waluty: XYZ", exception.getMessage());
    }
}