
import com.google.gson.Gson;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;

public class CurrencyConverterClient {

	private static final String API_URL = "https://v6.exchangerate-api.com/v6/3e0f167adeb53cc91f086012/latest/";

	// Método que obtiene las tasas de cambio para una moneda base
	public String getRates(String baseCurrency) throws IOException {
		URL url = new URL(API_URL + baseCurrency);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.connect();

		int responseCode = conn.getResponseCode();

		if (responseCode != 200) {
			throw new RuntimeException("Ocurrió un error: " + responseCode);
		} else {
			Scanner scanner = new Scanner(url.openStream());
			StringBuilder jsonResponse = new StringBuilder();

			while (scanner.hasNext()) {
				jsonResponse.append(scanner.nextLine());
			}
			scanner.close();
			return jsonResponse.toString();
		}
	}

	// Método para obtener los códigos de las divisas
	public Map<String, Double> getCurrencies(String baseCurrency) throws IOException {
		String jsonResponse = getRates(baseCurrency);
		Gson gson = new Gson();
		Map<String, Object> responseMap = gson.fromJson(jsonResponse, Map.class);

		// Extraer las tasas de conversión, donde las claves son los códigos de divisa
		return (Map<String, Double>) responseMap.get("conversion_rates");
	}
}