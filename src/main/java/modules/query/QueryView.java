package modules.query;

import modules.queries.enums.QueryType;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class QueryView {
    private QueryPresenter presenter;

    private final JPanel queryPanel = new JPanel();
    private final JButton backToQueriesButton = new JButton("Назад к запросам");
    private final JButton executeQueryButton = new JButton("Выполнить запрос");
    private final ArrayList<Component> parameterComponents = new ArrayList<>();
    private final ArrayList<JLabel> parameterLabels = new ArrayList<>();
    private final DefaultTableModel queryTableModel = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable queryTable = new JTable(queryTableModel);
    private final JScrollPane queryScrollPane = new JScrollPane(queryTable);

    public void setPresenter(QueryPresenter presenter) {
        this.presenter = presenter;
    }

    public void didLoad() {
        this.presenter.viewDidLoad();
    }

    public void configureView(QueryType queryType, ArrayList<ArrayList<String>> comboBoxData) {
        this.setupView(queryType, comboBoxData);
    }

    public JPanel getQueryPanel(){
        return queryPanel;
    }

    public void setQueryResultData(ArrayList<ArrayList<String>> resultRows) {
        queryTableModel.setRowCount(0);
        for (ArrayList<String> row: resultRows) {
            queryTableModel.addRow(row.toArray());
        }
    }

    public void setQueryResultStructure(ArrayList<String> columnNames) {
        for (String columnName: columnNames) {
            queryTableModel.addColumn(columnName);
        }
    }

    public ArrayList<String> getAllParameters() {
        ArrayList<String> parameters = new ArrayList<>();
        for (Component component: parameterComponents) {
            if (component instanceof JTextField) {
                parameters.add(((JTextField)component).getText());
            } else if (component instanceof JComboBox) {
                parameters.add((String) ((JComboBox)component).getSelectedItem());
            }
        }
        return parameters;
    }

    public void setErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(queryPanel, errorMessage, "Error!", JOptionPane.ERROR_MESSAGE);
    }

    //MARK: private methods

    private void setupView(QueryType queryType, ArrayList<ArrayList<String>> comboBoxData) {
        this.queryPanel.setPreferredSize(new Dimension(750, 500));
        this.queryPanel.setLayout(new GridBagLayout());
        this.queryPanel.setBackground(Color.LIGHT_GRAY);
        this.setupSubComponents(queryType, comboBoxData);
        this.placeSubComponents();
    }

    private void setupSubComponents(QueryType queryType, ArrayList<ArrayList<String>> comboBoxData){
        this.backToQueriesButton.setPreferredSize(new Dimension(400, 40));
        this.backToQueriesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                presenter.backToQueriesButtonPressed();
            }
        });

        switch (queryType) {
            case ORGANIZATION_ROSTER: {
                this.setupOrganizationRosterQueryParameters(comboBoxData);
                break;
            }
            case CONTRACTS_LIST: {
                this.setupContractsListQueryParameters();
                break;
            }
            case PROJECTS_LIST: {
                this.setupProjectsListQueryParameters();
                break;
            }
            case EQUIPMENT_PROJECTS: {
                this.setupEquipmentProjectsQueryParameters();
                break;
            }
            case PROJECTS_OF_CONTRACT: {
                this.setupProjectsOfContractQueryParameters(comboBoxData);
                break;
            }
            case CONTRACT_OF_PROJECT: {
                this.setupContractOfProjectQueryParameters(comboBoxData);
                break;
            }
            case EQUIPMENT_OF_PROJECT: {
                this.setupEquipmentOfProjectQueryParameters(comboBoxData);
                break;
            }
            case EMPLOYEES_OF_PROJECT: {
                this.setupEmployeesOfProjectQueryParameters(comboBoxData);
                break;
            }
            case COST_OF_PROJECTS: {
                this.setupCostOfProjectsQueryParameters();
                break;
            }
            case PROJECT_ROSTER_COUNT: {
                this.setupProjectRosterCountQueryParameters(comboBoxData);
                break;
            }
        }

        this.executeQueryButton.setPreferredSize(new Dimension(400, 40));
        this.executeQueryButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                presenter.executeQueryButtonPressed();
            }
        });

        this.queryTable.setPreferredSize(new Dimension(730, 150));
        this.queryScrollPane.setPreferredSize(new Dimension(730, 150));
        this.queryScrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                "Результат",
                TitledBorder.CENTER,
                TitledBorder.TOP));
        this.queryTable.setColumnSelectionAllowed(false);
        this.queryTable.setRowSelectionAllowed(false);
        this.queryTable.setCellSelectionEnabled(false);
    }

    private void setupOrganizationRosterQueryParameters(ArrayList<ArrayList<String>> comboBoxData) {
        String[] fieldNames = new String[]{"Мин. дата устройства (гггг-мм-дд)", "Макс. дата устройства (гггг-мм-дд)",
                "Профессия", "Мин. зарплата", "Макс. зарплата", "Мин. возраст", "Макс. возраст"};
        for (String fieldName: fieldNames) {
            parameterLabels.add(new JLabel(fieldName));
            if (fieldName.equals("Профессия")) {
                JComboBox<String> professionsComboBox = new JComboBox<>();
                ArrayList<String> professions = comboBoxData.get(0);
                for (String profession: professions) {
                    professionsComboBox.addItem(profession);
                }
                parameterComponents.add(professionsComboBox);
            } else {
                JTextField textField = new JTextField();
                textField.setPreferredSize(new Dimension(180, 35));
                parameterComponents.add(textField);
            }
        }
    }

    private void setupContractsListQueryParameters() {
        String[] fieldNames = new String[]{"Мин. дата (гггг-мм-дд)", "Макс. дата (гггг-мм-дд)"};
        for (String fieldName: fieldNames) {
            parameterLabels.add(new JLabel(fieldName));
            JTextField textField = new JTextField();
            textField.setPreferredSize(new Dimension(180, 35));
            parameterComponents.add(textField);
        }
    }

    private void setupProjectsListQueryParameters() {
        String[] fieldNames = new String[]{"Мин. дата (гггг-мм-дд)", "Макс. дата (гггг-мм-дд)"};
        for (String fieldName: fieldNames) {
            parameterLabels.add(new JLabel(fieldName));
            JTextField textField = new JTextField();
            textField.setPreferredSize(new Dimension(180, 35));
            parameterComponents.add(textField);
        }
    }

    private void setupEquipmentProjectsQueryParameters() {
        String[] fieldNames = new String[]{"Мин. дата (гггг-мм-дд)", "Макс. дата (гггг-мм-дд)"};
        for (String fieldName: fieldNames) {
            parameterLabels.add(new JLabel(fieldName));
            JTextField textField = new JTextField();
            textField.setPreferredSize(new Dimension(180, 35));
            parameterComponents.add(textField);
        }
    }

    private void setupProjectsOfContractQueryParameters(ArrayList<ArrayList<String>> comboBoxData) {
        String[] fieldNames = new String[]{"Договор"};
        for (String fieldName: fieldNames) {
            parameterLabels.add(new JLabel(fieldName));
            JComboBox<String> contractsComboBox = new JComboBox<>();
            ArrayList<String> contracts = comboBoxData.get(0);
            for (String contract: contracts) {
                contractsComboBox.addItem(contract);
            }
            parameterComponents.add(contractsComboBox);
        }
    }

    private void setupContractOfProjectQueryParameters(ArrayList<ArrayList<String>> comboBoxData) {
        String[] fieldNames = new String[]{"Проект"};
        for (String fieldName: fieldNames) {
            parameterLabels.add(new JLabel(fieldName));
            JComboBox<String> projectsComboBox = new JComboBox<>();
            ArrayList<String> projects = comboBoxData.get(0);
            for (String project: projects) {
                projectsComboBox.addItem(project);
            }
            parameterComponents.add(projectsComboBox);
        }
    }

    private void setupEquipmentOfProjectQueryParameters(ArrayList<ArrayList<String>> comboBoxData) {
        String[] fieldNames = new String[]{"Проект"};
        for (String fieldName: fieldNames) {
            parameterLabels.add(new JLabel(fieldName));
            JComboBox<String> projectsComboBox = new JComboBox<>();
            ArrayList<String> projects = comboBoxData.get(0);
            for (String project: projects) {
                projectsComboBox.addItem(project);
            }
            parameterComponents.add(projectsComboBox);
        }
    }

    private void setupEmployeesOfProjectQueryParameters(ArrayList<ArrayList<String>> comboBoxData) {
        String[] fieldNames = new String[]{"Проект"};
        for (String fieldName: fieldNames) {
            parameterLabels.add(new JLabel(fieldName));
            JComboBox<String> projectsComboBox = new JComboBox<>();
            ArrayList<String> projects = comboBoxData.get(0);
            for (String project: projects) {
                projectsComboBox.addItem(project);
            }
            parameterComponents.add(projectsComboBox);
        }
    }

    private void setupCostOfProjectsQueryParameters() {
        String[] fieldNames = new String[]{"Дата начала (гггг-мм-дд)", "Дата окончания (гггг-мм-дд)"};
        for (String fieldName: fieldNames) {
            parameterLabels.add(new JLabel(fieldName));
            JTextField textField = new JTextField();
            textField.setPreferredSize(new Dimension(180, 35));
            parameterComponents.add(textField);
        }
    }

    private void setupProjectRosterCountQueryParameters(ArrayList<ArrayList<String>> comboBoxData) {
        String[] fieldNames = new String[]{"Проект"};
        for (String fieldName: fieldNames) {
            parameterLabels.add(new JLabel(fieldName));
            JComboBox<String> projectsComboBox = new JComboBox<>();
            ArrayList<String> projects = comboBoxData.get(0);
            for (String project: projects) {
                projectsComboBox.addItem(project);
            }
            parameterComponents.add(projectsComboBox);
        }
    }

    private void placeSubComponents(){
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets.bottom = 15;
        this.queryPanel.add(this.backToQueriesButton, constraints);

        constraints.insets.bottom = 0;
        int currentLabelGridY = 1;
        int currentTextFieldGridY = 2;
        for (int i = 0; i < parameterComponents.size(); i++) {
            constraints.gridy = currentLabelGridY;
            constraints.insets.top = 5;
            queryPanel.add(parameterLabels.get(i), constraints);
            constraints.gridy = currentTextFieldGridY;
            constraints.insets.top = 2;
            queryPanel.add(parameterComponents.get(i), constraints);
            currentLabelGridY += 2;
            currentTextFieldGridY += 2;
        }

        constraints.gridy = 2 * parameterComponents.size() + 1;
        constraints.insets.top = 15;
        queryPanel.add(executeQueryButton, constraints);

        constraints.gridy = 2 * parameterComponents.size() + 2;
        constraints.insets.top = 15;
        queryPanel.add(queryScrollPane, constraints);

        queryPanel.setPreferredSize(new Dimension(750, 2 * 55 + parameterComponents.size() * 60 + 170));
    }

}
