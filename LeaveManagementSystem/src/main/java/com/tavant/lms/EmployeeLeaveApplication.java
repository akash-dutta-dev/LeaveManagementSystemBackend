package com.tavant.lms;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class EmployeeLeaveApplication {

    public static void main(String[] args) {
        //PasswordEncoder encoder = new BCryptPasswordEncoder();
        //System.out.println( "Password is - " + encoder.encode("abc"));
        SpringApplication.run(EmployeeLeaveApplication.class, args);
    }
}
