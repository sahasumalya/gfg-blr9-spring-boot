package org.example.gfgblr9.services;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;

@Service
public class WeatherClient {

    private HttpClient httpClient;
    public WeatherClient() {
        this.httpClient = HttpClients.createDefault();
    }

    public String getWeather(String city) throws IOException, ParseException {
        HttpGet httpGet = new HttpGet("https://api.weatherapi.com/v1/current.json?key=4d13d7ea7e6f4f789ae163958251706&"+"q="+city);
        /*HttpPost httpPost = new HttpPost("dfdf");
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setHeader("Autho");*
        // RestTemplate, WebClient
         */
        CloseableHttpResponse response = (CloseableHttpResponse) httpClient.execute(httpGet);
        return EntityUtils.toString(response.getEntity());

    }
}
