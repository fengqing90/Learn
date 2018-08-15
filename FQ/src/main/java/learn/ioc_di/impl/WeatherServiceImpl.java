package learn.ioc_di.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import learn.aop.RecordLog;
import learn.ioc_di.WeatherData;
import learn.ioc_di.Interface.WeatherDao;
import learn.ioc_di.Interface.WeatherService;

@Service
@Transactional
public class WeatherServiceImpl implements WeatherService {
    @Autowired
    private WeatherDao weatherDao;

    // set注入
    //	@Resource
    //	public void setWeatherDao(WeatherDao weatherDao) {
    //		this.weatherDao = weatherDao;
    //	}

    // 构造注入 2种写法
    // 构造注入写法1:
    // public WeatherServiceImpl(){};
    // @Autowired
    // public WeatherServiceImpl(WeatherDao weatherDao) {
    // this.weatherDao = weatherDao;
    // }

    // 构造注入写法2:
    // @Resource
    // private WeatherDao weatherDao;
    // public WeatherServiceImpl(){};
    // public WeatherServiceImpl(WeatherDao weatherDao) {
    // this.weatherDao = weatherDao;
    // }
    //

    // @Resource
    // public void setWeatherDao(WeatherDao weatherDao) {
    // this.weatherDao = weatherDao;
    // }

    @RecordLog
    @Override
    public Double getHistoricalHigh(Date date) {
        System.out.println("getHistoricalHigh(Date date)");
        WeatherData wd = this.weatherDao.find(date);
        if (wd != null) {
            return new Double(wd.getHigh());
        }
        return null;
    }

}
