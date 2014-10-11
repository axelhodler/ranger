package xorrr.github.io;

import io.dropwizard.Configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RangerConfiguration extends Configuration {
    private String name;

    @JsonProperty
    public String getName() {
        return name;
    }

    @JsonProperty
    public void setName(String name) {
        this.name = name;
    }
}
