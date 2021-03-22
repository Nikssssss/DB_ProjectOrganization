package modules.main;

import application.MainWindow;

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
    }

}
