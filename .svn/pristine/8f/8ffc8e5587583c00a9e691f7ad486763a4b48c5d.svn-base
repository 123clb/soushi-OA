/**
 * 
 */
package com.kaoshidian.oa.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.kaoshidian.oa.base.exception.SecurityException;
import com.kaoshidian.oa.permission.dao.UserDAO;
import com.kaoshidian.oa.permission.entity.UserStateEnum;
import com.kaoshidian.oa.permission.entity.UserTypeEnum;

/**
 * @author <p>Innate Solitary 于 2012-6-5 下午2:51:56</p>
 *
 */
public final class SerureUtil {
	
	public static final char[] DIGIT = { '0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
	
	public static boolean md5Verify(UserDAO userDao, String key, Long time, String psm) throws NoSuchAlgorithmException {
		
		if(userDao == null) {
			throw new IllegalArgumentException("参数 userDao 为空");
		}
		
		if(StringUtils.isEmpty(psm) || StringUtils.isEmpty(key) || time == null) {
			throw new IllegalArgumentException("参数 key, time, psm 为空");
		}
		
		List<Map> usermap = userDao.findBySql("select loginName, password from mrms_perm_user where state = ? and userType = ?", new Object[] {UserStateEnum.ACTIVITY.name(), UserTypeEnum.API_VERIFY.name()});
		for (Map map : usermap) {
	        String loginName = map.get("loginName").toString();
	        String password = map.get("password").toString();
	        String loginNameMD5 = md5ValueForHex((loginName + "@" + time)).toLowerCase();
	        String passwordMD5 = md5ValueForHex(password).toLowerCase();
	        if(loginNameMD5.equals(key) && psm.equals(passwordMD5)) {
	        	return true;
	        }
        }
		return false;
	}
	
	public static String md5ValueForHex(String str) throws NoSuchAlgorithmException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		byte[] digest = md5.digest(str.getBytes());
		return toHex(digest);
	}
	
	public static String desEncrypt(String symmetricKey, String data) throws SecurityException {
        try {
        	Key key = new SecretKeySpec(symmetricKey.getBytes("UTF-8"), "des");
	        Cipher cipher = Cipher.getInstance("DES");
	        cipher.init(Cipher.ENCRYPT_MODE, key);
	        byte[] encryptCode = cipher.doFinal(data.getBytes("UTF-8"));
	        BASE64Encoder encoder = new BASE64Encoder();
	        return encoder.encode(encryptCode);
        } catch (UnsupportedEncodingException e) {
	        throw new SecurityException(e);
        } catch (NoSuchAlgorithmException e) {
        	throw new SecurityException(e);
        } catch (NoSuchPaddingException e) {
        	throw new SecurityException(e);
        } catch (InvalidKeyException e) {
        	throw new SecurityException(e);
        } catch (IllegalBlockSizeException e) {
        	throw new SecurityException(e);
        } catch (BadPaddingException e) {
        	throw new SecurityException(e);
        }
	}
	
	public static String desDecrypt(String symmetricKey, String encryptData) throws SecurityException {
		if(encryptData.matches("^ftp://[^\\s]*")) {
			// 如果是ftp url则直接返回，不进行解密
			return encryptData;
		}
		try {
	        BASE64Decoder decoder = new BASE64Decoder();
	        byte[] encryptCode = decoder.decodeBuffer(encryptData);
	        Cipher cipher = Cipher.getInstance("DES");
	        Key key = new SecretKeySpec(symmetricKey.getBytes(), "des");
	        cipher.init(Cipher.DECRYPT_MODE, key);
	        byte[] decryptCode = cipher.doFinal(encryptCode);
	        return new String(decryptCode, "UTF-8");
        } catch (UnsupportedEncodingException e) {
	        throw new SecurityException(e);
        } catch (NoSuchAlgorithmException e) {
        	throw new SecurityException(e);
        } catch (NoSuchPaddingException e) {
        	throw new SecurityException(e);
        } catch (InvalidKeyException e) {
        	throw new SecurityException(e);
        } catch (IllegalBlockSizeException e) {
        	throw new SecurityException(e);
        } catch (BadPaddingException e) {
        	throw new SecurityException(e);
        } catch (IOException e) {
        	throw new SecurityException(e);
        }
	}
	
	public static String toHex(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		for (byte b : bytes) {
			char [] ob = new char[2];
			ob[0] = DIGIT[(b >>> 4) & 0X0F];
			ob[1] = DIGIT[b & 0X0F];
			String s = new String(ob);
			sb.append(s);
        }
		
		return sb.toString();
	}
	
	
	public static void main(String[] args) throws SecurityException, NoSuchAlgorithmException {
		
    }
}
