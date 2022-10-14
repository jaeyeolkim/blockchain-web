package com.okbank.blockchain.domain.user;

public class UserMock {

    public static User buildUser(String name) {
        return User.builder()
                .email(name + "@test.com")
                .name(name)
                .role(Role.USER)
                .build();
    }
}
