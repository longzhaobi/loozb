package com.loozb.core.utils;

import com.loozb.model.SysUser;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * 用户密码加密解密
 * 加密：随机生成uuid跟密码组合后md5加密，同时把uuid保存到sys_user的salt字段。
 * 解密：从数据库获取到用户信息，得到salt跟传入的密码组合，如果相等则通过
 * @Author： 龙召碧
 * @Author： 龙召碧
 * @Date: Created in 2017-2-26 11:48
 */
public class PasswordUtil {
    private static RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
    private static String algorithmName = "md5";
    private static int hashIterations = 2;

    public static void encryptPassword(SysUser user) {
        user.setSalt(randomNumberGenerator.nextBytes().toHex());
        System.out.println("延：" + user.getSalt());
        String newPassword = new SimpleHash(algorithmName, user.getPassword(),
                ByteSource.Util.bytes(user.getSalt()), hashIterations).toHex();
        user.setPassword(newPassword);
    }

    public static String decryptPassword(String password, String salt) {
        return new SimpleHash(algorithmName, password,
                ByteSource.Util.bytes(salt), hashIterations).toHex();
    }

    public static void main(String[] args) {
//        SysUser user = new SysUser();
//        user.setUsername("longzb");
//        user.setPassword("123456");
//        PasswordUtil.encryptPassword(user);
//        System.out.println(user.getPassword());
        System.out.println(PasswordUtil.decryptPassword("123456", "885100637988ba9bd7092172375c994e"));
    }
}
