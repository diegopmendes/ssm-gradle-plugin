package io.github.diegopmendes.ssmgradle.actions;

import io.github.diegopmendes.ssmgradle.utils.SsmUtil;
import io.github.diegopmendes.ssmgradle.utils.SystemUtil;

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
            System.out.println("#### USED VARIABLES: " + System.getenv());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
