package com.keyblock.test.mock;

import java.util.HashMap;

/**
 * Mock for IAM
 */
public class IAMMock {
    private HashMap<Integer, UserMock> users = new HashMap<Integer, UserMock>();

    public static final Integer USER_ALWAYS_ADMIN = 1;
    public static final Integer USER_NEVER_ADMIN = 2;
    public static final Integer USER_TO_UPDATE = 3;
    public static final Integer UNKNOWN_USER = 4;
    public static final Integer NO_ADDRESS_USER = 5;

    public static final String ADMIN_CLAIM="isadmin";

    public IAMMock() {
        users.put(USER_ALWAYS_ADMIN, new UserMock(USER_ALWAYS_ADMIN, "0x8433e11fd3c2dc5f9e44558be8fccce8db0fcd1e",true));
        users.put(USER_NEVER_ADMIN, new UserMock(USER_NEVER_ADMIN, "0xB9E9A10Ba80518A5a70893d14CCcB6b5A9343fEF",false));
        users.put(USER_TO_UPDATE, new UserMock(USER_TO_UPDATE, "0x60bDD80B595890E75AA6Bae497dd5d8deaEEFd14",true));
        users.put(UNKNOWN_USER, new UserMock(UNKNOWN_USER, "0x00000000005890E75AA6Bae497dd5d0000000000",true));
        users.put(NO_ADDRESS_USER, new UserMock(NO_ADDRESS_USER, "",true));
    }

    public UserMock getUser(Integer userId) {
        return users.get(userId);
    }
}
