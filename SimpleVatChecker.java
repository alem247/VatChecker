import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class SimpleVatChecker {

    private static final String CHECK_VAT_NUMBER_ENDPOINT = "https://ec.europa.eu/taxation_customs/vies/rest-api/check-vat-number";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("Vnesi davcno stevilko v formatu SI XXXXXXXX: ");
        String input = sc.nextLine();
        System.out.println("--------------------------------------------------------");
        String countryCode =  "SI";
        String vatNumber = input.substring(3);

        VATRequest vatRequest = new VATRequest(countryCode, vatNumber);
        VATInfo vatInfo = checkVatNumber(vatRequest);

        System.out.println(vatInfo.getName());
        System.out.println(vatInfo.getAddress());
        System.out.println("--------------------------------------------------------");
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
