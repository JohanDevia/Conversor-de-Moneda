import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// Crear el cliente de conversión
		CurrencyConverterClient client = new CurrencyConverterClient();
		CurrencyConverter converter = new CurrencyConverter(client);
		UserInterface ui = new UserInterface(client);

		// Crear un Scanner para leer entradas del usuario
		Scanner scanner = new Scanner(System.in);
		boolean continuar = true;

		// Bucle principal para continuar el proceso de conversión
		while (continuar) {
			// Seleccionar la moneda base
			String baseCurrency = ui.selectCurrency(scanner, "Selecciona la moneda base:");

			// Seleccionar la moneda objetivo
			String targetCurrency = ui.selectCurrency(scanner, "Selecciona la moneda a convertir:");

			// Leer el monto a convertir
			System.out.println("Ingresa el monto que deseas convertir:");
			double amount = scanner.nextDouble();

			// Realizar la conversión
			try {
				double conversionRate = converter.getConversionRate(baseCurrency, targetCurrency);
				System.out.printf("La tasa de cambio de %s a %s es: %.4f%n", baseCurrency, targetCurrency, conversionRate);
				double convertedAmount = converter.convert(baseCurrency, targetCurrency, amount);
				System.out.printf("%.2f %s equivalen a %.2f %s%n", amount, baseCurrency, convertedAmount, targetCurrency);
			} catch (Exception e) {
				System.out.println("Ocurrió un error durante la conversión: " + e.getMessage());
			}

			// Preguntar si el usuario desea realizar otra conversión
			System.out.println("¿Quieres realizar otra conversión? (S/N): ");
			String respuesta = scanner.next().trim().toLowerCase();

			if (!respuesta.equals("s")) {
				continuar = false;
			}
		}

		// Cerrar el scanner
		scanner.close();
		System.out.println("Gracias por usar el conversor de moneda. ¡Hasta pronto!");
	}
}