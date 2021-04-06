package modules.adding;

import modules.tables.enums.TableType;

import java.sql.ResultSet;

public class AddingAssembly {
    public static AddingView assemble(ResultSet resultSet, TableType tableType) {
        AddingView view = new AddingView();
        AddingPresenter presenter = new AddingPresenter(view);
        AddingInteractor interactor = new AddingInteractor();
        AddingRouter router = new AddingRouter();

        view.setPresenter(presenter);
        presenter.setInteractor(interactor);
        presenter.setRouter(router);

        interactor.setResultSet(resultSet);
        interactor.setCurrentTableType(tableType);

        return view;
    }
}
