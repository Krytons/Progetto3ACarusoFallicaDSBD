package carusofallica.lab.paymentmanager.data;

import javax.validation.constraints.NotNull;

//This class is used to recognize received Ipn
public class Ipn {

    @NotNull(message = "A ipn must have an invoice value")
    private String invoice; //This is a sort of order id
    @NotNull(message = "A ipn must have an item_id value")
    private Integer item_id; //This is the user_id
    @NotNull(message = "A ipn must have a message value")
    private double mc_gross; //This is the amount_payed
    @NotNull(message = "A ipn must have a business value")
    private String business; //This is the receiver payment mail

    public String getInvoice() {
        return invoice;
    }

    public Ipn setInvoice(String invoice) {
        this.invoice = invoice;
        return this;
    }

    public Integer getItem_id() {
        return item_id;
    }

    public Ipn setItem_id(Integer item_id) {
        this.item_id = item_id;
        return this;
    }

    public double getMc_gross() {
        return mc_gross;
    }

    public Ipn setMc_gross(double mc_gross) {
        this.mc_gross = mc_gross;
        return this;
    }

    public String getBusiness() {
        return business;
    }

    public Ipn setBusiness(String business) {
        this.business = business;
        return this;
    }
}
