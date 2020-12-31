package carusofallica.lab.paymentmanager.controller;

import carusofallica.lab.paymentmanager.data.Ipn;
import carusofallica.lab.paymentmanager.data.KafkaHttpValue;
import carusofallica.lab.paymentmanager.data.KafkaMessage;
import carusofallica.lab.paymentmanager.data.PaypalIpn;
import carusofallica.lab.paymentmanager.service.PaymentService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import payment.Payment;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;

@Controller
@RequestMapping(path = "/payment")
public class PaymentController {

    @Autowired
    PaymentService service;

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
        Timestamp from = new Timestamp(fromTimestamp);
        Timestamp end = new Timestamp(endTimestamp);
        return service.getPaymentByDate(x_userId, from, end);
    }

    @PostMapping(path = "/ipn")
    public @ResponseBody Payment
    ipnFunction(@Valid @RequestBody Ipn ipn, @RequestHeader Integer x_userId, HttpServletRequest request){
        //TODO: controlla x_userId
        return service.ipn(ipn, x_userId);
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

}
