package com.example.springboottutorial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.jwt.crypto.sign.*;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.util.Assert;

/**
 * Created by IntelliJ IDEA.
 * User: treepay
 * Date: 28 September 2018
 * Time: 11:24 AM
 **/
@Configuration
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

    private String clientid = "cyberpunkz";
    private String clientSecret = "javarockstar";
    private String privateKey = "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIEpAIBAAKCAQEAu+/EFazL5PhU+5emyS4mAXrR0z0faWLie/Pfl0KLjjwsppai\n" +
            "npGMe39ZYFggWmYrhLVgaSMaX585xKA2N241uW4FH1OXhW9CPVQP3bhX8bXSrPlE\n" +
            "Sfjs8nNGYo9bFx3UK1NoAcCGDYJZtS+QxqPIom48Dhmrr88naOP1QdSMm5A5/2rr\n" +
            "5AsjNttN5i0chhK4u0Pq4nt6al3p1+/M52tzRaV0ssrHG+sHpS71Zc75a2g7UfoS\n" +
            "oB3oBEGLlCMAfhhfj4nHv2oh9Q6hKvSHjEt6ZfohekYipSOvMTcd/eEJdjggTcpz\n" +
            "PWkYamf7F5izEHmnqP15GikSJEt5MTTkezAxOwIDAQABAoIBAEptP7hdwDmjZvRk\n" +
            "MHcHwaFgZGxFjoUL1inEgM3EWsQvbHdkvG4TshDOdDTFcbqbL6zUr6OzdSRdSGFo\n" +
            "GVCGYtxWh8zKK/15/D+RZN8nvBxLJ229IaAx5pR1tnAd2bMj6vxlgZEAD5aWArTQ\n" +
            "a4NfJXu+MCZdYDK11SOi82Poqu6H1rvoI5veXk/tm8rf+a5lSxjMi/bvHgegbu9J\n" +
            "0SiYv9GMEm8Y/1UJF8MDrYTM9e+GLOzuWMRtGCGLLQgecyz8VDkiIR9FXj/rM3A7\n" +
            "5ZHdzeSxqs49nd7kzwiDkpRXm0cjEkiafFsdr3gEU9oVRnLqs+XlZVtcWKyijg+m\n" +
            "kDvmSyECgYEA9WqP9uU2FFrq99niNTbaPEWgVLn60N6g5m8FEtMpYmLoZx0QskLz\n" +
            "XbdiSEu3w8v79+izwHxq6R89zQX91sfb11ZLRLO7gjKMHNrmYimYeyT3J8iuGFj9\n" +
            "YhfFLo3vp+GP0y9pC0x8E3mtLZlJ6o3GzZqro6hINbMZrLQK6tv3ickCgYEAxAqe\n" +
            "Pc75aduaru8eS3sGcXh2qQC4y+IyuFSwaKkt/9GYvEcKaAlu2QRGOuBsXVQuppvD\n" +
            "iE2ApXcAuG1S/wcaaW9LE1sogOTNF4/rNgmjFGB7GWp+LT/BFOIu5DadIUhKOldx\n" +
            "l7Rh9NiL0FR+ot5/ajdrHJfqVG7f00rzufUq5OMCgYEAlNzrsJ5+fEBd8LOoZBD+\n" +
            "tbqiM2KQgmvYe45yLGYL31MRl6Jow0ibKIiZRDMxp8Gu+ESd7C9MBlZd/WQaAlM+\n" +
            "xdLhWy4Omj4hAUvCnV6P0HqutOIkwD4SjJLQl5me+l13CWnjtOfTB1kyM8ucq3sO\n" +
            "UR716BKmonQeKcUYLjf5YgkCgYAFpQSfZPk7eg4/ITVvGPC7jatk/FdVz4KEWjPm\n" +
            "H5Dqf791FFqRWkwtxXi8M0ALg+P/+hYsM4cTvJRxuvwbybmR9ZlkykglRfE9z3AH\n" +
            "U0m1yX1h4vYVGXPWxRhyi7wEfE9e1Ku5oYD8isujyPsId32VsljMLveZTMP3En5o\n" +
            "CU4d3wKBgQDKSAoYrEUgeHqpCruVGMfcPDRCoWV2grv/IvZAaO7EjSAiBvL0h8dV\n" +
            "8g/uHT7jqcmiweTljstzn1t7x78y3CfVBQcHliGkKaYwZdPeGmVLqFrf2dgtrDQZ\n" +
            "p1ESOd89QjeL09i2+WqYwOKXs2kkSz9hGQ6ri8qxjW3nODaXuE+jLg==\n" +
            "-----END RSA PRIVATE KEY-----";
    private String publicKey = "-----BEGIN PUBLIC KEY-----\n" +
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAu+/EFazL5PhU+5emyS4m\n" +
            "AXrR0z0faWLie/Pfl0KLjjwsppainpGMe39ZYFggWmYrhLVgaSMaX585xKA2N241\n" +
            "uW4FH1OXhW9CPVQP3bhX8bXSrPlESfjs8nNGYo9bFx3UK1NoAcCGDYJZtS+QxqPI\n" +
            "om48Dhmrr88naOP1QdSMm5A5/2rr5AsjNttN5i0chhK4u0Pq4nt6al3p1+/M52tz\n" +
            "RaV0ssrHG+sHpS71Zc75a2g7UfoSoB3oBEGLlCMAfhhfj4nHv2oh9Q6hKvSHjEt6\n" +
            "ZfohekYipSOvMTcd/eEJdjggTcpzPWkYamf7F5izEHmnqP15GikSJEt5MTTkezAx\n" +
            "OwIDAQAB\n" +
            "-----END PUBLIC KEY-----\n";

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Bean
    public JwtAccessTokenConverter tokenEnhancer() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(privateKey);
        converter.setVerifierKey(publicKey);
        return converter;
    }

    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(tokenEnhancer());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore())
                .accessTokenConverter(tokenEnhancer());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient(clientid).secret(clientSecret).scopes("read", "write")
                .authorizedGrantTypes("password", "refresh_token").accessTokenValiditySeconds(20000)
                .refreshTokenValiditySeconds(20000);
    }

}
