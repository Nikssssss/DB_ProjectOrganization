insert into departments(department_name) values('Инжиниринг');
insert into departments(department_name) values('Финансы');
insert into departments(department_name) values('HR');

insert into professions(profession_name, management_ability, department_id) values('Техник', 0, 1);
insert into professions(profession_name, management_ability, department_id) values('Инженер', 1, 1);
insert into professions(profession_name, management_ability, department_id) values('Конструктор', 1, 1);
insert into professions(profession_name, management_ability, department_id) values('Бухгалтер', 0, 2);
insert into professions(profession_name, management_ability, department_id) values('Менеджер', 0, 3);

insert into employees(first_name, last_name, hire_date, profession_id, salary, age)
       values('Слава', 'Стёпин', to_date('29.10.2008', 'DD.MM.YYYY'), 1, 40000, 25);
insert into employees(first_name, last_name, hire_date, profession_id, salary, age)
       values('Никита', 'Повер', to_date('23.09.2012', 'DD.MM.YYYY'), 2, 70000, 35);
insert into employees(first_name, last_name, hire_date, profession_id, salary, age)
       values('Михаил', 'Лерн', to_date('06.05.2020', 'DD.MM.YYYY'), 2, 80000, 32);
insert into employees(first_name, last_name, hire_date, profession_id, salary, age)
       values('Аркадий', 'Истер', to_date('07.04.2004', 'DD.MM.YYYY'), 3, 50000, 29);
insert into employees(first_name, last_name, hire_date, profession_id, salary, age)
       values('Миша', 'Лидев', to_date('15.11.2015', 'DD.MM.YYYY'), 3, 60000, 37);
insert into employees(first_name, last_name, hire_date, profession_id, salary, age)
       values('Кирилл', 'Сусев', to_date('08.09.2010', 'DD.MM.YYYY'), 4, 30000, 30);
insert into employees(first_name, last_name, hire_date, profession_id, salary, age)
       values('Стас', 'Верин', to_date('08.12.1999', 'DD.MM.YYYY'), 5, 35000, 31);

update departments set manager_id = 3 where department_name = 'Инжиниринг';
update departments set manager_id = 6 where department_name = 'Финансы';
update departments set manager_id = 7 where department_name = 'HR';

insert into EquipmentType(equipment_type_name) values('Принтер');
insert into EquipmentType(equipment_type_name) values('Компьютер');
insert into EquipmentType(equipment_type_name) values('Плоттер');
insert into EquipmentType(equipment_type_name) values('Тахеометр');
insert into EquipmentType(equipment_type_name) values('Уровень');
insert into EquipmentType(equipment_type_name) values('Тепловизор');

insert into equipment(equipment_name, equipment_type_id, department_id)
       values('Принтер1', 1, 1);
insert into equipment(equipment_name, equipment_type_id, department_id)
       values('Принтер2', 1, 2);
insert into equipment(equipment_name, equipment_type_id, department_id)
       values('Принтер3', 1, 3);
insert into equipment(equipment_name, equipment_type_id, department_id)
       values('Компьютер1', 2, 1);
insert into equipment(equipment_name, equipment_type_id, department_id)
       values('Компьютер2', 2, 2);
insert into equipment(equipment_name, equipment_type_id, department_id)
       values('Компьютер3', 2, 3);
insert into equipment(equipment_name, equipment_type_id, department_id)
       values('Плоттер1', 3, 1);
insert into equipment(equipment_name, equipment_type_id, department_id)
       values('Тахеометр1', 4, 1);
insert into equipment(equipment_name, equipment_type_id, department_id)
       values('Тахеометр2', 4, 1);
insert into equipment(equipment_name, equipment_type_id, department_id)
       values('Уровень1', 5, 1);
insert into equipment(equipment_name, equipment_type_id, department_id)
       values('Уровень2', 5, 1);
insert into equipment(equipment_name, equipment_type_id, department_id)
       values('Тепловизор1', 6, 1);
insert into equipment(equipment_name, equipment_type_id, department_id)
       values('Тепловизор2', 6, 1);
insert into equipment(equipment_name, equipment_type_id, department_id)
       values('Тепловизор3', 6, 1);

insert into projects(project_manager, project_cost, start_date, finish_date)
       values(2, 100000, to_date('2021-09-09', 'yyyy-mm-dd'), to_date('2022-09-09', 'yyyy-mm-dd'));
insert into projects(project_manager, project_cost, start_date, finish_date)
       values(3, 200000, to_date('2018-09-12', 'yyyy-mm-dd'), to_date('2021-08-12', 'yyyy-mm-dd'));
insert into projects(project_manager, project_cost, start_date, finish_date)
       values(4, 60000, to_date('2021-01-09', 'yyyy-mm-dd'), to_date('2021-06-09', 'yyyy-mm-dd'));
insert into projects(project_manager, project_cost, start_date, finish_date)
       values(5, 50000, to_date('2019-03-15', 'yyyy-mm-dd'), to_date('2022-03-15', 'yyyy-mm-dd'));

insert into contracts(contract_manager, start_date, finish_date)
       values(2, to_date('2021-09-09', 'yyyy-mm-dd'), to_date('2022-09-09', 'yyyy-mm-dd'));
insert into contracts(contract_manager, start_date, finish_date)
       values(3, to_date('2018-09-12', 'yyyy-mm-dd'), to_date('2021-08-12', 'yyyy-mm-dd'));
insert into contracts(contract_manager, start_date, finish_date)
       values(4, to_date('2021-01-09', 'yyyy-mm-dd'), to_date('2021-06-09', 'yyyy-mm-dd'));
insert into contracts(contract_manager, start_date, finish_date)
       values(5, to_date('2019-03-15', 'yyyy-mm-dd'), to_date('2022-03-15', 'yyyy-mm-dd'));

insert into subcontracts(subcontractor_name, start_date, finish_date)
       values('Company1', to_date('2020-08-09', 'yyyy-mm-dd'), to_date('2022-12-09', 'yyyy-mm-dd'));
insert into subcontracts(subcontractor_name, start_date, finish_date)
       values('Company2', to_date('2018-12-05', 'yyyy-mm-dd'), to_date('2020-12-05', 'yyyy-mm-dd'));