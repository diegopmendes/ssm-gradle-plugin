package io.github.diegopmendes.ssmgradle;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class AwsEnvironmentsPlugin implements Plugin<Project> {

    public static final String PLUGIN_EXTENSION_NAME = "environments";
    private static final String CI_NAME = "importEnvironments";

    @Override
    public void apply(final Project project) {
        PluginExtension extension = project.getExtensions().create(PLUGIN_EXTENSION_NAME, PluginExtension.class);
        project.getTasks().register(CI_NAME, AwsEnvironmentTask.class);

        project.getTasks().named("compileJava", task -> {
            task.dependsOn(CI_NAME);
            System.out.println("Task :COMPILE_JAVA NOVO 2 executed.");
        });

        project.getTasks().named("prepareKotlinBuildScriptModel", task -> {
            task.dependsOn(CI_NAME);
            System.out.println("Task :COMPILE_JAVA executed.");
        });
    }
//
//    private void importEnvironments(final PluginExtension pluginExtension, final Project project) {
//        try {
//            for (String awsEnvironmentKey : pluginExtension.getEnvironmentsNames().get().keySet()) {
//                String jvmEnvironmentKey = pluginExtension.getEnvironmentsNames().get().get(awsEnvironmentKey);
//                String awsEnvironmentValue = SsmUtil.getParaValue(pluginExtension.getAwsProfile().get(), awsEnvironmentKey);
//                project.getExtensions().getExtraProperties().set(jvmEnvironmentKey, awsEnvironmentValue);
//                System.out.println(String.format("#### SET ENV: %s -> %s ", awsEnvironmentKey, jvmEnvironmentKey));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
