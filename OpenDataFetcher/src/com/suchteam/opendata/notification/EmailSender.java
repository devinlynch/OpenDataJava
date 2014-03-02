package com.suchteam.opendata.notification;

import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.suchteam.database.DataAccess;
import com.suchteam.database.SubscribeNotified;

public class EmailSender extends Thread {
	private boolean stopped;
	private DataAccess access;
	final String username = System.getProperty("emailUsername", "canadaopendata@gmail.com");
	final String password = System.getProperty("emailPassword", "suchpasswordverystrong");
	
	public static void main(String[] args) {
		EmailSender sender = new EmailSender();
		sender.setAccess(new DataAccess());
		sender.start();
	}
	
	@Override
	public void run() {
		while(!isStopped()) {
			handleSendEmails();
			
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				System.out.println("Shutdown notice received");
				setStopped(true);
			}
		}
	}
	
	protected void handleSendEmails() {
		getAccess().beginTransaction();
		List<String> emails = getAccess().getEmailsThatNeedSending();
		getAccess().commit();
		
		for(String eId: emails) {
			try{
				sendEmail(eId);
			} catch(Exception e) {
				try{
					if(getAccess().isTransactionActive())
						getAccess().rollback();
				} catch(Exception e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}
	}
	
	
	protected void sendEmail(String eId) {
		getAccess().beginTransaction();
		
		SubscribeNotified notify = getAccess().get(SubscribeNotified.class, eId);
		String content = notify.getContent();
		String subject = notify.getSubject();
		String emailAddress = notify.getSubscribe().getEmailAddress();
		
		boolean didSend = false;
		try{
			actuallySendEmail(content, subject, emailAddress);
			didSend=true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		if(didSend) {
			notify.setSent(true);
			getAccess().save(notify);
		}
		
		getAccess().commit();
	}
	
	protected Session getSession(){
		Properties props = new Properties();
		props.put("mail.smtp.auth", System.getProperty("smtpAuth", "true"));
		props.put("mail.smtp.starttls.enable", System.getProperty("smtpTtlsEnabled", "true"));
		props.put("mail.smtp.host", System.getProperty("smtpHost", "smtp.gmail.com"));
		props.put("mail.smtp.port", System.getProperty("smtpPort", "587"));
 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
		return session;
	}
	
	protected void actuallySendEmail(String content, String subject, String emailAddress) throws Exception {
		Message message = new MimeMessage(getSession());
		message.setFrom(new InternetAddress("canadaopendata@gmail.com"));
		message.setRecipients(Message.RecipientType.TO,
		InternetAddress.parse(emailAddress));
		message.setSubject(subject);
		message.setText(content);
 
		Transport.send(message);
 
		System.out.println("Sent email to "+ emailAddress);
 
	}


	public boolean isStopped() {
		return stopped;
	}



	public void setStopped(boolean stopped) {
		this.stopped = stopped;
	}

	public DataAccess getAccess() {
		return access;
	}

	public void setAccess(DataAccess access) {
		this.access = access;
	}
}
