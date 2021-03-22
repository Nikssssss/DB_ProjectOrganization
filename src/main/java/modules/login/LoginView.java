package modules.login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginView {
    private LoginPresenter presenter;

    private final JPanel loginPanel = new JPanel();
    private final JTextField ipTF = new JTextField("Enter server ip");
    private final JTextField portTF = new JTextField("Enter server port");
    private final JTextField loginTF = new JTextField("Enter your login");
    private final JPasswordField passwordTF = new JPasswordField("Enter your password");
    private final JLabel errorLabel = new JLabel();
    private final JButton loginButton = new JButton("Sign in");

    public void setPresenter(LoginPresenter presenter) {
        this.presenter = presenter;
    }

    public void setErrorMessage(String errorMessage){
        errorLabel.setText(errorMessage);
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

    //MARK: private methods

    private void setupView() {
        loginPanel.setPreferredSize(new Dimension(400, 400));
        loginPanel.setLayout(new GridBagLayout());
        loginPanel.setBackground(Color.LIGHT_GRAY);
        this.setupSubComponents();
        this.placeSubComponents();
    }

    private void setupSubComponents(){
        ipTF.setPreferredSize(new Dimension(250, 40));
        ipTF.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (ipTF.getText().equals("Enter server ip")) {
                    ipTF.setText("");
                }
            }
        });
        ipTF.setText("192.168.0.6");
        portTF.setPreferredSize(new Dimension(250, 40));
        portTF.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (portTF.getText().equals("Enter server port")) {
                    portTF.setText("");
                }
            }
        });
        portTF.setText("1521");
        loginTF.setPreferredSize(new Dimension(250, 40));
        loginTF.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (loginTF.getText().equals("Enter your login")) {
                    loginTF.setText("");
                }
            }
        });
        loginTF.setText("nikita");
        passwordTF.setPreferredSize(new Dimension(250, 40));
        passwordTF.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (String.valueOf(passwordTF.getPassword()).equals("Enter your password")) {
                    passwordTF.setText("");
                }
            }
        });
        passwordTF.setText("nikita2710");
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        errorLabel.setVerticalAlignment(SwingConstants.CENTER);

        loginButton.setPreferredSize(new Dimension(200, 40));
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                presenter.loginButtonPressed(ipTF.getText(), portTF.getText(), loginTF.getText(), String.valueOf(passwordTF.getPassword()));
            }
        });
    }

    private void placeSubComponents() {
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        loginPanel.add(ipTF, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.insets.top = 5;
        loginPanel.add(portTF, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.insets.top = 30;
        loginPanel.add(loginTF, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.insets.top = 5;
        loginPanel.add(passwordTF, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.insets.top = 5;
        loginPanel.add(errorLabel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.insets.top = 5;
        loginPanel.add(loginButton, constraints);
    }

}
