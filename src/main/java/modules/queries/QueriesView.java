package modules.queries;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

public class QueriesView {
    private QueriesPresenter presenter;

    private final JPanel queriesPanel = new JPanel();
    private final JButton backToMenuButton = new JButton("Назад в меню");
    private final DefaultTableModel queriesTableModel = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable queriesTable = new JTable(queriesTableModel);
    private final JScrollPane queriesScrollPane = new JScrollPane(queriesTable);

    public void setPresenter(QueriesPresenter presenter) {
        this.presenter = presenter;
    }

    public void didLoad() {
        this.presenter.viewDidLoad();
    }

    public void configureView() {
        this.setupView();
    }

    public JPanel getQueriesPanel(){
        return queriesPanel;
    }

    public void setQueriesTableData(ArrayList<String> queries) {
        for (String query: queries) {
            queriesTableModel.addRow(new Object[] {query});
        }
    }

    //MARK: private methods

    private void setupView() {
        this.queriesPanel.setPreferredSize(new Dimension(600, 600));
        this.queriesPanel.setLayout(new GridBagLayout());
        this.queriesPanel.setBackground(Color.LIGHT_GRAY);
        this.setupSubComponents();
        this.placeSubComponents();
    }

    private void setupSubComponents(){
        this.backToMenuButton.setPreferredSize(new Dimension(150, 40));
        this.backToMenuButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                presenter.backToMenuButtonPressed();
            }
        });

        this.queriesTableModel.addColumn("Название запроса");

        this.queriesTable.setPreferredSize(new Dimension(500, 500));
        this.queriesScrollPane.setPreferredSize(new Dimension(550, 480));
        this.queriesScrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                "Запросы",
                TitledBorder.CENTER,
                TitledBorder.TOP));
        this.queriesTable.setColumnSelectionAllowed(false);
        this.queriesTable.setRowSelectionAllowed(false);
        this.queriesTable.setCellSelectionEnabled(true);
        this.queriesTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.queriesTable.getSelectionModel().addListSelectionListener(event -> {
            if (event.getValueIsAdjusting()) {
                presenter.rowDidSelect((String) ((Vector)queriesTableModel.getDataVector().elementAt(queriesTable.getSelectedRow())).elementAt(0));
            }
        });
    }

    private void placeSubComponents(){
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        this.queriesPanel.add(this.backToMenuButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.insets.top = 20;
        this.queriesPanel.add(this.queriesScrollPane, constraints);
    }

}
