package tech.marechal;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class StringFormatUtils {

	private static final int NUMBER_OF_COLUMNS = 80;
	private static final String STAR = "*";
	private static final String PIPE = "|";
	private static final int LENGTH_MAX_KEY = 80;
	private static final int LENGTH_MAX_VALUE = 80;
	private static final String[] CONFIDENTIAL_WORDS = { "secret", "password" };
	private static final String SECRET = "*****";

	private StringFormatUtils() {
	}

	/**
	 * return a framed String with the title centered.
	 *
	 * @param string
	 * @return
	 */
	public static String titleInFrame(final String title, final boolean startWithNewLine) {
		final StringBuilder result = new StringBuilder();
		if (startWithNewLine) {
			result.append(System.lineSeparator());
		}
		final String centeredTitle = StringUtils.center(title, NUMBER_OF_COLUMNS - 2);
		result.append(StringUtils.repeat(STAR, NUMBER_OF_COLUMNS)).append(System.lineSeparator())
				.append(STAR).append(centeredTitle).append(STAR).append(System.lineSeparator())
				.append(StringUtils.repeat(STAR, NUMBER_OF_COLUMNS)).append(System.lineSeparator());
		return result.toString();
	}

	/**
	 * This method returns <code>true</code> if the key is used to store
	 * confidential value.
	 *
	 * @return <code>true</code> if the key is used to store confidential value.
	 */
	public static boolean containsSecret(final String key) {
		int c = 0;
		boolean result = false;
		do {
			result = StringUtils.containsIgnoreCase(key, CONFIDENTIAL_WORDS[c]);
			c++;
		} while (!result && c < CONFIDENTIAL_WORDS.length);
		return result;
	}

	public static String printProperties(final Properties properties, final boolean startWithNewLine) {
		final Map<String, String> map = properties.entrySet().stream().collect(Collectors
				.toMap(k -> String.valueOf(k.getKey()), k -> String.valueOf(k.getValue())));
		return printMap(map, startWithNewLine);

	}

	public static <K, V> String printMap(final Map<K, V> map, final boolean startWithNewLine) {
		final StringBuilder result = new StringBuilder();
		final Set<Entry<K, V>> entries = map.entrySet();
		String tmpKey = "";
		String tmpValue = "";
		final String ls = System.lineSeparator();
		if (startWithNewLine) {
			result.append(ls);
		}
		result.append("+").append(StringUtils.repeat("-", LENGTH_MAX_KEY)).append("+").append(StringUtils.repeat("-", LENGTH_MAX_VALUE)).append("+").append(ls);
		result.append(PIPE).append(StringUtils.center("KEY", LENGTH_MAX_KEY)).append(PIPE).append(StringUtils.center("VALUE", LENGTH_MAX_VALUE))
				.append(PIPE).append(ls);
		result.append("+").append(StringUtils.repeat("-", LENGTH_MAX_KEY)).append("+").append(StringUtils.repeat("-", LENGTH_MAX_VALUE)).append("+").append(ls);
		for (final Entry<K, V> entry : entries) {
			tmpKey = String.valueOf(entry.getKey());
			if (containsSecret(tmpKey)) {
				tmpValue = SECRET;
			} else {
				tmpValue = String.valueOf(entry.getValue());
			}
			if (StringUtils.length(tmpKey) > LENGTH_MAX_KEY) {
				tmpKey = StringUtils.abbreviate(tmpKey, LENGTH_MAX_KEY);
			} else {
				tmpKey = StringUtils.rightPad(tmpKey, LENGTH_MAX_KEY);
			}
			if (StringUtils.length(tmpValue) > LENGTH_MAX_VALUE) {
				tmpValue = StringUtils.abbreviate(tmpValue, LENGTH_MAX_VALUE);
			} else {
				tmpValue = StringUtils.rightPad(tmpValue, LENGTH_MAX_VALUE);
			}
			result.append(PIPE).append(tmpKey).append(PIPE).append(tmpValue).append(PIPE).append(ls);
		}
		return result.toString();
	}
}
