package com.example.fxproject.view.tdm;

public class EmployeeTM {
    private String emp_id;
    private String emp_name;
    private String emp_phone;
    private String emp_email;
    private String emp_address;
    private String emp_role;
    public EmployeeTM() {}

    public EmployeeTM(String emp_id, String emp_name, String emp_phone, String emp_email, String emp_address, String emp_role) {
        this.emp_id = emp_id;
        this.emp_name = emp_name;
        this.emp_phone = emp_phone;
        this.emp_email = emp_email;
        this.emp_address = emp_address;
        this.emp_role = emp_role;
    }

    public String getEmp_role() {
        return emp_role;
    }

    public void setEmp_role(String emp_role) {
        this.emp_role = emp_role;
    }

    public String getEmp_address() {
        return emp_address;
    }

    public void setEmp_address(String emp_address) {
        this.emp_address = emp_address;
    }

    public String getEmp_email() {
        return emp_email;
    }

    public void setEmp_email(String emp_email) {
        this.emp_email = emp_email;
    }

    public String getEmp_phone() {
        return emp_phone;
    }

    public void setEmp_phone(String emp_phone) {
        this.emp_phone = emp_phone;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }
}
