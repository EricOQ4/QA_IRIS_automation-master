package pageobjects.api.historical;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

/**
 * Created by dannyl on 2017-05-19.
 */

public class QuandlConnectToApi {
    // Api key may change over time so just change it here if it does
    private static String key = "c_Zfy3So98bKcz1kgAGs";
    private static final String baseUrl = "https://www.quandl.com/api/v3/datasets/";

    /**
     * This method uses the createURL method to return the dataset corresponding to the ticker and exchange.
     *
     * @param  ticker this is the stock you want.
     *                exchange is the stock exchange you want (exchange = "EOD" means end of day stock info)
     *                start and end will create a date range between start and end
     */

    public static QuandlDataset getDataset(String ticker, String exchange) {
            return new QuandlDataset(createUrl(baseUrl + exchange +  "/" + ticker + "/data.json?api_token=" + key), "json");
    }

    public static QuandlDataset getDatasetBetweenDates(String ticker, String exchange, String start, String end) {
            return new QuandlDataset(createUrl(baseUrl + exchange +"/"+ ticker + "/data.json?api_key=" + key + "&start_date=" + start + "&end_date=" + end), "json");
    }

    public static QuandlDataset getDatasetFromDate(String ticker, String exchange, String from){
        return new QuandlDataset(createUrl(baseUrl + exchange +"/"+ ticker + "/data.json?api_key=" + key + "&start_date=" + from), "json");
    }

    public static QuandlDataset getDatasetBetweenDatesAndTicker(String ticker, String exchange, String start, String end) {
        return new QuandlDataset(createUrl(baseUrl + exchange +  "/" + ticker + "data.json?api_key=" + key + "&end_date=" + end + "&start_date=" + start), "json");
    }

    /**
     * This method just executes HTTP requests.
     *
     * @param url this is the url for the http request... it assumes "http://" is already included.
     * @return it returns the response from the url in string form... or the message of the exception if one is thrown.
     */
    private static String createUrl(String url) {

        String output;

        HttpClient httpclient = new DefaultHttpClient();
        try {
            HttpGet httpget = new HttpGet(url);

            System.out.println("Executing Request: " + httpget.getURI());

            ResponseHandler<String> responseHandler = new BasicResponseHandler();

            output = httpclient.execute(httpget, responseHandler);

        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        } finally {

            httpclient.getConnectionManager().shutdown();
        }
        return output;

    }

}
