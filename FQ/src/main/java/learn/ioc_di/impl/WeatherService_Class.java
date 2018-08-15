package learn.ioc_di.impl;

import java.util.Date;

import learn.ioc_di.WeatherData;
import learn.ioc_di.Interface.WeatherDao;

/**
 * http://www.javastar.org
 */
public class WeatherService_Class {
    WeatherDao weatherDao = new StaticDataWeatherDaoImpl();

    public Double getHistoricalHigh(Date date) {
        WeatherData wd = this.weatherDao.find(date);
        if (wd != null) {
            return new Double(wd.getHigh());
        }
        return null;
    }
}