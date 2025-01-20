package com.application.payment.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class FeignClientInterceptor  implements RequestInterceptor {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_TOKEN_TYPE = "Bearer ";
    private final OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager;

    public FeignClientInterceptor(OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager) {
        this.oAuth2AuthorizedClientManager = oAuth2AuthorizedClientManager;
    }

    // Intercept the feign client calls
    // and add the Bearer token to the hearer

    @Override
    public void apply(RequestTemplate requestTemplate) {

        String token = oAuth2AuthorizedClientManager
                .authorize(OAuth2AuthorizeRequest
                        .withClientRegistrationId("my-internal-client")
                        .principal("internal")
                        .build())
                .getAccessToken()
                .getTokenValue();

        requestTemplate.header(AUTHORIZATION_HEADER, BEARER_TOKEN_TYPE + token);
    }
}
