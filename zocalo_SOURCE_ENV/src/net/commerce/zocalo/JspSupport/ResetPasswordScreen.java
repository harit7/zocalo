package net.commerce.zocalo.JspSupport;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.commerce.zocalo.hibernate.HibernateUtil;
import net.commerce.zocalo.logging.Log4JInitializer;
import net.commerce.zocalo.mail.MailUtil;
import net.commerce.zocalo.service.Config;
import net.commerce.zocalo.user.PasswordChangeRequest;
import net.commerce.zocalo.user.SecureUser;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.apache.log4j.Logger;

// Copyright 2007, 2008 Chris Hibbert.  All rights reserved.

// This software is published under the terms of the MIT license, a copy
// of which has been included with this distribution in the LICENSE file.

/** A screen that allows users to create new accounts. */
public class ResetPasswordScreen extends LoginScreen {
    static final public String RESET_PASSWORD_JSP  = "resetPassword.jsp";
    static final public String RESET_PASSWORD_NAME = "resetPassword";
    static final public String RESET_PASSWORD      = "Reset Password";
    
    static final public String EMAIL_DOES_NOT_EXIST = "<font color=red size=4><b>Sorry! Account with given email address does not exist </b></font>";
    static final public String EMAIL_NOT_NULL      = "<font color=red size=4><b> Email can't be empty!</b></font>";
    static final public String LOOK_FOR_RESET_URL  = "<font color=green size=4><b>We've sent you a URL to reset your password . Please check your Inbox.</b></font>";
    static final public String RESET_UPDATE_SUCCESS= "<font color=green size=4><b>Password updated successfully.</br><a href=\"Welcome.jsp\">Click here to Login</a> </b></font>";
    static final public String PASSWORD_WARNING    = "<font color=red size=4><b>Oops! Given password doesn't qualify! You must have at least 7 characters, with at least one being a number.</b></font>";
    static final public String PASSWORD_DO_NOT_MATCH = "<font color=red size=4><b>Passwords Don't Match.</b></font>"; 
    static final public String SOME_THING_WENT_WRONG= "<font color=green size=4><b>Oops! Something went Wrong.</b></font>";
    static final public String STATUS_GET_EMAIL    = "getEmail";
    static final public String STATUS_GET_PASSWORD = "getPassword";
    static final public String STATUS_INVALID_EMAIL= "invalidEmail";
    static final public String ACTION_SEND_EMAIL   = "sendEmail";
    static final public String ACTION_RESET_PASSWORD = "resetPassword";
    
    private boolean successfulCreation;
    private String emailAddress;
    private String password;
    private String password2;
    private String confirmation;
    private String status;
    private String action;
    private String resetReqId;
    Logger logger = Logger.getLogger(Log4JInitializer.UserError);
    
    public void processRequest(HttpServletRequest request, HttpServletResponse response) {
        // inputs are name, email, password, password2, and confirmation.  If a proposed name
        // is turned down, we can leave the email.  We should never remember or repopulate the
        // passwords or confirmation tokens across sessions.

    	/*
    	 * check if account exists..
    	 * check if it exists in unconfirmed users.. then send confirmation link
    	 * 
    	 * 1. generete reset pass link
    	 * 2. jsp will handle the request with the reset id link 
    	 *    it should handle timing details...
    	 * 3. accept the new password and store it. .. redirect to login page.
    	 * */
    	
        successfulCreation = false;
        System.out.println("Action: "+action + "Status: "+ status + "resetReqId "+ resetReqId); 
        if(action != null && action.equals(ACTION_SEND_EMAIL))
        {
        	logger.info("received password change request from :"+emailAddress); 
        	
    		if(emailAddress == null || emailAddress.equals("")) 
    		{
    			warn(EMAIL_NOT_NULL);
    			status = STATUS_GET_EMAIL;
    			
    			return ;
    		}
    		boolean    emailExists   = HibernateUtil.emailExists(emailAddress);
    		if(!emailExists)
    		{
    			warn(EMAIL_DOES_NOT_EXIST);
    			status= STATUS_GET_EMAIL;
    			emailAddress = "";
    			return;
    		}
    	
    		request.getRequestURL();
    		// email is valid and it exists in db
    		// generate reset url.
    		String  url=  request.getRequestURL().toString();
			if(request.getServerName().equals("lcm.csa.iisc.ernet.in") )
			{	
			    int i = url.lastIndexOf("/");
						
			    String s = url.substring(0,i);
			    url = s+"/pm/createAccount.jsp";
			}
			System.out.println("*****"+url);
			try{
				sendResetPasswordUrl(emailAddress, url);
				warn(LOOK_FOR_RESET_URL);
				
			}
			catch(Exception e)
			{
				warn(SOME_THING_WENT_WRONG);
				logger.error(e.getMessage()); 
				logger.error(e.getCause()); 

			}
			
			status    = null;
			action    = null;
			//status  = STATUS_GET_PASSWORD;
    	
        }
        else if(resetReqId != null)
        {
        	
        	status = STATUS_GET_PASSWORD;
        	
        	// check time validity of request..
        	PasswordChangeRequest passReq = HibernateUtil.getPasswordChangeRequest(resetReqId);
        	
        	if(action!= null && action.equals(ACTION_RESET_PASSWORD) && validatePassword())
        	{
        		// validate password
        		
        		
        		// update password
        		SecureUser user = passReq.getUser();
        		try{
	        		
	        		user.setPassword(password); 
	                HibernateUtil.save(user);
	                logger.info("Password updated successfuly for :"+user.getName());  
	                warn(RESET_UPDATE_SUCCESS);
        		}
        		catch(Exception e)
        		{
        			logger.error(e.getMessage());
        			logger.error(e.getCause()); 
        			
        			warn(SOME_THING_WENT_WRONG);
        			
        			setResetReqId(null); 
                    setAction(null); 
        			
                    return;
        			
        		}
        		// send notification email .. saying password has been changed.
                try{
                	notifyPasswordReset(user);
                }
                catch(Exception e)
                {
                	
                	logger.error(e.getMessage());
                	logger.error(e.getCause());
                	
                }
                
                setResetReqId(null); 
                setAction(null); 
                //redirectResult(request, response);
                
        	}
        	
        }
        else 
         {
        	warn("");
            redirectResult(request, response);
            setUserName("");
         }
         resetPasswords();
         
    }
    private void sendResetPasswordUrl(String emailAddress, String requestURL) throws Exception
    {
    	PasswordChangeRequest req = makePasswordChangeRequest(emailAddress);
    	req.sendEmailNotification(requestURL);
    	logger.info("sent password reset url to: "+ emailAddress +" request"+ req); 
    }
    private PasswordChangeRequest makePasswordChangeRequest(String emailAddress)
    {
    	SecureUser user  = HibernateUtil.getUserByEmail(emailAddress);
    	PasswordChangeRequest req = new PasswordChangeRequest();
    	String uuid =  UUID.randomUUID().toString().replace("-", "");
    	req.setRequestId(uuid);
    	req.setUser(user);
    	HibernateUtil.save(req); 
    	return req;
    	
    }
    private void resetPasswords() {
        setPassword(null);
        setPassword2(null);
        setConfirmation(null);
    }

    public void warn(String arg) {
        getUserAsWarnable().warn(arg);
    }

    private boolean validatePassword() {
        
        if (password2 != null && !"".equals(password2) && !password2.equals(password)) {
            warn(PASSWORD_DO_NOT_MATCH);
            return false;
        }
        if (! SecureUser.validatePassword(password)) {
            warn(PASSWORD_WARNING);
            return false;
        }
        return true;
    }

    

    public String getRequestURL(HttpServletRequest request) {
        if (successfulCreation) {
	    if(request.getServerName().equals("lcm.csa.iisc.ernet.in"))
	            return "pm/"+WelcomeScreen.WELCOME_JSP;
	    else
		    return WelcomeScreen.WELCOME_JSP;
        } else {
            return null;
        }
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    private String getEmailAddress() {
        return emailAddress;
    }

    public String getEmailAddressForWeb() {
        String address = getEmailAddress();
        if (null == address) {
            return "";
        }
        return address;
    }

    public String getUserNameForWeb() {
        String userName = getUserName();
        if (userName == null) {
            return "";
        }
        return userName;
    }

    public boolean hasWarnings() {
        return getUserAsWarnable().hasWarnings();
    }

    public String getWarnings() {
        return getUserAsWarnable().getWarningsHTML();
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	public String getResetReqId() {
		return resetReqId;
	}
	public void setResetReqId(String resetReqId) {
		this.resetReqId = resetReqId;
	}
    
	public void notifyPasswordReset(SecureUser user) throws Exception
	{
		    StringTemplateGroup group = MailUtil.EmailTemplates;
	        StringTemplate tpl = group.getInstanceOf("NotifyPasswordReset");
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

	        String subject = "Your Prediction Market Passowrd has been Changed";
	        
	        Config.sendMail(user.getEmail(), subject, tpl, props);

	}
	
	public void clear()
	{
		this.action = null;
		this.emailAddress = null;
		this.password = null;
		this.password2 = null;
		this.resetReqId = null;
		this.status    = null;
		this.successfulCreation = false;
		
	}
    
}
