package modules.tables;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class TablesView {
    private TablesPresenter presenter;

    private final JPanel tablePanel = new JPanel();
    private DefaultTableModel entityTableModel;
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

    public void setTableData(String[] columnNames, ArrayList<String[]> rows) {
        this.entityTableModel.setColumnCount(0);
        this.entityTableModel.getDataVector().removeAllElements();
        for (String column: columnNames) {
            this.entityTableModel.addColumn(column);
        }
        for (String[] row: rows) {
            this.entityTableModel.addRow(row);
        }
    }

    public String getCurrentTableName() {
        return (String) entitiesComboBox.getSelectedItem();
    }

    //MARK: private methods

    private void setupView() {
        this.tablePanel.setBackground(Color.LIGHT_GRAY);
        this.tablePanel.setLayout(new GridBagLayout());
        this.tablePanel.setPreferredSize(new Dimension(700, 700));
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

        this.entityTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        };
        this.entityTable = new JTable(this.entityTableModel);
        this.entityScrollPane = new JScrollPane(this.entityTable);
        this.entityTable.setPreferredSize(new Dimension(650, 650));
        this.entityScrollPane.setPreferredSize(new Dimension(650, 600));
        this.entityTableModel.addColumn("Имя");
        this.entityTableModel.addColumn("Фамилия");
        this.entityTableModel.addColumn("Возраст");
        this.entityScrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                (String) this.entitiesComboBox.getSelectedItem(),
                TitledBorder.CENTER,
                TitledBorder.TOP));
        this.entityTableModel.addRow(new String[]{"Никита", "Гусев", "10"});
        this.entityTableModel.addRow(new String[]{"Никита", "Гусев", "10"});
        this.entityTableModel.addRow(new String[]{"Никита", "Гусев", "10"});
        this.entityTableModel.addRow(new String[]{"Никита", "Гусев", "10"});
        this.entityTableModel.addRow(new String[]{"Никита", "Гусев", "10"});
    }

    private void placeSubComponents() {
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets.right = 100;
        this.tablePanel.add(this.backButton, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.insets.right = 0;
        this.tablePanel.add(this.entitiesComboBox, constraints);

        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.insets.left = 100;
        this.tablePanel.add(this.addRowButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.insets.top = 20;
        constraints.insets.left = 0;
        constraints.gridwidth = 3;
        this.tablePanel.add(this.entityScrollPane, constraints);
    }

}
