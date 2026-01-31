package aplicacionsimple;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class VentanaPrincipal extends JFrame {

    private JTextField txtNombre, txtApellidos;
    private JSpinner spFecha;
    private JComboBox<String> cbPrograma;

    public VentanaPrincipal() {
        setTitle("Universidad Ciudadana de Nuevo León");
        setSize(700, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 2, 10, 10)); // Ajustado: 7 filas x 2 columnas

        txtNombre = new JTextField();
        txtApellidos = new JTextField();

        spFecha = new JSpinner(new SpinnerDateModel());
        spFecha.setEditor(new JSpinner.DateEditor(spFecha, "dd/MM/yyyy"));

        // Botón para abrir selector de fecha (calendario simple)
        JButton btnCalendario = new JButton("📅");
        btnCalendario.setToolTipText("Seleccionar fecha");
        btnCalendario.addActionListener(e -> {
            Date d = showDatePicker((Date) spFecha.getValue());
            if (d != null) spFecha.setValue(d);
        });

        cbPrograma = new JComboBox<>(new String[]{
            "Licenciatura en Administración con acentuación en Empresas",
            "Licenciatura en Ingeniería en Desarrollo de Software",
            "Licenciatura en Logística, Aduanas y Comercio",
            "Licenciatura en Derecho",
            "Licenciatura en Marketing y Gestión de Ventas",
            "Licenciatura en Educación con acentuación en Tecnología Educativa",
            "Maestría en Administración con acentuación en Finanzas",
            "Maestría en Administración con acentuación en Planeación Estratégica"
        });

        JButton btnGuardar = new JButton("Guardar");
        JButton btnListado = new JButton("Listado de alumnos");
        JButton btnLic = new JButton("Alumnos por licenciatura");
        JButton btnMtr = new JButton("Alumnos por maestría");
        JButton btnSalir = new JButton("Salir");

        add(new JLabel("Nombre:"));
        add(txtNombre);
        add(new JLabel("Apellidos:"));
        add(txtApellidos);
        add(new JLabel("Fecha de nacimiento:"));
        // colocamos un panel con spinner + boton calendario
        JPanel pFecha = new JPanel(new BorderLayout(4, 0));
        pFecha.add(spFecha, BorderLayout.CENTER);
        pFecha.add(btnCalendario, BorderLayout.EAST);
        add(pFecha);
        add(new JLabel("Programa:"));
        add(cbPrograma);

        add(btnGuardar);
        add(btnListado);
        add(btnLic);
        add(btnMtr);
        add(btnSalir);

        // ACCIONES
        btnGuardar.addActionListener(e -> guardarAlumno());
        btnListado.addActionListener(e -> mostrarListado("TODOS"));
        btnLic.addActionListener(e -> mostrarListado("Licenciatura"));
        btnMtr.addActionListener(e -> mostrarListado("Maestría"));
        btnSalir.addActionListener(e -> dispose()); // Mejor que System.exit(0)
    }

    private void guardarAlumno() {
        if (txtNombre.getText().isEmpty() || txtApellidos.getText().isEmpty() || cbPrograma.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos");
            return;
        }

        Alumno a = new Alumno(
                txtNombre.getText(),
                txtApellidos.getText(),
                (Date) spFecha.getValue(),
                cbPrograma.getSelectedItem().toString()
        );
        BaseDatos.agregarAlumno(a);
        JOptionPane.showMessageDialog(this, "Alumno guardado correctamente");

        // Limpiar campos para nueva entrada
        txtNombre.setText("");
        txtApellidos.setText("");
        spFecha.setValue(new Date());
        if (cbPrograma.getItemCount() > 0) cbPrograma.setSelectedIndex(0);
        txtNombre.requestFocusInWindow();
    }

    private void mostrarListado(String tipo) {
        StringBuilder sb = new StringBuilder();
        for (Alumno a : BaseDatos.alumnos) {
            String programa = a.getPrograma();
            if (tipo.equals("TODOS")
                || (tipo.equals("Licenciatura") && programa.startsWith("Licenciatura"))
                || (tipo.equals("Maestría") && programa.startsWith("Maestría"))) {
                sb.append(a).append("\n");
            }
        }
        JOptionPane.showMessageDialog(this, sb.length() > 0 ? sb.toString() : "No hay alumnos registrados en este grupo");
    }

    // Dialogo simple de selección de fecha (día/mes/año)
    private Date showDatePicker(Date initial) {
        JPanel panel = new JPanel(new GridLayout(2, 3, 4, 4));

        java.util.Calendar cal = java.util.Calendar.getInstance();
        if (initial != null) cal.setTime(initial);

        Integer[] days = new Integer[31];
        for (int i = 0; i < 31; i++) days[i] = i + 1;
        String[] months = new java.text.DateFormatSymbols().getMonths();
        int startYear = 1960;
        int endYear = 2016;
        int len = endYear - startYear + 1;
        Integer[] years = new Integer[len];
        for (int i = 0; i < len; i++) years[i] = startYear + i;

        JComboBox<Integer> cbDay = new JComboBox<>(days);
        JComboBox<String> cbMonth = new JComboBox<>();
        for (int i = 0; i < 12; i++) cbMonth.addItem(months[i]);
        JComboBox<Integer> cbYear = new JComboBox<>(years);

        cbDay.setSelectedItem(cal.get(java.util.Calendar.DAY_OF_MONTH));
        cbMonth.setSelectedIndex(cal.get(java.util.Calendar.MONTH));
        int selYear = cal.get(java.util.Calendar.YEAR);
        if (selYear < startYear || selYear > endYear) selYear = endYear;
        cbYear.setSelectedItem(selYear);

        panel.add(new JLabel("Día"));
        panel.add(new JLabel("Mes"));
        panel.add(new JLabel("Año"));
        panel.add(cbDay);
        panel.add(cbMonth);
        panel.add(cbYear);

        int res = JOptionPane.showConfirmDialog(this, panel, "Seleccionar fecha", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (res == JOptionPane.OK_OPTION) {
            int day = (Integer) cbDay.getSelectedItem();
            int month = cbMonth.getSelectedIndex();
            int year = (Integer) cbYear.getSelectedItem();
            java.util.Calendar c = java.util.Calendar.getInstance();
            c.set(java.util.Calendar.YEAR, year);
            c.set(java.util.Calendar.MONTH, month);
            c.set(java.util.Calendar.DAY_OF_MONTH, day);
            c.set(java.util.Calendar.HOUR_OF_DAY, 0);
            c.set(java.util.Calendar.MINUTE, 0);
            c.set(java.util.Calendar.SECOND, 0);
            c.set(java.util.Calendar.MILLISECOND, 0);
            return c.getTime();
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }
}
