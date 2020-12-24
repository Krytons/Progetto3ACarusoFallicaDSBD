package carusofallica.lab.paymentmanager.health;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class Heartbeater {

    @Autowired
    JdbcTemplate template;

    @Scheduled(fixedDelayString = "${heartbeaterPeriod}")
    public void heartbeat(){
        RestTemplate restTemplate = new RestTemplate();
        String url = "/ping";
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

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>(new Gson().toJson(jsonBody),headers);
        String answer = restTemplate.postForObject(url, entity, String.class);
        System.out.println(answer);
    }

    private int check(){
        List<Object> results = template.query("select 1 from payment", new SingleColumnRowMapper<>());
        return results.size();
    }

}
