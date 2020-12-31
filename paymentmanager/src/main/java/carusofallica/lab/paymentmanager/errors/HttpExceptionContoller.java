package carusofallica.lab.paymentmanager.errors;

import carusofallica.lab.paymentmanager.data.KafkaHttpValue;
import carusofallica.lab.paymentmanager.data.KafkaMessage;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice
public class HttpExceptionContoller {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafkaTopicErrors}")
    private String topicErrors;

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> generateHttpErrorMessage(HttpServletRequest request, ResponseStatusException exception){
        KafkaHttpValue value = new KafkaHttpValue();
        value.setTimestamp(System.currentTimeMillis());
        value.setSourceIp(request.getRemoteAddr());
        value.setService("Payment_Service");
        value.setRequest(request.getRequestURI() + " " + request.getMethod());
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
        kafkaTemplate.send(topicErrors, (new Gson().toJson(message)));
        return new ResponseEntity<Object>(new ReturnMessage(exception.getStatus().value(), exception.getStatus().getReasonPhrase(), exception.getReason(), request.getRequestURI()), new HttpHeaders(), exception.getStatus());
    }

}
