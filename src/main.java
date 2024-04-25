import java.io.IOException;
import java.util.Scanner;

public class main {
  public static void main(String[] args) throws IOException, InterruptedException {

	  ApiService apiServices = new ApiService();

    String conversion_codes = """
            ================================
            💱 ¿Qué conversion deseas realizar?
            1. USD --> ARS
            2. ARS --> USD
            3. USD --> BRL
            4. BRL --> USD
            5. USD --> COP
            6. COP --> USD
            7. Salir
            ================================
            """;

    Scanner input = new Scanner(System.in);

    while (true) {
        System.out.println(conversion_codes + "");
        int selected = input.nextInt();

        if (selected == 7) {
            System.out.println("Muchas Gracias, vuelva Prontos");
            break;
        }

        double amount;
        String baseCurrency, targetCurrency;

        switch (selected) {
            case 1:
                System.out.println("💰 Cantidad que desea convertir: ");
                amount = input.nextDouble();
                baseCurrency = "USD";
                targetCurrency = "ARS";
                break;
            case 2:
                System.out.println("💰 Cantidad que desea convertir: ");
                amount = input.nextDouble();
                baseCurrency = "ARS";
                targetCurrency = "USD";
                break;
            case 3:
                System.out.println("💰 Cantidad que desea convertir: ");
                amount = input.nextDouble();
                baseCurrency = "USD";
                targetCurrency = "BRL";
                break;
            case 4:
                System.out.println("💰 Cantidad que desea convertir: ");
                amount = input.nextDouble();
                baseCurrency = "BRL";
                targetCurrency = "USD";
                break;
            case 5:
                System.out.println("💰 Cantidad que desea convertir: ");
                amount = input.nextDouble();
                baseCurrency = "USD";
                targetCurrency = "COP";
                break;
            case 6:
                System.out.println("💰 Cantidad que desea convertir: ");
                amount = input.nextDouble();
                baseCurrency = "COP";
                targetCurrency = "USD";
                break;
            default:
                if (selected > 7) {
                    System.out.println("🔴 Opción no soportada");
                }
                continue;
        }

        ParesConversion respuesta = apiServices.convertPair(baseCurrency, targetCurrency, amount);
        apiServices.showConversion(respuesta, amount);
    }


  }

}
