package com.harium.api.geolocation;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;

public class GeoLocationAPI {

    private static final String BASE_URL = "https://geolocation.com/en_US?ip=";
    private static final int TIMEOUT = 10000;

    private static final String COUNTRY = "Country";
    private static final String REGION = "Region";
    private static final String CITY = "City";
    private static final String ZIP = "ZIP or Postal Code";
    private static final String LATITUDE = "Latitude";
    private static final String LONGITUDE = "Longitude";

    public static Place getData(String ip) throws IOException {
        return getData(ip, TIMEOUT);
    }

    /**
     *
     * @param ip - IPv4
     * @return the place
     */
    public static Place getData(String ip, int timeout) throws IOException {
        Place place = new Place();

        URL url = new URL(BASE_URL + "/" + ip);
        Document document = Jsoup.parse(url, timeout);
        Elements elements = document.select(".table-ip tbody tr td");

        for (Element element : elements) {
            String text = element.text();

            if (text.startsWith(COUNTRY)) {
                place.country = text.substring(COUNTRY.length() + 1);
            } else if (text.startsWith(REGION)) {
                place.region = text.substring(REGION.length() + 1);
            } else if (text.startsWith(CITY)) {
                place.city = text.substring(CITY.length() + 1);
            } else if (text.startsWith(ZIP)) {
                place.zipCode = text.substring(ZIP.length() + 1);
            } else if (text.startsWith(LATITUDE)) {
                place.latitude = text.substring(LATITUDE.length() + 1);
            } else if (text.startsWith(LONGITUDE)) {
                place.longitude = text.substring(LONGITUDE.length() + 1);
            }
        }

        return place;
    }

}
