package com.paymybuddy.service;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String email, String password);
}