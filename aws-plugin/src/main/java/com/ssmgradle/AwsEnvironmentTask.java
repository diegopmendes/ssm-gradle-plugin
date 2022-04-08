package com.ssmgradle;

import com.ssmgradle.actions.EnvironmentsAction;
import org.gradle.api.DefaultTask;
import org.gradle.api.provider.MapProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.TaskAction;

import java.util.HashSet;
import java.util.Set;

abstract public class AwsEnvironmentTask extends DefaultTask {

    private static final String DEFAULT_PROFILE = "default";
    private static final Set<String> DEFAULT_ENVIRONMENTS = new HashSet<>();

    @Input
    @Optional
    abstract public Property<String> getAwsProfile();

    @Input
    @Optional
    abstract public MapProperty<String, String> getEnvironmentsNames();

    @TaskAction
    public void execute() {
        if (!getAwsProfile().isPresent()) {
            EnvironmentsAction.execute(DEFAULT_PROFILE, getEnvironmentsNames().get());
            System.out.println("Profile used: " + DEFAULT_PROFILE);
        } else {
            getAwsProfile().get();
            EnvironmentsAction.execute(getAwsProfile().get(), getEnvironmentsNames().get());
            System.out.println("Profile used: " + getAwsProfile().get());
        }
    }
}

