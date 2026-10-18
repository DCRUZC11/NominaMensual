package aplicacion;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

import modelo.Empleado;
import modelo.EmpleadoFijo;
import modelo.EmpleadoPorHora;

/*
 * Autor: Denis Alejandro Cruz Castañeda
 * Carné: 7690-22-9100
 * Práctica 11: Nómina mensual.
 *
 * EVIDENCIA DE CONCEPTOS:
 *
 * Proyecto: NominaMensual.
 * Packages: modelo organiza empleados; aplicacion contiene Main.
 * Imports: permiten usar clases de otros paquetes.
 *
 * Clase: define atributos y comportamiento.
 * Objeto o instancia: cada empleado creado con new.
 * Instanciación: new EmpleadoFijo(...) o new EmpleadoPorHora(...).
 *
 * Constructores: reciben los datos iniciales de los empleados.
 * Parámetro: sueldoMensual en el constructor de EmpleadoFijo.
 * Argumento: sueldo, enviado al ejecutar new EmpleadoFijo(...).
 * this: distingue el atributo del parámetro en los constructores.
 * super: llama al constructor de Empleado desde sus clases hijas.
 *
 * Encapsulación: todos los atributos son private; se consultan con getters.
 * Abstracción: Empleado es abstract y declara calcularPago().
 * Herencia: las clases hijas usan extends Empleado.
 * Sobrescritura: @Override implementa los métodos de la clase padre.
 * Polimorfismo: el reporte llama calcularPago() mediante una referencia
 * Empleado; cada objeto ejecuta su propia fórmula.
 *
 * Arreglo simple: String[] meses contiene doce nombres fijos.
 * Acceso al mes: meses[numeroMes - 1].
 * ArrayList: empleados contiene ambos tipos de trabajadores.
 * add(): agrega un empleado.
 * size(): obtiene la cantidad de trabajadores.
 * Recorrido: for (Empleado empleado : empleados).
 *
 * Métodos: main inicia el programa y delega tareas.
 * leerEntero recibe parámetros y devuelve un resultado.
 *
 * Ámbito:
 * - numeroMes es un atributo accesible en los métodos de Main.
 * - minimo es un parámetro disponible dentro de leerEntero.
 * - total es una variable local disponible solo en imprimirNomina.
 *
 * Caso esperado:
 * Marzo; Ana: 4000 + 300 = Q 4300.00.
 * Luis: 25 * 120 = Q 3000.00.
 * Dos trabajadores; total Q 7300.00.
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static final String[] meses = {
        "Enero", "Febrero", "Marzo", "Abril",
        "Mayo", "Junio", "Julio", "Agosto",
        "Septiembre", "Octubre", "Noviembre", "Diciembre"
    };

    private static final ArrayList<Empleado> empleados = new ArrayList<>();

    private static int numeroMes;

    public static void main(String[] args) {
        System.out.println("CÁLCULO DE NÓMINA");

        numeroMes = leerEntero(
                "Ingrese el mes a trabajar (1-12): ", 1, 12);

        boolean continuar = true;

        while (continuar) {
            limpiarPantalla();
            mostrarTitulo();

            System.out.println("\n1 - Ingreso de nómina");
            System.out.println("2 - Impresión de nómina");
            System.out.println("3 - Salir");

            int opcion = leerEntero("\nSeleccione una opción: ", 1, 3);

            switch (opcion) {
                case 1:
                    ingresarNomina();
                    break;
                case 2:
                    imprimirNomina();
                    break;
                case 3:
                    System.out.println(
                            "\nGracias por utilizar el sistema de nómina.");
                    continuar = false;
                    break;
            }
        }

        scanner.close();
    }

    private static void mostrarTitulo() {
        System.out.println(
                "NÓMINA DEL MES: "
                + meses[numeroMes - 1].toUpperCase(Locale.ROOT));
    }

    private static void ingresarNomina() {
        boolean ingresarOtro = true;

        while (ingresarOtro) {
            limpiarPantalla();
            mostrarTitulo();

            System.out.println("INGRESO DE EMPLEADO A NÓMINA");
            System.out.println(
                    "Trabajadores actuales = " + empleados.size());

            System.out.println("\n1 - Empleado fijo");
            System.out.println("2 - Empleado por hora");

            int tipo = leerEntero(
                    "\nSeleccione el tipo de empleado: ", 1, 2);

            limpiarPantalla();
            mostrarTitulo();

            if (tipo == 1) {
                System.out.println("INGRESO DE EMPLEADO FIJO\n");
            } else {
                System.out.println("INGRESO DE EMPLEADO POR HORA\n");
            }

            String dpi = leerDpi();
            String nombre = leerNombre();

            Empleado empleado;

            if (tipo == 1) {
                double sueldo = leerNoNegativo("Sueldo mensual (Q): ");
                double bonificacion = leerNoNegativo(
                        "Bonificación de este mes (Q): ");

                // Los valores enviados son argumentos del constructor.
                empleado = new EmpleadoFijo(
                        dpi, nombre, sueldo, bonificacion);

                System.out.println(
                        "\nPago del mes = sueldo mensual + bonificación");
            } else {
                double tarifa = leerNoNegativo("Pago por hora (Q): ");
                double horas = leerNoNegativo(
                        "Horas trabajadas este mes: ");

               empleado = new EmpleadoPorHora(dpi, nombre, tarifa, horas);

                System.out.println(
                        "\nPago del mes = pago por hora x horas trabajadas");
            }

            // Se guarda únicamente después de validar todos los datos.
            empleados.add(empleado);

            System.out.printf(
                    Locale.US,
                    "Pago de %s = Q %.2f%n",
                    empleado.getNombre(),
                    empleado.calcularPago());

            System.out.println("\nEmpleado agregado correctamente.");
            System.out.println(
                    "Trabajadores actuales = " + empleados.size());

            System.out.println("\n¿Desea ingresar otro empleado?");
            System.out.println("1 - Sí, ingresar otro empleado");
            System.out.println("2 - Volver al menú principal");

            ingresarOtro = leerEntero(
                    "\nSeleccione una opción: ", 1, 2) == 1;
        }
    }

    private static void imprimirNomina() {
        limpiarPantalla();
        mostrarTitulo();

        double total = 0;

        if (empleados.isEmpty()) {
            System.out.println(
                    "\nNo hay empleados registrados en la nómina de este mes");
        } else {
            System.out.printf(
                    "%n%-15s %-30s %-12s %12s%n",
                    "DPI", "Nombre", "Tipo", "Pago (Q)");

            System.out.println(
                    "--------------------------------------------------------------------------");

            // Polimorfismo: no se usan condiciones para elegir la fórmula.
            for (Empleado empleado : empleados) {
                double pago = empleado.calcularPago();
                total += pago;

                System.out.printf(
                        Locale.US,
                        "%-15s %-30s %-12s %12.2f%n",
                        empleado.getDpi(),
                        empleado.getNombre(),
                        empleado.obtenerTipo(),
                        pago);
            }
        }

        System.out.println(
                "\nTotal de trabajadores: " + empleados.size());

        System.out.printf(
                Locale.US, "Total de nómina: Q %.2f%n", total);

        System.out.println(
                "\nPresione Enter para volver al menú principal...");

        scanner.nextLine();
    }

    private static int leerEntero(
            String mensaje, int minimo, int maximo) {

        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine().trim();

            try {
                int valor = Integer.parseInt(entrada);

                if (valor >= minimo && valor <= maximo) {
                    return valor;
                }

                System.out.println(
                        "Error: ingrese un número entre "
                        + minimo + " y " + maximo + ".");
            } catch (NumberFormatException e) {
                System.out.println("Error: ingrese un número entero válido.");
            }
        }
    }

    private static double leerNoNegativo(String mensaje) {
        while (true) {
            System.out.print(mensaje);

            // Se permite punto o coma como separador decimal, sin miles.
            String entrada = scanner.nextLine().trim().replace(',', '.');

            try {
                double valor = Double.parseDouble(entrada);

                if (Double.isFinite(valor) && valor >= 0) {
                    return valor;
                }

                System.out.println(
                        "Error: ingrese un número finito mayor o igual a 0.");
            } catch (NumberFormatException e) {
                System.out.println(
                        "Error: ingrese un valor numérico válido.");
            }
        }
    }

    private static String leerDpi() {
        while (true) {
            System.out.print("DPI: ");
            String dpi = scanner.nextLine().trim();

            if (dpi.matches("[0-9]{13}")) {
                return dpi;
            }

            System.out.println(
                    "Error: el DPI debe contener exactamente 13 dígitos.");
        }
    }

    private static String leerNombre() {
        while (true) {
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine().trim();

            if (!nombre.isEmpty()) {
                return nombre;
            }

            System.out.println("Error: el nombre no puede estar vacío.");
        }
    }

    private static void limpiarPantalla() {
        // El enunciado permite simular la limpieza en la consola del IDE.
        for (int i = 0; i < 30; i++) {
            System.out.println();
        }
    }
}