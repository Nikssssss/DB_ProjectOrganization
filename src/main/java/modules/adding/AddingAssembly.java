package modules.adding;

import application.MainWindow;
import modules.tables.enums.TableType;

import java.sql.Connection;
import java.sql.ResultSet;

public class AddingAssembly {
    public static AddingView assemble(MainWindow mainWindow, Connection connection, ResultSet resultSet, TableType tableType) {
        AddingView view = new AddingView();
        AddingPresenter presenter = new AddingPresenter(view);
        AddingInteractor interactor = new AddingInteractor();
        AddingRouter router = new AddingRouter(mainWindow);

        view.setPresenter(presenter);
        presenter.setInteractor(interactor);
        presenter.setRouter(router);

        interactor.setConnection(connection);
        interactor.setResultSet(resultSet);
        interactor.setCurrentTableType(tableType);

        return view;
    }
}
