package aplicacionsimple;
// Paquete donde se agrupan las clases principales de la aplicacion

import javax.swing.*;
// Libreria Swing para crear interfaces graficas

import java.awt.*;
// Libreria AWT para layouts y componentes graficos

import java.util.Date;
// Clase Date para el manejo de fechas

public class VentanaPrincipal extends JFrame {
    // Clase VentanaPrincipal
    // Hereda de JFrame para crear una ventana principal con interfaz grafica

    private JTextField txtNombre, txtApellidos;
    // Campos de texto donde el usuario ingresa el nombre y apellidos del alumno

    private JSpinner spFecha;
    // Componente spinner para seleccionar la fecha de nacimiento

    private JComboBox<String> cbPrograma;
    // Lista desplegable para seleccionar la licenciatura o maestria

    public VentanaPrincipal() {
        // Constructor de la clase
        // Se ejecuta cuando se crea un objeto VentanaPrincipal

        setTitle("Universidad Ciudadana de Nuevo Leon");
        // Define el titulo de la ventana

        setSize(700, 400);
        // Establece el tamaño de la ventana en pixeles

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Finaliza la aplicacion cuando se cierra la ventana

        setLayout(new GridLayout(7, 2, 10, 10));
        // Define el diseño de la ventana:
        // 7 filas, 2 columnas, con separacion horizontal y vertical

        txtNombre = new JTextField();
        txtApellidos = new JTextField();
        // Inicializa los campos de texto vacios

        spFecha = new JSpinner(new SpinnerDateModel());
        // Inicializa el spinner con un modelo de fechas

        spFecha.setEditor(new JSpinner.DateEditor(spFecha, "dd/MM/yyyy"));
        // Establece el formato de fecha dia/mes/año

        // Boton que permite abrir un selector de fecha adicional
        JButton btnCalendario = new JButton("📅");
        // icono visual del boton calendario

        btnCalendario.setToolTipText("Seleccionar fecha");
        // Texto que aparece al pasar el mouse sobre el boton

        btnCalendario.addActionListener(e -> {
            // Accion que se ejecuta al presionar el boton calendario

            Date d = showDatePicker((Date) spFecha.getValue());
            // Llama al metodo que muestra el selector de fecha personalizado

            if (d != null) spFecha.setValue(d);
            // Si se selecciona una fecha valida, se asigna al spinner
        });

        cbPrograma = new JComboBox<>(new String[]{
            // Programas academicos disponibles para inscripcion
            "Licenciatura en Administracion con acentuacion en Empresas",
            "Licenciatura en Ingenieria en Desarrollo de Software",
            "Licenciatura en Logistica, Aduanas y Comercio",
            "Licenciatura en Derecho",
            "Licenciatura en Marketing y Gestion de Ventas",
            "Licenciatura en Educacion con acentuacion en Tecnologia Educativa",
            "Maestria en Administracion con acentuacion en Finanzas",
            "Maestria en Administracion con acentuacion en Planeacion Estrategica"
        });

        // Botones principales de la aplicacion
        JButton btnGuardar = new JButton("Guardar");
        // Guarda la informacion del alumno

        JButton btnListado = new JButton("Listado de alumnos");
        // Muestra todos los alumnos registrados

        JButton btnLic = new JButton("Alumnos por licenciatura");
        // Muestra solo alumnos inscritos en licenciaturas

        JButton btnMtr = new JButton("Alumnos por maestria");
        // Muestra solo alumnos inscritos en maestrias

        JButton btnSalir = new JButton("Salir");
        // Cierra la aplicacion

        // Etiquetas descriptivas y campos de entrada
        add(new JLabel("Nombre:"));
        add(txtNombre);

        add(new JLabel("Apellidos:"));
        add(txtApellidos);

        add(new JLabel("Fecha de nacimiento:"));

        // Panel auxiliar que contiene el spinner de fecha y el boton calendario
        JPanel pFecha = new JPanel(new BorderLayout(4, 0));
        // BorderLayout permite colocar componentes uno junto a otro

        pFecha.add(spFecha, BorderLayout.CENTER);
        // Spinner en el centro del panel

        pFecha.add(btnCalendario, BorderLayout.EAST);
        // Boton calendario a la derecha

        add(pFecha);

        add(new JLabel("Programa:"));
        add(cbPrograma);

        // Agrega los botones a la ventana
        add(btnGuardar);
        add(btnListado);
        add(btnLic);
        add(btnMtr);
        add(btnSalir);

        // =====================
        // ASIGNACIoN DE EVENTOS
        // =====================

        btnGuardar.addActionListener(e -> guardarAlumno());
        // Ejecuta el metodo guardarAlumno al presionar Guardar

        btnListado.addActionListener(e -> mostrarListado("TODOS"));
        // Muestra todos los alumnos registrados

        btnLic.addActionListener(e -> mostrarListado("Licenciatura"));
        // Filtra y muestra alumnos de licenciatura

        btnMtr.addActionListener(e -> mostrarListado("Maestria"));
        // Filtra y muestra alumnos de maestria

        btnSalir.addActionListener(e -> dispose());
        // Cierra la ventana actual
    }

    private void guardarAlumno() {
        // Metodo que valida la informacion y guarda un nuevo alumno

        if (txtNombre.getText().isEmpty()
                || txtApellidos.getText().isEmpty()
                || cbPrograma.getSelectedItem() == null) {
            // Verifica que ningun campo obligatorio este vacio

            JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos");
            // Muestra mensaje de advertencia

            return;
        }

        Alumno a = new Alumno(
                txtNombre.getText(),
                txtApellidos.getText(),
                (Date) spFecha.getValue(),
                cbPrograma.getSelectedItem().toString()
        );
        // Crea un objeto Alumno con los datos ingresados

        BaseDatos.agregarAlumno(a);
        // Agrega el alumno a la base de datos en memoria

        JOptionPane.showMessageDialog(this, "Alumno guardado correctamente");
        // Confirma que el alumno fue guardado

        // Limpia los campos para permitir un nuevo registro
        txtNombre.setText("");
        txtApellidos.setText("");
        spFecha.setValue(new Date());
        cbPrograma.setSelectedIndex(0);
        txtNombre.requestFocusInWindow();
        // Coloca el cursor nuevamente en el campo Nombre
    }

    private void mostrarListado(String tipo) {
        // Metodo que genera y muestra listados de alumnos segun el filtro

        StringBuilder sb = new StringBuilder();
        // Permite construir texto de forma eficiente

        for (Alumno a : BaseDatos.alumnos) {
            // Recorre todos los alumnos almacenados

            String programa = a.getPrograma();
            // Obtiene el programa academico del alumno

            if (tipo.equals("TODOS")
                || (tipo.equals("Licenciatura") && programa.startsWith("Licenciatura"))
                || (tipo.equals("Maestria") && programa.startsWith("Maestria"))) {

                sb.append(a).append("\n");
                // Agrega el alumno al listado
            }
        }

        JOptionPane.showMessageDialog(
            this,
            sb.length() > 0
                ? sb.toString()
                : "No hay alumnos registrados en este grupo"
        );
        // Muestra el listado o un mensaje si no hay resultados
    }

    private Date showDatePicker(Date initial) {
        // Metodo que muestra un dialogo personalizado para seleccionar una fecha

        JPanel panel = new JPanel(new GridLayout(2, 3, 4, 4));
        // Panel con disposicion en cuadricula

        java.util.Calendar cal = java.util.Calendar.getInstance();
        // Instancia del calendario del sistema

        if (initial != null) cal.setTime(initial);
        // Inicializa el calendario con la fecha actual

        Integer[] days = new Integer[31];
        for (int i = 0; i < 31; i++) days[i] = i + 1;
        // Crea arreglo de dias del 1 al 31

        String[] months = new java.text.DateFormatSymbols().getMonths();
        // Obtiene los nombres de los meses

        int startYear = 1960;
        int endYear = 2016;
        // Rango de años permitidos

        Integer[] years = new Integer[endYear - startYear + 1];
        for (int i = 0; i < years.length; i++) years[i] = startYear + i;
        // Genera los años disponibles

        JComboBox<Integer> cbDay = new JComboBox<>(days);
        JComboBox<String> cbMonth = new JComboBox<>();
        JComboBox<Integer> cbYear = new JComboBox<>(years);
        // Combos para seleccionar dia, mes y año

        for (int i = 0; i < 12; i++) cbMonth.addItem(months[i]);

        cbDay.setSelectedItem(cal.get(java.util.Calendar.DAY_OF_MONTH));
        cbMonth.setSelectedIndex(cal.get(java.util.Calendar.MONTH));
        cbYear.setSelectedItem(cal.get(java.util.Calendar.YEAR));

        // Etiquetas del selector de fecha
        panel.add(new JLabel("Dia"));
        panel.add(new JLabel("Mes"));
        panel.add(new JLabel("Año"));
        panel.add(cbDay);
        panel.add(cbMonth);
        panel.add(cbYear);

        int res = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Seleccionar fecha",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );
        // Muestra el dialogo de seleccion

        if (res == JOptionPane.OK_OPTION) {
            java.util.Calendar c = java.util.Calendar.getInstance();
            c.set(
                (Integer) cbYear.getSelectedItem(),
                cbMonth.getSelectedIndex(),
                (Integer) cbDay.getSelectedItem(),
                0, 0, 0
            );
            c.set(java.util.Calendar.MILLISECOND, 0);
            return c.getTime();
            // Devuelve la fecha seleccionada
        }

        return null;
        // Devuelve null si el usuario cancela
    }

    public static void main(String[] args) {
        // Metodo principal para ejecutar directamente la ventana

        SwingUtilities.invokeLater(() -> new VentanaPrincipal().setVisible(true));
        // Ejecuta la interfaz grafica en el hilo de eventos de Swing
    }
}
