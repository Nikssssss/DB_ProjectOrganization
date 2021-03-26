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
