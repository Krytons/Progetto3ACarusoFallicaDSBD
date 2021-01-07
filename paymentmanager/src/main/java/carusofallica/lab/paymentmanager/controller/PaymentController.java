package carusofallica.lab.paymentmanager.controller;

import carusofallica.lab.paymentmanager.data.Ipn;
import carusofallica.lab.paymentmanager.data.PaypalIpn;
import carusofallica.lab.paymentmanager.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import payment.Payment;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/payment")
public class PaymentController {

    @Autowired
    PaymentService service;

    /*
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
     */

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
        try {
            return service.ipn(ipn, x_userId);
        }
        catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "duplicate_order_id");
        }
    }

    @PostMapping(path = "/real_ipn", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Payment
    ipnRealFunction(HttpEntity<String> httpEntity, PaypalIpn ipn) throws IOException {
        try{
            return service.real_ipn(httpEntity, ipn);
        }
        catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "duplicate_order_id");
        }
    }

}
