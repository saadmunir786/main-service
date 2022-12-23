package com.exampleMainService.utils.entities;

import com.exampleMainService.constants.constants;
import de.taimos.totp.TOTP;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;

import java.security.SecureRandom;

public class AuthenticatorUtils {
    public String makeAuthenticationString(String email, String secret){
        String generatedString = "otpauth://totp/"+ constants.ISSUER+":"+email+"?secret="+secret+"&issuer="
                +constants.ISSUER+"&algorithm=SHA1&digits="
                +constants.VERIFICATION_CODE_LENGTH+"&period="+constants.VALIDITY;
        return generatedString;
    }
    public String generateSecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        Base32 base32 = new Base32();
        return base32.encodeToString(bytes);
    }
    public String getTOTPCode(String secretKey) {
        Base32 base32 = new Base32();
        byte[] bytes = base32.decode(secretKey);
        String hexKey = Hex.encodeHexString(bytes);
        return TOTP.getOTP(hexKey);
    }
}
