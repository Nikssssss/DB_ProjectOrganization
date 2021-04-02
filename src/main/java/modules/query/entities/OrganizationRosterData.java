package modules.query.entities;

public class OrganizationRosterData {
    private final String minHireDate;
    private final String maxHireDate;
    private final String profession;
    private final String minSalary;
    private final String maxSalary;
    private final String minAge;
    private final String maxAge;

    public OrganizationRosterData(String minHireDate, String maxHireDate, String profession, String minSalary, String maxSalary, String minAge, String maxAge) {
        this.minHireDate = minHireDate;
        this.maxHireDate = maxHireDate;
        this.profession = profession;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    public String getMinHireDate() {
        return minHireDate;
    }

    public String getMaxHireDate() {
        return maxHireDate;
    }

    public String getProfession() {
        return profession;
    }

    public String getMinSalary() {
        return minSalary;
    }

    public String getMaxSalary() {
        return maxSalary;
    }

    public String getMinAge() {
        return minAge;
    }

    public String getMaxAge() {
        return maxAge;
    }
}
