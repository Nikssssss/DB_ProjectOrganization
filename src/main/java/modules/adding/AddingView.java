package modules.adding;

import modules.tables.enums.TableType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class AddingView {
    private AddingPresenter presenter;

    private JPanel addingPanel;
    private ArrayList<Component> fieldComponents;
    private ArrayList<JLabel> fieldLabels;
    private final JButton addRowButton = new JButton("Добавить");
    private final JButton backToTablesButton = new JButton("Назад к таблицам");

    public void setPresenter(AddingPresenter presenter) {
        this.presenter = presenter;
    }

    public void setErrorMessage(String errorMessage){
        JOptionPane.showMessageDialog(addingPanel, errorMessage, "Error!", JOptionPane.ERROR_MESSAGE);
    }

    public JPanel getAddingPanel(){
        return addingPanel;
    }

    public void didLoad() {
        this.presenter.viewDidLoad();
    }

    public void configureView(TableType tableType, ArrayList<ArrayList<String>> comboBoxData) {
        this.setupView(tableType, comboBoxData);
    }

    public ArrayList<String> getAllInsertingData() {
        ArrayList<String> insertingData = new ArrayList<>();
        for (Component component: fieldComponents) {
            if (component instanceof JTextField) {
                insertingData.add(((JTextField)component).getText());
            } else if (component instanceof JComboBox) {
                insertingData.add((String) ((JComboBox) component).getSelectedItem());
            }
        }
        return insertingData;
    }

    public ArrayList<Object> getAllProjectsInsertingData() {
        ArrayList<Object> insertingData = new ArrayList<>();
        for (Component component: fieldComponents) {
            if (component instanceof JTextField) {
                insertingData.add(((JTextField) component).getText());
            } else if (component instanceof JComboBox) {
                insertingData.add((String) ((JComboBox) component).getSelectedItem());
            } else if (component instanceof JScrollPane) {
                insertingData.add(((JList)(((JScrollPane) component).getViewport().getComponent(0))).getSelectedValuesList());
            }
        }
        return insertingData;
    }

    public boolean hasBlankFields() {
        for (Component component: fieldComponents) {
            if (component instanceof JTextField) {
                if (((JTextField) component).getText() == null || ((JTextField) component).getText().equals("")) {
                    return true;
                }
            } else if (component instanceof JScrollPane) {
                if ((((JList)(((JScrollPane) component).getViewport().getComponent(0))).getSelectedValuesList()).isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void clearAllFields() {
        for (Component component: fieldComponents) {
            if (component instanceof JTextField) {
                ((JTextField) component).setText("");
            }
        }
    }

    public String getEmployeeCategory() {
        return (String) ((JComboBox)fieldComponents.get(3)).getSelectedItem();
    }

    public void changeEmployeeCategoryTo(String category, ArrayList<String> dropDownListData) {
        switch (category) {
            case "Техник":
                fieldLabels.get(fieldLabels.size() - 1).setText("Тип оборудования");
                JComboBox<String> equipmentTypesComboBox = new JComboBox<>();
                for (String equipmentType : dropDownListData) {
                    equipmentTypesComboBox.addItem(equipmentType);
                }
                fieldComponents.set(fieldComponents.size() - 1, equipmentTypesComboBox);
                placeSubComponents();
                return;
            case "Инженер":
                fieldLabels.get(fieldLabels.size() - 1).setText("Программа для проектирования");
                break;
            case "Конструктор":
                fieldLabels.get(fieldLabels.size() - 1).setText("Число свидетельств");
                break;
            case "Бухгалтер":
                fieldLabels.get(fieldLabels.size() - 1).setText("Программа для бух.учета");
                break;
            case "Менеджер":
                fieldLabels.get(fieldLabels.size() - 1).setText("Количество собеседований");
                break;
        }
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(180, 35));
        fieldComponents.set(fieldComponents.size() - 1, textField);
        placeSubComponents();
    }

    public Boolean chooseOrganizationForProject() {
        addingPanel = new JPanel();
        String[] organizations = {"Подрядная организация", "Субподрядная организация"};
        String chosenOrganization = (String) JOptionPane.showInputDialog(addingPanel, "Выберите организацию для реализации проекта",
                "Выбор организации", JOptionPane.QUESTION_MESSAGE, null, organizations, organizations[0]);
        if (chosenOrganization != null) {
            return chosenOrganization.equals(organizations[0]);
        } else {
            return null;
        }
    }

    public Boolean chooseProject() {
        addingPanel = new JPanel();
        String[] projects = {"Новый проект", "Существующий проект"};
        String chosenProject = (String) JOptionPane.showInputDialog(addingPanel, "Выберите тип проекта",
                "Выбор типа проекта", JOptionPane.QUESTION_MESSAGE, null, projects, projects[0]);
        if (chosenProject != null) {
            return chosenProject.equals(projects[0]);
        } else {
            return null;
        }
    }

    public void configureProjectsView(ArrayList<ArrayList<String>> comboBoxData, boolean isOwnOrganization, boolean isNewProject) {
        if (isOwnOrganization) {
            this.setupProjectsContractView(comboBoxData, isNewProject);
        } else {
            this.setupProjectsSubcontractView(comboBoxData, isNewProject);
        }
    }

    public boolean isOwnProjectOrganization() {
        for (JLabel jLabel: fieldLabels) {
            if (jLabel.getText().equals("Договор")) {
                return true;
            }
        }
        return false;
    }

    //MARK: private methods

    private void setupView(TableType tableType, ArrayList<ArrayList<String>> comboBoxData) {
        switch (tableType) {
            case EMPLOYEES: {
                this.setupEmployeesAddingView(comboBoxData);
                break;
            }
            case DEPARTMENTS: {
                this.setupDepartmentsAddingView(comboBoxData);
                break;
            }
            case PROFESSIONS: {
                this.setupProfessionsAddingView(comboBoxData);
                break;
            }
            case EQUIPMENTTYPE: {
                this.setupEquipmentTypeAddingView();
                break;
            }
            case EQUIPMENT: {
                this.setupEquipmentAddingView(comboBoxData);
                break;
            }
            case CONTRACTS: {
                this.setupContractsAddingView(comboBoxData);
                break;
            }
            case SUBCONTRACTS: {
                this.setupSubcontractsAddingView();
                break;
            }
        }
    }

    private void placeSubComponents() {
        addingPanel.removeAll();

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets.bottom = 15;
        addingPanel.add(backToTablesButton, constraints);

        constraints.gridwidth = 3;
        constraints.insets.bottom = 0;
        int currentLabelGridY = 1;
        int currentTextFieldGridY = 2;
        for (int i = 0; i < fieldComponents.size(); i++) {
            constraints.gridy = currentLabelGridY;
            constraints.insets.top = 5;
            addingPanel.add(fieldLabels.get(i), constraints);
            constraints.gridy = currentTextFieldGridY;
            constraints.insets.top = 2;
            addingPanel.add(fieldComponents.get(i), constraints);
            currentLabelGridY += 2;
            currentTextFieldGridY += 2;
        }

        constraints.gridy = 2 * fieldComponents.size() + 1;
        constraints.insets.top = 30;
        addingPanel.add(addRowButton, constraints);

        addingPanel.setPreferredSize(new Dimension(300, 2 * 40 + 2 * 30 + fieldComponents.size() * 60));
    }

    private void setupButtons() {
        backToTablesButton.setPreferredSize(new Dimension(200, 40));
        backToTablesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                presenter.backToTablesButtonPressed();
            }
        });

        addRowButton.setPreferredSize(new Dimension(200, 40));
        addRowButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                presenter.addRowButtonPressed();
            }
        });
    }

    private void setupEmployeesAddingView(ArrayList<ArrayList<String>> comboBoxData) {
        addingPanel = new JPanel();
        addingPanel.setLayout(new GridBagLayout());
        addingPanel.setBackground(Color.LIGHT_GRAY);

        String[] fieldNames = new String[]{"Имя", "Фамилия", "Дата устройства (гггг-мм-дд)", "Профессия", "Зарплата", "Возраст"};
        fieldComponents = new ArrayList<>();
        fieldLabels = new ArrayList<>();
        for (String fieldName: fieldNames) {
            fieldLabels.add(new JLabel(fieldName));
            if (fieldName.equals("Профессия")) {
                JComboBox<String> professionsComboBox = new JComboBox<>();
                ArrayList<String> professions = comboBoxData.get(0);
                for (String profession: professions) {
                    professionsComboBox.addItem(profession);
                }
                fieldComponents.add(professionsComboBox);
                professionsComboBox.addActionListener(e -> {
                    presenter.employeeCategoryDidChange();
                });
            } else {
                JTextField textField = new JTextField();
                textField.setPreferredSize(new Dimension(180, 35));
                fieldComponents.add(textField);
            }
        }

        fieldLabels.add(new JLabel("Тип оборудования"));
        JComboBox<String> equipmentTypesComboBox = new JComboBox<>();
        ArrayList<String> equipmentTypes = comboBoxData.get(1);
        for (String profession: equipmentTypes) {
            equipmentTypesComboBox.addItem(profession);
        }
        fieldComponents.add(equipmentTypesComboBox);

        this.setupButtons();
        this.placeSubComponents();
    }

    private void setupDepartmentsAddingView(ArrayList<ArrayList<String>> comboBoxData) {
        addingPanel = new JPanel();
        addingPanel.setLayout(new GridBagLayout());
        addingPanel.setBackground(Color.LIGHT_GRAY);

        String[] fieldNames = new String[]{"Название отдела", "Менеджер"};
        fieldComponents = new ArrayList<>();
        fieldLabels = new ArrayList<>();
        for (String fieldName: fieldNames) {
            fieldLabels.add(new JLabel(fieldName));
            if (fieldName.equals("Менеджер")) {
                JComboBox<String> managersComboBox = new JComboBox<>();
                ArrayList<String> managers = comboBoxData.get(0);
                for (String profession: managers) {
                    managersComboBox.addItem(profession);
                }
                fieldComponents.add(managersComboBox);
            } else {
                JTextField textField = new JTextField();
                textField.setPreferredSize(new Dimension(180, 35));
                fieldComponents.add(textField);
            }
        }

        this.setupButtons();
        this.placeSubComponents();
    }

    private void setupProfessionsAddingView(ArrayList<ArrayList<String>> comboBoxData) {
        addingPanel = new JPanel();
        addingPanel.setLayout(new GridBagLayout());
        addingPanel.setBackground(Color.LIGHT_GRAY);

        String[] fieldNames = new String[]{"Название профессии", "Способность руководить", "Отдел"};
        fieldComponents = new ArrayList<>();
        fieldLabels = new ArrayList<>();
        for (String fieldName: fieldNames) {
            fieldLabels.add(new JLabel(fieldName));
            if (fieldName.equals("Отдел")) {
                JComboBox<String> departmentsComboBox = new JComboBox<>();
                ArrayList<String> departments = comboBoxData.get(1);
                for (String profession: departments) {
                    departmentsComboBox.addItem(profession);
                }
                fieldComponents.add(departmentsComboBox);
            } else if (fieldName.equals("Способность руководить")) {
                JComboBox<String> managementAbilityComboBox = new JComboBox<>();
                ArrayList<String> managementAbilities = comboBoxData.get(0);
                for (String profession: managementAbilities) {
                    managementAbilityComboBox.addItem(profession);
                }
                fieldComponents.add(managementAbilityComboBox);
            } else {
                JTextField textField = new JTextField();
                textField.setPreferredSize(new Dimension(180, 35));
                fieldComponents.add(textField);
            }
        }

        this.setupButtons();
        this.placeSubComponents();
    }

    private void setupEquipmentTypeAddingView() {
        addingPanel = new JPanel();
        addingPanel.setLayout(new GridBagLayout());
        addingPanel.setBackground(Color.LIGHT_GRAY);

        String[] fieldNames = new String[]{"Название типа"};
        fieldComponents = new ArrayList<>();
        fieldLabels = new ArrayList<>();
        for (String fieldName: fieldNames) {
            fieldLabels.add(new JLabel(fieldName));
            JTextField textField = new JTextField();
            textField.setPreferredSize(new Dimension(180, 35));
            fieldComponents.add(textField);
        }

        this.setupButtons();
        this.placeSubComponents();
    }

    private void setupEquipmentAddingView(ArrayList<ArrayList<String>> comboBoxData) {
        addingPanel = new JPanel();
        addingPanel.setLayout(new GridBagLayout());
        addingPanel.setBackground(Color.LIGHT_GRAY);

        String[] fieldNames = new String[]{"Название оборудования", "Тип оборудования", "Отдел"};
        fieldComponents = new ArrayList<>();
        fieldLabels = new ArrayList<>();
        for (String fieldName: fieldNames) {
            fieldLabels.add(new JLabel(fieldName));
            if (fieldName.equals("Отдел")) {
                JComboBox<String> departmentsComboBox = new JComboBox<>();
                ArrayList<String> departments = comboBoxData.get(1);
                for (String profession: departments) {
                    departmentsComboBox.addItem(profession);
                }
                fieldComponents.add(departmentsComboBox);
            } else if (fieldName.equals("Тип оборудования")) {
                JComboBox<String> equipmentTypesComboBox = new JComboBox<>();
                ArrayList<String> equipmentTypes = comboBoxData.get(0);
                for (String profession: equipmentTypes) {
                    equipmentTypesComboBox.addItem(profession);
                }
                fieldComponents.add(equipmentTypesComboBox);
            } else {
                JTextField textField = new JTextField();
                textField.setPreferredSize(new Dimension(180, 35));
                fieldComponents.add(textField);
            }
        }

        this.setupButtons();
        this.placeSubComponents();
    }

    private void setupContractsAddingView(ArrayList<ArrayList<String>> comboBoxData) {
        addingPanel = new JPanel();
        addingPanel.setLayout(new GridBagLayout());
        addingPanel.setBackground(Color.LIGHT_GRAY);

        String[] fieldNames = new String[]{"Руководитель договора", "Дата начала (гггг-мм-дд)", "Дата окончания (гггг-мм-дд)"};
        fieldComponents = new ArrayList<>();
        fieldLabels = new ArrayList<>();
        for (String fieldName: fieldNames) {
            fieldLabels.add(new JLabel(fieldName));
            if (fieldName.equals("Руководитель договора")) {
                JComboBox<String> managersComboBox = new JComboBox<>();
                ArrayList<String> managers = comboBoxData.get(0);
                for (String profession: managers) {
                    managersComboBox.addItem(profession);
                }
                fieldComponents.add(managersComboBox);
            } else {
                JTextField textField = new JTextField();
                textField.setPreferredSize(new Dimension(180, 35));
                fieldComponents.add(textField);
            }
        }

        this.setupButtons();
        this.placeSubComponents();
    }

    private void setupSubcontractsAddingView() {
        addingPanel = new JPanel();
        addingPanel.setLayout(new GridBagLayout());
        addingPanel.setBackground(Color.LIGHT_GRAY);

        String[] fieldNames = new String[]{"Название суборганизации", "Дата начала (гггг-мм-дд)", "Дата окончания (гггг-мм-дд)"};
        fieldComponents = new ArrayList<>();
        fieldLabels = new ArrayList<>();
        for (String fieldName: fieldNames) {
            fieldLabels.add(new JLabel(fieldName));
            JTextField textField = new JTextField();
            textField.setPreferredSize(new Dimension(180, 35));
            fieldComponents.add(textField);
        }

        this.setupButtons();
        this.placeSubComponents();
    }

    private void setupProjectsContractView(ArrayList<ArrayList<String>> comboBoxData, boolean isNewProject) {
        addingPanel = new JPanel();
        addingPanel.setLayout(new GridBagLayout());
        addingPanel.setBackground(Color.LIGHT_GRAY);

        String[] fieldNames;
        if (isNewProject) {
            fieldNames = new String[]{"Договор", "Руководитель проекта", "Исполнители",
                    "Оборудование", "Стоимость проекта", "Дата начала (гггг-мм-дд)", "Дата окончания (гггг-мм-дд)"};
        } else {
            fieldNames = new String[]{"Проект", "Договор"};
        }
        fieldComponents = new ArrayList<>();
        fieldLabels = new ArrayList<>();
        for (String fieldName: fieldNames) {
            fieldLabels.add(new JLabel(fieldName));
            switch (fieldName) {
                case "Проект":
                    JComboBox<String> projectsComboBox = new JComboBox<>();
                    ArrayList<String> projects = comboBoxData.get(0);
                    for (String profession : projects) {
                        projectsComboBox.addItem(profession);
                    }
                    fieldComponents.add(projectsComboBox);
                    break;
                case "Договор":
                    JComboBox<String> contractComboBox = new JComboBox<>();
                    ArrayList<String> contracts = comboBoxData.get(1);
                    for (String profession : contracts) {
                        contractComboBox.addItem(profession);
                    }
                    fieldComponents.add(contractComboBox);
                    break;
                case "Руководитель проекта":
                    JComboBox<String> managersComboBox = new JComboBox<>();
                    ArrayList<String> managers = comboBoxData.get(3);
                    for (String profession : managers) {
                        managersComboBox.addItem(profession);
                    }
                    fieldComponents.add(managersComboBox);
                    break;
                case "Исполнители":
                    JList<String> employeesList = new JList<>(comboBoxData.get(4).toArray(new String[0]));
                    employeesList.setVisibleRowCount(3);
                    employeesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                    fieldComponents.add(new JScrollPane(employeesList));
                    break;
                case "Оборудование":
                    JList<String> equipmentList = new JList<>(comboBoxData.get(5).toArray(new String[0]));
                    equipmentList.setVisibleRowCount(3);
                    equipmentList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                    fieldComponents.add(new JScrollPane(equipmentList));
                    break;
                default:
                    JTextField textField = new JTextField();
                    textField.setPreferredSize(new Dimension(180, 35));
                    fieldComponents.add(textField);
            }
        }

        this.setupButtons();
        this.placeSubComponents();
    }

    private void setupProjectsSubcontractView(ArrayList<ArrayList<String>> comboBoxData, boolean isNewProject) {
        addingPanel = new JPanel();
        addingPanel.setLayout(new GridBagLayout());
        addingPanel.setBackground(Color.LIGHT_GRAY);

        String[] fieldNames;
        if (isNewProject) {
            fieldNames = new String[]{"Субдоговор", "Руководитель проекта",
                    "Стоимость проекта", "Дата начала (гггг-мм-дд)", "Дата окончания (гггг-мм-дд)"};
        } else {
            fieldNames = new String[]{"Проект", "Субдоговор"};
        }
        fieldComponents = new ArrayList<>();
        fieldLabels = new ArrayList<>();
        for (String fieldName: fieldNames) {
            fieldLabels.add(new JLabel(fieldName));
            switch (fieldName) {
                case "Проект":
                    JComboBox<String> projectsComboBox = new JComboBox<>();
                    ArrayList<String> projects = comboBoxData.get(0);
                    for (String profession : projects) {
                        projectsComboBox.addItem(profession);
                    }
                    fieldComponents.add(projectsComboBox);
                    break;
                case "Субдоговор":
                    JComboBox<String> subcontractComboBox = new JComboBox<>();
                    ArrayList<String> subcontracts = comboBoxData.get(2);
                    for (String profession : subcontracts) {
                        subcontractComboBox.addItem(profession);
                    }
                    fieldComponents.add(subcontractComboBox);
                    break;
                case "Руководитель проекта":
                    JComboBox<String> managersComboBox = new JComboBox<>();
                    ArrayList<String> managers = comboBoxData.get(3);
                    for (String profession : managers) {
                        managersComboBox.addItem(profession);
                    }
                    fieldComponents.add(managersComboBox);
                    break;
                default:
                    JTextField textField = new JTextField();
                    textField.setPreferredSize(new Dimension(180, 35));
                    fieldComponents.add(textField);
            }
        }

        this.setupButtons();
        this.placeSubComponents();
    }

}
