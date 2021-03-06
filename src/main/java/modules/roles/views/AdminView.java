package modules.roles.views;

import modules.roles.RolesPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminView implements UserRoleView {
    private RolesPresenter presenter;

    private final JPanel panel = new JPanel();

    @Override
    public void configureView() {
        setupView();
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void didLoad() {
        presenter.configureView();
    }

    @Override
    public void setPresenter(RolesPresenter rolesPresenter) {
        this.presenter = rolesPresenter;
    }

    @Override
    public void setErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(panel, errorMessage, "Ошибка!", JOptionPane.ERROR_MESSAGE);
    }

    private void setupView() {
        panel.setLayout(new GridBagLayout());
        panel.setPreferredSize(new Dimension(500, 500));
        panel.setBackground(Color.LIGHT_GRAY);
        setupSubComponents();
    }

    private void setupSubComponents() {
        GridBagConstraints constraints = new GridBagConstraints();

        JButton backButton = new JButton("Назад к авторизации");
        backButton.setPreferredSize(new Dimension(180, 70));
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                presenter.backButtonPressed();
            }
        });
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.insets.bottom = 20;
        panel.add(backButton, constraints);
        constraints.insets.bottom = 0;
        constraints.gridwidth = 1;

        JButton createDatabaseButton = new JButton("Cоздать БД");
        createDatabaseButton.setPreferredSize(new Dimension(200, 100));
        createDatabaseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                presenter.createDatabaseButtonPressed();
            }
        });
        constraints.gridy = 1;
        panel.add(createDatabaseButton, constraints);

        JButton removeDatabaseButton = new JButton("Удалить БД");
        removeDatabaseButton.setPreferredSize(new Dimension(200, 100));
        removeDatabaseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                presenter.removeDatabaseButtonPressed();
            }
        });
        constraints.gridx = 1;
        constraints.insets.left = 30;
        panel.add(removeDatabaseButton, constraints);

        JButton fillDatabaseButton = new JButton("Заполнить данными");
        fillDatabaseButton.setPreferredSize(new Dimension(200, 100));
        fillDatabaseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                presenter.fillDatabaseButtonPressed();
            }
        });
        constraints.gridy = 2;
        constraints.gridx = 0;
        constraints.insets.left = 0;
        constraints.insets.top = 10;
        panel.add(fillDatabaseButton, constraints);

        JButton clearDatabaseButton = new JButton("Очистить от данных");
        clearDatabaseButton.setPreferredSize(new Dimension(200, 100));
        clearDatabaseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                presenter.clearDatabaseButtonPressed();
            }
        });
        constraints.gridx = 1;
        constraints.insets.left = 30;
        panel.add(clearDatabaseButton, constraints);

        JButton addUserButton = new JButton("Зарегистрировать пользователя");
        addUserButton.setPreferredSize(new Dimension(250, 100));
        addUserButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                presenter.addUserButtonPressed();
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.insets.left = 0;
        constraints.insets.top = 20;
        constraints.gridwidth = 2;
        panel.add(addUserButton, constraints);
    }
}
