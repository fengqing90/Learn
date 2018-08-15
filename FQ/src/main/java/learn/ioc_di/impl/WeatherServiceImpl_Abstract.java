package learn.ioc_di.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import learn.ioc_di.WeatherData;
import learn.ioc_di.Interface.WeatherDao;

@Service
@Transactional
public abstract class WeatherServiceImpl_Abstract {

    @Autowired
    protected abstract WeatherDao getWeatherDao();

    public Double getHistoricalHigh(Date date) {
        WeatherData wd = this.getWeatherDao().find(date);
        if (wd != null) {
            return new Double(wd.getHigh());
        }
        return null;
    }
}
