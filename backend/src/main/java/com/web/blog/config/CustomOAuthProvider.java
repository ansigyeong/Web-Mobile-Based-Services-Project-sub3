// package com.web.blog.config;


// import org.springframework.security.oauth2.client.registration.ClientRegistration;
// import org.springframework.security.oauth2.core.AuthorizationGrantType;
// import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

// /*
// *spring boot에서는 facebook에 대한 OAuth2정보를 기본적으로 제공 
// * but, kakao는 기본적인 정보를 제공하지 않아 해당 정보를 제공하는 class
// */
// public enum CustomOAuthProvider {
//     KAKAO {
//         // @Override
//         public ClientRegistration.Builder getBuilder(String registrationId){
//             ClientRegistration.Builder builder = getBuilder(registrationId, ClientAuthenticationMethod.POST, DEFAULT_LOGIN_REDIRECT_URL);
//             builder.scope("profile");   //요청 권한
//             builder.authorizationUri("https://kauth.kakao.com/oauth/authorize");    //authorization code얻는 api
//             builder.tokenUri("https://kauth.kakao.com/oauth/token");
//             builder.userInfoUri("https://kapi.kakao.com/v2/user/me");
//             builder.userNameAttributeName("id");
//             builder.clientName("Kakao");
//             return builder;
//         }
//     };

//     private static final String DEFAULT_LOGIN_REDIRECT_URL = "{baseUrl}/login/oauth2/code/{registrationId}";

//     protected final ClientRegistration.Builder getBuilder(String registrationId, ClientAuthenticationMethod method, String redirectUri) {
//         ClientRegistration.Builder builder = ClientRegistration.withRegistrationId(registrationId);
//         builder.clientAuthenticationMethod(method);
//         builder.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE);
//         builder.redirectUriTemplate(redirectUri);
        
//         return builder;

//     }

//     public abstract ClientRegistration.Builder getBuilder(String registrationId);
// }