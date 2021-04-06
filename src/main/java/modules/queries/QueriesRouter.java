package modules.queries;

import common.MainWindow;
import modules.main.MainAssembly;
import modules.main.MainView;
import modules.queries.enums.QueryType;
import modules.query.QueryAssembly;
import modules.query.QueryView;

public class QueriesRouter {

    public void showMenuScene() {
        MainView mainView = MainAssembly.assemble();
        mainView.didLoad();
        MainWindow.getMainWindow().showPanel(mainView.getMainPanel());
    }

    public void showQueryScene(QueryType queryType) {
        QueryView queryView = QueryAssembly.assemble(queryType);
        queryView.didLoad();
        MainWindow.getMainWindow().showPanel(queryView.getQueryPanel());
    }
}
