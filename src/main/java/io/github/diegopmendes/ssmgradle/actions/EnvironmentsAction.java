package io.github.diegopmendes.ssmgradle.actions;

import io.github.diegopmendes.ssmgradle.utils.SsmUtil;
import io.github.diegopmendes.ssmgradle.utils.SystemUtil;
import org.gradle.api.Project;

import java.util.Map;

public class EnvironmentsAction {
    public static void execute(final String awsProfile, final Map<String, String> environmentsParameterNames) {
        try {
            for (String awsEnvironmentKey : environmentsParameterNames.keySet()) {
                String jvmEnvironmentKey = environmentsParameterNames.get(awsEnvironmentKey);
                String awsEnvironmentValue = SsmUtil.getParaValue(awsProfile, awsEnvironmentKey);
                SystemUtil.setEnv(jvmEnvironmentKey, awsEnvironmentValue);
                System.out.println(String.format("#### SET ENV: %s -> %s ", awsEnvironmentKey, jvmEnvironmentKey));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
