package com.project.scambiolavoro.asyncall;

import android.content.Context;
import android.os.AsyncTask;
import com.project.scambiolavoro.models.Contact;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendThis extends AsyncTask<Contact, Void, Void> {
    
    private Contact selContact;
    private Contact logContact;
    private Context context;
    
    public SendThis(Context context, Contact selContact, Contact logContact){
        this.context = context;
        this.selContact = selContact;
        this.logContact = logContact;
    }
    @Override
    protected Void doInBackground(Contact... contacts) {

        send(selContact, logContact);
        return null;
    }

    public void send(Contact selContact, Contact logContact) {
        final String senderEmailAddress = "staifrescofrescone.it@carlotrombetta.it";
        final String senderEmailPassword = "staifrescofrescone!";
        //String recipientEmailAddress = selContact.getMail();
        String recipientEmailAddress = "carlo.chisamico92@gmail.com";
        String emailSubject = "Congratulazioni "+selContact.getName()+"! "+logContact.getName()+" vuole conoscerti!";
        String emailBody = "<b>Ciao "+selContact.getName()+"</b>, abbiamo buone notizie! Qualcuno è interessato alla tua realtà lavorativa." +
                "<br><br> Ti invitiamo perciò a visionare con il tuo datore di lavoro il suo profilo, così che possa scoprire se sei compatibile con lui."+
                "<br>In caso affermativo contattalo subito mandandogli una mail per poter procedere!"+
                "<br> Qua sotto ti lasciamo i suoi dati!<br>"+
                "<br><b>Nome: </b>"+logContact.getName()+
                "<br><b>Cognome: </b>"+logContact.getSurname()+
                "<br><b>Sesso: </b>"+logContact.getGender()+
                "<br><b>Data di Nascita: </b>"+logContact.getBirthDate()+
                "<br><b>Residenza: </b>"+logContact.getFromPlace()+
                "<br><b>Lavoro: </b>"+logContact.getWork()+
                "<br><b>Esperienza Lavorativa: </b>"+logContact.getWorkExp()+
                "<br><b>Luogo di Lavoro: </b>"+logContact.getWorkPlace()+
                "<br><b>Numero telefonico: </b>"+logContact.getPhone()+
                "<br><b>Mail: </b>"+logContact.getMail()+
                "<br><br> Ti facciamo le nostre congratulazioni e <b> in Bocca al lupo!</b>"+
                "<br>Lo staff di ScambioLavoro";
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "mail.tophost.it");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmailAddress, senderEmailPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(senderEmailAddress));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmailAddress));
            message.setSubject(emailSubject);
            message.setContent(emailBody, "text/html");
            Transport.send(message);
            //new MyNewAsyncContainer(SearchActivity.this).execute(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}
