package learn.ioc_di.impl;

import java.util.Date;

import org.springframework.stereotype.Repository;

import learn.ioc_di.WeatherData;
import learn.ioc_di.Interface.WeatherDao;

/**
 * Implementation of WeatherDao http://www.javastar.org
 */
@Repository
public class StaticDataWeatherDaoImpl implements WeatherDao {

    //	 private JdbcTemplate jdbcT = (JdbcTemplate) SpringUtil.getBean("jdbcTemplate");

    @Override
    public WeatherData find(Date date) {

        WeatherData wd = new WeatherData();
        wd.setDate((Date) date.clone());
        // some bogus values
        wd.setLow(date.getMonth() + 5);
        wd.setHigh(date.getMonth() + 15);
        return wd;
    }

    @Override
    public WeatherData save(Date date) {
        throw new UnsupportedOperationException(
            "This class uses static data only");
    }

    /*
     * (non-Javadoc)
     * @see ch02.sample1.WeatherDao#update(java.util.Date)
     */
    @Override
    public WeatherData update(Date date) {
        throw new UnsupportedOperationException(
            "This class uses static data only");
    }
}