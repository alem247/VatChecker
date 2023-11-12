import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SimpleVatChecker {

    private static final String CHECK_VAT_NUMBER_ENDPOINT = "http://ec.europa.eu/taxation_customs/vies/services/checkVatService";

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java VatChecker <countryCode> <vatNumber>");
            System.exit(1);
        }

        String countryCode = args[0];
        String vatNumber = args[1];

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

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(CHECK_VAT_NUMBER_ENDPOINT))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonInputString))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return VATInfo.fromJson(response.body());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
