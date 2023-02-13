package com.hunt.springeshop.service;

import com.hunt.springeshop.domain.User;

public interface MailSenderService {
    void sendActivateCode(User user);
}
