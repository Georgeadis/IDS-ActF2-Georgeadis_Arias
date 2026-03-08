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
    private JButton btnCalificaciones;
    private JComboBox<String> cbAlumnos;
    private JButton btnActualizarLista;
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private java.util.List<Alumno> cbCurrentAlumnos = new java.util.ArrayList<>();
    private JButton btnBuscarAlumnos;

    public VentanaPrincipal() {
        // Constructor de la clase
        // Se ejecuta cuando se crea un objeto VentanaPrincipal

        setTitle("Universidad Ciudadana de Nuevo Leon");
        // Define el titulo de la ventana

        setSize(1000, 500);
        // Establece el tamaño de la ventana en pixeles

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Finaliza la aplicacion cuando se cierra la ventana

        // Usar GridBagLayout para una mejor alineación y espaciamiento
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 8, 6, 8);

        txtNombre = new JTextField();
        txtApellidos = new JTextField();
        txtNombre.setPreferredSize(new Dimension(320, 26));
        txtApellidos.setPreferredSize(new Dimension(320, 26));

        spFecha = new JSpinner(new SpinnerDateModel());
        spFecha.setEditor(new JSpinner.DateEditor(spFecha, "dd/MM/yyyy"));
        JButton btnCalendario = new JButton("📅");
        btnCalendario.setToolTipText("Seleccionar fecha");
        btnCalendario.addActionListener(e -> {
            Date d = showDatePicker((Date) spFecha.getValue());
            if (d != null) spFecha.setValue(d);
        });

        cbPrograma = new JComboBox<>(new String[]{
            "Licenciatura en Administracion con acentuacion en Empresas",
            "Licenciatura en Ingenieria en Desarrollo de Software",
            "Licenciatura en Logistica, Aduanas y Comercio",
            "Licenciatura en Derecho",
            "Licenciatura en Marketing y Gestion de Ventas",
            "Licenciatura en Educacion con acentuacion en Tecnologia Educativa",
            "Maestria en Administracion con acentuacion en Finanzas",
            "Maestria en Administracion con acentuacion en Planeacion Estrategica"
        });

        JButton btnGuardar = new JButton("Guardar");
        JButton btnListado = new JButton("Listado de alumnos");
        JButton btnLic = new JButton("Alumnos por licenciatura");
        JButton btnMtr = new JButton("Alumnos por maestria");
        JButton btnSalir = new JButton("Salir");

        btnCalificaciones = new JButton("Calificaciones");
        btnCalificaciones.setToolTipText("Ver calificaciones (solo Desarrollo de Software)");
        btnCalificaciones.setVisible(false);

        // Campo de búsqueda y selector de alumnos guardados (ocultos en la vista principal)
        txtBuscar = new JTextField();
        btnBuscar = new JButton("Buscar");
        cbAlumnos = new JComboBox<>();
        btnActualizarLista = new JButton("Actualizar lista");

        JPanel pBuscar = new JPanel(new BorderLayout(4,0));
        pBuscar.add(txtBuscar, BorderLayout.CENTER);
        pBuscar.add(btnBuscar, BorderLayout.EAST);

        JPanel pAl = new JPanel(new BorderLayout(4,0));
        pAl.add(cbAlumnos, BorderLayout.CENTER);
        pAl.add(btnActualizarLista, BorderLayout.EAST);

        btnBuscarAlumnos = new JButton("Buscar alumnos");
        btnBuscarAlumnos.addActionListener(evt -> new VentanaBusqueda(this).setVisible(true));

        // Row 0: search label + field (hidden by default)
        c.gridx = 0; c.gridy = 0; c.anchor = GridBagConstraints.WEST; add(new JLabel("Buscar (nombre/apellido):"), c);
        c.gridx = 1; c.gridy = 0; c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 1.0; add(pBuscar, c);

        // Row 1: alumnos label + selector (hidden by default)
        c.gridx = 0; c.gridy = 1; c.fill = GridBagConstraints.NONE; c.weightx = 0; add(new JLabel("Alumnos:"), c);
        c.gridx = 1; c.gridy = 1; c.fill = GridBagConstraints.HORIZONTAL; add(pAl, c);

        // Row 2: Nombre
        c.gridx = 0; c.gridy = 2; c.fill = GridBagConstraints.NONE; add(new JLabel("Nombre:"), c);
        c.gridx = 1; c.gridy = 2; c.fill = GridBagConstraints.HORIZONTAL; add(txtNombre, c);

        // Row 3: Apellidos
        c.gridx = 0; c.gridy = 3; c.fill = GridBagConstraints.NONE; add(new JLabel("Apellidos:"), c);
        c.gridx = 1; c.gridy = 3; c.fill = GridBagConstraints.HORIZONTAL; add(txtApellidos, c);

        // Row 4: Fecha
        c.gridx = 0; c.gridy = 4; c.fill = GridBagConstraints.NONE; add(new JLabel("Fecha de nacimiento:"), c);
        JPanel pFecha = new JPanel(new BorderLayout(4,0)); pFecha.add(spFecha, BorderLayout.CENTER); pFecha.add(btnCalendario, BorderLayout.EAST);
        c.gridx = 1; c.gridy = 4; c.fill = GridBagConstraints.HORIZONTAL; add(pFecha, c);

        // Row 5: Programa
        c.gridx = 0; c.gridy = 5; c.fill = GridBagConstraints.NONE; add(new JLabel("Programa:"), c);
        c.gridx = 1; c.gridy = 5; c.fill = GridBagConstraints.HORIZONTAL; add(cbPrograma, c);

        // Row 6: botones (panel que abarca dos columnas)
        JPanel pButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        pButtons.add(btnGuardar); pButtons.add(btnListado); pButtons.add(btnLic); pButtons.add(btnMtr); pButtons.add(btnBuscarAlumnos); pButtons.add(btnCalificaciones); pButtons.add(btnSalir);
        c.gridx = 0; c.gridy = 6; c.gridwidth = 2; c.fill = GridBagConstraints.HORIZONTAL; add(pButtons, c);

        // Ocultar los paneles embebidos de búsqueda para mantener la ventana como formulario de inscripción
        pBuscar.setVisible(false);
        pAl.setVisible(false);

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

        // Mostrar/ocultar boton Calificaciones segun programa seleccionado
        cbPrograma.addActionListener(e -> {
            String sel = cbPrograma.getSelectedItem() != null ? cbPrograma.getSelectedItem().toString() : "";
            boolean esDev = sel.contains("Ingenieria en Desarrollo de Software");
            btnCalificaciones.setVisible(esDev);
        });

        // Acción para actualizar la lista de alumnos
        btnActualizarLista.addActionListener(e -> refreshAlumnosList());
        btnBuscar.addActionListener(e -> refreshAlumnosListFiltered(txtBuscar.getText().trim()));

        // Cuando se selecciona un alumno en la lista, poblar campos
        cbAlumnos.addActionListener(e -> {
            int idx = cbAlumnos.getSelectedIndex();
            if (idx <= 0) {
                txtNombre.setText("");
                txtApellidos.setText("");
                cbPrograma.setSelectedIndex(0);
                btnCalificaciones.setVisible(false);
                return;
            }
            Alumno a = cbCurrentAlumnos.get(idx - 1);
            txtNombre.setText(a.getNombre());
            txtApellidos.setText(a.getApellidos());
            spFecha.setValue(a.getFechaNacimiento());
            cbPrograma.setSelectedItem(a.getPrograma());
            btnCalificaciones.setVisible(a.getPrograma().contains("Ingenieria en Desarrollo de Software"));
        });

        btnCalificaciones.addActionListener(e -> new VentanaCalificacionesBusqueda(this).setVisible(true));
    }

    private void refreshAlumnosList() {
        refreshAlumnosListFiltered("");
    }

    private void refreshAlumnosListFiltered(String q) {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("-- Nuevo alumno --");
        cbCurrentAlumnos.clear();
        for (Alumno a : BaseDatos.alumnos) {
            if (q == null || q.isEmpty()) {
                cbCurrentAlumnos.add(a);
                model.addElement(a.toString());
            } else {
                String txt = (a.getNombre() + " " + a.getApellidos()).toLowerCase();
                if (txt.contains(q.toLowerCase())) {
                    cbCurrentAlumnos.add(a);
                    model.addElement(a.toString());
                }
            }
        }
        cbAlumnos.setModel(model);
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

        String programaSel = cbPrograma.getSelectedItem().toString();
        // Crear Alumno especializado si corresponde a Desarrollo de Software
        if (programaSel.contains("Ingenieria en Desarrollo de Software")) {
            java.util.List<Materia> materias = java.util.Arrays.asList(
                new Programacion(), new EstructurasDatos(), new BasesDeDatosMateria(), new IngenieriaSoftwareMateria(), new Redes()
            );
            AlumnoDesarrolloSoftware ads = new AlumnoDesarrolloSoftware(
                txtNombre.getText(),
                txtApellidos.getText(),
                (Date) spFecha.getValue(),
                programaSel,
                materias
            );
            BaseDatos.agregarAlumno(ads);
        } else {
            Alumno a = new Alumno(
                txtNombre.getText(),
                txtApellidos.getText(),
                (Date) spFecha.getValue(),
                programaSel
            );
            BaseDatos.agregarAlumno(a);
        }
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
