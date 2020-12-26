package carusofallica.lab.paymentmanager;

import carusofallica.lab.paymentmanager.health.Heartbeater;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EntityScan(basePackages = {"payment"})
@EnableScheduling
public class PaymentmanagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentmanagerApplication.class, args);
    }

}
