package carusofallica.lab.paymentmanager.data;

import org.springframework.data.repository.CrudRepository;
import payment.Payment;

import java.util.Date;

public interface PaymentRepository extends CrudRepository<Payment, Integer> {
    public Iterable<Payment> findByUserIdAndCreatedAtGreaterThanEqualAndCreatedAtLessThanEqual(Integer userId, Date fromTimestamp, Date endTimestamp);
    public Iterable<Payment> findByCreatedAtGreaterThanEqualAndCreatedAtLessThanEqual(Date fromTimestamp, Date endTimestamp);
}
