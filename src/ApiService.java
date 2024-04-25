import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.*;

class ApiService {

  String apikey = "5c3472b55622e8038452d48e";
  String url_service = "https://v6.exchangerate-api.com/v6/";


  Conversor conversionRates(String base_code) {

    String url_req = url_service + apikey + "/latest/" + base_code;

    HttpResponse<String> response = null;

    try {
      HttpClient client = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder()
              .uri(URI.create(url_req))
              .build();
      response = client
              .send(request, HttpResponse.BodyHandlers.ofString());


    } catch (IOException | InterruptedException e) {
      System.out.println("Ups! algo salio mal" + e);
    }

    Gson gson = new Gson();
    Conversor rates = gson.fromJson(response.body(), Conversor.class);

    return rates;

  }

  ParesConversion convertPair(String base_code, String target_code, double amount) throws IOException, InterruptedException {

    String url_req = url_service + apikey + "/pair/" + base_code + "/" + target_code + "/" + amount;

    HttpResponse<String> response = null;

    try {
      HttpClient client = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder()
              .uri(URI.create(url_req))
              .build();
      response = client
              .send(request, HttpResponse.BodyHandlers.ofString());

    } catch (IOException | InterruptedException e) {
      System.out.println("Ups! algo salio mal" + e);
    }

    Gson gson = new GsonBuilder()
            .registerTypeAdapter(ParesConversion.class, (JsonDeserializer<ParesConversion>) (json, typeOfT, context) -> {
                JsonObject jsonObject = json.getAsJsonObject();
                String baseCurrency = jsonObject.get("base_code").getAsString();
                String targetCurrency = jsonObject.get("target_code").getAsString();
                double conversion_rate = jsonObject.get("conversion_rate").getAsDouble();
                double conversion_result = jsonObject.get("conversion_result").getAsDouble();
                return new ParesConversion(baseCurrency, targetCurrency, conversion_rate, conversion_result);
            })
            .create();
    ParesConversion conversion = gson.fromJson(response.body(), ParesConversion.class);

    return conversion;
  }

  public void showConversion(ParesConversion res, double amount) {
    String formatConversion = String.format(
            "🟢 %.1f %s ==> %.1f %s", amount, res.base_code(), res.conversion_result(), res.target_code());
    System.out.println(formatConversion);
  }

}