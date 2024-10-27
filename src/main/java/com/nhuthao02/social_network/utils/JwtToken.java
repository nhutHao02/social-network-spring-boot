package com.nhuthao02.social_network.utils;

import com.nhuthao02.social_network.exception.AppException;
import com.nhuthao02.social_network.exception.ErrorCode;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtToken {

    @Value("${jwt.issuer}")
    String issuer;

    @Value("${jwt.secret}")
    String secretKey;

    public String generateToken(String userName, String userId) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(userName)
                .issuer(issuer)
                .issueTime(new Date())
                .claim("id", userId)
//                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.DAYS).toEpochMilli()))
                .expirationTime(Date.from(LocalDateTime.now().plusMonths(1).atZone(ZoneId.systemDefault()).toInstant()))
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

    public String getIdFromToken(String token) {
        try {
            if (!verifyToken(token)) {
                throw new AppException(ErrorCode.INVALID_TOKEN);
            }

            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet().getStringClaim("id");
        } catch (ParseException e) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }
    }

    public String getBearToken(HttpServletRequest servletRequest) {
        String authorizationHeader = servletRequest.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }
        return authorizationHeader.substring(7);
    }
}
