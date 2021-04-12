create or replace trigger employees_increment
  before insert on employees
  for each row
 begin
  if :new.employee_id is null then
    select employees_sequence.nextval into :new.employee_id from dual;
  end if;
end;

create or replace trigger departments_increment
  before insert on departments
  for each row
 begin
  if :new.department_id is null then
    select departments_sequence.nextval into :new.department_id from dual;
  end if;
end;

create or replace trigger professions_increment
  before insert on professions
  for each row
 begin
  if :new.profession_id is null then
    select professions_sequence.nextval into :new.profession_id from dual;
  end if;
end;

create or replace trigger equipment_increment
  before insert on equipment
  for each row
 begin
  if :new.equipment_id is null then
    select equipment_sequence.nextval into :new.equipment_id from dual;
  end if;
end;

create or replace trigger projects_increment
  before insert on projects
  for each row
 begin
  if :new.project_id is null then
    select projects_sequence.nextval into :new.project_id from dual;
  end if;
end;

create or replace trigger contracts_increment
  before insert on contracts
  for each row
 begin
  if :new.contract_id is null then
    select contracts_sequence.nextval into :new.contract_id from dual;
  end if;
end;

create or replace trigger subcontracts_increment
  before insert on subcontracts
  for each row
 begin
  if :new.subcontract_id is null then
    select subcontracts_sequence.nextval into :new.subcontract_id from dual;
  end if;
end;

create or replace trigger equipment_type_increment
  before insert on equipmentType
  for each row
 begin
  if :new.equipment_type_id is null then
    select equipment_type_sequence.nextval into :new.equipment_type_id from dual;
  end if;
end;