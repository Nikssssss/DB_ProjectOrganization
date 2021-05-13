package modules.register;

import services.DataTransformer;
import services.QueriesExecutor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class RegisterView extends JFrame {
    private final JPanel panel = new JPanel();
    private final JComboBox<String> employeesComboBox = new JComboBox<>();
    private final JTextField loginTextField = new JTextField();
    private final JTextField passwordTextField = new JPasswordField();
    private final JButton registerButton = new JButton();

    public RegisterView() {
        this.setupView();
        this.getContentPane().add(panel);
        this.pack();
        this.centerWindow();
        this.setVisible(true);
    }

    private void setupView() {
        panel.setPreferredSize(new Dimension(400, 400));
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.LIGHT_GRAY);
        try {
            setupSubComponents();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setupSubComponents() throws SQLException {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        ArrayList<String> unregisteredEmployees = QueriesExecutor.getAllUnregisteredEmployees();
        for (String employee: unregisteredEmployees) {
            employeesComboBox.addItem(employee);
        }
        panel.add(employeesComboBox, constraints);

        constraints.gridy = 1;
        constraints.insets.top = 10;
        JLabel loginLabel = new JLabel();
        loginLabel.setText("Логин");
        panel.add(loginLabel, constraints);

        constraints.gridy = 2;
        constraints.insets.top = 5;
        loginTextField.setPreferredSize(new Dimension(250, 40));
        panel.add(loginTextField, constraints);

        constraints.gridy = 3;
        constraints.insets.top = 10;
        JLabel passwordLabel = new JLabel();
        passwordLabel.setText("Пароль");
        panel.add(passwordLabel, constraints);

        constraints.gridy = 4;
        constraints.insets.top = 5;
        passwordTextField.setPreferredSize(new Dimension(250, 40));
        panel.add(passwordTextField, constraints);

        constraints.gridy = 5;
        constraints.insets.top = 20;
        registerButton.setPreferredSize(new Dimension(200, 40));
        registerButton.setText("Зарегистрировать");
        panel.add(registerButton, constraints);
        registerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                registerButtonPressed();
            }
        });
    }

    private void registerButtonPressed() {
        if (loginTextField.getText().isEmpty()
                || passwordTextField.getText().isEmpty()
                || employeesComboBox.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(panel, "Пожалуйста, заполните все поля", "Ошибка!", JOptionPane.ERROR_MESSAGE);
        } else {
            String employeeId = DataTransformer.getDatabaseManagerIdBy((String) employeesComboBox.getSelectedItem());
            String login = loginTextField.getText();
            String password = passwordTextField.getText();
            try {
                QueriesExecutor.registerEmployee(employeeId, login, password);
                clearFields();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(panel, e.getMessage(), "Ошибка!", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    private void clearFields() throws SQLException {
        loginTextField.setText("");
        passwordTextField.setText("");
        employeesComboBox.removeAllItems();
        ArrayList<String> unregisteredEmployees = QueriesExecutor.getAllUnregisteredEmployees();
        for (String employee: unregisteredEmployees) {
            employeesComboBox.addItem(employee);
        }
    }

    private void centerWindow() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);
    }
}
