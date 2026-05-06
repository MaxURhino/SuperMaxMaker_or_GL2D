package net.max_rhino.gl2d.compat.slf4j;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.event.Level;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.stream.Collectors;

public class GL2DLogger implements Logger {

    private final String name;

    public GL2DLogger(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean isTraceEnabled() {
        return true;
    }

    @Override
    public void trace(String s) {

    }

    @Override
    public void trace(String s, Object o) {

    }

    @Override
    public void trace(String s, Object o, Object o1) {

    }

    @Override
    public void trace(String s, Object... objects) {

    }

    @Override
    public void trace(String s, Throwable throwable) {

    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        return false;
    }

    @Override
    public void trace(Marker marker, String s) {

    }

    @Override
    public void trace(Marker marker, String s, Object o) {

    }

    @Override
    public void trace(Marker marker, String s, Object o, Object o1) {

    }

    @Override
    public void trace(Marker marker, String s, Object... objects) {

    }

    @Override
    public void trace(Marker marker, String s, Throwable throwable) {

    }

    @Override
    public boolean isDebugEnabled() {
        return false;
    }

    @Override
    public void debug(String s) {

    }

    @Override
    public void debug(String s, Object o) {

    }

    @Override
    public void debug(String s, Object o, Object o1) {

    }

    @Override
    public void debug(String s, Object... objects) {

    }

    @Override
    public void debug(String s, Throwable throwable) {

    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return false;
    }

    @Override
    public void debug(Marker marker, String s) {

    }

    @Override
    public void debug(Marker marker, String s, Object o) {

    }

    @Override
    public void debug(Marker marker, String s, Object o, Object o1) {

    }

    @Override
    public void debug(Marker marker, String s, Object... objects) {

    }

    @Override
    public void debug(Marker marker, String s, Throwable throwable) {

    }

    @Override
    public boolean isInfoEnabled() {
        return true;
    }

    @Override
    public void info(String s) {
        log(Level.INFO, s);
    }

    @Override
    public void info(String s, Object o) {
        log(Level.INFO, s, o);
    }

    @Override
    public void info(String s, Object o, Object o1) {
        log(Level.INFO, s, o, o1);
    }

    @Override
    public void info(String s, Object... objects) {
        log(Level.INFO, s, objects);
    }

    @Override
    public void info(String s, Throwable throwable) {
        log(Level.INFO, throwable, s);
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return true;
    }

    @Override
    public void info(Marker marker, String s) {
        log(Level.INFO, marker, s);
    }

    @Override
    public void info(Marker marker, String s, Object o) {
        log(Level.INFO, marker, s, o);
    }

    @Override
    public void info(Marker marker, String s, Object o, Object o1) {
        log(Level.INFO, marker, s, o, o1);
    }

    @Override
    public void info(Marker marker, String s, Object... objects) {
        log(Level.INFO, marker, s, objects);
    }

    @Override
    public void info(Marker marker, String s, Throwable throwable) {
        log(Level.INFO, throwable, marker, s);
    }

    @Override
    public boolean isWarnEnabled() {
        return true;
    }

    @Override
    public void warn(String s) {
        log(Level.WARN, s);
    }

    @Override
    public void warn(String s, Object o) {
        log(Level.WARN, s, o);
    }

    @Override
    public void warn(String s, Object... objects) {
        log(Level.WARN, s, objects);
    }

    @Override
    public void warn(String s, Object o, Object o1) {
        log(Level.WARN, s, o, o1);
    }

    @Override
    public void warn(String s, Throwable throwable) {
        log(Level.WARN, throwable, s);
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return true;
    }

    @Override
    public void warn(Marker marker, String s) {
        log(Level.WARN, marker, s);
    }

    @Override
    public void warn(Marker marker, String s, Object o) {
        log(Level.WARN, marker, s, o);
    }

    @Override
    public void warn(Marker marker, String s, Object o, Object o1) {
        log(Level.WARN, marker, s, o, o1);
    }

    @Override
    public void warn(Marker marker, String s, Object... objects) {
        log(Level.WARN, marker, s, objects);
    }

    @Override
    public void warn(Marker marker, String s, Throwable throwable) {
        log(Level.WARN, throwable, marker, s);
    }

    @Override
    public boolean isErrorEnabled() {
        return true;
    }

    @Override
    public void error(String s) {
        log(Level.ERROR, s);
    }

    @Override
    public void error(String s, Object o) {
        log(Level.ERROR, s, o);
    }

    @Override
    public void error(String s, Object o, Object o1) {
        log(Level.ERROR, s, o, o1);
    }

    @Override
    public void error(String s, Object... objects) {
        log(Level.ERROR, s, objects);
    }

    @Override
    public void error(String s, Throwable throwable) {
        log(Level.ERROR, throwable, s);
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return true;
    }

    @Override
    public void error(Marker marker, String s) {
        log(Level.ERROR, marker, s);
    }

    @Override
    public void error(Marker marker, String s, Object o) {
        log(Level.ERROR, marker, s, o);
    }

    @Override
    public void error(Marker marker, String s, Object o, Object o1) {
        log(Level.ERROR, marker, s, o, o1);
    }

    @Override
    public void error(Marker marker, String s, Object... objects) {
        log(Level.ERROR, marker, s, objects);
    }

    @Override
    public void error(Marker marker, String s, Throwable throwable) {
        log(Level.ERROR, throwable, marker, s);
    }

    private void log(@NotNull Level level, @Nullable Throwable t, Object... s) {
        LocalTime time = LocalTime.now();

        String hour = String.format("%02d", time.getHour());
        String minute = String.format("%02d", time.getMinute());
        String second = String.format("%02d", time.getSecond());

        String levelColor = switch (level) {
            case WARN -> ConsoleColors.YELLOW;
            case ERROR -> ConsoleColors.RED;
            default -> ConsoleColors.GREEN;
        };

        System.out.println(
                ConsoleColors.BLUE + "[" + hour + ":" + minute + ":" + second +
                        "] " + levelColor + "[main/" + level + "] " + ConsoleColors.CYAN + "(" +
                        this.name + ") " + ConsoleColors.RESET +
                        Arrays.stream(s)
                                .map(String::valueOf)
                                .collect(Collectors.joining(" "))
        );

        if (t != null) {
            log(Level.ERROR, t.getLocalizedMessage());
        }
    }

    private void log(Level level, Object... s) {
        log(level, null, s);
    }
}
