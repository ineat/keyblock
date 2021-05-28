package com.keyblock.test.mock;

public class UserMock {
    private Integer id;
    private String userAddress;
    private Boolean isAdmin;

    public UserMock(Integer id, String userAddress, Boolean isAdmin) {
        this.id = id;
        this.userAddress = userAddress;
        this.isAdmin = isAdmin;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }
}
