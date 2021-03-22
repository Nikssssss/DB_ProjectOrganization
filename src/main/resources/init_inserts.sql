insert into departments(department_name) values('engineering');
insert into departments(department_name) values('financial');
insert into departments(department_name) values('hr');

insert into professions(profession_name, management_ability, department_id) values('technic', 0, 1);
insert into professions(profession_name, management_ability, department_id) values('engineer', 1, 1);
insert into professions(profession_name, management_ability, department_id) values('constructor', 1, 1);
insert into professions(profession_name, management_ability, department_id) values('accountant', 0, 2);
insert into professions(profession_name, management_ability, department_id) values('manager', 0, 3);

insert into employees(first_name, last_name, hire_date, profession_id, salary, age)
       values('Slava', 'Stepin', to_date('29.10.2008', 'DD.MM.YYYY'), 1, 40000, 25);
insert into employees(first_name, last_name, hire_date, profession_id, salary, age)
       values('Nikita', 'Pover', to_date('23.09.2012', 'DD.MM.YYYY'), 2, 70000, 35);
insert into employees(first_name, last_name, hire_date, profession_id, salary, age)
       values('Mikhail', 'Lern', to_date('06.05.2020', 'DD.MM.YYYY'), 2, 80000, 32);
insert into employees(first_name, last_name, hire_date, profession_id, salary, age)
       values('Arkadiy', 'Ister', to_date('07.04.2004', 'DD.MM.YYYY'), 3, 50000, 29);
insert into employees(first_name, last_name, hire_date, profession_id, salary, age)
       values('Misha', 'Lidev', to_date('15.11.2015', 'DD.MM.YYYY'), 3, 60000, 37);
insert into employees(first_name, last_name, hire_date, profession_id, salary, age)
       values('Kirill', 'Susev', to_date('08.09.2010', 'DD.MM.YYYY'), 4, 30000, 30);
insert into employees(first_name, last_name, hire_date, profession_id, salary, age)
       values('Stas', 'Verin', to_date('08.12.1999', 'DD.MM.YYYY'), 5, 35000, 31);

update departments set manager_id = 3 where department_name = 'engineering';
update departments set manager_id = 6 where department_name = 'financial';
update departments set manager_id = 7 where department_name = 'hr';
