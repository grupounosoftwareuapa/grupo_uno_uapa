package com.uapa.software;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.uapa.software.services.CognitoService;

import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

public class CognitoServiceTest {

	@Mock
	private CognitoIdentityProviderClient cognitoClientMock;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		// Mockear el cliente en la clase CognitoService
		CognitoService.setCognitoClient(cognitoClientMock);
	}

	@Test
	void testSignUp() {
		// Crear una respuesta mockeada
		SignUpResponse mockResponse = SignUpResponse.builder().userSub("mockUserSub").build();

		// Configurar el comportamiento del mock
		when(cognitoClientMock.signUp(any(SignUpRequest.class))).thenReturn(mockResponse);

		// Llamar al método a probar
		String result = CognitoService.signUp("testUser", "testPass", "test@example.com");

		// Verificar el resultado
		assertEquals("mockUserSub", result);

		// Verificar que el cliente mock fue llamado correctamente
		verify(cognitoClientMock, times(1)).signUp(any(SignUpRequest.class));
	}

	
	@Test
    void testAuthenticate() {
        // Crear una respuesta mockeada
        InitiateAuthResponse mockResponse = InitiateAuthResponse.builder()
                .authenticationResult(AuthenticationResultType.builder().idToken("mockIdToken").build())
                .build();

        // Configurar el comportamiento del mock
        when(cognitoClientMock.initiateAuth(any(InitiateAuthRequest.class))).thenReturn(mockResponse);

        // Llamar al método a probar
        String token = CognitoService.authenticate("testUser", "testPass");

        // Verificar el resultado
        assertEquals("mockIdToken", token);

        // Verificar que el cliente mock fue llamado correctamente
        verify(cognitoClientMock, times(1)).initiateAuth(any(InitiateAuthRequest.class));
    }
	
	 @Test
	    void testForgotPassword() {
	        // Mockear la respuesta
	        ForgotPasswordResponse mockResponse = ForgotPasswordResponse.builder()
	                .codeDeliveryDetails(CodeDeliveryDetailsType.builder()
	                        .destination("test@example.com")
	                        .build())
	                .build();

	        // Configurar el comportamiento del mock
	        when(cognitoClientMock.forgotPassword(any(ForgotPasswordRequest.class))).thenReturn(mockResponse);

	        // Llamar al método a probar
	        CognitoService.forgotPassword("testUser");

	        // Verificar que el cliente mock fue llamado correctamente
	        verify(cognitoClientMock, times(1)).forgotPassword(any(ForgotPasswordRequest.class));
	    }
}
