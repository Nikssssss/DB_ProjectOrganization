package common.entities;

import java.sql.Date;

public class EmployeeData {
    private final Integer employeeId;
    private final String firstName;
    private final String lastName;
    private final Date hireDate;
    private final int professionId;
    private final int salary;
    private final int age;

    public EmployeeData(Integer employeeId, String firstName, String lastName, Date hireDate, int professionId, int salary, int age) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.hireDate = hireDate;
        this.professionId = professionId;
        this.salary = salary;
        this.age = age;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public int getProfessionId() {
        return professionId;
    }

    public int getSalary() {
        return salary;
    }

    public int getAge() {
        return age;
    }
}
