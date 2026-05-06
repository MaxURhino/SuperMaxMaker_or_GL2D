package net.max_rhino.gl2d;

public class GL2D {
    public static int booleanToInt(boolean bool) {
        if (bool) return 1;
        else return 0;
    }

    public static String toPascalCase(String text) {
        if (text == null || text.isBlank()) {
            return "";
        }

        StringBuilder builder = new StringBuilder();

        for (String part : text.trim().split("[\\s_\\-]+")) {
            if (part.isEmpty()) {
                continue;
            }

            builder.append(Character.toUpperCase(part.charAt(0)));

            if (part.length() > 1) {
                builder.append(part.substring(1).toLowerCase());
            }
        }

        return builder.toString();
    }
}
