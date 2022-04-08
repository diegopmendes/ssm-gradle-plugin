package com.ssmgradle;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class AwsEnvironmentsPlugin implements Plugin<Project> {

    private static final String CI_NAME = "importEnvironments";

    @Override
    public void apply(Project project) {
        project.getTasks().register(CI_NAME, AwsEnvironmentTask.class);
        System.out.println(String.format("Task :%s registered.", CI_NAME));

        project.getTasks().named("build", task -> {
            task.dependsOn(CI_NAME);
            System.out.println("Task :BUILD executed.");
        });

        project.getTasks().named("prepareKotlinBuildScriptModel", task -> {
            task.dependsOn(CI_NAME);
            System.out.println("Task :PREPARE_KOTLIN_BUILD_SCRIPT_MODEL executed.");
        });
    }
}
