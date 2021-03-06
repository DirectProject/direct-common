package org.nhindirect.common.mail;

import java.util.List;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Wraps an SMTP mail message which includes the mime message and the SMTP
 * rcpt_to and mail_from headers.
 * @author Greg Meyer
 * @since 6.0
 */
public class SMTPMailMessage
{
	  private final MimeMessage mimeMessage;
	  private final List<InternetAddress> recipientAddresses;
	  private final InternetAddress mailFrom;

      public SMTPMailMessage(MimeMessage mimeMessage, List<InternetAddress> recipientAddresses, InternetAddress mailFrom) 
      {
    	  this.mimeMessage = mimeMessage;
    	  this.recipientAddresses = recipientAddresses;
    	  this.mailFrom = mailFrom;
	  }

      public MimeMessage getMimeMessage()
      {
    	  return mimeMessage;
      }
      
      public List<InternetAddress> getRecipientAddresses()
      {
    	  return recipientAddresses;
      }
      
	  public InternetAddress getMailFrom() 
	  {
		  return mailFrom;
	  }
}
