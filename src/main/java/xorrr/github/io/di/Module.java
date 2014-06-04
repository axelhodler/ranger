package xorrr.github.io.di;

import xorrr.github.io.frontend.JsonCompliance;
import xorrr.github.io.frontend.ember.EmberCompliance;

import com.google.inject.AbstractModule;

public class Module extends AbstractModule {

    @Override
    protected void configure() {
        bind(JsonCompliance.class).to(EmberCompliance.class);
    }
}
