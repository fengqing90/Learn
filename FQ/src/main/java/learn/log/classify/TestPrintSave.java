package learn.log.classify;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import learn.util.DateTimeUtils;

public class TestPrintSave {
    static final Logger logger = LoggerFactory.getLogger(TestPrintSave.class);

    public TestPrintSave() {
        this.log_Debug();
        this.log_Info();
        this.log_Warn();
        this.log_Error();
        this.log_Trace();
    }

    public void log_Debug() {
        TestPrintSave.logger.debug("log_Debug():"
            + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
    }

    public void log_Info() {
        TestPrintSave.logger
            .info("log_Info():" + DateTimeUtils.validateDateTime(new Date()));
    }

    public void log_Warn() {
        TestPrintSave.logger
            .warn("log_Warn():" + DateTimeUtils.validateDateTime(new Date()));
    }

    public void log_Error() {
        TestPrintSave.logger
            .error("log_Error():" + DateTimeUtils.validateDateTime(new Date()));
    }

    public void log_Trace() {
        TestPrintSave.logger
            .trace("log_Trace():" + DateTimeUtils.validateDateTime(new Date()));
    }

    public static void main(String[] args) {
        new TestPrintSave();
    }
}
