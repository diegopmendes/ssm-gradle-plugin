package io.github.diegopmendes.ssmgradle.actions;

import org.gradle.api.provider.MapProperty;
import org.gradle.api.provider.Property;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class EnvironmentsActionTest {

    @Test
    public void testWithForceUpdate() {
        Map<String, String> parameters = Map.of("/CodeBuild/GIT_PKG_USER", "GIT_PKG_USER", "/CodeBuild/GIT_PKG_TOKEN", "GIT_PKG_TOKEN");
        MapProperty<String, String> parameterProperty = mock(MapProperty.class);
        Property<String> awsProfile = mock(Property.class);

        Mockito.doReturn(parameters).when(parameterProperty).get();
        Mockito.doReturn("default").when(awsProfile).get();
        Mockito.doReturn(true).when(parameterProperty).isPresent();
        Mockito.doReturn(true).when(awsProfile).isPresent();

        EnvironmentsAction.execute(awsProfile.get(), parameterProperty.get());
        Map<String, String> environments = System.getenv();
        for (String awsKey : parameters.keySet()) {
            String jvmKey = parameters.get(awsKey);
            assertTrue(environments.containsKey(jvmKey));
        }
    }
}