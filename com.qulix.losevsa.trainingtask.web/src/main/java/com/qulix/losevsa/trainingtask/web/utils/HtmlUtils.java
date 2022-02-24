package com.qulix.losevsa.trainingtask.web.utils;

/**
 * HTML utils using for escaping HTML.
 */
public class HtmlUtils {

    /**
     * Escapes the characters in a {@code String} using HTML entities.
     *
     * @param source the source string to escape
     * @return a new escaped String, null if null string input
     */
    public String escapeHtml(String source) {
        return source.replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")
            .replace("'", "&#039;");
    }
}
