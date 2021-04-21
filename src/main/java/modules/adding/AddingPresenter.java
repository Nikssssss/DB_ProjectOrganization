package modules.adding;

import modules.tables.enums.TableType;

import java.lang.ref.WeakReference;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.util.ArrayList;

public class AddingPresenter {
    private final WeakReference<AddingView> view;
    private AddingInteractor interactor;
    private AddingRouter router;

    public AddingPresenter(AddingView view) {
        this.view = new WeakReference<>(view);
    }

    public void setInteractor(AddingInteractor interactor) {
        this.interactor = interactor;
    }

    public void setRouter(AddingRouter router) {
        this.router = router;
    }

    public void viewDidLoad() {
        AddingView addingView = view.get();
        assert addingView != null;
        try {
            ArrayList<ArrayList<String>> columnsDropDownListData = interactor.getColumnsDropDownListData();
            if (interactor.getCurrentTableType() == TableType.PROJECTS) {
                Boolean isOwnOrganization;
                while ((isOwnOrganization = addingView.chooseOrganizationForProject()) == null) {}
                addingView.configureProjectsView(columnsDropDownListData, isOwnOrganization, true);
            } else {
                addingView.configureView(interactor.getCurrentTableType(), columnsDropDownListData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void employeeCategoryDidChange() {
        AddingView addingView = view.get();
        assert addingView != null;
        try {
            String category = addingView.getEmployeeCategory();
            ArrayList<String> categoryDropDownListData = interactor.getEmployeeCategoryDropDownListData(category);
            addingView.changeEmployeeCategoryTo(category, categoryDropDownListData);
        } catch (SQLException e) {
            addingView.setErrorMessage(e.getMessage());
        }
    }

    public void backToTablesButtonPressed() {
        router.showTablesScene();
    }

    public void addRowButtonPressed() {
        AddingView addingView = view.get();
        assert addingView != null;
        boolean viewHasBlankFields = addingView.hasBlankFields();
        if (viewHasBlankFields) {
            addingView.setErrorMessage("Пожалуйста, заполните все поля");
        } else {
            if (interactor.getCurrentTableType() == TableType.PROJECTS) {
                ArrayList<Object> insertingData = addingView.getAllProjectsInsertingData();
                boolean isOwnOrganization = addingView.isOwnProjectOrganization();
                boolean isNewProject;
                if (isOwnOrganization) {
                    if (insertingData.size() == 8) {
                        isNewProject = true;
                    } else {
                        isNewProject = false;
                    }
                } else {
                    if (insertingData.size() == 6) {
                        isNewProject = true;
                    } else {
                        isNewProject = false;
                    }
                }
                try {
                    interactor.insertProjectRow(insertingData, isOwnOrganization, isNewProject);
                    router.showTablesScene();
                } catch (SQLException | DateTimeException e) {
                    e.printStackTrace();
                    addingView.setErrorMessage(e.getMessage());
                }
            } else if (interactor.getCurrentTableType() == TableType.EMPLOYEES){
                try {
                    ArrayList<String> insertingData = addingView.getAllInsertingData();
                    String employeeCategory = addingView.getEmployeeCategory();
                    interactor.insertRow(insertingData, employeeCategory);
                    addingView.clearAllFields();
                    addingView.setInfoMessage("Запись добавлена");
                } catch (SQLException | IllegalArgumentException e) {
                    e.printStackTrace();
                    addingView.setErrorMessage("Пожалуйста, введите корректные значения \n" + e.getMessage());
                }
            } else {
                try {
                    ArrayList<String> insertingData = addingView.getAllInsertingData();
                    interactor.insertRow(insertingData, null);
                    addingView.clearAllFields();
                    addingView.setInfoMessage("Запись добавлена");
                } catch (SQLException | DateTimeException | IllegalArgumentException e) {
                    e.printStackTrace();
                    addingView.setErrorMessage("Пожалуйста, введите корректные значения \n" + e.getMessage());
                }
            }
        }
    }

}
