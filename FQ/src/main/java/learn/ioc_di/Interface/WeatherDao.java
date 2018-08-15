package learn.ioc_di.Interface;

import java.util.Date;

import learn.ioc_di.WeatherData;

/**
 * Data Access Object Interface for getting and storing weather records
 * http://www.javastar.org
 */
public interface WeatherDao {

    /**
     * Returns the WeatherData for a date, or null if there is none
     *
     * @param date
     *        the date to search on
     * @return
     */
    WeatherData find(Date date);

    /**
     * Saves the WeatherData for a date
     *
     * @param date
     * @return
     */
    WeatherData save(Date date);

    WeatherData update(Date date);
}