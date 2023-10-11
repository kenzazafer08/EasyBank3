package dto;

import java.util.Date;

public class Employee extends Person {
    private String number;
    private Date recruitementDate;
    private String email;
    public Employee() {
    }

    public Employee(String firstName, String lastName,String phone, String address, String number, Date recruitementDate, String email) {
        super(firstName, lastName, phone, address);
        this.number = number;
        this.recruitementDate = recruitementDate;
        this.email = email;
    }


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getRecruitementDate() {
        return recruitementDate;
    }

    public void setRecruitementDate(Date recruitementDate) {
        this.recruitementDate = recruitementDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
