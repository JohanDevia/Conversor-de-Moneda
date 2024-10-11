import com.google.gson.Gson;
import java.util.Map;

public class CurrencyConverter {

	private CurrencyConverterClient client;

	public CurrencyConverter(CurrencyConverterClient client) {
		this.client = client;
	}

	public double convert(String baseCurrency, String targetCurrency, double amount) throws Exception {
		String jsonResponse = client.getRates(baseCurrency);

		Gson gson = new Gson();
		Map<String, Object> responseMap = gson.fromJson(jsonResponse, Map.class);
		Map<String, Double> rates = (Map<String, Double>) responseMap.get("conversion_rates");

		if (rates.containsKey(targetCurrency)) {
			double conversionRate = rates.get(targetCurrency);
			return amount * conversionRate;
		} else {
			throw new Exception("La moneda objetivo no está disponible.");
		}
	}
	public double getConversionRate(String baseCurrency, String targetCurrency) throws Exception {
		// Obtener las tasas de cambio para la moneda base
		Map<String, Double> rates = client.getCurrencies(baseCurrency);

		// Verificar que la moneda objetivo esté en las tasas
		if (rates.containsKey(targetCurrency)) {
			return rates.get(targetCurrency); // Retornar la tasa de cambio
		} else {
			throw new Exception("No se encontró la tasa de cambio para " + targetCurrency);
		}
	}
}
