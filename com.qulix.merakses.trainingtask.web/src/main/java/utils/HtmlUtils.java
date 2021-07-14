package utils;

public class HtmlUtils {
    public String escapeHtml(String source) {
        return source.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#039;");
    }
}
