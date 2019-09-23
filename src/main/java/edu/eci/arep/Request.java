package edu.eci.arep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;

public class Request implements Callable {
    private URL url;
    private int succed;
    private int numberOfRequests;
    public Request(String url,int numberOfRequests) throws MalformedURLException {
        this.url=new URL(url);
        this.numberOfRequests=numberOfRequests;
    }


    @Override
    public Object call() throws Exception {
        for(int i=0;i<numberOfRequests;i++ ) {
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream()))) {

                succed++;
            } catch (IOException x) {

            }
        }
        return succed;
    }
}
