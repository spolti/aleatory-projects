package com.aleatory.claim_free_book.utils.httpConn;

import com.aleatory.claim_free_book.utils.Properties.LoadProperties;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by fspolti on 3/30/16.
 */
public class HttpUtils {

    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String FREE_LEARNING_URL = "https://www.packtpub.com/packt/offers/free-learning";
    private static final String PACKT_HOME_URL = "https://www.packtpub.com";

    private BasicCookieStore cookieStore = new BasicCookieStore();
    CloseableHttpClient httpClient;
    private static LoadProperties loadProperties;

    /*
    * Claim the free book
    */
    public void claimFreeBook() {
        //logging in
        formLogin();

        String URL = String.format("https://www.packtpub.com%s", obtainFreeBookUri());
        System.out.println("Trying to claim the free book against url [" + URL + "]");

        HttpPost request = new HttpPost(URL);
        request.setHeader("User-Agent", USER_AGENT);
        CloseableHttpResponse response3 = null;

        try {
            response3 = httpClient.execute(request);

            if (response3.getStatusLine().toString().contains("200")) {
                System.out.println("HTTP Response code [" + response3.getStatusLine() + "]. Book successfully claimed, check your account. :)");
            } else {
                System.out.println("Failed to claim the book, verify your credentials and try again.");
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                response3.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
    * Perform the login in the packt's website
    */
    private void formLogin() {

        //Verifying if the file with credentials exist, if not create it
        Properties props = loadProperties.load();
        System.out.println("Using the credentials [" + props.getProperty("username") + ":" + props.getProperty("password") + "]");

        httpClient = HttpClients.custom()
                .setDefaultCookieStore(cookieStore)
                .setRedirectStrategy(new LaxRedirectStrategy())
                .build();

        try {
            HttpGet httpget = new HttpGet(PACKT_HOME_URL);
            CloseableHttpResponse response1 = httpClient.execute(httpget);
            try {
                HttpEntity entity = response1.getEntity();
                EntityUtils.consume(entity);

            } finally {
                response1.close();
            }

            HttpUriRequest login = RequestBuilder.post()
                    .setUri(PACKT_HOME_URL)
                    .addParameter("email", props.getProperty("username"))
                    .addParameter("password", props.getProperty("password"))
                    .addParameter("op", "Login")
                    .addParameter("form_build_id", "form-2604994366377c6af77ab45668c0de67")
                    .addParameter("form_id", "packt_user_login_form")
                    .build();
            CloseableHttpResponse responseLogin = httpClient.execute(login);
            try {
                HttpEntity entity = responseLogin.getEntity();

                System.out.println("Login form get: " + responseLogin.getStatusLine());

                //Abort if status is different from 200
                if (!responseLogin.getStatusLine().toString().contains("200")){
                    throw new HttpException("Failed to login, please review the credentials");
                }

                EntityUtils.consume(entity);

            } catch (HttpException e) {
                e.printStackTrace();
            } finally {
                responseLogin.close();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /*
    * As the URL to claim the daily free book changes everyday, we need to get it before to claim the book
    * Returns the free book URI
    */
    private String obtainFreeBookUri() {

        HttpGet request = new HttpGet(FREE_LEARNING_URL);
        request.setHeader("User-Agent", USER_AGENT);
        String[] URI = new String[0];

        try {
            HttpResponse response = getClient().execute(request);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            String inputLine;
            StringBuffer responseBuffer = new StringBuffer();

            while ((inputLine = reader.readLine()) != null) {
                // we need to get only the most important part
                if (inputLine.contains("/freelearning-claim/")) {
                    responseBuffer.append(inputLine);
                }
            }

            //it should return something like /freelearning-claim/16787/21478
            URI = responseBuffer.toString().trim().replace(" ", "").replace("\"class", "").split("=\"");

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return URI[1];
    }

    /*
    * Return the CloseableHttpClient
    */
    private static HttpClient getClient() {
        return HttpClients.createDefault();
    }
}