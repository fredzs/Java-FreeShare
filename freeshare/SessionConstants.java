package com.free4lab.freeshare;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.free4lab.freeshare.util.ConfigurationUtil;



//session参数名称，其值待抽离出.properties文件
public class SessionConstants {

	//session
	public static final String UserSessionContext = "userSessionContext";
	
	public static final String UserName = "userName";
	public static final String UserEmail = "email";
	public static final String AccessToken = "accessToken";
	public static final String UserID = "userId";
	public static final String Avatar = "avatar";
	public static final String Groups = "groups";
	public final static String KEY_ACCTOKEN = "acc_token";//截取之后的AccessToken
	public final static String CLIENT_SECRET_KEY = "freeshareSecretKey";
	 
//	public final static String KEY_USER_ID = "uid";
//    public final static String KEY_SCREEN_NAME = "screen_name";
//    public final static String KEY_USER_EMAIL = "email";
//    public final static String KEY_PROFILE_IMAGE_URL = "profile_image_url";
//    public final static String KEY_ACCESSTOKEN = "access_token";
   
    
   /* public final static String CLIENT_SECRET_KEY;
    
    static{
    	final Logger logger = Logger.getLogger("App configuration");
        logger.info("+++++++++++App configuration information++++++++++++");
        try {
        	 Properties p = new ConfigurationUtil().getPropertyFileConfiguration("app.properties");
        	 CLIENT_SECRET_KEY = p.getProperty("CLIENT_SECRET_KEY");
             logger.info("CLIENT_SECRET_KEY:" + CLIENT_SECRET_KEY);
        } catch (IOException e) {
            logger.fatal("Failed to init app configuration", e);
            throw new RuntimeException("Failed to init app configuration", e);
        }
    }*/
}
