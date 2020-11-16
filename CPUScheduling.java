import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class CPUScheduling extends JFrame implements ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    JTable jTable;
    JScrollPane jScrollPane;
    JButton btnCalculate, btnAdd, btnDelete, btnClear;
    JTextField tfBT, tfAT, tfTQ;
    JLabel labelTQ;
    JComboBox<String> algoList;
    String[] algoName = { "FCFS", "SJF", "SRTF", "Priority Non-Premtive", "Priority Premtive", "Round Robin" };
    List<Process> list;
    // MyKeyListener myKeyListener;
    boolean flag = true;

    // Algorithms Classes
    CPUAlgos algorithm;

    // Table Model for JTable
    // Object[][] data = { { "A", 3, 4, 0 }, { "B", 5, 3, 0 }, { "C", 0, 2, 0 }, { "D", 5, 1, 0 }, { "E", 4, 3, 0 } };
    Object[][] data = { { "P1", 5, 1, 0 }, { "P2", 3, 4, 0 }, { "P3", 6, 2, 0 }, { "P4", 0, 2, 0 }, { "P5", 4, 3, 0 } };
    // Object[][] data = { { "P1", 0, 4, 2 }, { "P2", 1, 3, 3 }, { "P3", 2, 1, 4 }, { "P4", 3, 5, 5 }, { "P5", 4, 2, 5 } };
    DefaultTableModel m1 = new DefaultTableModel(data, new String[] { "Pid", "AT", "BT" });
    DefaultTableModel m2 = new DefaultTableModel(data, new String[] { "Pid", "AT", "BT", "Priority" });

    public CPUScheduling() {
        changeUI();
        setLayout(null);
        setResizable(false);

        list = new ArrayList<>();
        // myKeyListener = new MyKeyListener();

        labelTQ = new JLabel("Time Slice");
        labelTQ.setFont(new Font("Arial", Font.BOLD, 15));
        labelTQ.setBounds(45, 7, 100, 30);

        tfTQ = new JTextField("");
        tfTQ.setFont(new Font("Arial", Font.PLAIN, 18));
        tfTQ.setBounds(45, 30, 70, 30);
        // tfTQ.addKeyListener(myKeyListener);

        btnAdd = new JButton("Add");
        btnAdd.setFont(new Font("Arial", Font.BOLD, 18));
        btnAdd.setBounds(400, 30, 100, 28);
        btnAdd.addActionListener(this);
        add(btnAdd);

        btnDelete = new JButton("Delete");
        btnDelete.setFont(new Font("Arial", Font.BOLD, 18));
        btnDelete.setBounds(520, 30, 100, 28);
        btnDelete.addActionListener(this);
        add(btnDelete);

        btnClear = new JButton("Clear");
        btnClear.setFont(new Font("Arial", Font.BOLD, 18));
        btnClear.setBounds(640, 30, 100, 28);
        btnClear.addActionListener(this);
        add(btnClear);

        btnCalculate = new JButton("Calculate");
        btnCalculate.setFont(new Font("Arial", Font.BOLD, 18));
        btnCalculate.setBounds(760, 30, 130, 28);
        btnCalculate.addActionListener(this);
        add(btnCalculate);

        jScrollPane = new JScrollPane();
        jTable = new JTable();
        jTable.setAutoCreateRowSorter(true);
        jTable.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        jTable.setModel(m1);
        jTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        jTable.setRowHeight(30);
        jTable.setRowMargin(2);
        jTable.setSelectionBackground(new java.awt.Color(85, 242, 73));
        jTable.setSurrendersFocusOnKeystroke(true);
        tablePreference(jTable);
        jScrollPane.setViewportView(jTable);
        jScrollPane.setBounds(100, 100, 800, 800);
        add(jScrollPane);

        algoList = new JComboBox<>(algoName);
        algoList.setFont(new Font("Arial", Font.PLAIN, 18));
        algoList.setBounds(150, 30, 200, 28);
        algoList.addActionListener(e -> {
            changeTableAccordingToAlgo();
        });
        add(algoList);

        setTitle("CPU Scheduling");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void changeTableAccordingToAlgo() {
        int id = algoList.getSelectedIndex();

        if (id == 3 || id == 4) {
            jTable.setModel(m2);
        } else {
            jTable.setModel(m1);
        }

        if (id == 5) {
            add(tfTQ);
            add(labelTQ);
        } else {
            remove(tfTQ);
            remove(labelTQ);
        }

        validate();
        repaint();
        jTable.revalidate();
        tablePreference(jTable);
    }

    public void tablePreference(JTable jTable) {
        // Change Header.
        JTableHeader tj = jTable.getTableHeader();
        tj.setFont(new Font("Segoe UI", Font.BOLD, 26));
        tj.setOpaque(false);
        tj.setBackground(new Color(32, 136, 203));
        tj.setForeground(new Color(255, 255, 255));

        // Column Center align.
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) tj.getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);

        // Cell Alignment
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(JLabel.CENTER);
        int column = jTable.getColumnCount();
        for (int i = 0; i < column; i++) {
            jTable.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }

        jTable.setAutoCreateRowSorter(true);
        jTable.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        jTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        jTable.setRowHeight(30);
        jTable.setRowMargin(2);
        jTable.setSelectionBackground(new java.awt.Color(102, 255, 51));
        jTable.setSurrendersFocusOnKeystroke(true);
    }

    public void changeUI() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new CPUScheduling();
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        // TODO Auto-generated method stub
        DefaultTableModel model = (DefaultTableModel) jTable.getModel();
        try {
            if (evt.getSource() == btnAdd) {
                model.addRow(new Object[] {});
            } else if (evt.getSource() == btnDelete) {
                model.removeRow(model.getRowCount() - 1);
            } else if (evt.getSource() == btnClear) {
                model.setRowCount(1);
                for (int i = 0; i < model.getColumnCount(); i++)
                    model.setValueAt(null, 0, i);
            } else if (evt.getSource() == btnCalculate) {
                checkVaidate();
                int index = algoList.getSelectedIndex();
                switch (index) {
                    case 0:
                        algorithm = new FCFS(list);
                        break;
                    case 1:
                        algorithm = new SJF(list);
                        break;
                    case 3:
                        algorithm = new PriorityNP(list);
                        break;
                    default:
                        break;
                }
                algorithm.schedule();

                System.out.println("Gantt Chart:\n" + Process.Gantt);
                System.out.println("Average Tat: " + Process.AvgTat);
                System.out.println("Average Wt: " + Process.AvgWt + "\n\n");

                // Process.TQ = 0;
                // Process.AvgTat = 0;
                // Process.AvgWt = 0;
                // Process.Gantt = null;
            }
        } catch (Exception ex) {
        } finally {
            jTable.setModel(model);
        }
        jTable.requestFocusInWindow();
    }

    private void checkVaidate() {
        int index = algoList.getSelectedIndex();
        if (algoName[index].equals(algoName[5])) {
            try {
                if (Integer.parseInt(tfTQ.getText()) <= 0)
                    throw new NumberFormatException();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please Enter Valid Integer", "Error Box",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        int row = jTable.getRowCount();
        int column = jTable.getColumnCount();

        try {
            String str;
            list.clear();
            for (int i = 0; i < row; i++) {
                for (int j = 1; j < column; j++) {
                    str = jTable.getValueAt(i, j).toString();
                    if (!str.matches("[0-9]+")) {
                        throw new Exception();
                    }
                }
            }

            for (int i = 0; i < row; i++) {
                int arr[] = new int[3];
                String name = jTable.getValueAt(i, 0).toString();
                for (int j = 1; j < column; j++) {
                    str = jTable.getValueAt(i, j).toString();
                    arr[j - 1] = Integer.parseInt(str);
                }
                list.add(new Process(i, name, arr[0], arr[1], arr[2]));
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Please Enter Valid Integer in Table", "Error Box",
                    JOptionPane.ERROR_MESSAGE);
        }

    }
}