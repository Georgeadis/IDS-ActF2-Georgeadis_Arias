package aplicacionsimple;
// Paquete principal de la aplicacion

public class Principal {
    // Clase Principal que contiene el punto de entrada del programa

    public static void main(String[] args) {
        // Metodo main: punto de inicio de la aplicacion Java

        javax.swing.SwingUtilities.invokeLater(() -> new VentanaPrincipal().setVisible(true));
        // Ejecuta la creacion de la interfaz grafica en el hilo de eventos de Swing
        // Se crea un objeto de la clase VentanaPrincipal y se hace visible
    }
}
