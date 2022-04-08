package com.ssmgradle.utils;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;
import software.amazon.awssdk.services.ssm.model.GetParameterResponse;
import software.amazon.awssdk.services.ssm.model.SsmException;

public class SsmUtil {

    private static SsmClient ssmClient;

    public static String getParaValue(final String profile, final String paraName) throws Exception {
        try {
            GetParameterRequest parameterRequest = GetParameterRequest.builder()
                    .name(paraName)
                    .withDecryption(true)
                    .build();
            GetParameterResponse parameterResponse = getSsmClient(profile).getParameter(parameterRequest);
            return parameterResponse.parameter().value();
        } catch (SsmException e) {
            throw new Exception("ERRO");
        }
    }

    private static SsmClient getSsmClient(final String profile) {
        if (ssmClient == null) {
            ssmClient = SsmClient.builder()
                    .credentialsProvider(ProfileCredentialsProvider.create(profile))
                    .build();
        }
        return ssmClient;
    }
}
