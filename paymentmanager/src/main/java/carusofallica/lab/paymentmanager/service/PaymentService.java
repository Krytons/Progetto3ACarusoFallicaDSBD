package carusofallica.lab.paymentmanager.service;

import carusofallica.lab.paymentmanager.data.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import payment.Payment;

import java.sql.Timestamp;

@Service
@Transactional
public class PaymentService {

    @Value("${paypalBusinessMail}")
    private String mail;

    @Autowired
    PaymentRepository repository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafkaTopicOrders}")
    private String topicOrders;

    @Value("${kafkaTopicErrors}")
    private String topicErrors;

    //***** RICHIESTE DAL PROGETTO *****
    public void sendErrorMessage(String msg) {
        kafkaTemplate.send(topicErrors, msg);
    }

    public void sendOrderMessage(String msg) {
        kafkaTemplate.send(topicOrders, msg);
    }

    public Iterable<Payment> getPaymentByDate(Integer userId, Timestamp fromTimestamp, Timestamp endTimestamp){
        try{
            if (userId == null || userId == 0) {
                return repository.findByCreatedAtGreaterThanEqualAndCreatedAtLessThanEqual(fromTimestamp, endTimestamp);
            } else {
                return repository.findByUserIdAndCreatedAtGreaterThanEqualAndCreatedAtLessThanEqual(userId, fromTimestamp, endTimestamp);
            }
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No payments found");
        }
    }

    public Payment ipn(Ipn paypal_ipn, Integer user_id){
        if (verify_request()){
            if (mail.equals(paypal_ipn.getBusiness())){
                //Generate payment
                try {
                    Payment new_payment = new Payment();
                    new_payment.setAmountPayed(paypal_ipn.getMc_gross());
                    new_payment.setUserId(paypal_ipn.getItem_id());
                    new_payment.setOrderId(paypal_ipn.getInvoice());
                    Timestamp currentSqlTimestamp = new Timestamp(System.currentTimeMillis());
                    new_payment.setCreatedAt(currentSqlTimestamp);
                    new_payment.setModifiedAt(currentSqlTimestamp);
                    generateOrderMessage(new_payment, user_id);
                    return repository.save(new_payment);
                }
                catch (NumberFormatException e){
                    generateErrorMessage("bad_ipn_error", paypal_ipn, user_id);
                    throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "invalid_ipn_field");
                }
            }
            else {
                generateErrorMessage("received_wrong_business_paypal_payment", paypal_ipn, user_id);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "received_wrong_business_paypal_payment");
            }
        }
        else {
            generateErrorMessage("bad_ipn_error", paypal_ipn, user_id);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "bad_ipn_error");
        }
    }

    public Payment real_ipn(HttpEntity<String> httpEntity, PaypalIpn paypal_ipn){
        if (verify_paypal_request(httpEntity)){
            if (mail.equals(paypal_ipn.getReceiver_email())){
                //Generate payment
                try {
                    Payment new_payment = new Payment();
                    new_payment.setAmountPayed(paypal_ipn.getMc_gross());
                    new_payment.setUserId(Integer.parseInt(paypal_ipn.getOption_name1()));
                    new_payment.setOrderId(paypal_ipn.getInvoice());
                    generateOrderMessage(new_payment, new_payment.getUserId());
                    System.out.println("All good");
                    return repository.save(new_payment);
                }
                catch (NumberFormatException e){
                    generatePaypalErrorMessage("bad_ipn_error", paypal_ipn, Integer.parseInt(paypal_ipn.getOption_name1()));
                    throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "invalid_ipn_field");
                }
            }
            else {
                generatePaypalErrorMessage("received_wrong_business_paypal_payment", paypal_ipn, Integer.parseInt(paypal_ipn.getOption_name1()));
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "received_wrong_business_paypal_payment");
            }
        }
        else {
            try {
                generatePaypalErrorMessage("bad_ipn_error", paypal_ipn, Integer.parseInt(paypal_ipn.getOption_name1()));
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "bad_ipn_error");
            }
            catch (NumberFormatException e){
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "invalid_ipn_payer_id");
            }
        }
    }

    private boolean verify_request(){
        return true;
    }

    private boolean verify_paypal_request(HttpEntity<String> httpEntity){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://ipnpb.sandbox.paypal.com/cgi-bin/webscr?cmd=_notify-validate";
        try {
            String answer = restTemplate.postForObject(url, httpEntity.getBody(), String.class);
            System.out.println(answer);
            return answer.equals("VERIFIED");
        }
        catch (Exception e){
            return false;
        }
    }

    private void generatePaypalErrorMessage(String error_message, PaypalIpn ipn, Integer userId){
        KafkaErrorValue value = new KafkaErrorValue();
        value.setTimestamp(System.currentTimeMillis());

        value.setBody(new Gson().toJson(ipn));
        value.setParameters("{\"User_id\":\"" + userId + "\"}");

        KafkaMessage message = new KafkaMessage();
        message.setValue(value);
        message.setKey(error_message);
        sendErrorMessage(new Gson().toJson(message));
    }

    private void generateErrorMessage(String error_message, Ipn ipn, Integer userId){
        KafkaErrorValue value = new KafkaErrorValue();
        value.setTimestamp(System.currentTimeMillis());

        value.setBody(new Gson().toJson(ipn));
        value.setParameters("{\"User_id\":\"" + userId + "\"}");

        KafkaMessage message = new KafkaMessage();
        message.setValue(value);
        message.setKey(error_message);
        sendErrorMessage(new Gson().toJson(message));
    }

    private void generateOrderMessage(Payment new_payment, Integer userId){
        KafkaOrderValue value = new KafkaOrderValue();
        value.setOrderId(new_payment.getOrderId());
        value.setUserId(new_payment.getUserId());
        value.setAmountPaid(new_payment.getAmountPayed());
        KafkaMessage message = new KafkaMessage();
        message.setValue(value);
        message.setKey("order_paid");
        sendOrderMessage(new Gson().toJson(message));
    }

}
