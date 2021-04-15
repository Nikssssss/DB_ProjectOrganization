package modules.tables;

import modules.tables.enums.TableType;

import java.lang.ref.WeakReference;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TablesPresenter {
    private final WeakReference<TablesView> view;
    private TablesInteractor interactor;
    private TablesRouter router;

    public TablesPresenter(TablesView view) {
        this.view = new WeakReference<>(view);
    }

    public void setInteractor(TablesInteractor interactor) {
        this.interactor = interactor;
    }

    public void setRouter(TablesRouter router) {
        this.router = router;
    }

    public void viewDidLoad() {
        Objects.requireNonNull(this.view.get()).configureView();
        this.replaceCurrentTable();
    }

    public void backButtonPressed() {
        this.router.showMainScene();
    }

    public void comboBoxItemChanged() {
        this.replaceCurrentTable();
    }

    public void cellDidEdit(int row) {
        ArrayList<String> rowData = Objects.requireNonNull(this.view.get()).getDataFromRow(row);
        TableType currentTableType = TableType.valueOfLabel(Objects.requireNonNull(this.view.get()).getCurrentTableName());
        try {
            this.interactor.updateRow(row + 1, rowData, currentTableType);
        } catch (SQLException e) {
            Objects.requireNonNull(view.get()).setErrorMessage(e.getMessage());
        }
    }

    public void deleteRowButtonPressed(int row) {
        try {
            this.interactor.deleteRow(row + 1);
            TableType currentTableType = TableType.valueOfLabel(Objects.requireNonNull(view.get()).getCurrentTableName());
            ArrayList<ArrayList<String>> currentTableData = this.interactor.getColumnsAndRowsFromCurrentTable(currentTableType);
            Objects.requireNonNull(view.get()).setTableRowsData(currentTableData.subList(1, currentTableData.size()));
        } catch (SQLException e) {
            e.printStackTrace();
            Objects.requireNonNull(view.get()).setErrorMessage(e.getMessage());
        }
    }

    public void addRowButtonPressed() {
        router.showAddingScene(interactor.getCurrentTableResultSet(),
                TableType.valueOfLabel(Objects.requireNonNull(view.get()).getCurrentTableName()));
    }

    //MARK: private methods

    private void replaceCurrentTable() {
        TablesView view = this.view.get();
        assert view != null;
        TableType currentTableType = TableType.valueOfLabel(view.getCurrentTableName());
        try {
            ArrayList<ArrayList<String>> currentTableData = interactor.getColumnsAndRowsFromTable(currentTableType);
            ArrayList<ArrayList<String>> columnsDropDownListData = interactor.getColumnsDropDownListDataForTable(currentTableType);
            ArrayList<String> columnNames = currentTableData.get(0);
            List<ArrayList<String>> tableRows = currentTableData.subList(1, currentTableData.size());
            view.setTableModelForTableType(currentTableType, columnNames, columnsDropDownListData);
            view.setTableRowsData(tableRows);
        } catch (SQLException e) {
            e.printStackTrace();
            view.setErrorMessage(e.getMessage());
        }
    }

}
