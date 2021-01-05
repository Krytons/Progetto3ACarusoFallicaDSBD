package carusofallica.lab.paymentmanager.health;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
@EnableScheduling
public class Heartbeater {

    @Autowired
    JdbcTemplate template;

    @Scheduled(fixedDelayString = "${heartbeaterPeriod}")
    public void heartbeat(){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://192.168.1.19:8080/ping";
        HeartBeatBody jsonBody = new HeartBeatBody();
        jsonBody.setService("paymentService");
        jsonBody.setServiceStatus("up");

        //Check DB connection is up
        int errorCode = check();
        if (errorCode != 1){
            jsonBody.setDbStatus("down");
        }
        else{
            jsonBody.setDbStatus("up");
        }

        System.out.println(new Gson().toJson(jsonBody));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>(new Gson().toJson(jsonBody),headers);
        try {
            String answer = restTemplate.postForObject(url, entity, String.class);
            System.out.println(answer);
        }
        catch (ResourceAccessException e){
            System.out.println("Connection error to /ping");
        }
    }

    private int check(){
        List<Object> results = template.query("select 1", new SingleColumnRowMapper<>());
        return results.size();
    }

}
