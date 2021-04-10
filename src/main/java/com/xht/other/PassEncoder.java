package com.xht.other;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class PassEncoder {
    public static void main(String[] arg){

        StandardPBEStringEncryptor standardPBEStringEncryptor =new StandardPBEStringEncryptor();
        /*配置文件中配置如下的算法*/
        standardPBEStringEncryptor.setAlgorithm("PBEWithMD5AndDES");
        /*配置文件中配置的password*/
        standardPBEStringEncryptor.setPassword("EWRREWRERWECCCXC");
        /*要加密的文本*/
        String name = standardPBEStringEncryptor.encrypt("root");
        String password =standardPBEStringEncryptor.encrypt("123456");
        /*将加密的文本写到配置文件中*/
        System.out.println("name="+name);
        System.out.println("password="+password);
    }
}
