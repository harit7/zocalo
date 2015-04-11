package net.commerce.zocalo.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;

import net.commerce.zocalo.hibernate.HibernateUtil;
import net.commerce.zocalo.logging.Log4JInitializer;
import net.commerce.zocalo.mail.MailUtil;
import net.commerce.zocalo.user.SecureUser;

import org.apache.log4j.Logger;

public class EmailSender 
{
	public static void main(String[] args) throws Exception
	{
		Properties props = AllMarkets.readConfigFile();
                Log4JInitializer.initializeLog4J(props.getProperty("log.file"), "log4j.properties");
        
                Logger logger = Logger.getLogger(EmailSender.class);
        
		Config.initializeConfiguration(props); 
		
		HibernateUtil.initializeSessionFactory(Config.getDefaultDBFile(), HibernateUtil.SCHEMA_UPDATE);
		
		int BATCH_SIZE  = 10;
		File emailFile = new File("email.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(emailFile)));
		String s = "";
		String subject = br.readLine();
		String content = "";
		while((s=br.readLine())!=null)
		{
			content += s+ "\n";
		}
		
		int c = BATCH_SIZE;
		int st = 1;
		if(args[0].equals("test"))
		{
			logger.info("sending test email");
 //                       logger.info("Sending email to: "+user.getName() +" at " +user.getEmail());
                        String content1 = content.replace("$user", "harit");

                        MailUtil.sendSMTPMail(props, "harit.vishwakarma@gmail.com" , subject, content1, false);
			
			
		}	
		else if(args[0].equals("all"))
		{
			logger.info("Do you really want to send email to all users.");
			String z = br.readLine();
			if(!z.equals("Y") || ! z.equals("y") || ! z.equals("yes") || !z.equals("Yes"))
				return;

			while(c==BATCH_SIZE)
			{
				List<SecureUser> listUsers = HibernateUtil.getUsers(st, BATCH_SIZE);	
				if(listUsers!=null && listUsers.size() !=0)
				{
					for(SecureUser user: listUsers)
					{	
					 
						logger.info("Sending email to: "+user.getName() +" at " +user.getEmail()); 
						String content1 = content.replace("$user", user.getName());
					 
						MailUtil.sendSMTPMail(props, user.getEmail() , subject, content1, false); 
					}
				}
				c = listUsers.size();
				st++;
				Thread.sleep(10000); 
			}
		}
		logger.info("Done Sending Email");
	}
}
