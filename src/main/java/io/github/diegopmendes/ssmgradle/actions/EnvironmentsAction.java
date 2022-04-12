package io.github.diegopmendes.ssmgradle.actions;

import io.github.diegopmendes.ssmgradle.utils.SsmUtil;
import io.github.diegopmendes.ssmgradle.utils.SystemUtil;

import java.util.Map;

public class EnvironmentsAction {
    public static void execute(final String awsProfile, final Map<String, String> environmentsParameterNames) {
        try {
            for (String awsEnvironmentKey : environmentsParameterNames.keySet()) {
                String jvmEnvironmentKey = environmentsParameterNames.get(awsEnvironmentKey);
                if (System.getenv(jvmEnvironmentKey) == null || System.getenv(jvmEnvironmentKey).isEmpty()) {
                    String awsEnvironmentValue = SsmUtil.getParaValue(awsProfile, awsEnvironmentKey);
                    SystemUtil.setEnv(jvmEnvironmentKey, awsEnvironmentValue);
                    System.out.println(String.format("#### SET ENV: %s -> %s ", awsEnvironmentKey, jvmEnvironmentKey));
                } else {
                    System.out.println(String.format("#### The ENV: %s already exists", jvmEnvironmentKey));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
