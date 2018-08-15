package learn.ioc_di;

import java.util.Date;

/**
 * Represents a daily weather record http://www.javastar.org
 */
public class WeatherData {

    Date date;

    double low;

    double high;

    /**
     * @return Returns the date.
     */
    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return Returns the low.
     */
    public double getLow() {
        return this.low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    /**
     * @return Returns the high.
     */
    public double getHigh() {
        return this.high;
    }

    public void setHigh(double high) {
        this.high = high;
    }
}