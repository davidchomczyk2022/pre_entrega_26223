import java.util.ArrayList;
import java.util.Scanner;
public class App {
   
    public static void main(String[] args) {
        
                // Creamos un Scanner para leer lo que el usuario escribe por teclado.
        Scanner scanner = new Scanner(System.in);


        ArrayList<String> articulos = new ArrayList<>();

        
        int opcion;
            mostrarMenu();
        do {
            //leo el valor ingresado por teclado
            opcion = leerEntero(scanner, "Ingrese una opcion 1-5 o 0 para salir: ");
            // Evaluamos la opción con switch. utilizando un metodo ejecutarOpcion
            ejecutarOpcio(scanner, opcion, articulos);  

        } while (opcion != 0);

        // Cerramos el Scanner al final del programa.
        scanner.close();
    }

    /*
     * MÉTODO: ingresarArticulo
     * ------------------------------------------------------
     * Este método se encarga de dar de alta un nuevo artículo.
     *
     * En esta primera versión, el artículo es solo un String.
     *
     * ¿Qué hace?
     * 1) Pide una descripción.
     * 2) Valida que no esté vacía.
     * 3) Verifica que no exista repetida.
     * 4) Si está todo bien, la agrega a la lista.
     */
    public static void ingresarArticulo(Scanner s, ArrayList<String> listaArticulos) {

        System.out.println("\n--- INGRESAR ARTÍCULO ---");

        // Pedimos la descripción utilizando un método auxiliar.
        String descripcion = leerTextoNoVacio(s, "Ingrese la descripción del artículo: ");

        // Antes de agregar el artículo, validamos que no exista ya en la lista.
        if (existeArticulo(listaArticulos, descripcion)) {
            System.out.println("Error: ese artículo ya existe en el sistema.");
            return;
        }

        // Si no existe, lo agregamos a la lista.
        listaArticulos.add(descripcion);

        System.out.println("Artículo ingresado correctamente: " + descripcion);
    }

    /*
     * MÉTODO: listarArticulos
     * ------------------------------------------------------
     * Este método muestra todos los artículos almacenados en memoria.
     *
     * ¿Qué situaciones contempla?
     * - Si la lista está vacía, informa al usuario.
     * - Si hay artículos, los recorre uno por uno y los muestra.
     */
    public static void listarArticulos(ArrayList<String> articulos) {

        System.out.println("\n--- LISTADO DE ARTÍCULOS ---");

        // Si la lista está vacía, no hay nada que mostrar.
        if (articulos.isEmpty()) {
            System.out.println("No hay artículos cargados.");
            return;
        }

        // Recorremos la lista usando un for tradicional para mostrar también la posición.
        for (int i = 0; i < articulos.size(); i++) {
            System.out.println((i + 1) + " - " + articulos.get(i));
        }
    }

    /*
     * MÉTODO: consultarArticulo
     * ------------------------------------------------------
     * Este método permite buscar un artículo por su descripción.
     *
     * En esta versión básica:
     * - no buscamos por código
     * - buscamos por nombre exacto
     *
     * ¿Qué hace?
     * 1) Verifica si la lista tiene datos.
     * 2) Pide la descripción.
     * 3) Busca si existe.
     * 4) Informa el resultado.
     */
    public static void consultarArticulo(Scanner scanner, ArrayList<String> articulos) {

        System.out.println("\n--- CONSULTAR ARTÍCULO ---");

        // Si la lista está vacía, no tiene sentido pedir búsqueda.
        if (articulos.isEmpty()) {
            System.out.println("No hay artículos cargados.");
            return;
        }

        String descripcionBuscada = leerTextoNoVacio(scanner, "Ingrese la descripción del artículo a consultar: ");

        int posicion = buscarPosicionArticulo(articulos, descripcionBuscada);

        if (posicion == -1) {
            System.out.println("El artículo no existe.");
        } else {
            System.out.println("Artículo encontrado en la posición: " + (posicion + 1));
            System.out.println("Descripción: " + articulos.get(posicion));
        }
    }

    /*
     * MÉTODO: modificarArticulo
     * ------------------------------------------------------
     * Este método permite cambiar la descripción de un artículo existente.
     *
     * ¿Qué hace?
     * 1) Verifica si hay artículos cargados.
     * 2) Pide la descripción actual.
     * 3) Busca el artículo.
     * 4) Si existe, pide una nueva descripción.
     * 5) Verifica que la nueva descripción no esté repetida.
     * 6) Reemplaza el valor en la lista.
     */
    public static void modificarArticulo(Scanner scanner, ArrayList<String> articulos) {

        System.out.println("\n--- MODIFICAR ARTÍCULO ---");

        if (articulos.isEmpty()) {
            System.out.println("No hay artículos cargados.");
            return;
        }

        String descripcionActual = leerTextoNoVacio(scanner, "Ingrese la descripción del artículo a modificar: ");

        int posicion = buscarPosicionArticulo(articulos, descripcionActual);

        if (posicion == -1) {
            System.out.println("El artículo no existe.");
            return;
        }

        String nuevaDescripcion = leerTextoNoVacio(scanner, "Ingrese la nueva descripción: ");

        // Si la nueva descripción ya existe y no es exactamente el mismo artículo,
        // no permitimos la modificación.
        if (existeArticulo(articulos, nuevaDescripcion) &&
                !articulos.get(posicion).equalsIgnoreCase(nuevaDescripcion)) {
            System.out.println("Error: ya existe otro artículo con esa descripción.");
            return;
        }

        // Reemplazamos el valor viejo por el nuevo.
        articulos.set(posicion, nuevaDescripcion);

        System.out.println("Artículo modificado correctamente.");
    }

    /*
     * MÉTODO: eliminarArticulo
     * ------------------------------------------------------
     * Este método elimina un artículo de la lista.
     *
     * ¿Qué hace?
     * 1) Verifica si hay artículos.
     * 2) Pide la descripción del artículo a eliminar.
     * 3) Busca su posición.
     * 4) Si existe, lo elimina.
     */
    public static void eliminarArticulo(Scanner scanner, ArrayList<String> articulos) {

        System.out.println("\n--- ELIMINAR ARTÍCULO ---");

        if (articulos.isEmpty()) {
            System.out.println("No hay artículos cargados.");
            return;
        }

        String descripcionAEliminar = leerTextoNoVacio(scanner, "Ingrese la descripción del artículo a eliminar: ");

        int posicion = buscarPosicionArticulo(articulos, descripcionAEliminar);

        if (posicion == -1) {
            System.out.println("El artículo no existe.");
            return;
        }

        // Eliminamos el elemento según su posición.
        articulos.remove(posicion);

        System.out.println("Artículo eliminado correctamente.");
    }

    /*
     * MÉTODO: existeArticulo
     * ------------------------------------------------------
     * Este método responde true o false según el artículo exista o no.
     *
     * Recorre toda la lista comparando sin importar mayúsculas/minúsculas.
     *
     * Ejemplo:
     * "Mouse" y "mouse" se consideran el mismo artículo.
     */
    public static boolean existeArticulo(ArrayList<String> articulos, String descripcion) {
        // buscar una alternativa a este method con algun method de ArrayList, como contains, pero no es case insensitive, entonces lo hacemos a mano
        for (String articulo : articulos) {
            if (articulo.equalsIgnoreCase(descripcion.trim())) {
                return true;
            }
        }

        return false;
    }

    /*
     * MÉTODO: buscarPosicionArticulo
     * ------------------------------------------------------
     * Este método devuelve la posición donde se encuentra un artículo.
     *
     * Si lo encuentra, devuelve el índice real dentro del ArrayList.
     * Si no lo encuentra, devuelve -1.
     *
     * Esto es útil porque:
     * - para modificar necesitamos saber la posición
     * - para eliminar también necesitamos la posición
     */
    public static int buscarPosicionArticulo(ArrayList<String> articulos, String descripcion) {

        for (int i = 0; i < articulos.size(); i++) {
            if (articulos.get(i).equalsIgnoreCase(descripcion.trim())) {
                return i;
            }
        }

        return -1;
    }

    /*
     * MÉTODO: leerEntero
     * ------------------------------------------------------
     * Este método sirve para leer enteros de forma segura.
     *
     * ¿Por qué lo necesitamos?
     * Porque si el usuario escribe letras cuando esperamos un número,
     * el programa no debe romperse.
     */
    // public es la visibilidad que le das a este method por fuera de esta clase
    // static significa que no necesitas crear un objeto de la clase para usarlo
    // int es el tipo de dato que devuelve el método
    // leerEntero es el nombre del método
    // Scanner scanner es el objeto que usaremos para leer desde el teclado
    // String mensaje es el texto que mostraremos al usuario para pedirle el número
    public static int leerEntero(Scanner scanner, String mensaje) {
       // mientras sea true, el programa seguirá pidiendo un número hasta que el usuario ingrese uno válido
        while (true) {
            // manejo de excepcion
            // el bloque try intenta ejecutar el código que puede generar una excepción
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.nextLine());
            // el bloque catch captura la excepción específica que se produce cuando el usuario ingresa un texto que no se puede convertir a entero
            } catch (NumberFormatException e) {
                System.out.println("Error: debe ingresar un número entero válido.");
            }
        }
    }

    /*   */

    /*
     * MÉTODO: leerTextoNoVacio
     * ------------------------------------------------------
     * Este método obliga al usuario a ingresar un texto que no esté vacío.
     *
     * En esta clase todavía no hacemos grandes validaciones,
     * pero al menos evitamos que el alumno ingrese una cadena vacía.
     */
    public static String leerTextoNoVacio(Scanner scanner, String mensaje) {

        while (true) {
            System.out.print(mensaje);
            String texto = scanner.nextLine();
            // texto.trim() elimina los espacios en blanco al principio y al final del texto
            // isEmpty() verifica si el texto está vacío después de eliminar los espacios
            // !true -> false
            // !texto.trim().isEmpty() -> true si el texto no está vacío
            if (!texto.trim().isEmpty()) {
                // voy a retornar a donde fue llamada esta funcion, el texto sin espacios al principio ni al final
                return texto.trim();
            }
             // este mensaje solo se mostrara si el texto esta vacio luego de sacarle los espacios
            System.out.println("Error: el texto no puede estar vacío.");
        }
    }

    public static void mostrarMenu(){
                    // Mostramos el menú principal.
            System.out.println("\n==========================================");
            System.out.println("   SISTEMA BASICO DE ARTICULOS - CLASE 1");
            System.out.println("==========================================");
            System.out.println("1 - Ingresar articulo");
            System.out.println("2 - Listar articulos");
            System.out.println("3 - Consultar un articulo");
            System.out.println("4 - Modificar un articulo");
            System.out.println("5 - Eliminar un articulo");
            System.out.println("0 - Salir");
            System.out.println("==========================================");
    }
    static void ejecutarOpcio( Scanner scanner  ,int opcion, ArrayList<String> articulos){

                    switch (opcion) {
                case 1:
                    ingresarArticulo(scanner, articulos);
                    break;
                case 2:
                    listarArticulos(articulos);
                    break;
                case 3:
                    consultarArticulo(scanner, articulos);
                    break;
                case 4:
                    modificarArticulo(scanner, articulos);
                    break;
                case 5:
                    eliminarArticulo(scanner, articulos);
                    break;
                case 0:
                    System.out.println("\nSaliendo del sistema. ¡Hasta luego!");
                    break;
                default:
                    System.out.println("\nError: la opción ingresada no es válida.");
            }
    }
}
