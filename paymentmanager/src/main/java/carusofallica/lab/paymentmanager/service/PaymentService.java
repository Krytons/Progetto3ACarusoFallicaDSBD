package carusofallica.lab.paymentmanager.service;

import carusofallica.lab.paymentmanager.data.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import payment.Payment;

import java.util.Date;

@Service
@Transactional
public class PaymentService {

    @Autowired
    PaymentRepository repository;

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
    public Iterable<Payment> getPaymentByDate(Integer userId, Date fromTimestamp, Date endTimestamp){
        try{
            if (userId == null || userId == 0){
                return repository.findByCreatedAtGreaterThanEqualAndCreatedAtLessThanEqual(fromTimestamp, endTimestamp);
            }
            else{
                return repository.findByUserIdAndCreatedAtGreaterThanEqualAndCreatedAtLessThanEqual(userId, fromTimestamp, endTimestamp);
            }
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No payments found");
        }
    }

}
