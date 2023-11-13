import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SimpleVatChecker {

    private static final String CHECK_VAT_NUMBER_ENDPOINT = "https://ec.europa.eu/taxation_customs/vies/rest-api/check-vat-test-service";

    public static void main(String[] args) {


        // no input just for faster testing
        String countryCode =  "SI";//args[0];
        String vatNumber = "44046421";//args[1];

        VATRequest vatRequest = new VATRequest(countryCode, vatNumber);
        VATInfo vatInfo = checkVatNumber(vatRequest);

        System.out.println(vatInfo.getName());
        System.out.println(vatInfo.getAddress());
    }

    private static VATInfo checkVatNumber(VATRequest vatRequest) {
        try {
            HttpClient client = HttpClient.newHttpClient();

            String jsonInputString = String.format("{\"countryCode\":\"%s\",\"vatNumber\":\"%s\"}",
                    vatRequest.getCountryCode(), vatRequest.getVatNumber());

            System.out.println(jsonInputString);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(CHECK_VAT_NUMBER_ENDPOINT))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonInputString))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println(response.body());

            return VATInfo.fromJson(response.body());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
