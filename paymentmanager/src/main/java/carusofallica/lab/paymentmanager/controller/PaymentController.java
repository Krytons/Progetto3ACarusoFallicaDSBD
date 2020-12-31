package carusofallica.lab.paymentmanager.controller;

import carusofallica.lab.paymentmanager.data.Ipn;
import carusofallica.lab.paymentmanager.data.KafkaHttpValue;
import carusofallica.lab.paymentmanager.data.KafkaMessage;
import carusofallica.lab.paymentmanager.data.PaypalIpn;
import carusofallica.lab.paymentmanager.service.PaymentService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import payment.Payment;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;

@Controller
@RequestMapping(path = "/payment")
public class PaymentController {

    @Autowired
    PaymentService service;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafkaTopicErrors}")
    private String topicErrors;

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Payment> getAllPayments(){
        return service.getAllPayment();
    }

    @GetMapping(path = "/id/{id}")
    public @ResponseBody Payment getPaymentById(@PathVariable Integer id){
        return service.getByPaymentId(id);
    }

    @PostMapping(path="/insert")
    public @ResponseBody Payment insertPayment(@RequestBody Payment payment){
        return service.insertPayment(payment);
    }

    //@DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    @GetMapping(path = "/transactions/{fromTimestamp}/{endTimestamp}")
    public @ResponseBody Iterable<Payment>
    getPaymentByDate(@PathVariable long fromTimestamp, @PathVariable long endTimestamp, @RequestHeader Integer x_userId, HttpServletRequest request){
        try{
            Timestamp from = new Timestamp(fromTimestamp);
            Timestamp end = new Timestamp(endTimestamp);
            return service.getPaymentByDate(x_userId, from, end);
        }
        catch(ResponseStatusException e){
            generateHttpErrorMessage(request, e);
            throw e;
        }
    }

    @PostMapping(path = "/ipn")
    public @ResponseBody Payment
    ipnFunction(@RequestBody Ipn ipn, @RequestHeader Integer x_userId, HttpServletRequest request){
        //TODO: controlla x_userId
        try {
            return service.ipn(ipn, x_userId);
        }
        catch(ResponseStatusException e){
            generateHttpErrorMessage(request, e);
            throw e;
        }
    }

    /*
    @RequestMapping(value = "/real_ipn", method = RequestMethod.POST,
    consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
     */
    @PostMapping(path = "/real_ipn", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Payment
    ipnRealFunction(PaypalIpn ipn){
        return service.real_ipn(ipn);
    }

    public void sendErrorMessage(String msg) {
        kafkaTemplate.send(topicErrors, msg);
    }

    private void generateHttpErrorMessage(HttpServletRequest request, ResponseStatusException exception){
        KafkaHttpValue value = new KafkaHttpValue();
        value.setTimestamp(System.currentTimeMillis());
        value.setSourceIp(request.getRemoteAddr());
        value.setService("Payment_Service");
        value.setRequest(request.getPathInfo() + request.getMethod());
        if (exception.getStatus().is5xxServerError()) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            exception.printStackTrace(pw);
            String stacktrace = sw.toString();
            value.setError(stacktrace);
        }
        if (exception.getStatus().is4xxClientError()) {
            value.setError(String.valueOf(exception.getStatus().value()));
        }
        KafkaMessage message = new KafkaMessage();
        message.setValue(value);
        message.setKey("http_errors");
        sendErrorMessage(new Gson().toJson(message));
    }

}
