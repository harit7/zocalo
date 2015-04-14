package net.commerce.zocalo.user;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Properties;

import javax.mail.MessagingException;

import net.commerce.zocalo.mail.MailUtil;
import net.commerce.zocalo.service.Config;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.apache.log4j.Logger;

public class PasswordChangeRequest 
{
	private String requestId;
	private SecureUser user;
	private Timestamp time;
	
	private static final int LIFE_IN_HOURS = 24;

    static public final String RESET_PASSWORD_URL = "resetPasswordURL";
	
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public SecureUser getUser() {
		return user;
	}
	public void setUser(SecureUser user) {
		this.user = user;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	
	public String toString()
	{
		return requestId+"\t"+user.getName()+ "\t" + time;
	}
	
	  public void sendEmailNotification(String requestURL) throws MessagingException 
	  {
	        StringTemplateGroup group = MailUtil.EmailTemplates;
	        StringTemplate tpl = group.getInstanceOf("ResetPassword");
	        Properties props = new Properties();
	        String constantsFileName = MailUtil.SITE_CONSTANTS_FILE_NAME;
	        try {
	            InputStream inputStream = null;
	            inputStream = new FileInputStream(MailUtil.TemplateDir + "/" + constantsFileName);
	            props.load(inputStream);
	        } catch (FileNotFoundException e) {
	            Logger.getLogger(MailUtil.class).warn("unable to open " + constantsFileName + ".", e);
	        } catch (IOException e) {
	            Logger.getLogger(MailUtil.class).warn("unable to load " + constantsFileName + ".", e);
	        }

	        props.put("userName", user.getName());
	        //props.put(CONFIRMATION_CODE, confirmationToken);
	        props.put(RESET_PASSWORD_URL, requestURL + "?resetReqId=" + requestId);
	        String subject = "Please Reset Your password, " + user.getName();
	        Config.sendMail(user.getEmail(), subject,tpl, props);
	    }
	  
	  

}
