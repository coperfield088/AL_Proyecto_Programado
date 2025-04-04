package org.example;
import java.util.*;

public class Main {


    static Scanner scanner = new Scanner(System.in);
    static int[][] matrizA, matrizB; // Matrices globales para operar

    static boolean isBasicOperation = false;
    static  boolean isAlgebraOperation = false;

    static void menu() {
        while (true) {
            System.out.println("\n--- MENÚ DE OPERACIONES CON MATRICES ---");
            System.out.println("1. Instrucciones");
            System.out.println("2. Definir matrices");
            if (isBasicOperation) {
                System.out.println("3. Sumar matrices");
                System.out.println("4. Restar matrices");
                System.out.println("5. Multiplicar matrices");
            } else if (isAlgebraOperation){
                System.out.println("6. Calcular inversa");
                System.out.println("7. Calcular determinante");
                System.out.println("8. Reducción de matriz");
                System.out.println("9. Resolver sistema de ecuaciones");
            }
            if(isAlgebraOperation || isBasicOperation) {
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
                case 2:  definirMatrices(); break;
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
                    calcularDeterminanteGauss();
                    break;
                case 8:
                    reducirMatriz();
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
            System.out.println("Para operaciones de calculo de determinante, inversa, reducción o solución de sistema  seleccione opción 2");
            System.out.println("");
            //System.out.println("1. 2x2");
            //System.out.println("2. 3x3");
            System.out.println("1. Ingresar matrices");
            System.out.println("2. Ingrese matriz");
            System.out.println("");
            System.out.print("********* Ingrese su elección: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

             if (opcion == 1) {
                System.out.print("Ingrese el número de filas de la primera matriz: ");
                filasMatrizA = scanner.nextInt();
                System.out.print("Ingrese el número de columnas de la primera matriz: ");
                columnasMatrizA = scanner.nextInt();
                System.out.print("Ingrese el número de filas de la segunda matriz: ");
                filasMatrizB = scanner.nextInt();
                System.out.print("Ingrese el número de columnas de la segunda matriz: ");
                columnasMatrizB = scanner.nextInt();
                scanner.nextLine();
                matrizA = ingresarMatriz(filasMatrizA, columnasMatrizA);
                matrizB = ingresarMatriz(filasMatrizB, columnasMatrizB);
                isAlgebraOperation = false;
                isBasicOperation =true;
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

                        // Si la dimensión es válida, asignar la matriz
                        matrizA = ingresarMatriz(dimension, dimension);
                        isBasicOperation = false;
                        isAlgebraOperation = true;
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

        // Verificar si la matriz A es cuadrada
        if (matrizA[0].length != n) {
            System.out.println("La matriz de coeficientes debe ser cuadrada.");
            return;
        }

        // Si ya tenemos la matriz, solo pedimos los términos independientes b
        int[] b = new int[n];
        System.out.println("Ingrese los términos independientes (b):");

        for (int i = 0; i < n; i++) {
            while (true) {
                System.out.print("b[" + (i + 1) + "] = ");
                String input = scanner.nextLine().trim();  // Leer entrada como String y eliminar espacios

                try {
                    // Intentar convertir la entrada a un número
                    b[i] = Integer.parseInt(input);
                    break;  // Salir del bucle si la conversión es exitosa
                } catch (NumberFormatException e) {
                    System.out.println("Error: Ingresa un número válido (sin comas ni caracteres no numéricos).");
                }
            }
        }

        // Calcular el determinante de la matriz A
        double detA = calcularDeterminanteGauss(matrizA);
        if (detA == 0) {
            System.out.println("El determinante de la matriz A es cero, el sistema no tiene solución única.");
            menu();
            //return;
        }

        // Resolver el sistema usando la regla de Cramer
        double[] soluciones = new double[n];
        for (int i = 0; i < n; i++) {
            // Crear una copia de la matriz A
            int[][] matrizAux = new int[n][n];
            for (int j = 0; j < n; j++) {
                System.arraycopy(matrizA[j], 0, matrizAux[j], 0, n);
            }

            // Reemplazar la columna i de la matriz A por el vector b
            for (int j = 0; j < n; j++) {
                matrizAux[j][i] = b[j];
            }

            // Calcular el determinante de la matriz modificada
            double detAux = calcularDeterminanteGauss(matrizAux);

            // La solución es el determinante de la matriz modificada dividido entre el determinante de A
            soluciones[i] = detAux / detA;
        }

        // Mostrar las soluciones como fracciones
        System.out.println("Soluciones del sistema:");
        for (int i = 0; i < n; i++) {
            String fraccion = convertirADecimalAFraccion(soluciones[i], 0.0001); // Convertir cada solución a fracción
            System.out.println("x" + (i + 1) + " = " + fraccion); // Mostrar la fracción
        }
        menu();
    }

    static double calcularDeterminanteGauss(int[][] matriz) {
        int n = matriz.length;
        double[][] temp = new double[n][n];

        // Copiar la matriz original a temp como double
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                temp[i][j] = matriz[i][j];
            }
        }

        double det = 1;
        for (int i = 0; i < n; i++) {
            // Si el pivote es cero, intercambiar filas
            if (temp[i][i] == 0) {
                boolean cambiado = false;
                for (int k = i + 1; k < n; k++) {
                    if (temp[k][i] != 0) {
                        double[] aux = temp[i];
                        temp[i] = temp[k];
                        temp[k] = aux;
                        det *= -1; // Cambiar signo del determinante
                        cambiado = true;
                        break;
                    }
                }
                if (!cambiado) {
                    return 0; // Si no se puede cambiar, el determinante es cero
                }
            }

            // Reducir filas debajo del pivote
            for (int k = i + 1; k < n; k++) {
                double factor = temp[k][i] / temp[i][i];
                for (int j = i; j < n; j++) {
                    temp[k][j] -= factor * temp[i][j];
                }
            }

            det *= temp[i][i]; // Multiplicar valores de la diagonal
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
        if (isBasicOperation) {
            if (matrizA == null || matrizB == null) {
                System.out.println("Primero debes definir las matrices.");
                menu();
                return;
            }

            // Imprimir Matriz A
            System.out.println("Matriz A:");
            imprimirMatrizAux(matrizA);

            // Imprimir Matriz B
            System.out.println("Matriz B:");
            imprimirMatrizAux(matrizB);
        }

        if (isAlgebraOperation) {
            if (matrizA == null) {
                System.out.println("Primero debes definir la matriz.");
                menu();
                return;
            }

            // Imprimir Matriz A
            System.out.println("Matriz A:");
            imprimirMatrizAux(matrizA);
        }

        menu();  // Regresar al menú después de imprimir
    }

    // Método auxiliar para imprimir una matriz con formato
    static void imprimirMatrizAux(int[][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            System.out.print(" | ");
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print(matriz[i][j] + (j < matriz[i].length - 1 ? " , " : ""));
            }
            System.out.println(" |"); // Nueva línea después de cada fila
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
        // Comprobar que las matrices A y B están definidas
        if (matrizA == null || matrizB == null) {
            System.out.println("Primero debes definir las matrices.");
            return;
        }

        // Verificar que las matrices tengan el mismo tamaño
        if (matrizA.length != matrizB.length || matrizA[0].length != matrizB[0].length) {
            System.out.println("Las matrices no tienen el mismo tamaño, no se pueden restar.");
            return;
        }

        // Crear la matriz para almacenar la resta
        int[][] matrizResta = new int[matrizA.length][matrizA[0].length];

        // Restar elemento por elemento
        for (int i = 0; i < matrizA.length; i++) {
            for (int j = 0; j < matrizA[i].length; j++) {
                matrizResta[i][j] = matrizA[i][j] - matrizB[i][j];
            }
        }

        // Imprimir la matriz resultado de la resta
        System.out.println("Matriz Resultado de la Resta:");
        printMatrix(matrizResta);
        menu(); // Regresar al menú
    }

    static void multiplicarMatrices() {
        if (matrizA == null || matrizB == null) {
            System.out.println("Primero debes definir las matrices.");
            return;
        }

        // Verificamos si las matrices pueden multiplicarse
        if (matrizA[0].length != matrizB.length) {
            System.out.println("Las matrices no se pueden multiplicar. El número de columnas de la primera matriz debe ser igual al número de filas de la segunda matriz.");
            return;
        }

        // Creamos la matriz resultado con las dimensiones adecuadas
        int[][] matrizResultado = new int[matrizA.length][matrizB[0].length];

        // Realizamos la multiplicación
        for (int i = 0; i < matrizA.length; i++) {
            for (int j = 0; j < matrizB[0].length; j++) {
                for (int k = 0; k < matrizB.length; k++) {
                    matrizResultado[i][j] += matrizA[i][k] * matrizB[k][j];
                }
            }
        }

        // Imprimimos el resultado
        System.out.println("Matriz Resultado de la Multiplicación:");
        printMatrix(matrizResultado);
        menu();  // Volver al menú
    }

    static void calcularDeterminanteGauss() {
        if (matrizA == null) {
            System.out.println("Primero debes definir la matriz.");
            return;
        }

        int n = matrizA.length;

        // Verificar si la matriz es cuadrada
        for (int[] fila : matrizA) {
            if (fila.length != n) {
                System.out.println("El determinante solo se puede calcular para matrices cuadradas.");
                return;
            }
        }

        double[][] temp = new double[n][n];

        // Copiar matrizA a temp como double
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                temp[i][j] = matrizA[i][j];
            }
        }

        double det = 1;
        for (int i = 0; i < n; i++) {
            // Si el pivote es cero, intercambiar filas
            if (temp[i][i] == 0) {
                boolean cambiado = false;
                for (int k = i + 1; k < n; k++) {
                    if (temp[k][i] != 0) {
                        double[] aux = temp[i];
                        temp[i] = temp[k];
                        temp[k] = aux;
                        det *= -1; // Cambiar signo del determinante
                        cambiado = true;
                        break;
                    }
                }
                if (!cambiado) {
                    System.out.println("Determinante: 0");
                    return;
                }
            }

            // Reducir filas debajo del pivote
            for (int k = i + 1; k < n; k++) {
                double factor = temp[k][i] / temp[i][i];
                for (int j = i; j < n; j++) {
                    temp[k][j] -= factor * temp[i][j];
                }
            }

            det *= temp[i][i]; // Multiplicar valores de la diagonal
        }

        // Imprimir el determinante en lugar de retornarlo
        System.out.println("Determinante: " + det);
    }

    static void calcularInversaGaussJordan() {
        if (matrizA == null) {
            System.out.println("Primero debes definir la matriz.");
            return;
        }

        int n = matrizA.length;

        // Verificar si la matriz es cuadrada
        for (int[] fila : matrizA) {
            if (fila.length != n) {
                System.out.println("La matriz debe ser cuadrada para calcular la inversa.");
                return;
            }
        }

        // Crear una matriz identidad de la misma dimensión
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

        // Crear una copia de la matriz original para realizar la eliminación de Gauss-Jordan
        double[][] temp = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                temp[i][j] = matrizA[i][j];
            }
        }

        // Realizar las operaciones de Gauss-Jordan
        for (int i = 0; i < n; i++) {
            // Si el pivote es 0, intercambiar filas
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

            // Normalizar la fila para que el pivote sea 1
            double pivote = temp[i][i];
            for (int j = 0; j < n; j++) {
                temp[i][j] /= pivote;
                matrizInversa[i][j] /= pivote;
            }

            // Eliminar las otras filas usando la fila actual
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

        // Imprimir la matriz inversa
        System.out.println("Matriz inversa:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(String.format("%.2f", matrizInversa[i][j]) + " ");
            }
            System.out.println();
        }
    }

    static void reducirMatriz() {
        if (matrizA == null) {
            System.out.println("Primero debes definir la matriz.");
            return;
        }

        int filas = matrizA.length;
        int columnas = matrizA[0].length;

        // Crear una copia de la matriz para trabajar sobre ella
        double[][] temp = new double[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                temp[i][j] = matrizA[i][j];
            }
        }

        // Realizar la eliminación Gaussiana
        for (int i = 0; i < filas; i++) {
            // Encuentra el pivote (el mayor valor en la columna)
            int pivoteFila = i;
            for (int j = i + 1; j < filas; j++) {
                if (Math.abs(temp[j][i]) > Math.abs(temp[pivoteFila][i])) {
                    pivoteFila = j;
                }
            }

            // Si el pivote es 0, la columna está "bloqueada" y no se puede hacer la reducción
            if (temp[pivoteFila][i] == 0) {
                System.out.println("La matriz tiene filas linealmente dependientes.");
                return;
            }

            // Intercambiar filas para poner el pivote en la fila i
            if (pivoteFila != i) {
                double[] aux = temp[i];
                temp[i] = temp[pivoteFila];
                temp[pivoteFila] = aux;
            }

            // Hacer que todos los elementos debajo del pivote sean 0
            for (int j = i + 1; j < filas; j++) {
                double factor = temp[j][i] / temp[i][i];
                for (int k = i; k < columnas; k++) {
                    temp[j][k] -= factor * temp[i][k];
                }
            }

            // Hacer que todos los elementos encima del pivote sean 0
            for (int j = i - 1; j >= 0; j--) {
                double factor = temp[j][i] / temp[i][i];
                for (int k = i; k < columnas; k++) {
                    temp[j][k] -= factor * temp[i][k];
                }
            }
        }

        // Imprimir la matriz reducida
        System.out.println("Matriz reducida a forma escalonada:");
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                System.out.print(String.format("%.2f", temp[i][j]) + " ");
            }
            System.out.println();
        }
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

