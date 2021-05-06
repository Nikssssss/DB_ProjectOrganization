package modules.login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Enumeration;

public class LoginView {
    private LoginPresenter presenter;

    private final JPanel loginPanel = new JPanel();
    private final ArrayList<JLabel> fieldLabels = new ArrayList<>();
    private final ArrayList<JTextField> fieldTextFields = new ArrayList<>();
    private final JButton loginButton = new JButton("Войти");
    private final JButton localTemplateButton = new JButton("Локальная БД");
    private final JButton remoteTemplateButton = new JButton("Удалённая БД");

    public void setPresenter(LoginPresenter presenter) {
        this.presenter = presenter;
    }

    public void setErrorMessage(String errorMessage){
        JOptionPane.showMessageDialog(loginPanel, errorMessage, "Ошибка!", JOptionPane.ERROR_MESSAGE);
    }

    public JPanel getLoginPanel(){
        return loginPanel;
    }

    public void didLoad() {
        this.presenter.viewDidLoad();
    }

    public void configureView() {
        this.setupView();
    }

    public boolean hasBlankFields() {
        for (JTextField textField: fieldTextFields) {
            if (textField.getText() == null || textField.getText().equals("")) {
                return true;
            }
        }
        return false;
    }

    public void setLocalTemplateData() {
        for (int i = 0; i < fieldTextFields.size(); i++) {
            if (i == 0) {
                fieldTextFields.get(i).setText("192.168.0.6");
            } else if (i == 1) {
                fieldTextFields.get(i).setText("1521");
            } else if (i == 2) {
                fieldTextFields.get(i).setText("nikita");
            } else if (i == 3) {
                fieldTextFields.get(i).setText("nikita2710");
            }
        }
    }

    public void setRemoteTemplateData() {
        for (int i = 0; i < fieldTextFields.size(); i++) {
            if (i == 0) {
                fieldTextFields.get(i).setText("84.237.50.81");
            } else if (i == 1) {
                fieldTextFields.get(i).setText("1521");
            } else if (i == 2) {
                fieldTextFields.get(i).setText("18204_GUSEV");
            } else if (i == 3) {
                fieldTextFields.get(i).setText("nikita2710");
            }
        }
    }

    //MARK: private methods

    private void setupView() {
        loginPanel.setPreferredSize(new Dimension(550, 550));
        loginPanel.setLayout(new GridBagLayout());
        loginPanel.setBackground(Color.LIGHT_GRAY);
        this.setupSubComponents();
        this.placeSubComponents();
    }

    private void setupSubComponents(){
        fieldLabels.add(new JLabel("IP-адрес"));
        fieldLabels.add(new JLabel("Порт"));
        fieldLabels.add(new JLabel("Логин в БД"));
        fieldLabels.add(new JLabel("Пароль в БД"));
        fieldLabels.add(new JLabel("Логин в системе"));
        fieldLabels.add(new JLabel("Пароль в системе"));

        for (int i = 0; i < fieldLabels.size(); i++) {
            JTextField textField;
            if (i != 3 && i != 5) {
                textField = new JTextField();
            } else {
                textField = new JPasswordField();
            }
            textField.setPreferredSize(new Dimension(250, 40));
            fieldTextFields.add(textField);
        }

        loginButton.setPreferredSize(new Dimension(200, 40));
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                String ip = fieldTextFields.get(0).getText();
                String port = fieldTextFields.get(1).getText();
                String login = fieldTextFields.get(2).getText();
                String password = fieldTextFields.get(3).getText();
                String systemLogin = fieldTextFields.get(4).getText();
                String systemPassword = fieldTextFields.get(5).getText();
                presenter.loginButtonPressed(ip, port, login, password, systemLogin, systemPassword);
            }
        });

        localTemplateButton.setPreferredSize(new Dimension(150, 40));
        localTemplateButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                presenter.localTemplateButtonPressed();
            }
        });

        remoteTemplateButton.setPreferredSize(new Dimension(150, 40));
        remoteTemplateButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                presenter.remoteTemplateButtonPressed();
            }
        });
    }

    private void placeSubComponents() {
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets.bottom = 15;
        loginPanel.add(localTemplateButton, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.insets.bottom = 15;
        loginPanel.add(remoteTemplateButton, constraints);

        int currentLabelGridY = 1;
        int currentTextFieldGridY = 2;
        constraints.gridx = 0;
        constraints.gridwidth = 2;
        constraints.insets.bottom = 0;
        for (int i = 0; i < fieldTextFields.size(); i++) {
            constraints.gridy = currentLabelGridY;
            constraints.insets.top = 5;
            loginPanel.add(fieldLabels.get(i), constraints);
            constraints.gridy = currentTextFieldGridY;
            constraints.insets.top = 2;
            loginPanel.add(fieldTextFields.get(i), constraints);
            currentLabelGridY += 2;
            currentTextFieldGridY += 2;
        }
        int lastTextFieldGridY = 2 * fieldTextFields.size();

        constraints.gridy = lastTextFieldGridY + 1;
        constraints.insets.top = 30;
        loginPanel.add(loginButton, constraints);
    }

}
