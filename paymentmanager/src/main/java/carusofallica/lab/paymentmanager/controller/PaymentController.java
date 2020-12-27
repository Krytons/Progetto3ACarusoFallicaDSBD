package carusofallica.lab.paymentmanager.controller;

import carusofallica.lab.paymentmanager.data.Ipn;
import carusofallica.lab.paymentmanager.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import payment.Payment;

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
    getPaymentByDate(@PathVariable long fromTimestamp, @PathVariable long endTimestamp, @RequestHeader Integer x_userId){
        Timestamp from = new Timestamp(fromTimestamp);
        Timestamp end = new Timestamp(endTimestamp);
        return service.getPaymentByDate(x_userId, from, end);
    }

    @PostMapping(path = "/ipn")
    public @ResponseBody Payment
    ipnFunction(@RequestBody Ipn ipn, @RequestHeader Integer x_userId){
        //TODO: controlla x_userId
        return service.ipn(ipn, x_userId);
    }

}
