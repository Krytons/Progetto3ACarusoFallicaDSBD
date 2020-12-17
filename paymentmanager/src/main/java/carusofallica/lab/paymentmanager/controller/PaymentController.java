package carusofallica.lab.paymentmanager.controller;

import carusofallica.lab.paymentmanager.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import payment.Payment;

import java.util.Date;

@Controller
@RequestMapping(path = "/payment")
public class PaymentController {
    @Autowired
    PaymentService service;

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Payment> getAllPayments(){
        return service.getAllPayment();
    }

    @GetMapping(path = "/id/{id}")
    public @ResponseBody Payment getPaymentById(@PathVariable Integer id){
        return service.getByPaymentId(id);
    }

    @GetMapping(path = "/transactions/{fromTimestamp}/{endTimestamp}")
    public @ResponseBody Iterable<Payment>
    getPaymentByDate(@PathVariable Date fromTimestamp, @PathVariable Date endTimestamp, @RequestHeader Integer x_userId){
        return service.getPaymentByDate(x_userId, fromTimestamp, endTimestamp);
    }
}
