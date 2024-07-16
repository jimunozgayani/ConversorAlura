package Principal;



import modulos.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Principal {
    public static void main(String[] args) throws FileNotFoundException, ConnectException {
        String apiKey= "ab3580054f5e25c54b528904";
        String dirApi = "https://v6.exchangerate-api.com/v6/*/latest/*";
        Map<Integer,String> map = new HashMap<>();
        map.put(1,"USDARS");
        map.put(2,"ARSUSD");
        map.put(3,"USDBRL");
        map.put(4,"BRLUSD");
        map.put(5,"USDCLP");
        map.put(6,"CLPUSD");
        map.put(7,"USDMXN");
        map.put(8,"MXNUSD");
        map.put(9,"USDGBP");
        map.put(10,"GBPUSD");

        String menu = """
                ****************************************
                Bienvenido(a) al Conversor de Moneda  :D
                
                1) Dólar -----------> Peso Argentino
                2) Peso Argentino --> Dólar
                3) Dólar -----------> Real Brasileño
                4) Real Brasileño --> Dólar
                5) Dólar -----------> Peso Chileno
                6) Peso Chileno -> Dólar
                7) Dólar -----------> Peso Mexicano
                8) Peso Mexicano ---> Dólar
                9) Dólar -----------> Libra
                10) Libra ----------> Dólar
                11) ----- Salir ----------------------
                
                Elija una opción del menú:
                ****************************************""";

        String ejemploJson;

        ConectApi conectApi = new ConectApi();
        JsonParsing jsonParsing = new JsonParsing();
        Divisa divisa;
        Monedas monedas;
        Scanner scanner = new Scanner(System.in);
        Pattern pattern = Pattern.compile("[^(a-z0-9)]", Pattern.LITERAL);
        Matcher matcher;

        try {


                ejemploJson = conectApi.getRespuesta(crearPeticion(dirApi, apiKey, "USD"));
            } catch (FileNotFoundException e){
                throw new FileNotFoundException();
            } catch (ConnectException e){
                throw new ConnectException();
            } catch (InterruptedException | IOException e) {
                throw new RuntimeException(e);
            }

            monedas = jsonParsing.getMonedas(ejemploJson);
            divisa = new Divisa(monedas);
            double entrada;
            int opcion;

            do {

                System.out.println(menu);

                try {

                    opcion = scanner.nextInt();

                    if (opcion < 1 || opcion > 10){
                        if (opcion == 11){break;}
                        System.out.println("Opción no válida. Intente de nuevo.");
                        continue;
                    }
                    System.out.println("Ingrese el valor que desea convertir");
                    entrada = scanner.nextDouble();
                    divisa.setBase_code(map.get(opcion), entrada);
                    System.out.println(divisa);

                }catch (InputMismatchException e){
                    System.out.println("Error con el valor ingresado. Intente de nuevo.");
                }
                scanner.nextLine();

            } while (true);

//        }catch (IllegalArgumentException e){
//            System.out.println("Error: Verifique la dirección de la API." + e.getMessage());
////        }catch (NullPointerException e){
////            System.out.println("Error: Verifique su API KEY.");
////        }catch (FileNotFoundException e){
////            System.out.println("Error: Verifique el archivo apiKey");
//        }catch (ConnectException e){
//            System.out.println("Error de conexion.");
//        }

        scanner.close();
        System.out.println("Hasta pronto! Programa finalizado.");

    }

    private static String crearPeticion(String dir, String... args){
        for (String temp : args){
            dir = dir.replaceFirst("\\*", temp);
        }
        return dir;
    }
}