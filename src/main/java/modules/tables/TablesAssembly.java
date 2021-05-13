package modules.tables;

import common.CurrentUserRole;
import modules.roles.enums.UserRole;

import java.util.ArrayList;
import java.util.Arrays;

public class TablesAssembly {
    public static TablesView assemble() {
        TablesView view = new TablesView();
        TablesPresenter presenter = new TablesPresenter(view);
        TablesInteractor interactor = new TablesInteractor();
        TablesRouter router = new TablesRouter();

        view.setPresenter(presenter);
        presenter.setInteractor(interactor);
        presenter.setRouter(router);

        String[] entities = new String[]{};
        switch (CurrentUserRole.getUserRole()) {
            case HR: {
                entities = new String[]{"Сотрудники", "Техники", "Инженеры",
                        "Конструкторы", "Бухгалтеры", "Менеджеры"};
                break;
            }
            case ADMIN: {
                entities = new String[]{"Сотрудники", "Отделы", "Оборудование", "Типы оборудования",
                        "Проекты", "Договоры", "Субдоговоры", "Исполнители проектов", "Оборудование на проектах"};
                break;
            }
            case MANAGER: {
                entities = new String[]{"Проекты", "Договоры", "Субдоговоры",
                        "Исполнители проектов", "Оборудование на проектах"};
                break;
            }
            case DIRECTOR: {
                entities = new String[]{"Отделы", "Оборудование", "Типы оборудования"};
                break;
            }
        }
        view.setEntities(new ArrayList<>(Arrays.asList(entities)));

        return view;
    }
}
