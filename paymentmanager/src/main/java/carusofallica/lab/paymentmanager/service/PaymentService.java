package carusofallica.lab.paymentmanager.service;

import carusofallica.lab.paymentmanager.data.KafkaMessage;
import carusofallica.lab.paymentmanager.data.KafkaValue;
import carusofallica.lab.paymentmanager.data.PaymentRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import payment.Ipn;
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

    //***** DA TEST *****
    public Iterable<Payment> getAllPayment(){
        return repository.findAll();
    }

    public Payment getByPaymentId(Integer id){
        try{
            return repository.findById(id).get();
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There are no payments with that id");
        }
    }

    public Payment insertPayment(Payment payment){
        try{
            return repository.save(payment);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid insert");
        }
    }


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
        try {
            if (verify_request()){
                if (mail.equals(paypal_ipn.getBusiness())){
                    //Generate payment
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
                else {
                    generateErrorMessage("received_wrong_business_paypal_payment", paypal_ipn, user_id);
                    throw new Exception();
                }
            }
            else {
                generateErrorMessage("bad_ipn_error", paypal_ipn, user_id);
                throw new Exception();
            }
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Ipn");
        }
    }

    private boolean verify_request(){
        /*
        This function will contain some calls to Paypal to verify the ipn.
         */
        return true;
    }

    private void generateErrorMessage(String error_message, Ipn ipn, Integer userId){
        KafkaValue value = new KafkaValue();
        value.setTimestamp(System.currentTimeMillis());

        value.setBody(new Gson().toJson(ipn));
        /*
        String userString = "{\"User_id\":\"" + userId + "\"}";
        value.setParameters(new Gson().toJson(userString));
         */
        value.setParameters("{\"User_id\":\"" + userId + "\"}");

        KafkaMessage message = new KafkaMessage();
        message.setValue(value);
        message.setOrder_paid(error_message);
        sendErrorMessage(new Gson().toJson(message));
    }

    private void generateOrderMessage(Payment new_payment, Integer userId){
        KafkaValue value = new KafkaValue();
        value.setOrderId(new_payment.getOrderId());
        value.setUserId(new_payment.getUserId());
        value.setAmountPaid(new_payment.getAmountPayed());
        KafkaMessage message = new KafkaMessage();
        message.setValue(value);
        message.setOrder_paid("order_paid");
        sendOrderMessage(new Gson().toJson(message));
    }

}
