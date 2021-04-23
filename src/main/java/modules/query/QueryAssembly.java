package modules.query;

import modules.queries.enums.QueryType;

public class QueryAssembly {
    public static QueryView assemble(QueryType queryType) {
        QueryView view = new QueryView();
        QueryPresenter presenter = new QueryPresenter(view);
        QueryInteractor interactor = new QueryInteractor();
        QueryRouter router = new QueryRouter();

        view.setPresenter(presenter);
        presenter.setInteractor(interactor);
        presenter.setRouter(router);

        interactor.setQueryType(queryType);

        return view;
    }
}
