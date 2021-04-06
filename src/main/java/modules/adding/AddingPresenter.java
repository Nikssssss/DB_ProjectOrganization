package modules.adding;

import java.lang.ref.WeakReference;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class AddingPresenter {
    private WeakReference<AddingView> view;
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
        ArrayList<ArrayList<String>> comboBoxData = new ArrayList<>();
        try {
            switch (interactor.getCurrentTableType()) {
                case EMPLOYEES: {
                    comboBoxData.add(interactor.getAllProfessionNames());
                    break;
                }
                case DEPARTMENTS: {
                    comboBoxData.add(interactor.getAllManagersInfo());
                    break;
                }
                case PROFESSIONS: {
                    comboBoxData.add(interactor.getAllManagementAbilities());
                    comboBoxData.add(interactor.getAllDepartmentNames());
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Objects.requireNonNull(this.view.get()).configureView(interactor.getCurrentTableType(), comboBoxData);
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
            ArrayList<String> insertingData = addingView.getAllInsertingData();
            try {
                interactor.insertRow(insertingData);
                addingView.clearAllFields();
            } catch (SQLException | IllegalArgumentException e) {
                e.printStackTrace();
                addingView.setErrorMessage("Пожалуйста, введите корректные значения");
            }
        }
    }

    //MARK: private methods


}
