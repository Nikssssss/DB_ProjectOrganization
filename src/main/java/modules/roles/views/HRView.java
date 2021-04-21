package modules.roles.views;

import modules.roles.RolesPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HRView implements UserRoleView{
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
        constraints.insets.bottom = 70;
        panel.add(backButton, constraints);
        constraints.insets.bottom = 0;
        constraints.gridwidth = 1;

        JButton tablesButton = new JButton("Изменение данных");
        tablesButton.setPreferredSize(new Dimension(200, 100));
        tablesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                presenter.tablesButtonPressed();
            }
        });
        constraints.gridy = 1;
        panel.add(tablesButton, constraints);

        JButton queriesButton = new JButton("Просмотр информации");
        queriesButton.setPreferredSize(new Dimension(200, 100));
        queriesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                presenter.queriesButtonPressed();
            }
        });
        constraints.gridx = 1;
        constraints.insets.left = 30;
        panel.add(queriesButton, constraints);
    }
}
