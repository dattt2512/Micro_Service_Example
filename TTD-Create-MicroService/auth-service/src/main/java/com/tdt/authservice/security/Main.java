package com.tdt.authservice.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Main {
    public static void main(String[] args) {
        BCryptPasswordEncoder bpe = new BCryptPasswordEncoder(10);

        String p1 = "abc";
//        String p2 = "abc";

        System.out.println(bpe.encode(p1));
        System.out.println(bpe.encode(p1));
    }
}
