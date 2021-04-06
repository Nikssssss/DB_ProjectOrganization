package modules.query;

import common.MainWindow;
import modules.queries.QueriesAssembly;
import modules.queries.QueriesView;

public class QueryRouter {

    public void showQueriesScene() {
        QueriesView queriesView = QueriesAssembly.assemble();
        queriesView.didLoad();
        MainWindow.getMainWindow().showPanel(queriesView.getQueriesPanel());
    }

}
