package ibf2022.day25.model;

public class BankAccount {
    private Integer id;
    private String fullName;
    private Boolean isActive;
    private String acctType;
    private Float balance;

    public Integer getId() {return this.id;}
    public void setId(Integer id) {this.id = id;}

    public String getFullName() {return this.fullName;}
    public void setFullName(String fullName) {this.fullName = fullName;}

    public Boolean isIsActive() {return this.isActive;}
    public Boolean getIsActive() {return this.isActive;}
    public void setIsActive(Boolean isActive) {this.isActive = isActive;}

    public String getAcctType() {return this.acctType;}
    public void setAcctType(String acctType) {this.acctType = acctType;}

    public Float getBalance() {return this.balance;}
    public void setBalance(Float balance) {this.balance = balance;}
}
