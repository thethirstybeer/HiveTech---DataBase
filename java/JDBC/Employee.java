package JDBC;

public class Employee {
    private int employeeNumber;
    private String lastName;
    private String firstName;
    private String extension;
    private String email;
    private String officeCode;
    private int reportTo;
    private String jobTitle;

    public Employee(int employeeNumber, String lastName, String firstName, String extension, String email, String officeCode, int reportTo, String jobTitle) {
        this.employeeNumber = employeeNumber;
        this.lastName = lastName;
        this.firstName = firstName;
        this.extension = extension;
        this.email = email;
        this.officeCode = officeCode;
        this.reportTo = reportTo;
        this.jobTitle = jobTitle;
    }

    public int getEmployeeNumber() {
        return employeeNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getExtension() {
        return extension;
    }

    public String getEmail() {
        return email;
    }

    public String getOfficeCode() {
        return officeCode;
    }

    public int getReportTo() {
        return reportTo;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    @Override
    public String toString() {
        return String.format("Employee[EmployeeNumber: %d, lastName: %s, firstName: %s, extension: %s, email: %s, officeCode: %d, reportTo: %d, jobTitle: %s",
                employeeNumber, lastName, firstName, extension, email, officeCode, reportTo, jobTitle);
    }
}
