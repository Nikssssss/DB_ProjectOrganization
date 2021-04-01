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
    private JButton addRowButton = new JButton("Добавить");
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

    public boolean hasBlankFields() {
        for (Component component: fieldComponents) {
            if (component instanceof JTextField) {
                if (((JTextField) component).getText() == null || ((JTextField) component).getText().equals("")) {
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
        }
    }

    private void placeSubComponents() {
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
            } else {
                JTextField textField = new JTextField();
                textField.setPreferredSize(new Dimension(180, 35));
                fieldComponents.add(textField);
            }
        }

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

}
