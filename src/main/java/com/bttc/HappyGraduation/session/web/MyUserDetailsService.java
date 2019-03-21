package com.bttc.HappyGraduation.session.web;

import ch.qos.logback.classic.Logger;
import com.bttc.HappyGraduation.session.pojo.po.UserPO;
import com.bttc.HappyGraduation.session.service.interfaces.IUserSV;
import com.bttc.HappyGraduation.common.BeanMapperUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

@Component
public class MyUserDetailsService implements UserDetailsService {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(MyUserDetailsService.class);

    private static final String BOMC_AES_KEY = "Osrdc2018";

    @Autowired
    private IUserSV userSV;

    @Override
    public UserDetails loadUserByUsername(String username) {
        //这里可以可以通过username（登录时输入的用户名）然后到数据库中找到对应的用户信息，并构建成我们自己的UserInfo来返回。
        //这里可以通过数据库来查找到实际的用户信息，这里我们先模拟下,后续我们用数据库来实现
        try {
            UserPO user = userSV.queryByUsername(username);
            UserInfo userInfo = BeanMapperUtil.map(user, UserInfo.class);
            logger.info(userInfo.toString());
            return userInfo;
        } catch (Exception e) {
            throw new BadCredentialsException("用户名不存在");
        }

    }

//    public UserDetails loadUserByBomcUsername(String username) {
//        try {
////            String decodename = AESDncode(BOMC_AES_KEY,username); // 解密
//            String decodename = username;
//            UserAgileRelPO userAgileRelPO = iUserAgileRelSV.queryByAgileUserId(decodename);
//            UserPO user = userSV.queryByUserId(userAgileRelPO.getUserId());
//            UserInfo userInfo = BeanMapperUtil.map(user, UserInfo.class);
//            logger.info(userInfo.toString());
//            return userInfo;
//        } catch (Exception e) {
//            throw new BadCredentialsException("用户名不存在");
//        }
//
//    }

    private static String AESDncode(String encodeRules, String content) {
        try {
            //1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            //2.根据ecnodeRules规则初始化密钥生成器
            //生成一个128位的随机源,根据传入的字节数组
            keygen.init(128, new SecureRandom(encodeRules.getBytes()));
            //3.产生原始对称密钥
            SecretKey original_key = keygen.generateKey();
            //4.获得原始对称密钥的字节数组
            byte[] raw = original_key.getEncoded();
            //5.根据字节数组生成AES密钥
            SecretKey key = new SecretKeySpec(raw, "AES");
            //6.根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance("AES");
            //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.DECRYPT_MODE, key);
            //8.将加密并编码后的内容解码成字节数组
            byte[] byte_content = new BASE64Decoder().decodeBuffer(content);
            /*
             * 解密
             */
            byte[] byte_decode = cipher.doFinal(byte_content);
            String AES_decode = new String(byte_decode, "utf-8");
            return AES_decode;
        } catch (Exception e) {
            throw new BadCredentialsException("用户名不存在");
        }
    }
}