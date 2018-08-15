package learn.log.classify;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NamedHierarchy {
    private static final Logger logger = LogManager
        .getLogger(NamedHierarchy.class);

    public static void main(String[] args) {
        String nh = "Named Hierarchy";
        NamedHierarchy.logger.getLevel();
        NamedHierarchy.logger
            .trace("TRACE: " + nh + " " + NamedHierarchy.logger.getLevel());
        NamedHierarchy n = new NamedHierarchy();
        n.run();
        NamedHierarchy.logger
            .error("ERROR: " + nh + " " + NamedHierarchy.logger.getLevel());

    }

    public void run() {
        String nh = "NamedHierarchy.run";
        NamedHierarchy.logger
            .debug("DEBUG: " + nh + " " + NamedHierarchy.logger.getLevel());
    }
}
