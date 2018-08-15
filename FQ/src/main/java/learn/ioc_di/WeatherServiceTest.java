package learn.ioc_di;

import java.util.GregorianCalendar;

import learn.ioc_di.Interface.WeatherService;
import learn.util.SpringUtil;

import org.junit.Test;

public class WeatherServiceTest {
    @Test
    public void testSample1() {
        //普通
        //		WeatherService_Class ws = new WeatherService_Class();

        WeatherService ws = (WeatherService) SpringUtil
            .getBean("WeatherServiceImpl");

        Double high = ws.getHistoricalHigh(new GregorianCalendar(2004, 0, 1)
            .getTime());
        // ... do more validation of returned value here, this test is not
        // realistic
        System.out.println("High was: " + high);
    }
}
