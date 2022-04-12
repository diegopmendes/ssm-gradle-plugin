package io.github.diegopmendes.ssmgradle;

import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.MapProperty;
import org.gradle.api.provider.Property;

import java.util.Map;

abstract public class PluginExtension {

    abstract public Property<String> getAwsProfile();

    abstract public MapProperty<String, String> getEnvironmentsNames();

    abstract public ListProperty<Map<String, String>> getRepositories();
}
