package com.uapa.software.services;

import java.util.Map;

import com.uapa.software.utils.EnvConfig;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

public class CognitoService {

	private static CognitoIdentityProviderClient cognitoClient;

	public static void setCognitoClient(CognitoIdentityProviderClient client) {
		cognitoClient = client;
	}

	private static CognitoIdentityProviderClient createCognitoClient() {
		if (cognitoClient != null) {
			return cognitoClient; // Usar el cliente mockeado en las pruebas
		}

		AwsBasicCredentials awsCreds = AwsBasicCredentials.create(EnvConfig.get("AWS_ACCESS_KEY_ID"),
				EnvConfig.get("AWS_SECRET_ACCESS_KEY"));

		return CognitoIdentityProviderClient.builder().region(Region.of(EnvConfig.get("AWS_REGION")))
				.credentialsProvider(StaticCredentialsProvider.create(awsCreds)).build();
	}

	public static String authenticate(String username, String password) {
		CognitoIdentityProviderClient client = createCognitoClient();

		InitiateAuthRequest authRequest = InitiateAuthRequest.builder().authFlow(AuthFlowType.USER_PASSWORD_AUTH)
				.clientId(EnvConfig.get("COGNITO_CLIENT_ID"))
				.authParameters(Map.of("USERNAME", username, "PASSWORD", password)).build();

		InitiateAuthResponse authResponse = client.initiateAuth(authRequest);
		return authResponse.authenticationResult().idToken();
	}

	public static String signUp(String username, String password, String email) {
		CognitoIdentityProviderClient client = createCognitoClient();

		SignUpRequest signUpRequest = SignUpRequest.builder().clientId(EnvConfig.get("COGNITO_CLIENT_ID"))
				.username(username).password(password)
				.userAttributes(AttributeType.builder().name("email").value(email).build()).build();

		SignUpResponse signUpResponse = client.signUp(signUpRequest);
		return signUpResponse.userSub();
	}
	
	public static void forgotPassword(String username) {
	    CognitoIdentityProviderClient client = createCognitoClient();

	    ForgotPasswordRequest forgotPasswordRequest = ForgotPasswordRequest.builder()
	            .clientId(EnvConfig.get("COGNITO_CLIENT_ID"))
	            .username(username)
	            .build();

	    ForgotPasswordResponse forgotPasswordResponse = client.forgotPassword(forgotPasswordRequest);
	    System.out.println("Código de verificación enviado a: " +
	            forgotPasswordResponse.codeDeliveryDetails().destination());
	}


}
