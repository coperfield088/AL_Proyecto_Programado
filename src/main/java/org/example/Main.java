package org.example;

import java.util.*;

public class Main {


    static Scanner scanner = new Scanner(System.in);
    static int[][] matrizA, matrizB;

    static boolean isAOperation = false;
    static boolean isBOperation = false;

    static void menu() {
        while (true) {
            System.out.println("\n--- MENÚ DE OPERACIONES CON MATRICES ---");
            System.out.println("1. Instrucciones");
            System.out.println("2. Definir matrices");
            if (isAOperation) {
                System.out.println("3. Sumar matrices");
                System.out.println("4. Restar matrices");
                System.out.println("5. Multiplicar matrices");
            } else if (isBOperation) {
                System.out.println("6. Calcular inversa - Gauss-Jordan");
                System.out.println("7. Calcular determinante");
                System.out.println("8. Multiplicar por escalar");
                System.out.println("9. Resolver sistema de ecuaciones ( Cramer )");
            }
            if (isBOperation || isAOperation) {
                System.out.println("10. Imprimir marices");
            }
            System.out.println("11. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    System.out.println("Instrucciones: Defina matrices antes de operar.");
                    System.out.println("");
                    System.out.println("-- Las matrices solo pueden sumarse o restarse si tienen" +
                            "\n las mismas dimensiones. Es decir, las matrices deben tener el mismo número de filas y columnas.");
                    System.out.println("");
                    System.out.println("-- Para la multiplicación de matrices " +
                            "\n el número de columnas de la primera matriz debe ser igual al número de filas de la segunda matriz.");
                    System.out.println("");
                    System.out.println("-- Para las otras operaciones en el menu " +
                            "\n debera ingresar una matriz");
                    System.out.println("");
                    System.out.println("-- El sistema le mostrara los mensajes de error respectivos, \n podrá volver a definir la matriz en caso de que sea necesario ");

                    break;
                case 2:
                    definirMatrices();
                    break;
                case 3:
                    if (matrizA != null && matrizB != null) {
                        System.out.println("Sumando matrices...");
                        sumMatrices();
                    } else {
                        System.out.println("Primero debes definir las matrices.");
                    }
                    break;
                case 4:
                    if (matrizA != null && matrizB != null) {
                        System.out.println("Restando matrices...");
                        restarMatrices();
                    } else {
                        System.out.println("Primero debes definir las matrices.");
                    }
                    break;
                case 5:
                    if (matrizA != null && matrizB != null) {
                        System.out.println("Multiplicando matrices...");
                        multiplicarMatrices();
                    } else {
                        System.out.println("Primero debes definir las matrices.");
                    }
                    break;
                case 6:
                    calcularInversaGaussJordan();
                    break;
                case 7:
                    calcularDeterminante();
                    break;
                case 8:
                    multiplicarPorEscalar();
                    break;
                case 9:
                    resolverSistemaEcuaciones();
                    return;
                case 10:
                    imprimirMatricesConFormato();
                    return;
                case 11:
                    System.out.println("Saliendo...");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Opción no válida, intente de nuevo.");
            }
        }
    }

    static void definirMatrices() {
        int filas = 0, columnas = 0, dimension = 0;
        int filasMatrizA = 0, columnasMatrizA = 0;
        int filasMatrizB = 0, columnasMatrizB = 0;
        while (true) {
            System.out.println("\n--- Seleccione el tamaño de la matriz ---");
            System.out.println("Para operaciones de multiplicación, suma y resta seleccione opciones 1");
            System.out.println("");
            System.out.println("Para operaciones de calculo de determinante, inversa, \n multiplicacion por escalar  o solución de sistema  seleccione opción 2");
            System.out.println("");
            System.out.println("1. Ingresar matrices");
            System.out.println("2. Ingrese matriz");
            System.out.println("");
            System.out.print("********* Ingrese su elección: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            if (opcion == 1) {
                boolean entradaValida = false;

                while (!entradaValida) {
                    try {
                        System.out.print("Ingrese el número de filas de la primera matriz: ");
                        filasMatrizA = scanner.nextInt();

                        System.out.print("Ingrese el número de columnas de la primera matriz: ");
                        columnasMatrizA = scanner.nextInt();

                        System.out.print("Ingrese el número de filas de la segunda matriz: ");
                        filasMatrizB = scanner.nextInt();

                        System.out.print("Ingrese el número de columnas de la segunda matriz: ");
                        columnasMatrizB = scanner.nextInt();

                        scanner.nextLine();

                        if (columnasMatrizA != filasMatrizB) {
                            System.out.println("Error: El número de columnas de la primera matriz debe ser igual al número de filas de la segunda matriz.");
                            continue;
                        }

                        entradaValida = true;

                        matrizA = ingresarMatriz(filasMatrizA, columnasMatrizA);
                        matrizB = ingresarMatriz(filasMatrizB, columnasMatrizB);
                        isBOperation = false;
                        isAOperation = true;

                    } catch (InputMismatchException e) {
                        System.out.println("Error: Entrada no válida. Por favor, ingrese un número entero.");
                        scanner.nextLine();
                    }
                }

                break;
            } else if (opcion == 2) {

                while (true) {
                    try {
                        System.out.print("Ingrese la dimensión de la matriz: ");
                        dimension = Integer.parseInt(scanner.nextLine().trim());

                        if (dimension <= 0) {
                            System.out.println("La dimensión debe ser un número mayor que cero. Intente nuevamente.");
                            continue;
                        }

                        matrizA = ingresarMatriz(dimension, dimension);
                        isAOperation = false;
                        isBOperation = true;
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Error: La dimensión debe ser un número entero válido.");
                    }
                }
                break;

            } else {
                System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    static void resolverSistemaEcuaciones() {
        if (matrizA == null) {
            System.out.println("Primero debes definir la matriz de coeficientes.");
            return;
        }
        int n = matrizA.length;

        if (matrizA[0].length != n) {
            System.out.println("La matriz de coeficientes debe ser cuadrada.");
            return;
        }

        int[] b = new int[n];
        System.out.println("Ingrese los términos independientes (b), para aumentar la matriz:");

        for (int i = 0; i < n; i++) {
            while (true) {
                System.out.print("b[" + (i + 1) + "] = ");
                String input = scanner.nextLine().trim();

                try {
                    b[i] = Integer.parseInt(input);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Error: Ingresa un número válido (sin comas ni caracteres no numéricos).");
                }
            }
        }

        double detA = calcularDeterminante(matrizA);
        if (detA == 0) {
            System.out.println("El determinante de la matriz A es cero, el sistema no tiene solución única.");
            menu();
            //return;
        }

        double[] soluciones = new double[n];
        for (int i = 0; i < n; i++) {
            int[][] matrizAux = new int[n][n];
            for (int j = 0; j < n; j++) {
                System.arraycopy(matrizA[j], 0, matrizAux[j], 0, n);
            }

            for (int j = 0; j < n; j++) {
                matrizAux[j][i] = b[j];
            }
            double detAux = calcularDeterminante(matrizAux);

            soluciones[i] = detAux / detA;
        }

        System.out.println("Soluciones del sistema:");
        for (int i = 0; i < n; i++) {
            String fraccion = convertirADecimalAFraccion(soluciones[i], 0.0001);
            System.out.println("x" + (i + 1) + " = " + fraccion);
        }
        menu();
    }

    static double calcularDeterminante(int[][] matriz) {
        int n = matriz.length;
        double[][] temp = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                temp[i][j] = matriz[i][j];
            }
        }

        double det = 1;
        for (int i = 0; i < n; i++) {
            if (temp[i][i] == 0) {
                boolean cambiado = false;
                for (int k = i + 1; k < n; k++) {
                    if (temp[k][i] != 0) {
                        double[] aux = temp[i];
                        temp[i] = temp[k];
                        temp[k] = aux;
                        det *= -1;
                        cambiado = true;
                        break;
                    }
                }
                if (!cambiado) {
                    return 0;
                }
            }

            for (int k = i + 1; k < n; k++) {
                double factor = temp[k][i] / temp[i][i];
                for (int j = i; j < n; j++) {
                    temp[k][j] -= factor * temp[i][j];
                }
            }

            det *= temp[i][i];
        }

        return det;
    }

    static int[][] ingresarMatriz(int filas, int columnas) {
        int[][] matriz = new int[filas][columnas];

        for (int i = 0; i < filas; i++) {
            while (true) {
                System.out.print("Ingrese los " + columnas + " valores de la fila " + (i + 1) + " separados por comas: ");
                String[] valores = scanner.nextLine().split(",");

                if (valores.length != columnas) {
                    System.out.println("Error: Debes ingresar exactamente " + columnas + " valores.");
                    continue;
                }

                try {
                    for (int j = 0; j < columnas; j++) {
                        matriz[i][j] = Integer.parseInt(valores[j].trim());
                    }
                    break; // Salir del ciclo si se ingresaron correctamente los valores
                } catch (NumberFormatException e) {
                    System.out.println("Error: Ingresa solo números.");
                }
            }
        }

        System.out.println("");
        System.out.println("Matriz ingresada correctamente:");
        System.out.println("");
        printMatrix(matriz);
        System.out.println("");
        return matriz;
    }

    static void printMatrix(int[][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
    }

    static void imprimirMatricesConFormato() {
        if (isAOperation) {
            if (matrizA == null || matrizB == null) {
                System.out.println("Primero debes definir las matrices.");
                menu();
                return;
            }

            System.out.println("Matriz A:");
            imprimirMatrizAux(matrizA);

            System.out.println("Matriz B:");
            imprimirMatrizAux(matrizB);
        }

        if (isBOperation) {
            if (matrizA == null) {
                System.out.println("Primero debes definir la matriz.");
                menu();
                return;
            }

            System.out.println("Matriz A:");
            imprimirMatrizAux(matrizA);
        }

        menu();
    }

    static void imprimirMatrizAux(int[][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            System.out.print(" | ");
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print(matriz[i][j] + (j < matriz[i].length - 1 ? " , " : ""));
            }
            System.out.println(" |");
        }
    }

    static void sumMatrices() {

        if (matrizA == null || matrizB == null) {
            System.out.println("Primero debes definir las matrices.");
            return;
        }

        if (matrizA.length != matrizB.length || matrizA[0].length != matrizB[0].length) {
            System.out.println("Las matrices no tienen el mismo tamaño, no se pueden sumar.");
            return;
        }

        int[][] matrizSuma = new int[matrizA.length][matrizA[0].length];

        for (int i = 0; i < matrizA.length; i++) {
            for (int j = 0; j < matrizA[i].length; j++) {
                matrizSuma[i][j] = matrizA[i][j] + matrizB[i][j];
            }
        }

        System.out.println("Matriz Resultado de la Suma:");
        printMatrix(matrizSuma);
        menu();
    }

    static void restarMatrices() {

        if (matrizA == null || matrizB == null) {
            System.out.println("Primero debes definir las matrices.");
            return;
        }

        if (matrizA.length != matrizB.length || matrizA[0].length != matrizB[0].length) {
            System.out.println("Las matrices no tienen el mismo tamaño, no se pueden restar.");
            return;
        }

        int[][] matrizResta = new int[matrizA.length][matrizA[0].length];

        for (int i = 0; i < matrizA.length; i++) {
            for (int j = 0; j < matrizA[i].length; j++) {
                matrizResta[i][j] = matrizA[i][j] - matrizB[i][j];
            }
        }

        System.out.println("Matriz Resultado de la Resta:");
        printMatrix(matrizResta);
        menu();
    }

    static void multiplicarMatrices() {
        if (matrizA == null || matrizB == null) {
            System.out.println("Primero debes definir las matrices.");
            return;
        }

        if (matrizA[0].length != matrizB.length) {
            System.out.println("Las matrices no se pueden multiplicar. El número de columnas de la primera matriz debe ser igual al número de filas de la segunda matriz.");
            return;
        }

        int[][] matrizResultado = new int[matrizA.length][matrizB[0].length];

        for (int i = 0; i < matrizA.length; i++) {
            for (int j = 0; j < matrizB[0].length; j++) {
                for (int k = 0; k < matrizB.length; k++) {
                    matrizResultado[i][j] += matrizA[i][k] * matrizB[k][j];
                }
            }
        }

        System.out.println("Matriz Resultado de la Multiplicación:");
        printMatrix(matrizResultado);
        menu();
    }

    static void calcularDeterminante() {
        if (matrizA == null) {
            System.out.println("Primero debes definir la matriz.");
            return;
        }

        int n = matrizA.length;

        for (int[] fila : matrizA) {
            if (fila.length != n) {
                System.out.println("El determinante solo se puede calcular para matrices cuadradas.");
                return;
            }
        }

        double[][] temp = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                temp[i][j] = matrizA[i][j];
            }
        }

        double det = 1;
        for (int i = 0; i < n; i++) {
            if (temp[i][i] == 0) {
                boolean cambiado = false;
                for (int k = i + 1; k < n; k++) {
                    if (temp[k][i] != 0) {
                        double[] aux = temp[i];
                        temp[i] = temp[k];
                        temp[k] = aux;
                        det *= -1;
                        cambiado = true;
                        break;
                    }
                }
                if (!cambiado) {
                    System.out.println("Determinante: 0");
                    return;
                }
            }

            for (int k = i + 1; k < n; k++) {
                double factor = temp[k][i] / temp[i][i];
                for (int j = i; j < n; j++) {
                    temp[k][j] -= factor * temp[i][j];
                }
            }

            det *= temp[i][i];
        }

        System.out.println("Determinante: " + det);
    }

    static void calcularInversaGaussJordan() {
        if (matrizA == null) {
            System.out.println("Primero debes definir la matriz.");
            return;
        }

        int n = matrizA.length;

        for (int[] fila : matrizA) {
            if (fila.length != n) {
                System.out.println("La matriz debe ser cuadrada para calcular la inversa.");
                return;
            }
        }

        double[][] matrizInversa = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    matrizInversa[i][j] = 1;
                } else {
                    matrizInversa[i][j] = 0;
                }
            }
        }

        double[][] temp = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                temp[i][j] = matrizA[i][j];
            }
        }

        for (int i = 0; i < n; i++) {

            if (temp[i][i] == 0) {
                boolean cambiado = false;
                for (int k = i + 1; k < n; k++) {
                    if (temp[k][i] != 0) {
                        // Intercambiar filas
                        double[] aux = temp[i];
                        temp[i] = temp[k];
                        temp[k] = aux;

                        double[] aux2 = matrizInversa[i];
                        matrizInversa[i] = matrizInversa[k];
                        matrizInversa[k] = aux2;

                        cambiado = true;
                        break;
                    }
                }
                if (!cambiado) {
                    System.out.println("La matriz es singular, no tiene inversa.");
                    return;
                }
            }

            double pivote = temp[i][i];
            for (int j = 0; j < n; j++) {
                temp[i][j] /= pivote;
                matrizInversa[i][j] /= pivote;
            }

            for (int k = 0; k < n; k++) {
                if (k != i) {
                    double factor = temp[k][i];
                    for (int j = 0; j < n; j++) {
                        temp[k][j] -= factor * temp[i][j];
                        matrizInversa[k][j] -= factor * matrizInversa[i][j];
                    }
                }
            }
        }

        System.out.println("Matriz inversa:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(String.format("%.2f", matrizInversa[i][j]) + " ");
            }
            System.out.println();
        }
    }

    static void multiplicarPorEscalar() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingresa el escalar por el que deseas multiplicar la matriz: ");
        int escalar = scanner.nextInt();

        int filas = matrizA.length;
        int columnas = matrizA[0].length;
        int[][] resultado = new int[filas][columnas];

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                resultado[i][j] = matrizA[i][j] * escalar;
            }
        }

        System.out.println("Matriz resultante:");
        printMatrix(resultado);
    }

    public static String convertirADecimalAFraccion(double numeroDecimal, double umbralError) {
        // Determina el signo del número
        int signo = (numeroDecimal < 0) ? -1 : 1;
        numeroDecimal = Math.abs(numeroDecimal);

        // Inicializa la mejor aproximación
        double numeradorOptimo = 1;
        double denominadorOptimo = 1;
        double errorOptimo = Math.abs(numeroDecimal - (numeradorOptimo / denominadorOptimo));

        double numerador, denominador = 1;

        // Calcula la mejor aproximación mientras el denominador sea menor a 10,000 y el error sea mayor que el umbral
        while (denominador < 10000 && errorOptimo > umbralError) {
            numerador = Math.floor(numeroDecimal * denominador + 0.5); // Redondea el numerador
            double error = Math.abs(numeroDecimal - (numerador / denominador)); // Calcula el error
            if (error < errorOptimo) {
                numeradorOptimo = numerador;
                denominadorOptimo = denominador;
                errorOptimo = error;
            }
            denominador++;
        }

        // Obtiene el numerador y denominador como enteros
        int numFinal = (int) numeradorOptimo;
        int denFinal = (int) denominadorOptimo;

        // Simplifica la fracción utilizando el máximo común divisor (mcd)
        int divisorComun = calcularMCD(numFinal, denFinal);
        numFinal /= divisorComun;
        denFinal /= divisorComun;

        // Devuelve la fracción con el signo correspondiente
        return (signo * numFinal) + "/" + denFinal;
    }

    public static int calcularMCD(int a, int b) {
        while (b != 0) {
            int temporal = b;
            b = a % b;
            a = temporal;
        }
        return a;
    }


    public static void main(String[] args) {
        menu();
    }
}

