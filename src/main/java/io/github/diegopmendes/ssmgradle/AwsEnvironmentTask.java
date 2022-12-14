package io.github.diegopmendes.ssmgradle;

import io.github.diegopmendes.ssmgradle.actions.EnvironmentsAction;
import org.gradle.api.DefaultTask;
import org.gradle.api.artifacts.dsl.RepositoryHandler;
import org.gradle.api.provider.MapProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.TaskAction;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.github.diegopmendes.ssmgradle.AwsEnvironmentsPlugin.PLUGIN_EXTENSION_NAME;

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
        PluginExtension pluginExtension = (PluginExtension) getProject().getExtensions().findByName(PLUGIN_EXTENSION_NAME);
        Map<String, String> environments = pluginExtension.getEnvironmentsNames().getOrElse(Map.of());

        if (!getAwsProfile().isPresent()) {
            EnvironmentsAction.execute(DEFAULT_PROFILE, environments);
            System.out.println("Profile used ESSE: " + DEFAULT_PROFILE);
        } else {
            EnvironmentsAction.execute(getAwsProfile().get(), environments);
            System.out.println("Profile used ESSE: " + getAwsProfile().get());
        }
        setEnvironmentsSystem(pluginExtension);
    }

    private void setEnvironmentsSystem(final PluginExtension extension) {
        List<Map<String, String>> repositoriesConfig = extension.getRepositories().getOrElse(List.of());
        for (Map<String, String> repositoryConfig : repositoriesConfig) {
            RepositoryHandler repositories = getProject().getRepositories();
            repositories.add(repositories.maven(repo -> {
                repo.credentials(passwordCredentials -> {
                    passwordCredentials.setUsername(System.getenv(repositoryConfig.get("username")));
                    passwordCredentials.setPassword(System.getenv(repositoryConfig.get("password")));
                });
                repo.setName(repositoryConfig.get("name"));
                repo.setUrl(repositoryConfig.get("url"));
            }));
        }
    }
}

