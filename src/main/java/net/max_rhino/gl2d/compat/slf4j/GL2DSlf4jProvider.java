package net.max_rhino.gl2d.compat.slf4j;

import org.slf4j.ILoggerFactory;
import org.slf4j.IMarkerFactory;
import org.slf4j.spi.MDCAdapter;
import org.slf4j.spi.SLF4JServiceProvider;

public class GL2DSlf4jProvider implements SLF4JServiceProvider {

    private ILoggerFactory loggerFactory;

    @Override
    public ILoggerFactory getLoggerFactory() {
        return loggerFactory;
    }

    @Override
    public IMarkerFactory getMarkerFactory() {
        return null;
    }

    @Override
    public MDCAdapter getMDCAdapter() {
        return null;
    }

    @Override
    public String getRequestedApiVersion() {
        return "2.0.13";
    }

    @Override
    public void initialize() {

        this.loggerFactory = new GL2DLoggerFactory();
    }
}
