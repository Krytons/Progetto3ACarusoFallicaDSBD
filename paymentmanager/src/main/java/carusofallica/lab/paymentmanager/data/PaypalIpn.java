package carusofallica.lab.paymentmanager.data;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class PaypalIpn {

    private String option_name1;
    private String payment_type;
    private String payment_date;
    private String payment_status;
    private String address_status;
    private String payer_status;
    private String first_name;
    private String last_name;
    private String payer_email;
    private String payer_id;
    private String address_name;
    private String address_country;
    private String address_country_code;
    private String address_zip;
    private String address_state;
    private String address_city;
    private String address_street;
    private String business; //This one
    private String receiver_email;
    private String receiver_id;
    private String residence_country;
    private String item_name;
    private String item_number;
    private Integer quantity;
    private String shipping;
    private String tax;
    private String mc_currency;
    private double mc_fee;
    private double mc_gross; //This one
    private double mc_gross_1;
    private String txn_type;
    private String txn_id;
    private double notify_version;
    private String custom;
    private String invoice;
    private Integer test_ipn;
    private String verify_sign;

    public String getOption_name1() {
        return option_name1;
    }

    public PaypalIpn setOption_name1(String option_name1) {
        this.option_name1 = option_name1;
        return this;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public PaypalIpn setPayment_type(String payment_type) {
        this.payment_type = payment_type;
        return this;
    }

    public String getPayment_date() {
        return payment_date;
    }

    public PaypalIpn setPayment_date(String payment_date) {
        this.payment_date = payment_date;
        return this;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public PaypalIpn setPayment_status(String payment_status) {
        this.payment_status = payment_status;
        return this;
    }

    public String getAddress_status() {
        return address_status;
    }

    public PaypalIpn setAddress_status(String address_status) {
        this.address_status = address_status;
        return this;
    }

    public String getPayer_status() {
        return payer_status;
    }

    public PaypalIpn setPayer_status(String payer_status) {
        this.payer_status = payer_status;
        return this;
    }

    public String getFirst_name() {
        return first_name;
    }

    public PaypalIpn setFirst_name(String first_name) {
        this.first_name = first_name;
        return this;
    }

    public String getLast_name() {
        return last_name;
    }

    public PaypalIpn setLast_name(String last_name) {
        this.last_name = last_name;
        return this;
    }

    public String getPayer_email() {
        return payer_email;
    }

    public PaypalIpn setPayer_email(String payer_email) {
        this.payer_email = payer_email;
        return this;
    }

    public String getPayer_id() {
        return payer_id;
    }

    public PaypalIpn setPayer_id(String payer_id) {
        this.payer_id = payer_id;
        return this;
    }

    public String getAddress_name() {
        return address_name;
    }

    public PaypalIpn setAddress_name(String address_name) {
        this.address_name = address_name;
        return this;
    }

    public String getAddress_country() {
        return address_country;
    }

    public PaypalIpn setAddress_country(String address_country) {
        this.address_country = address_country;
        return this;
    }

    public String getAddress_country_code() {
        return address_country_code;
    }

    public PaypalIpn setAddress_country_code(String address_country_code) {
        this.address_country_code = address_country_code;
        return this;
    }

    public String getAddress_zip() {
        return address_zip;
    }

    public PaypalIpn setAddress_zip(String address_zip) {
        this.address_zip = address_zip;
        return this;
    }

    public String getAddress_state() {
        return address_state;
    }

    public PaypalIpn setAddress_state(String address_state) {
        this.address_state = address_state;
        return this;
    }

    public String getAddress_city() {
        return address_city;
    }

    public PaypalIpn setAddress_city(String address_city) {
        this.address_city = address_city;
        return this;
    }

    public String getAddress_street() {
        return address_street;
    }

    public PaypalIpn setAddress_street(String address_street) {
        this.address_street = address_street;
        return this;
    }

    public String getBusiness() {
        return business;
    }

    public PaypalIpn setBusiness(String business) {
        this.business = business;
        return this;
    }

    public String getReceiver_email() {
        return receiver_email;
    }

    public PaypalIpn setReceiver_email(String receiver_email) {
        this.receiver_email = receiver_email;
        return this;
    }

    public String getReceiver_id() {
        return receiver_id;
    }

    public PaypalIpn setReceiver_id(String receiver_id) {
        this.receiver_id = receiver_id;
        return this;
    }

    public String getResidence_country() {
        return residence_country;
    }

    public PaypalIpn setResidence_country(String residence_country) {
        this.residence_country = residence_country;
        return this;
    }

    public String getItem_name() {
        return item_name;
    }

    public PaypalIpn setItem_name(String item_name) {
        this.item_name = item_name;
        return this;
    }

    public String getItem_number() {
        return item_number;
    }

    public PaypalIpn setItem_number(String item_number) {
        this.item_number = item_number;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public PaypalIpn setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getShipping() {
        return shipping;
    }

    public PaypalIpn setShipping(String shipping) {
        this.shipping = shipping;
        return this;
    }

    public String getTax() {
        return tax;
    }

    public PaypalIpn setTax(String tax) {
        this.tax = tax;
        return this;
    }

    public String getMc_currency() {
        return mc_currency;
    }

    public PaypalIpn setMc_currency(String mc_currency) {
        this.mc_currency = mc_currency;
        return this;
    }

    public double getMc_fee() {
        return mc_fee;
    }

    public PaypalIpn setMc_fee(double mc_fee) {
        this.mc_fee = mc_fee;
        return this;
    }

    public double getMc_gross() {
        return mc_gross;
    }

    public PaypalIpn setMc_gross(double mc_gross) {
        this.mc_gross = mc_gross;
        return this;
    }

    public double getMc_gross_1() {
        return mc_gross_1;
    }

    public PaypalIpn setMc_gross_1(double mc_gross_1) {
        this.mc_gross_1 = mc_gross_1;
        return this;
    }

    public String getTxn_type() {
        return txn_type;
    }

    public PaypalIpn setTxn_type(String txn_type) {
        this.txn_type = txn_type;
        return this;
    }

    public String getTxn_id() {
        return txn_id;
    }

    public PaypalIpn setTxn_id(String txn_id) {
        this.txn_id = txn_id;
        return this;
    }

    public double getNotify_version() {
        return notify_version;
    }

    public PaypalIpn setNotify_version(double notify_version) {
        this.notify_version = notify_version;
        return this;
    }

    public String getCustom() {
        return custom;
    }

    public PaypalIpn setCustom(String custom) {
        this.custom = custom;
        return this;
    }

    public String getInvoice() {
        return invoice;
    }

    public PaypalIpn setInvoice(String invoice) {
        this.invoice = invoice;
        return this;
    }

    public Integer getTest_ipn() {
        return test_ipn;
    }

    public PaypalIpn setTest_ipn(Integer test_ipn) {
        this.test_ipn = test_ipn;
        return this;
    }

    public String getVerify_sign() {
        return verify_sign;
    }

    public PaypalIpn setVerify_sign(String verify_sign) {
        this.verify_sign = verify_sign;
        return this;
    }

    /*
    public String convert_to_string(PaypalIpn ipn){
        String return_string = "";
        return_string = "https://ipnpb.sandbox.paypal.com/cgi-bin/webscr?cmd=_notify-validate&" + "mc_gross=" + ipn.getMc_gross() + "&invoice=" + ipn.getInvoice() + "&address_status=" + ipn.getAddress_status() + "&payer_id=" + ipn.getPayer_id() + "&address_street=" + ipn.getAddress_street() + "&payment_date=" + ipn.getPayment_date() + "&payment_status=" + ipn.getPayment_status() + "&charset=" + "UTF-8" + "&address_zip=" + ipn.getAddress_zip() + "&first_name=" + ipn.getFirst_name() + "&address_country_code="
        + ipn.getAddress_country_code() + "&address_name=" + ipn.getAddress_name() + "&notify_version=" + ipn.getNotify_version() + "&payer_status=" + ipn.getPayer_status() + "&address_country=" + ipn.getAddress_country() + "&address_city=" + ipn.getAddress_city() + "&quantity=" + ipn.getQuantity() + "&verify_sign=" + ipn.getVerify_sign() + "&payer_email=" + ipn.getPayer_email() + "&txn_id=" + ipn.getTxn_id() + "&payment_type=" + ipn.getPayment_type() +
        "&last_name=" + ipn.getPayment_type() + "&address_state=" + ipn.getAddress_state() + "&receiver_email=" + ipn.getReceiver_email() + "&txn_type=" + ipn.getTxn_type() + "&item_name=" + ipn.getItem_name() + "&mc_currency=" + ipn.getMc_currency() + "&item_number=" + ipn.getItem_number() + "&residence_country=" + ipn.getResidence_country() + "&test_ipn=" + ipn.getTest_ipn() + "&shipping_method=" + ipn.getShipping();
        return return_string;
    }

    public MultiValueMap<String, String> convert_to_map(PaypalIpn ipn){
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("payment_type", ipn.getPayment_type());
        map.add("payment_date", ipn.getPayment_date());
        map.add("address_status", ipn.getAddress_status());
        map.add("payer_status", ipn.getPayer_status());
        map.add("first_name", ipn.getFirst_name());
        map.add("last_name", ipn.getLast_name());
        map.add("payer_email", ipn.getPayer_email());
        map.add("payer_id", ipn.getPayer_id());
        map.add("address_name", ipn.getAddress_name());
        map.add("address_country", ipn.getAddress_country());
        map.add("address_country_code", ipn.getAddress_country_code());
        map.add("address_zip", ipn.getAddress_zip());
        map.add("address_state", ipn.getAddress_state());
        map.add("address_city", ipn.getAddress_city());
        map.add("address_street", ipn.getAddress_street());
        map.add("business", ipn.getBusiness());
        map.add("receiver_email", ipn.getReceiver_email());
        map.add("receiver_id", ipn.getReceiver_id());
        map.add("residence_country", ipn.getResidence_country());
        map.add("item_name", ipn.getItem_name());
        map.add("item_number", ipn.getItem_number());
        map.add("quantity", ipn.getQuantity().toString());
        map.add("shipping", ipn.getShipping());
        map.add("tax", ipn.getTax());
        map.add("mc_currency", ipn.getMc_currency());
        map.add("mc_fee", String.valueOf(ipn.getMc_fee()));
        map.add("mc_gross", String.valueOf(ipn.getMc_gross()));
        map.add("mc_gross_1", String.valueOf(ipn.getMc_gross_1()));
        map.add("txn_type", ipn.getTxn_type());
        map.add("txn_id", ipn.getTxn_id());
        map.add("notify_version", String.valueOf(ipn.getNotify_version()));
        map.add("custom", ipn.getCustom());
        map.add("invoice", ipn.getInvoice());
        map.add("test_ipn", ipn.getTest_ipn().toString());
        map.add("verify_sign", ipn.getVerify_sign());
        map.add("verify_sign", ipn.getVerify_sign());
        return map;
    }
     */
}
