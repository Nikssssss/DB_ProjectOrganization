package modules.tables;

import modules.tables.enums.TableType;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

public class TablesView {
    private TablesPresenter presenter;

    private final JPanel tablePanel = new JPanel();
    private ColumnEditableTableModel entityTableModel;
    private JTable entityTable;
    private JScrollPane entityScrollPane;
    private final JButton backButton = new JButton("Назад в меню");
    private final JComboBox<String> entitiesComboBox = new JComboBox<>();
    private final JButton addRowButton = new JButton("Добавить запись");

    public void didLoad() {
        this.presenter.viewDidLoad();
    }

    public void setPresenter(TablesPresenter presenter) {
        this.presenter = presenter;
    }

    public void configureView() {
        this.setupView();
    }

    public JPanel getTablePanel() {
        return tablePanel;
    }

    public void setTableRowsData(List<ArrayList<String>> rows) {
        this.entityTableModel.setRowCount(0);
        for (ArrayList<String> row: rows) {
            row.add("Удалить");
            this.entityTableModel.addRow(row.toArray());
        }
        this.entityTable.clearSelection();
    }

    public String getCurrentTableName() {
        return (String) entitiesComboBox.getSelectedItem();
    }

    public void setTableModelForTableType(TableType tableType, ArrayList<String> columnNames, ArrayList<ArrayList<String>> dropDownListsData) {
        switch (tableType) {
            case EMPLOYEES: {
                this.setTableModelForEmployees(columnNames, dropDownListsData);
                break;
            }
        }
    }

    public ArrayList<String> getDataFromRow(int row) {
        return new ArrayList<>((Vector<String>)this.entityTableModel.getDataVector().get(row));
    }

    //MARK: private methods

    private void setupView() {
        this.tablePanel.setBackground(Color.LIGHT_GRAY);
        this.tablePanel.setLayout(new GridBagLayout());
        this.tablePanel.setPreferredSize(new Dimension(800, 800));
        this.setupSubComponents();
        this.placeSubComponents();
    }

    private void setupSubComponents() {
        this.backButton.setPreferredSize(new Dimension(152, 50));
        this.backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                presenter.backButtonPressed();
            }
        });
        this.addRowButton.setPreferredSize(new Dimension(152, 50));

        this.entitiesComboBox.addItem("Сотрудники");
        this.entitiesComboBox.addItem("Отделы");
        this.entitiesComboBox.addItem("Оборудование");
        this.entitiesComboBox.addItem("Профессии");
        this.entitiesComboBox.addItem("Проекты");
        this.entitiesComboBox.addItem("Договоры");
        this.entitiesComboBox.addItem("Субдоговоры");
        this.entitiesComboBox.setSelectedItem("Сотрудники");
        this.entitiesComboBox.addActionListener(e -> presenter.comboBoxItemChanged());

        this.entityTableModel = new ColumnEditableTableModel();
        this.entityTable = new JTable(this.entityTableModel);
        this.entityScrollPane = new JScrollPane(this.entityTable);
        this.entityTable.setPreferredSize(new Dimension(750, 750));
        this.entityScrollPane.setPreferredSize(new Dimension(750, 700));
        this.entityScrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                "Записи",
                TitledBorder.CENTER,
                TitledBorder.TOP));
        this.entityTable.setColumnSelectionAllowed(false);
        this.entityTable.setRowSelectionAllowed(false);
    }

    private void placeSubComponents() {
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets.right = 150;
        this.tablePanel.add(this.backButton, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.insets.right = 0;
        this.tablePanel.add(this.entitiesComboBox, constraints);

        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.insets.left = 150;
        this.tablePanel.add(this.addRowButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.insets.top = 20;
        constraints.insets.left = 0;
        constraints.gridwidth = 3;
        this.tablePanel.add(this.entityScrollPane, constraints);
    }

    private void setTableModelForEmployees(ArrayList<String> columnNames, ArrayList<ArrayList<String>> dropDownListsData) {
        if (this.entityTableModel.getTableModelListeners().length > 1) {
            this.entityTableModel.removeTableModelListener(this.entityTableModel.getTableModelListeners()[1]);
        }
        this.entityTableModel.setColumnCount(0);
        this.entityTableModel.setRowCount(0);
        this.entityTableModel.addAllEditableColumns(new Integer[]{1, 2, 4, 5, 6});
        for (String columnName: columnNames) {
            this.entityTableModel.addColumn(columnName);
        }
        this.entityTableModel.addColumn("");

        if (this.entityTable.getMouseListeners().length > 0) {
            this.entityTable.removeMouseListener(this.entityTable.getMouseListeners()[0]);
        }
        this.entityTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (entityTable.getSelectedColumn() == 7) {
                    if (entityTable.getSelectedRow() != -1) {
                        presenter.deleteRowButtonPressed(entityTable.getSelectedRow());
                    }
                }
            }
        });

        TableColumn professionColumn = this.entityTable.getColumnModel().getColumn(4);
        JComboBox<String> professionComboBox = new JComboBox<>();
        ArrayList<String> professions = dropDownListsData.get(0);
        for (String profession: professions) {
            professionComboBox.addItem(profession);
        }
        professionColumn.setCellEditor(new DefaultCellEditor(professionComboBox));

        TableColumn deleteColumn = this.entityTable.getColumnModel().getColumn(7);
        deleteColumn.setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                component.setForeground(Color.RED);
                return component;
            }
        });

        this.entityTableModel.addTableModelListener(event -> {
            if (event.getType() == TableModelEvent.UPDATE) {
                this.presenter.cellDidEdit(event.getFirstRow());
            }
        });
    }

}

class ColumnEditableTableModel extends DefaultTableModel {
    private final ArrayList<Integer> editableColumns = new ArrayList<>();

    @Override
    public boolean isCellEditable(int row, int column) {
        return editableColumns.contains(column);
    }

    public void addAllEditableColumns(Integer[] editableColumns) {
        this.editableColumns.clear();
        this.editableColumns.addAll(Arrays.asList(editableColumns));
        this.fireTableStructureChanged();
    }
}
