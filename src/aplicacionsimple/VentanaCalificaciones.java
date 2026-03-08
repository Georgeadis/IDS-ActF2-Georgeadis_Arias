package aplicacionsimple;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.*;

public class VentanaCalificaciones extends JDialog {
    public VentanaCalificaciones(Frame owner, AlumnoDesarrolloSoftware alumno) {
        super(owner, "Calificaciones - " + alumno.toString(), true);
        setSize(800, 400);
        setLocationRelativeTo(owner);

        String[] cols = {"Materia", "Creditos", "Calificacion"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int row, int column) {
                // permitir editar solo la columna de calificacion
                return column == 2;
            }
        };

        for (Materia m : alumno.getMaterias()) {
            Double cal = alumno.getCalificaciones().get(m.getNombre());
            String calStr = (cal == null || cal < 0) ? "-" : String.format("%.2f", cal);
            model.addRow(new Object[]{m.getNombre(), m.getCreditos(), calStr});
        }

        JTable table = new JTable(model);
        // columna 1 (creditos) como no editable y centrada
        table.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer() {{ setHorizontalAlignment(CENTER); }});
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton btnSave = new JButton("Guardar calificaciones");
        btnSave.addActionListener(e -> {
            // leer la columna de calificaciones y actualizar el alumno
            for (int r = 0; r < model.getRowCount(); r++) {
                String nombre = model.getValueAt(r, 0).toString();
                Object val = model.getValueAt(r, 2);
                double cal = -1.0;
                try {
                    if (val != null) {
                        String s = val.toString().trim();
                        if (!s.isEmpty() && !s.equals("-")) cal = Double.parseDouble(s);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Valor de calificación inválido en " + nombre);
                    return;
                }
                alumno.setCalificacion(nombre, cal);
            }
            JOptionPane.showMessageDialog(this, "Calificaciones guardadas");
        });

        // Guardar automáticamente al cerrar la ventana
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                // mismo comportamiento que boton guardar
                for (int r = 0; r < model.getRowCount(); r++) {
                    String nombre = model.getValueAt(r, 0).toString();
                    Object val = model.getValueAt(r, 2);
                    double cal = -1.0;
                    try {
                        if (val != null) {
                            String s = val.toString().trim();
                            if (!s.isEmpty() && !s.equals("-")) cal = Double.parseDouble(s);
                        }
                    } catch (NumberFormatException ex) {
                        // en cierre, ignorar formato inválido y continuar
                        cal = -1.0;
                    }
                    alumno.setCalificacion(nombre, cal);
                }
                JOptionPane.showMessageDialog(VentanaCalificaciones.this, "Calificaciones guardadas al cerrar");
            }
        });

        JButton btnClose = new JButton("Cerrar");
        btnClose.addActionListener(e -> dispose());

        JButton btnExport = new JButton("Exportar CSV");
        btnExport.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int res = chooser.showSaveDialog(this);
            if (res != JFileChooser.APPROVE_OPTION) return;
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

        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        p.add(btnSave);
        p.add(btnClose);
        add(p, BorderLayout.SOUTH);
    }
}
