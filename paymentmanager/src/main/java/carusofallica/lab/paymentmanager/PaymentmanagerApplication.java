package carusofallica.lab.paymentmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"payment"})
public class PaymentmanagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentmanagerApplication.class, args);
    }

}
