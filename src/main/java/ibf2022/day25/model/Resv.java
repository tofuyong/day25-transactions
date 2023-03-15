package ibf2022.day25.model;

import java.sql.Date;

public class Resv {
    private Integer id;
    private Date resvDate;
    private String fullName;


    public Integer getId() {return this.id;}
    public void setId(Integer id) {this.id = id;}

    public Date getResvDate() {return this.resvDate;}
    public void setResvDate(Date resvDate) {this.resvDate = resvDate;}

    public String getFullName() {return this.fullName;}
    public void setFullName(String fullName) {this.fullName = fullName;}
    
}
