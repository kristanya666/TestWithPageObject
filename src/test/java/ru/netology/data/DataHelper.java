package ru.netology.data;

import lombok.Value;

public class DataHelper {
    private DataHelper() {}

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCode(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }

    @Value
    public static class CardNumberFirst {
        private String number;
    }

    public static CardNumberFirst getCardNumberFirst() {
        return new CardNumberFirst("5559 0000 0000 0001");
    }

    @Value
    public static class CardNumberSecond {
        private String number;
    }

    public static CardNumberSecond getCardNumberSecond() {
        return new CardNumberSecond("5559 0000 0000 0002");
    }
}
