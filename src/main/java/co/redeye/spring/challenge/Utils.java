package co.redeye.spring.challenge;

/**
 * Helper functions.
 */
public class Utils {
    /**
     * Checks that a string value is non-null and non-empty
     *
     * @param string The String to check.
     * @return True if String is non-null and has a length >= 1
     */
    public static boolean stringPresent(String string) {
        return string != null && !string.isEmpty();
    }
}
