package com.icm.tiremanagementapi.repositories;

import java.io.File;

public interface IEmailRepository {
    void sendEmail(String[] toUser, String subject, String message);

    void sendEmailWithFile(String[] toUser, String subject, String message, File file);
}
