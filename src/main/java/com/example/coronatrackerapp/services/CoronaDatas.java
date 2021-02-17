package com.example.coronatrackerapp.services;

import com.example.coronatrackerapp.model.LocationStat;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoronaDatas {
    //the url we are fetching data from
    private static final String DATA_SOURCE = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
   //the list containing data associated with corona cases
    private List<LocationStat> allStat = new ArrayList<>();
    //variable for counting total cases globally
    private int totalCases;

    public int getTotalCases() {
        return totalCases;
    }

    public List<LocationStat> getAllStat() {
        return allStat;
    }

    @PostConstruct //running after coronaDatas construction
    @Scheduled(cron = "* * 1 * * *") //scheduling to run every first hour of each day
    //method for fetching data
    public void fetch() throws IOException, InterruptedException {
        //the temporary list updates based on schedule and replaces allStat list
        List<LocationStat> current = new ArrayList<>();
        //creating httpclient for http calls
        HttpClient client = HttpClient.newHttpClient();
        //creating http request and passing my uri
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(DATA_SOURCE))
                .build();
        //creating response based on the request we made and getting response in    string format
        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
        //retrieving data from response
        StringReader csvBodyReader = new StringReader(response.body());
        //creating iterable for looping through csv reader
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        for (CSVRecord record : records) {
            //creating temporary locationStat object for filling its fields by reading csv headers
            LocationStat locationStat = new LocationStat();
            //setting the province field of locationStat
            locationStat.setProvince(record.get("Province/State"));
            //setting the country field of locationStat
            locationStat.setCountry(record.get("Country/Region"));
            //storing the latest number of cases
            int latestCases = Integer.parseInt(record.get(record.size()-1));
            //storing the number of yesterday cases
            int dayBeforeCases = Integer.parseInt(record.get(record.size()-2));
            //setting the total number field in locationStat
            locationStat.setTotalNum(latestCases);
            //setting the diffWithPrevDay field of locationStat
            locationStat.setDiffWithPrevDay(latestCases-dayBeforeCases);
            //adding locationStat object to list
            current.add(locationStat);
            this.totalCases += Integer.parseInt(record.get(record.size()-1));
        }
        //filling allStat field with values of current list
        allStat = current;


    }


}
