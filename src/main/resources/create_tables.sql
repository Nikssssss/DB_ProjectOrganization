CREATE TABLE Contracts
(
	contract_id          NUMBER(6) NOT NULL ,
	contract_manager     NUMBER(4) NOT NULL ,
	start_date           DATE NOT NULL ,
	finish_date          DATE NULL
);

ALTER TABLE Contracts
	ADD CONSTRAINT  XPKContracts PRIMARY KEY (contract_id);

CREATE TABLE Subcontracts
(
	subcontract_id       NUMBER(6) NOT NULL ,
	subcontractor_name   VARCHAR2(50) NOT NULL ,
	start_date           DATE NOT NULL ,
	finish_date          DATE NULL
);

ALTER TABLE Subcontracts
	ADD CONSTRAINT  XPKSubcontracts PRIMARY KEY (subcontract_id);

CREATE TABLE Projects
(
	project_id           NUMBER(5) NOT NULL ,
	project_manager      NUMBER(4) NOT NULL ,
	project_cost         NUMBER(9) NOT NULL ,
	start_date           DATE NOT NULL ,
	finish_date          DATE NULL
);

ALTER TABLE Projects
	ADD CONSTRAINT  XPKProjects PRIMARY KEY (project_id);

CREATE TABLE Subcontracts_Projects
(
	project_id           NUMBER(5) NOT NULL ,
	subcontract_id       NUMBER(6) NOT NULL
);

ALTER TABLE Subcontracts_Projects
	ADD CONSTRAINT  XPKSubcontracts_Projects PRIMARY KEY (project_id,subcontract_id);

CREATE TABLE Projects_Contracts
(
	project_id           NUMBER(5) NOT NULL ,
	contract_id          NUMBER(6) NOT NULL
);

ALTER TABLE Projects_Contracts
	ADD CONSTRAINT  XPKProjects_Contracts PRIMARY KEY (project_id,contract_id);

CREATE TABLE Employees
(
	employee_id          NUMBER(4) NOT NULL ,
	first_name           VARCHAR2(150) NOT NULL ,
	last_name            VARCHAR2(150) NOT NULL ,
	hire_date            DATE NOT NULL ,
	profession_id        NUMBER(2) NOT NULL ,
	salary               NUMBER(6) NOT NULL ,
	age                  NUMBER(2) NOT NULL
);

ALTER TABLE Employees
	ADD CONSTRAINT  XPKEmployees PRIMARY KEY (employee_id);

CREATE TABLE Projects_Employees
(
	employee_id          NUMBER(4) NOT NULL ,
	project_id           NUMBER(5) NOT NULL
);

ALTER TABLE Projects_Employees
	ADD CONSTRAINT  XPKProjects_Employees PRIMARY KEY (employee_id,project_id);

CREATE TABLE Employees_Contracts
(
	employee_id          NUMBER(4) NOT NULL ,
	contract_id          NUMBER(6) NOT NULL
);

ALTER TABLE Employees_Contracts
	ADD CONSTRAINT  XPKEmployees_Contracts PRIMARY KEY (employee_id,contract_id);

CREATE TABLE Engineers
(
	employee_id          NUMBER(4) NOT NULL ,
	projecting_program   VARCHAR2(30) NOT NULL
);

ALTER TABLE Engineers
	ADD CONSTRAINT  XPKEngineers PRIMARY KEY (employee_id);

CREATE TABLE Constructors
(
	employee_id          NUMBER(4) NOT NULL ,
	certificates_number  NUMBER(4) NOT NULL
);

ALTER TABLE Constructors
	ADD CONSTRAINT  XPKConstructors PRIMARY KEY (employee_id);

CREATE TABLE Accountants
(
	employee_id         NUMBER(4) NOT NULL ,
	accounting_program  VARCHAR2(30) NOT NULL
);

ALTER TABLE Accountants
	ADD CONSTRAINT  XPKAccountants PRIMARY KEY (employee_id);

CREATE TABLE Managers
(
	employee_id          NUMBER(4) NOT NULL ,
	interviews_number    NUMBER(4) NOT NULL
);

ALTER TABLE Managers
	ADD CONSTRAINT  XPKManagers PRIMARY KEY (employee_id);

CREATE TABLE Departments
(
	department_id        NUMBER(2) NOT NULL ,
	department_name      VARCHAR2(150) NOT NULL ,
	manager_id           NUMBER(4) NULL
);

ALTER TABLE Departments
	ADD CONSTRAINT  XPKDepartments PRIMARY KEY (department_id);

CREATE TABLE Equipment
(
	equipment_id         NUMBER(3) NOT NULL ,
	department_id        NUMBER(2) NULL ,
	equipment_type       VARCHAR2(20) NULL ,
	equipment_name       VARCHAR2(40) NOT NULL
);

ALTER TABLE Equipment
	ADD CONSTRAINT  XPKEquipment PRIMARY KEY (equipment_id);

CREATE TABLE Equipment_Projects
(
	equipment_id         NUMBER(3) NOT NULL ,
	project_id           NUMBER(5) NOT NULL
);

ALTER TABLE Equipment_Projects
	ADD CONSTRAINT  XPKEquipment_Projects PRIMARY KEY (equipment_id,project_id);

CREATE TABLE EquipmentType
(
	equipment_type       VARCHAR2(20) NOT NULL
);

ALTER TABLE EquipmentType
	ADD CONSTRAINT  XPKEquipmentType PRIMARY KEY (equipment_type);

CREATE TABLE Technics
(
	employee_id          NUMBER(4) NOT NULL ,
	equipment_type       VARCHAR2(20) NOT NULL
);

ALTER TABLE Technics
	ADD CONSTRAINT  XPKTechnics PRIMARY KEY (employee_id);

CREATE TABLE Professions
(
	profession_id        NUMBER(2) NOT NULL ,
	profession_name      VARCHAR2(150) NOT NULL ,
	management_ability   NUMBER(1) NOT NULL ,
	department_id        NUMBER(2) NOT NULL
);

ALTER TABLE Professions
	ADD CONSTRAINT  XPKProfessions PRIMARY KEY (profession_id);

ALTER TABLE Professions
	ADD (CONSTRAINT R_40 FOREIGN KEY (department_id) REFERENCES Departments (department_id) ON DELETE CASCADE);

ALTER TABLE Contracts
	ADD (CONSTRAINT R_29 FOREIGN KEY (contract_manager) REFERENCES Employees (employee_id) ON DELETE SET NULL);

ALTER TABLE Projects
	ADD (CONSTRAINT R_28 FOREIGN KEY (project_manager) REFERENCES Employees (employee_id) ON DELETE SET NULL);

ALTER TABLE Subcontracts_Projects
	ADD (CONSTRAINT R_3 FOREIGN KEY (project_id) REFERENCES Projects (project_id));

ALTER TABLE Subcontracts_Projects
	ADD (CONSTRAINT R_2 FOREIGN KEY (subcontract_id) REFERENCES Subcontracts (subcontract_id));

ALTER TABLE Projects_Contracts
	ADD (CONSTRAINT R_5 FOREIGN KEY (project_id) REFERENCES Projects (project_id));

ALTER TABLE Projects_Contracts
	ADD (CONSTRAINT R_6 FOREIGN KEY (contract_id) REFERENCES Contracts (contract_id));

ALTER TABLE Employees
	ADD (CONSTRAINT R_24 FOREIGN KEY (profession_id) REFERENCES Professions (profession_id) ON DELETE CASCADE);

ALTER TABLE Projects_Employees
	ADD (CONSTRAINT R_9 FOREIGN KEY (employee_id) REFERENCES Employees (employee_id));

ALTER TABLE Projects_Employees
	ADD (CONSTRAINT R_8 FOREIGN KEY (project_id) REFERENCES Projects (project_id));

ALTER TABLE Employees_Contracts
	ADD (CONSTRAINT R_11 FOREIGN KEY (employee_id) REFERENCES Employees (employee_id));

ALTER TABLE Employees_Contracts
	ADD (CONSTRAINT R_12 FOREIGN KEY (contract_id) REFERENCES Contracts (contract_id));

ALTER TABLE Engineers
	ADD (CONSTRAINT R_20 FOREIGN KEY (employee_id) REFERENCES Employees (employee_id) ON DELETE CASCADE);

ALTER TABLE Constructors
	ADD (CONSTRAINT R_21 FOREIGN KEY (employee_id) REFERENCES Employees (employee_id) ON DELETE CASCADE);

ALTER TABLE Accountants
	ADD (CONSTRAINT R_22 FOREIGN KEY (employee_id) REFERENCES Employees (employee_id) ON DELETE CASCADE);

ALTER TABLE Managers
	ADD (CONSTRAINT R_23 FOREIGN KEY (employee_id) REFERENCES Employees (employee_id) ON DELETE CASCADE);

ALTER TABLE Departments
	ADD (CONSTRAINT R_26 FOREIGN KEY (manager_id) REFERENCES Employees (employee_id) ON DELETE SET NULL);

ALTER TABLE Equipment
	ADD (CONSTRAINT R_27 FOREIGN KEY (department_id) REFERENCES Departments (department_id) ON DELETE SET NULL);

ALTER TABLE Equipment
	ADD (CONSTRAINT R_14 FOREIGN KEY (equipment_type) REFERENCES EquipmentType (equipment_type) ON DELETE SET NULL);

ALTER TABLE Equipment_Projects
	ADD (CONSTRAINT R_31 FOREIGN KEY (equipment_id) REFERENCES Equipment (equipment_id));

ALTER TABLE Equipment_Projects
	ADD (CONSTRAINT R_32 FOREIGN KEY (project_id) REFERENCES Projects (project_id));

ALTER TABLE Technics
	ADD (CONSTRAINT R_19 FOREIGN KEY (employee_id) REFERENCES Employees (employee_id) ON DELETE CASCADE);

ALTER TABLE Technics
	ADD (CONSTRAINT R_25 FOREIGN KEY (equipment_type) REFERENCES EquipmentType (equipment_type) ON DELETE SET NULL);