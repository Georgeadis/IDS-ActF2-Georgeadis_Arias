package aplicacionsimple;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VentanaCalificacionesBusqueda extends JDialog {
    private DefaultTableModel model;
    private JTable table;
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private List<Alumno> results = new ArrayList<>();
    private JList<String> resultList;
    private JComboBox<String> cbMaterias;
    private JLabel lblMateriaCal;
    private JTextField txtMateriaCal;
    private JButton btnActualizarMateria;

    public VentanaCalificacionesBusqueda(Frame owner) {
        super(owner, "Calificaciones - Buscar", true);
        setSize(900, 450);
        setLocationRelativeTo(owner);

        setLayout(new BorderLayout(8,8));

        // Top: search
        JPanel top = new JPanel(new BorderLayout(6,6));
        JTextField txtBuscar = new JTextField();
        JButton btnBuscar = new JButton("Buscar");
        JButton btnClear = new JButton("Clear");
        JPanel pBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pBtns.add(btnClear);
        pBtns.add(btnBuscar);
        top.add(new JLabel("Buscar por nombre/apellido:"), BorderLayout.WEST);
        top.add(txtBuscar, BorderLayout.CENTER);
        top.add(pBtns, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);

        // Left: results list
        resultList = new JList<>(listModel);
        resultList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane left = new JScrollPane(resultList);
        left.setPreferredSize(new Dimension(300,300));
        add(left, BorderLayout.WEST);

        // Right: table with calificaciones
        String[] cols = {"Materia", "Creditos", "Calificacion"};
        model = new DefaultTableModel(cols,0) {
            public boolean isCellEditable(int row, int column) { return column == 2; }
        };
        table = new JTable(model);
        table.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer() {{ setHorizontalAlignment(CENTER); }});
        JScrollPane tableScroll = new JScrollPane(table);
        add(tableScroll, BorderLayout.CENTER);

        // Far right: panel para consultar por materia
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createTitledBorder("Consultar por materia"));
        cbMaterias = new JComboBox<>();
        cbMaterias.setMaximumSize(new Dimension(250, 26));
        lblMateriaCal = new JLabel("Calificación: -");
        txtMateriaCal = new JTextField();
        txtMateriaCal.setMaximumSize(new Dimension(250, 26));
        btnActualizarMateria = new JButton("Actualizar materia");

        rightPanel.add(Box.createVerticalStrut(8));
        rightPanel.add(new JLabel("Materia:"));
        rightPanel.add(cbMaterias);
        rightPanel.add(Box.createVerticalStrut(6));
        rightPanel.add(lblMateriaCal);
        rightPanel.add(Box.createVerticalStrut(6));
        rightPanel.add(new JLabel("Editar calificación:"));
        rightPanel.add(txtMateriaCal);
        rightPanel.add(Box.createVerticalStrut(8));
        rightPanel.add(btnActualizarMateria);
        rightPanel.add(Box.createVerticalGlue());

        add(rightPanel, BorderLayout.EAST);

        // Bottom: actions
        JButton btnGuardar = new JButton("Guardar calificaciones");
        JButton btnExport = new JButton("Exportar CSV");
        JButton btnCerrar = new JButton("Cerrar");
        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        p.add(btnExport);
        p.add(btnGuardar);
        p.add(btnCerrar);
        add(p, BorderLayout.SOUTH);

        // Actions
        btnBuscar.addActionListener(e -> {
            String q = txtBuscar.getText().trim().toLowerCase();
            listModel.clear(); results.clear();
            for (Alumno a : BaseDatos.alumnos) {
                String txt = (a.getNombre() + " " + a.getApellidos()).toLowerCase();
                if (q.isEmpty() || txt.contains(q)) {
                    listModel.addElement(a.toString());
                    results.add(a);
                }
            }
            if (listModel.isEmpty()) JOptionPane.showMessageDialog(this, "No se encontraron alumnos");
        });

        btnClear.addActionListener(e -> {
            txtBuscar.setText("");
            listModel.clear();
            results.clear();
            model.setRowCount(0);
        });

        resultList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
            int idx = resultList.getSelectedIndex();
                if (idx < 0) return;
            Alumno a = results.get(idx);
                if (a instanceof AlumnoDesarrolloSoftware) {
                    loadAlumno((AlumnoDesarrolloSoftware) a);
                } else if (a.getPrograma().contains("Ingenieria en Desarrollo de Software")) {
                    int opt = JOptionPane.showConfirmDialog(this, "Convertir este alumno a Desarrollo de Software para gestionar calificaciones?", "Convertir", JOptionPane.YES_NO_OPTION);
                    if (opt != JOptionPane.YES_OPTION) return;
                    List<Materia> materias = java.util.Arrays.asList(
                            new Programacion(), new EstructurasDatos(), new BasesDeDatosMateria(), new IngenieriaSoftwareMateria(), new Redes()
                    );
                    AlumnoDesarrolloSoftware ads = new AlumnoDesarrolloSoftware(a.getNombre(), a.getApellidos(), a.getFechaNacimiento(), a.getPrograma(), materias);
                    int idb = BaseDatos.alumnos.indexOf(a);
                    if (idb >= 0) BaseDatos.alumnos.set(idb, ads);
                    results.set(idx, ads);
                    loadAlumno(ads);
                } else {
                    JOptionPane.showMessageDialog(this, "Este alumno no pertenece a Ingeniería en Desarrollo de Software");
                }
            }
        });

        // cuando se selecciona materia en el combo, actualizar etiqueta y resaltar fila
        cbMaterias.addActionListener(e -> {
            int i = cbMaterias.getSelectedIndex();
            if (i < 0) return;
            String mat = cbMaterias.getItemAt(i);
            // buscar fila
            for (int r = 0; r < model.getRowCount(); r++) {
                if (model.getValueAt(r,0).toString().equals(mat)) {
                    table.setRowSelectionInterval(r, r);
                    Object val = model.getValueAt(r,2);
                    lblMateriaCal.setText("Calificación: " + (val == null ? "-" : val.toString()));
                    txtMateriaCal.setText(val == null || val.toString().equals("-") ? "" : val.toString());
                    break;
                }
            }
        });

        btnActualizarMateria.addActionListener(e -> {
            int sel = cbMaterias.getSelectedIndex();
            if (sel < 0) { JOptionPane.showMessageDialog(this, "Selecciona una materia"); return; }
            String materia = cbMaterias.getItemAt(sel);
            String s = txtMateriaCal.getText().trim();
            double cal = -1;
            try {
                if (!s.isEmpty()) cal = Double.parseDouble(s);
            } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Valor inválido"); return; }
            // actualizar en tabla y en objeto alumno seleccionado
            int row = -1;
            for (int r = 0; r < model.getRowCount(); r++) if (model.getValueAt(r,0).toString().equals(materia)) { row = r; break; }
            if (row >= 0) model.setValueAt(cal < 0 ? "-" : String.format("%.2f", cal), row, 2);
            int selAlumno = resultList.getSelectedIndex();
            if (selAlumno >= 0) {
                Alumno a = results.get(selAlumno);
                if (a instanceof AlumnoDesarrolloSoftware) ((AlumnoDesarrolloSoftware) a).setCalificacion(materia, cal);
            }
            lblMateriaCal.setText("Calificación: " + (cal < 0 ? "-" : String.format("%.2f", cal)));
            JOptionPane.showMessageDialog(this, "Calificación actualizada para " + materia);
        });

        btnGuardar.addActionListener(e -> {
            saveCurrentSelection();
        });

        btnExport.addActionListener(e -> {
            int sel = resultList.getSelectedIndex();
            if (sel < 0) { JOptionPane.showMessageDialog(this, "Selecciona un alumno para exportar"); return; }
            JFileChooser chooser = new JFileChooser();
            if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;
            java.io.File f = chooser.getSelectedFile();
            try (FileWriter fw = new FileWriter(f)) {
                fw.write("Materia,Creditos,Calificacion\n");
                for (int r = 0; r < model.getRowCount(); r++) {
                    String nombre = model.getValueAt(r,0).toString();
                    String cred = model.getValueAt(r,1).toString();
                    String cal = model.getValueAt(r,2).toString();
                    fw.write(String.format("\"%s\",%s,%s\n", nombre.replace("\"",""), cred, cal));
                }
                JOptionPane.showMessageDialog(this, "Exportado a " + f.getAbsolutePath());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al exportar: " + ex.getMessage());
            }
        });

        btnCerrar.addActionListener(e -> dispose());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                saveCurrentSelection();
                JOptionPane.showMessageDialog(VentanaCalificacionesBusqueda.this, "Calificaciones guardadas al cerrar");
            }
        });
    }

    private void saveCurrentSelection() {
        for (int r = 0; r < model.getRowCount(); r++) {
            String nombre = model.getValueAt(r,0).toString();
            Object val = model.getValueAt(r,2);
            double cal = -1;
            try {
                if (val != null) {
                    String s = val.toString().trim();
                    if (!s.isEmpty() && !s.equals("-")) cal = Double.parseDouble(s);
                }
            } catch (NumberFormatException ex) {
                // ignorar en auto-guardado
                cal = -1;
            }
            int sel = resultList.getSelectedIndex();
            if (sel >= 0) {
                Alumno a = results.get(sel);
                if (a instanceof AlumnoDesarrolloSoftware) ((AlumnoDesarrolloSoftware) a).setCalificacion(nombre, cal);
            }
        }
    }

    private void loadAlumno(AlumnoDesarrolloSoftware a) {
        model.setRowCount(0);
        cbMaterias.removeAllItems();
        for (Materia m : a.getMaterias()) {
            Double cal = a.getCalificaciones().get(m.getNombre());
            String calStr = (cal == null || cal < 0) ? "-" : String.format("%.2f", cal);
            model.addRow(new Object[]{m.getNombre(), m.getCreditos(), calStr});
            cbMaterias.addItem(m.getNombre());
        }
        if (cbMaterias.getItemCount() > 0) cbMaterias.setSelectedIndex(0);
    }
}
