package modules.adding;

import common.MainWindow;
import modules.tables.TablesAssembly;
import modules.tables.TablesView;

public class AddingRouter {

    public void showTablesScene() {
        TablesView tablesView = TablesAssembly.assemble();
        tablesView.didLoad();
        MainWindow.getMainWindow().showPanel(tablesView.getTablePanel());
    }

}
