package modules.tables;

import common.MainWindow;
import modules.adding.AddingAssembly;
import modules.adding.AddingView;
import modules.main.MainAssembly;
import modules.main.MainView;
import modules.tables.enums.TableType;

import java.sql.ResultSet;

public class TablesRouter {

    public void showMainScene() {
        MainView mainView = MainAssembly.assemble();
        mainView.didLoad();
        MainWindow.getMainWindow().showPanel(mainView.getMainPanel());
    }

    public void showAddingScene(ResultSet resultSet, TableType tableType) {
        AddingView addingView = AddingAssembly.assemble(resultSet, tableType);
        addingView.didLoad();
        MainWindow.getMainWindow().showPanel(addingView.getAddingPanel());
    }
}
