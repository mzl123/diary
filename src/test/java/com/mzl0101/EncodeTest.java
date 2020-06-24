package com.mzl0101;

import org.jasypt.encryption.StringEncryptor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EncodeTest {
    @Autowired
    StringEncryptor stringEncryptor;

    @Test
    public void getPass() {
        String url = stringEncryptor.encrypt("jdbc:mysql://127.0.0.1:3306/diary?useUnicode=true&characterEncoding=utf8");
        String name = stringEncryptor.encrypt("root");
        String password = stringEncryptor.encrypt("123456");
        System.out.println(url+"----------------");
        System.out.println(name+"----------------");
        System.out.println(password+"----------------");
        Assert.assertTrue(name.length() > 0);
        Assert.assertTrue(password.length() > 0);
    }
}
