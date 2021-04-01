package modules.tables;

import modules.tables.enums.TableType;

import java.lang.ref.SoftReference;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class TablesPresenter {
    private SoftReference<TablesView> view;
    private TablesInteractor interactor;
    private TablesRouter router;

    public TablesPresenter(TablesView view) {
        this.view = new SoftReference<>(view);
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
        this.router.showMainScene(this.interactor.getConnection());
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
            //TODO: show error message in view
            e.printStackTrace();
        }
    }

    public void deleteRowButtonPressed(int row) {
        try {
            this.interactor.deleteRow(row + 1);
            TableType currentTableType = TableType.valueOfLabel(Objects.requireNonNull(view.get()).getCurrentTableName());
            ArrayList<ArrayList<String>> currentTableData = this.interactor.getDataFromCurrentTable(currentTableType);
            Objects.requireNonNull(view.get()).setTableRowsData(currentTableData.subList(1, currentTableData.size()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addRowButtonPressed() {
        router.showAddingScene(interactor.getConnection(),
                interactor.getCurrentTableResultSet(),
                TableType.valueOfLabel(Objects.requireNonNull(view.get()).getCurrentTableName()));
    }

    //MARK: private methods

    private void replaceCurrentTable() {
        TablesView view = this.view.get();
        assert view != null;
        TableType currentTableType = TableType.valueOfLabel(view.getCurrentTableName());
        try {
            ArrayList<ArrayList<String>> currentTableData = this.interactor.getDataFromTable(currentTableType);
            this.setViewColumnsStructureForTable(currentTableType, currentTableData.get(0));
            view.setTableRowsData(currentTableData.subList(1, currentTableData.size()));
        } catch (SQLException e) {
            //TODO: show error message in view
            e.printStackTrace();
        }
    }

    private void setViewColumnsStructureForTable(TableType tableType, ArrayList<String> columnNames) throws SQLException {
        switch (tableType) {
            case EMPLOYEES: {
                ArrayList<ArrayList<String>> dropDownListData = new ArrayList<>();
                ArrayList<String> professions = this.interactor.getAllProfessions();
                dropDownListData.add(professions);
                Objects.requireNonNull(this.view.get()).setTableModelForTableType(tableType, columnNames, dropDownListData);
                break;
            }
            case DEPARTMENTS: {
                ArrayList<ArrayList<String>> dropDownListData = new ArrayList<>();
                ArrayList<String> managers = this.interactor.getAllManagers();
                dropDownListData.add(managers);
                Objects.requireNonNull(this.view.get()).setTableModelForTableType(tableType, columnNames, dropDownListData);
                break;
            }
            case PROFESSIONS: {
                ArrayList<ArrayList<String>> dropDownListData = new ArrayList<>();
                ArrayList<String> managementAbilities = this.interactor.getAllManagementAbilities();
                ArrayList<String> departments = this.interactor.getAllDepartments();
                dropDownListData.add(managementAbilities);
                dropDownListData.add(departments);
                Objects.requireNonNull(this.view.get()).setTableModelForTableType(tableType, columnNames, dropDownListData);
                break;
            }
        }
    }
}
