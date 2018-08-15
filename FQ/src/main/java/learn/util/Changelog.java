package learn. util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 版本控制 Changelog.txt
 * 
 * @author FengQing
 * 
 */
public class Changelog {
	private static final Logger log = LoggerFactory.getLogger(Changelog.class);

	private static final Changelog changeLog = new Changelog();

	private Changelog() {

	}

	public static Changelog getInstance() {
		return changeLog;
	}

	public String getLatestRelease() {
		String UNKNOWN_VERSION = "Unknown version";

		File changelog = loadChangelog();

		if ((changelog == null) || (!changelog.exists())) {
			return "Unknown version";
		}

		FileReader fr = null;
		try {
			fr = new FileReader(changelog);
		} catch (FileNotFoundException e) {
			log.error("Changelog not exist", e);
		}
		BufferedReader reader = new BufferedReader(fr);

		String verStr = null;
		String line = null;
		while (true) {
			try {
				line = reader.readLine();
			} catch (IOException e) {
				log.error("reading changelog error", e);
				break;
			}

			if (line == null) {
				break;
			}
			if (line.contains("Version")) {
				verStr = line;
				break;
			}
		}
		try {
			reader.close();
		} catch (IOException e) {
			log.warn("closed reading for changelog error", e);
		}

		verStr = getVersion(verStr);

		return verStr == null ? UNKNOWN_VERSION : verStr;
	}

	private File loadChangelog() {
		URL url = getClass().getResource("/Changelog.txt");
		try {
			log.debug("Loading changelog " + url);
			if (url != null) {
				File changelog = new File(url.toURI());
				return changelog;
			}
		} catch (URISyntaxException e) {
			log.error("Loading changelog error", e);
		}

		return null;
	}

	private String getVersion(String ver) {
		if (ver == null) {
			return null;
		}
		ver = ver.replace("\t", "").replace("\r", "").replace("\n", "");
		log.debug("Versiong string: " + ver);

		String[] ss = ver.split(" ");

		int len = ss.length;
		for (int i = 0; i < len; i++) {
			if (("version".equalsIgnoreCase(ss[i])) && (i + 1 < len)) {
				return ss[(i + 1)];
			}
		}

		return null;
	}

	public static void main(String[] args) {
		System.out.println(new Changelog().getLatestRelease());
	}
}
