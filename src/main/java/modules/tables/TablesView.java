package modules.tables;

import modules.tables.enums.TableType;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
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

    private final TableModelListener tableModelListener = new TableModelListener() {
        @Override
        public void tableChanged(TableModelEvent event) {
            if (event.getType() == TableModelEvent.UPDATE) {
                presenter.cellDidEdit(event.getFirstRow());
            }
        }
    };

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

    public void setErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(tablePanel, errorMessage, "Error!", JOptionPane.ERROR_MESSAGE);
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
            case DEPARTMENTS: {
                this.setTableModelForDepartments(columnNames, dropDownListsData);
                break;
            }
            case PROFESSIONS: {
                this.setTableModelForProfessions(columnNames, dropDownListsData);
                break;
            }
            case EQUIPMENT: {
                this.setTableModelForEquipment(columnNames, dropDownListsData);
                break;
            }
            case PROJECTS: {
                this.setTableModelForProjects(columnNames, dropDownListsData);
                break;
            }
            case CONTRACTS: {
                this.setTableModelForContracts(columnNames, dropDownListsData);
                break;
            }
            case SUBCONTRACTS: {
                this.setTableModelForSubcontracts(columnNames);
                break;
            }
            case EQUIPMENTTYPE: {
                this.setTableModelForEquipmentType(columnNames);
                break;
            }
            case PROJECTS_EMPLOYEES: {
                this.setTableModelForProjectsEmployees(columnNames);
                break;
            }
            case EQUIPMENT_PROJECTS: {
                this.setTableModelForEquipmentProjects(columnNames);
                break;
            }
        }
    }

    public ArrayList<String> getDataFromRow(int row) {
        Vector<String> dataRow = (Vector<String>) this.entityTableModel.getDataVector().get(row);
        ArrayList<String> requiredDataRow = new ArrayList<>();
        for (int i = 0; i < dataRow.size() - 1; i++) {
            requiredDataRow.add(dataRow.get(i));
        }
        return requiredDataRow;
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
        this.addRowButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                presenter.addRowButtonPressed();
            }
        });

        this.entitiesComboBox.addItem("Сотрудники");
        this.entitiesComboBox.addItem("Отделы");
        this.entitiesComboBox.addItem("Оборудование");
        this.entitiesComboBox.addItem("Типы оборудования");
        this.entitiesComboBox.addItem("Проекты");
        this.entitiesComboBox.addItem("Договоры");
        this.entitiesComboBox.addItem("Субдоговоры");
        this.entitiesComboBox.addItem("Исполнители проектов");
        this.entitiesComboBox.addItem("Оборудование на проектах");
        this.entitiesComboBox.addActionListener(e -> presenter.comboBoxItemChanged());
    }

    private void placeSubComponents() {
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets.right = 130;
        this.tablePanel.add(this.backButton, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.insets.right = 0;
        this.tablePanel.add(this.entitiesComboBox, constraints);

        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.insets.left = 130;
        this.tablePanel.add(this.addRowButton, constraints);
    }

    private void reinitializeTable() {
        if (this.entityScrollPane != null) {
            this.tablePanel.remove(this.entityScrollPane);
            this.tablePanel.validate();
        }

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

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.insets.top = 20;
        constraints.insets.left = 0;
        constraints.gridwidth = 3;
        this.tablePanel.add(this.entityScrollPane, constraints);
        this.tablePanel.validate();
    }

    private void setTableModelForEmployees(ArrayList<String> columnNames, ArrayList<ArrayList<String>> dropDownListsData) {
        this.reinitializeTable();
        this.entityTableModel.addAllEditableColumns(new Integer[]{1, 2, 4, 5, 6});
        for (String columnName: columnNames) {
            this.entityTableModel.addColumn(columnName);
        }
        this.entityTableModel.addColumn("");

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

        this.entityTableModel.addTableModelListener(this.tableModelListener);
    }

    private void setTableModelForDepartments(ArrayList<String> columnNames, ArrayList<ArrayList<String>> dropDownListsData) {
        this.reinitializeTable();
        this.entityTableModel.addAllEditableColumns(new Integer[]{1, 2});
        for (String columnName: columnNames) {
            this.entityTableModel.addColumn(columnName);
        }
        this.entityTableModel.addColumn("");

        this.entityTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (entityTable.getSelectedColumn() == 3) {
                    if (entityTable.getSelectedRow() != -1) {
                        presenter.deleteRowButtonPressed(entityTable.getSelectedRow());
                    }
                }
            }
        });

        TableColumn managerColumn = this.entityTable.getColumnModel().getColumn(2);
        JComboBox<String> managerComboBox = new JComboBox<>();
        ArrayList<String> managers = dropDownListsData.get(0);
        for (String manager: managers) {
            managerComboBox.addItem(manager);
        }
        managerColumn.setCellEditor(new DefaultCellEditor(managerComboBox));

        TableColumn deleteColumn = this.entityTable.getColumnModel().getColumn(3);
        deleteColumn.setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                component.setForeground(Color.RED);
                return component;
            }
        });

        this.entityTableModel.addTableModelListener(this.tableModelListener);
    }

    private void setTableModelForProfessions(ArrayList<String> columnNames, ArrayList<ArrayList<String>> dropDownListsData) {
        this.reinitializeTable();
        this.entityTableModel.addAllEditableColumns(new Integer[]{1, 2, 3});
        for (String columnName: columnNames) {
            this.entityTableModel.addColumn(columnName);
        }
        this.entityTableModel.addColumn("");

        this.entityTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (entityTable.getSelectedColumn() == 4) {
                    if (entityTable.getSelectedRow() != -1) {
                        presenter.deleteRowButtonPressed(entityTable.getSelectedRow());
                    }
                }
            }
        });

        TableColumn managementAbilityColumn = this.entityTable.getColumnModel().getColumn(2);
        JComboBox<String> managementAbilityComboBox = new JComboBox<>();
        ArrayList<String> managementAbilities = dropDownListsData.get(0);
        for (String manager: managementAbilities) {
            managementAbilityComboBox.addItem(manager);
        }
        managementAbilityColumn.setCellEditor(new DefaultCellEditor(managementAbilityComboBox));

        TableColumn departmentColumn = this.entityTable.getColumnModel().getColumn(3);
        JComboBox<String> departmentComboBox = new JComboBox<>();
        ArrayList<String> departments = dropDownListsData.get(1);
        for (String manager: departments) {
            departmentComboBox.addItem(manager);
        }
        departmentColumn.setCellEditor(new DefaultCellEditor(departmentComboBox));

        TableColumn deleteColumn = this.entityTable.getColumnModel().getColumn(4);
        deleteColumn.setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                component.setForeground(Color.RED);
                return component;
            }
        });

        this.entityTableModel.addTableModelListener(this.tableModelListener);
    }

    private void setTableModelForEquipment(ArrayList<String> columnNames, ArrayList<ArrayList<String>> dropDownListsData) {
        this.reinitializeTable();
        this.entityTableModel.addAllEditableColumns(new Integer[]{1, 2, 3});
        for (String columnName: columnNames) {
            this.entityTableModel.addColumn(columnName);
        }
        this.entityTableModel.addColumn("");

        this.entityTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (entityTable.getSelectedColumn() == 4) {
                    if (entityTable.getSelectedRow() != -1) {
                        presenter.deleteRowButtonPressed(entityTable.getSelectedRow());
                    }
                }
            }
        });

        TableColumn equipmentTypeColumn = this.entityTable.getColumnModel().getColumn(2);
        JComboBox<String> equipmentTypesComboBox = new JComboBox<>();
        ArrayList<String> equipmentTypes = dropDownListsData.get(0);
        for (String manager: equipmentTypes) {
            equipmentTypesComboBox.addItem(manager);
        }
        equipmentTypeColumn.setCellEditor(new DefaultCellEditor(equipmentTypesComboBox));

        TableColumn departmentColumn = this.entityTable.getColumnModel().getColumn(3);
        JComboBox<String> departmentComboBox = new JComboBox<>();
        ArrayList<String> departments = dropDownListsData.get(1);
        for (String manager: departments) {
            departmentComboBox.addItem(manager);
        }
        departmentColumn.setCellEditor(new DefaultCellEditor(departmentComboBox));

        TableColumn deleteColumn = this.entityTable.getColumnModel().getColumn(4);
        deleteColumn.setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                component.setForeground(Color.RED);
                return component;
            }
        });

        this.entityTableModel.addTableModelListener(this.tableModelListener);
    }

    private void setTableModelForProjects(ArrayList<String> columnNames, ArrayList<ArrayList<String>> dropDownListsData) {
        this.reinitializeTable();
        this.entityTableModel.addAllEditableColumns(new Integer[]{1, 2, 3, 5});
        for (String columnName: columnNames) {
            this.entityTableModel.addColumn(columnName);
        }
        this.entityTableModel.addColumn("");

        this.entityTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (entityTable.getSelectedColumn() == 6) {
                    if (entityTable.getSelectedRow() != -1) {
                        presenter.deleteRowButtonPressed(entityTable.getSelectedRow());
                    }
                }
            }
        });

        TableColumn managerColumn = this.entityTable.getColumnModel().getColumn(2);
        JComboBox<String> managerComboBox = new JComboBox<>();
        ArrayList<String> managers = dropDownListsData.get(0);
        for (String manager: managers) {
            managerComboBox.addItem(manager);
        }
        managerColumn.setCellEditor(new DefaultCellEditor(managerComboBox));

        TableColumn deleteColumn = this.entityTable.getColumnModel().getColumn(6);
        deleteColumn.setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                component.setForeground(Color.RED);
                return component;
            }
        });

        this.entityTableModel.addTableModelListener(this.tableModelListener);
    }

    private void setTableModelForContracts(ArrayList<String> columnNames, ArrayList<ArrayList<String>> dropDownListsData) {
        this.reinitializeTable();
        this.entityTableModel.addAllEditableColumns(new Integer[]{1, 2, 4});
        for (String columnName: columnNames) {
            this.entityTableModel.addColumn(columnName);
        }
        this.entityTableModel.addColumn("");

        this.entityTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (entityTable.getSelectedColumn() == 5) {
                    if (entityTable.getSelectedRow() != -1) {
                        presenter.deleteRowButtonPressed(entityTable.getSelectedRow());
                    }
                }
            }
        });

        TableColumn managerColumn = this.entityTable.getColumnModel().getColumn(2);
        JComboBox<String> managerComboBox = new JComboBox<>();
        ArrayList<String> managers = dropDownListsData.get(0);
        for (String manager: managers) {
            managerComboBox.addItem(manager);
        }
        managerColumn.setCellEditor(new DefaultCellEditor(managerComboBox));

        TableColumn deleteColumn = this.entityTable.getColumnModel().getColumn(5);
        deleteColumn.setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                component.setForeground(Color.RED);
                return component;
            }
        });

        this.entityTableModel.addTableModelListener(this.tableModelListener);
    }

    private void setTableModelForSubcontracts(ArrayList<String> columnNames) {
        this.reinitializeTable();
        this.entityTableModel.addAllEditableColumns(new Integer[]{1, 2, 4});
        for (String columnName: columnNames) {
            this.entityTableModel.addColumn(columnName);
        }
        this.entityTableModel.addColumn("");

        this.entityTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (entityTable.getSelectedColumn() == 5) {
                    if (entityTable.getSelectedRow() != -1) {
                        presenter.deleteRowButtonPressed(entityTable.getSelectedRow());
                    }
                }
            }
        });

        TableColumn deleteColumn = this.entityTable.getColumnModel().getColumn(5);
        deleteColumn.setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                component.setForeground(Color.RED);
                return component;
            }
        });

        this.entityTableModel.addTableModelListener(this.tableModelListener);
    }

    private void setTableModelForEquipmentType(ArrayList<String> columnNames) {
        this.reinitializeTable();
        this.entityTableModel.addAllEditableColumns(new Integer[]{1});
        for (String columnName: columnNames) {
            this.entityTableModel.addColumn(columnName);
        }
        this.entityTableModel.addColumn("");

        this.entityTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (entityTable.getSelectedColumn() == 2) {
                    if (entityTable.getSelectedRow() != -1) {
                        presenter.deleteRowButtonPressed(entityTable.getSelectedRow());
                    }
                }
            }
        });

        TableColumn deleteColumn = this.entityTable.getColumnModel().getColumn(2);
        deleteColumn.setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                component.setForeground(Color.RED);
                return component;
            }
        });

        this.entityTableModel.addTableModelListener(this.tableModelListener);
    }

    private void setTableModelForProjectsEmployees(ArrayList<String> columnNames) {
        this.reinitializeTable();
        for (String columnName: columnNames) {
            this.entityTableModel.addColumn(columnName);
        }
        this.entityTableModel.addColumn("");

        this.entityTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (entityTable.getSelectedColumn() == 2) {
                    if (entityTable.getSelectedRow() != -1) {
                        presenter.deleteRowButtonPressed(entityTable.getSelectedRow());
                    }
                }
            }
        });

        TableColumn deleteColumn = this.entityTable.getColumnModel().getColumn(2);
        deleteColumn.setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                component.setForeground(Color.RED);
                return component;
            }
        });

        this.entityTableModel.addTableModelListener(this.tableModelListener);
    }

    private void setTableModelForEquipmentProjects(ArrayList<String> columnNames) {
        this.reinitializeTable();
        for (String columnName: columnNames) {
            this.entityTableModel.addColumn(columnName);
        }
        this.entityTableModel.addColumn("");

        this.entityTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (entityTable.getSelectedColumn() == 2) {
                    if (entityTable.getSelectedRow() != -1) {
                        presenter.deleteRowButtonPressed(entityTable.getSelectedRow());
                    }
                }
            }
        });

        TableColumn deleteColumn = this.entityTable.getColumnModel().getColumn(2);
        deleteColumn.setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                component.setForeground(Color.RED);
                return component;
            }
        });

        this.entityTableModel.addTableModelListener(this.tableModelListener);
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
