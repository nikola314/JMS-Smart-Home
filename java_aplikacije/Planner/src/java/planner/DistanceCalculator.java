package planner;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.TravelMode;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nikola
 */
public class DistanceCalculator {

    private static final String API_KEY = ConstsAndPaths.API_KEY;

    public String timeToGetFromHereTo(String to) {
 
            /*
            String systemipaddress = "";
            URL url_name = new URL("http://bot.whatismyipaddress.com");
            BufferedReader sc
                    = new BufferedReader(new InputStreamReader(url_name.openStream()));
            systemipaddress = sc.readLine().trim();
            
            File database = new File(ConstsAndPaths.GEO_DB_PATH);
            DatabaseReader dbReader;
            dbReader = new DatabaseReader.Builder(database).build();
            CityResponse response = dbReader.city(InetAddress.getByName(systemipaddress));
            String cityName = response.getCity().getName();
           */

            return timeToGetFromAToB("etf beograd",to);
 }
    public String timeToGetFromAToB(String from, String to) {
        try {
            GeoApiContext distCalcer = new GeoApiContext.Builder()
                    .apiKey(API_KEY)
                    .build();

            DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(distCalcer);
            DistanceMatrix result = req.origins(from)
                    .destinations(to)
                    .mode(TravelMode.DRIVING)
                    .await();
            if (result == null) {
                return "";
            }
            if (result.rows[0] == null) {
                return "";
            }
            return result.rows[0].elements[0].duration.humanReadable + System.lineSeparator() + result.rows[0].elements[0].distance.humanReadable;
        } catch (ApiException ex) {
            Logger.getLogger(DistanceCalculator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(DistanceCalculator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DistanceCalculator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
        }
        return "Couldnt count!";
    }
}
