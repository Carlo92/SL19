package com.project.scambiolavoro.asyncall.sendContact;



import com.github.sendgrid.SendGrid;
import com.project.scambiolavoro.asyncall.MyAsyncContainer;

public class SendEmail extends MyAsyncContainer {

    @Override
    protected Void doInBackground(String... param) {
        String Currentname = param[0];
        String CurrentMail = param[1];
        String Contactname = param[2];
        String ContactMail = param[3];
        toAskerMail(Currentname, CurrentMail, Contactname, ContactMail);
        return null;
    }

    protected void toAskerMail(String Currentname, String CurrentMail, String ContactName, String ContactMail){
        SendGrid sg = new SendGrid("carlo.chimico92@gmail.com", "trinitadel92");

        sg.addTo(CurrentMail);
        sg.setFrom("info@scambio-lavoro.com");
        sg.setSubject("Info complete di "+ContactName);
        sg.setHtml("<b>Ciao "+Currentname+"</b><br><br>\n" +
                "A seguito della Tua richiesta, abbiamo raccolto tutte le informazioni richieste del Contatto da Te Selezionato! :-D<br>\n" +
                "Ti invitiamo a contattarlo subito per vedere se le vostre realtà lavorative sono compatibili!<br>\n" +
                "In caso affermativo, riuscirete rapidamente a trovare un accordo assieme ai Vostri datori di lavoro<br><br>\n" +
                "In questa mail troverai tutte le informazioni, relative al suo lavoro, la sua residenza e tutti altri dati di tuo interesse<br><br><br>\n" +
                "<i>Nome:</i><b>"+ContactName+"</b><br>\n" +
                "<i>Cognome:</i><b>surname</b><br>\n" +
                "<i>Sesso:</i><b>gender</b><br>\n" +
                "<i>Data di nascità</i><b>birthDate</b><br>\n" +
                "<i>Residenza:</i><b>fromPlace</b><br>\n" +
                "<i>Lavoro:</i><b>work</b><br>\n" +
                "<i>e lavora a:</i><b>workPlace</b><br>\n" +
                "<i>Email:</i><b>mail</b><br><br>\n" +
                "\n" +
                "<b>In bocca al lupo! ;)</b><br>\n" +
                "lo Staff di Scambio-Lavoro.com");
        sg.send();
    }

    protected void toSelectedContactMail(String name){
        SendGrid sg = new SendGrid("carlo.chimico92@gmail.com", "trinitadel92");

        sg.addTo("carlo.chimico92@gmail.com");
        sg.setFrom("info@scambio-lavoro.com");
        sg.setSubject("Questo è il sodddggetto");
        sg.setText("<b>Ciao "+"</b><br><br>\n" +
                "Congratulazioni! Qualcuno ha trovato il tuo lavoro interessante! :-D<br>\n" +
                "Se anche tu reputi la sua realtà lavorativa entusiasmante, allora non aspettare e contattalo immediatatamente\n" +
                "per farglielo sapere e trovare un accordo assieme ai Vostri datori di lavoro<br><br>\n" +
                "In questa mail troverai tutte le informazioni, relative al suo lavoro, la sua residenza e tutti altri dati di tuo interesse<br>\n" +
                "Ci auguriamo che possiate conoscervi e che reputi la sua opportunità interessante<br><br>\n" +
                "<i>Nome:</i><b>name</b><br>\n" +
                "<i>Cognome:</i><b>surname</b><br>\n" +
                "<i>Sesso:</i><b>gender</b><br>\n" +
                "<i>Data di nascità</i><b>birthDate</b><br>\n" +
                "<i>Residenza:</i><b>fromPlace</b><br>\n" +
                "<i>Lavoro:</i><b>work</b><br>\n" +
                "<i>e lavora a:</i><b>workPlace</b><br>\n" +
                "<i>Email:</i><b>mail</b><br><br>\n" +
                "\n" +
                "<b>In bocca al lupo! ;)</b><br>\n" +
                "lo Staff di Scambio-Lavoro.com"+name);
        sg.send();
    }
}
