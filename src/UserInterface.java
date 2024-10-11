import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserInterface {

	private CurrencyConverterClient client;

	public UserInterface(CurrencyConverterClient client) {
		this.client = client;
	}

	// seleccionar la divisa entre una lista filtrada de 10 divisas
	public String selectCurrency(Scanner scanner, String prompt) {
		System.out.println(prompt);

		// Obtener las divisas de la API (por ejemplo, usando USD como base)
		Map<String, Double> allCurrencies = null;
		try {
			allCurrencies = client.getCurrencies("USD");
		} catch (Exception e) {
			System.out.println("Error obteniendo la lista de monedas: " + e.getMessage());
			return null;
		}

		// Filtrar solo las monedas que nos interesan (las principales y latinoamericanas)
		Map<String, String> filteredCurrencies = new HashMap<>();
		filteredCurrencies.put("USD", "Dólar estadounidense");
		filteredCurrencies.put("EUR", "Euro");
		filteredCurrencies.put("JPY", "Yen japonés");
		filteredCurrencies.put("GBP", "Libra esterlina");
		filteredCurrencies.put("COP", "Peso colombiano");
		filteredCurrencies.put("MXN", "Peso mexicano");
		filteredCurrencies.put("BRL", "Real brasileño");
		filteredCurrencies.put("ARS", "Peso argentino");
		filteredCurrencies.put("CLP", "Peso chileno");
		filteredCurrencies.put("PEN", "Sol peruano");

		// Mostrar la lista filtrada de monedas
		int i = 1;
		for (Map.Entry<String, String> entry : filteredCurrencies.entrySet()) {
			System.out.println(i + ". " + entry.getValue() + " [" + entry.getKey() + "]");
			i++;
		}

		int selection = 0;
		while (selection < 1 || selection > filteredCurrencies.size()) {
			System.out.print("Selecciona una opción (1-" + filteredCurrencies.size() + "): ");
			if (scanner.hasNextInt()) {
				selection = scanner.nextInt();
			} else {
				System.out.println("Por favor ingresa un número válido.");
				scanner.next();  // Consumir la entrada no válida
			}
		}

		// Obtener la clave de la moneda seleccionada
		String selectedCurrency = (String) filteredCurrencies.keySet().toArray()[selection - 1];
		return selectedCurrency;
	}
}