package com.lucete.template.info.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.Base64;

public class KeyGenerator {
    public static String generateKey() {
        SecureRandom secureRandom = new SecureRandom(); // SecureRandom 인스턴스 생성
        byte[] key = new byte[256]; // 256 bit 키를 저장할 byte 배열
        secureRandom.nextBytes(key); // 랜덤한 키 생성
        return Base64.getEncoder().encodeToString(key); // Base64 인코딩하여 문자열로 반환
    }
    public static void saveKeyToFile(String key, String filePath) throws IOException {
        Files.write(Paths.get(filePath), key.getBytes());
    }

    public static String readKeyFromFile(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }
}
