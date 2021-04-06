package modules.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainView {
    private MainPresenter presenter;

    private final JPanel mainPanel = new JPanel();
    private final JLabel titleLabel = new JLabel("Проектная организация");
    private final JButton tablesButton = new JButton("Изменение данных");
    private final JButton queriesButton = new JButton("Получение информации");
    private final JButton createDatabaseButton = new JButton("Создать БД");
    private final JButton clearDatabaseButton = new JButton("Очистить БД");

    public void setPresenter(MainPresenter presenter) {
        this.presenter = presenter;
    }

    public void didLoad() {
        this.presenter.viewDidLoad();
    }

    public void configureView() {
        this.setupView();
    }

    public JPanel getMainPanel(){
        return mainPanel;
    }

    public void setErrorMessage(String errorMessage){
        JOptionPane.showMessageDialog(mainPanel, errorMessage, "Error!", JOptionPane.ERROR_MESSAGE);
    }

    public void disableAllButtons() {
        tablesButton.setEnabled(false);
        queriesButton.setEnabled(false);
        clearDatabaseButton.setEnabled(false);
        clearDatabaseButton.setEnabled(false);
    }

    public void enableAllButtons() {
        tablesButton.setEnabled(true);
        queriesButton.setEnabled(true);
        clearDatabaseButton.setEnabled(true);
        clearDatabaseButton.setEnabled(true);
    }

    //MARK: private methods

    private void setupView() {
        this.mainPanel.setPreferredSize(new Dimension(500, 500));
        this.mainPanel.setLayout(new GridBagLayout());
        this.mainPanel.setBackground(Color.LIGHT_GRAY);
        this.setupSubComponents();
        this.placeSubComponents();
    }

    private void setupSubComponents(){
        this.titleLabel.setFont(new Font("Serif", Font.PLAIN, 25));

        this.tablesButton.setPreferredSize(new Dimension(200, 100));
        this.tablesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                presenter.tablesButtonPressed();
            }
        });

        this.queriesButton.setPreferredSize(new Dimension(200, 100));
        this.queriesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                presenter.queriesButtonPressed();
            }
        });

        this.createDatabaseButton.setPreferredSize(new Dimension(180, 50));
        this.createDatabaseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                presenter.createDatabaseButtonPressed();
            }
        });

        this.clearDatabaseButton.setPreferredSize(new Dimension(180, 50));
        this.clearDatabaseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                presenter.clearDatabaseButtonPressed();
            }
        });
    }

    private void placeSubComponents(){
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        this.mainPanel.add(this.titleLabel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.insets.top = 20;
        constraints.gridwidth = 1;
        this.mainPanel.add(this.tablesButton, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.insets.top = 20;
        constraints.insets.left = 20;
        this.mainPanel.add(this.queriesButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.insets.top = 20;
        this.mainPanel.add(this.createDatabaseButton, constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.insets.top = 20;
        constraints.insets.left = 0;
        this.mainPanel.add(this.clearDatabaseButton, constraints);
    }

}
