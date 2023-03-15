package ibf2022.day25.payload;

public class TransferRequest {

    private Integer accountFrom;
    private Integer accountTo;
    private Float amount;


    public Integer getAccountFrom() {return this.accountFrom;}
    public void setAccountFrom(Integer accountFrom) {this.accountFrom = accountFrom;}

    public Integer getAccountTo() {return this.accountTo;}
    public void setAccountTo(Integer accountTo) {this.accountTo = accountTo;}

    public Float getAmount() {return this.amount;}
    public void setAmount(Float amount) {this.amount = amount;}

}
