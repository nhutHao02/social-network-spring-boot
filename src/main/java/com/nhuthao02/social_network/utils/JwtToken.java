package com.nhuthao02.social_network.utils;

import com.nhuthao02.social_network.exception.AppException;
import com.nhuthao02.social_network.exception.ErrorCode;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtToken {

    @Value("${jwt.issuer}")
    String issuer;

    @Value("${jwt.secret}")
    String secretKey;

    public String generateToken(String userName) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(userName)
                .issuer(issuer)
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .build();

        Payload payload = new Payload((jwtClaimsSet.toJSONObject()));

        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try {
            jwsObject.sign(new MACSigner(secretKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new AppException(ErrorCode.GENERATE_TOKEN_FAILURE);
        }
    }

    public boolean verifyToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier jwsVerifier = new MACVerifier(secretKey.getBytes());

            if (!signedJWT.verify(jwsVerifier)) {
                return false;
            }

            Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            Date now = new Date();

            return expiryTime != null && expiryTime.after(now);
        } catch (ParseException | JOSEException e) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }
    }

    public String getUsernameFromToken(String token) {
        try {
            if (!verifyToken(token)) {
                throw new AppException(ErrorCode.INVALID_TOKEN);
            }

            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet().getSubject();
        } catch (ParseException e) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }
    }
}
