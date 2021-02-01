package com.project.scambiolavoro.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
import com.project.scambiolavoro.R;
import com.project.scambiolavoro.adapters.ContactsAdapter;
import com.project.scambiolavoro.asyncall.SendThis;
import com.project.scambiolavoro.models.Contact;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;


import com.project.scambiolavoro.asyncall.sendContact.SendEmail;
import com.project.scambiolavoro.widgets.decorations.MyDividerItemDecoration;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class SearchActivity extends AppCompatActivity implements ContactsAdapter.ContactsAdapterListener {
    private static final String TAG = SearchActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private List<Contact> contactList;
    private final List<Contact> tempListContact = new ArrayList<>();
    private ContactsAdapter mAdapter;
    private SearchView searchView;
    private final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private AsyncTask<Contact, Void, Void> sendThis;



    // Così non può funzionare perché gli elementi si rimuovono e aggiornano continuamente

    // url to fetch contacts json
    private static final String URL = "https://scambiolavoro-60b1f.firebaseio.com/.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // toolbar fancy stuff
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.toolbar_title);

        recyclerView = findViewById(R.id.recycler_view);
        contactList = new ArrayList<>();
        mAdapter = new ContactsAdapter(this, contactList, this, dbRef);

        // white background notification bar
        whiteNotificationBar(recyclerView);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, DividerItemDecoration.VERTICAL, 36));
        recyclerView.setAdapter(mAdapter);


        fetchContacts();
    }

    /**
     * fetches json by making http calls
     */
    private void fetchContacts() {


        //this choose how to order the items. In this case, it order by registration key
        Query query = dbRef.orderByKey();

        ValueEventListener mQueryValueListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                while (iterator.hasNext()) {

                    //take the values data of the children.
                    //Children are the contact items and after them, the relatives values
                    DataSnapshot next = iterator.next();
                    String name = (String) next.child("name").getValue();
                    String image = (String) next.child("image").getValue();
                    String work = (String) next.child("work").getValue();


                    Contact fromJSON = new Contact(name, image, work);
                    tempListContact.add(fromJSON);

                    //  Log.i(TAG, "Value = " + next.child("name").getValue());


                }

                // adding contacts to contacts list*//*
                contactList.clear();
                contactList.addAll(tempListContact);

                // refreshing recycler view
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };

        query.addListenerForSingleValueEvent(mQueryValueListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
            case R.id.logout:
                this.finish();
                Intent myIntent = new Intent(this, MainActivity.class);
                FirebaseAuth.getInstance().signOut();
                startActivity(myIntent);

                return true;

        }
        return super.onOptionsItemSelected(item);
    }
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        /*int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {


            return true;
        }

        if (id == R.id.logout) {

            Intent myIntent = new Intent(this, MainActivity.class);
            FirebaseAuth.getInstance().signOut();
            startActivity(myIntent);
            SearchActivity.this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);*/


    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }

    @Override
    public void onContactSelected(final Contact contactSelected) {

        try {
       //     final SendEmail sendEmail = new SendEmail();



            final String userUID = FirebaseAuth.getInstance().getCurrentUser().getUid();





            final FirebaseUser CurrentUser = mAuth.getCurrentUser();
            final DatabaseReference currentUser = dbRef.child(userUID);
            currentUser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    String currentUserMail = CurrentUser.getEmail();
                    String contactUserMail = contactSelected.getMail();


                    //l'errore è qui! Relativo forse alla firma del costruttore relativo

                    String one = "";

                    Contact currentContact = dataSnapshot.getValue(Contact.class);


                    sendThis = new SendThis(getBaseContext(), contactSelected, currentContact).execute(contactSelected, currentContact);
                    //send(contactSelected, currentContact);

                    //String currentUserName = dataSnapshot.child("name").getValue(String.class);
                    //sendEmail.execute(currentUserName, currentUserMail, contact.getName(), contactUserMail);
                    //Toast.makeText(getApplicationContext(), "bentornato " + currentUserName, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });





            if (userUID != null) {
                Toast.makeText(getApplicationContext(), "Abbiamo appena mandato i tuoi dati a  : " + contactSelected.getName() + "! Ci auguriamo che risponda presto", Toast.LENGTH_LONG).show();
              //  Toast.makeText(getApplicationContext(), "bentornato " + userUID, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Errore nell'identificazione. Si consiglia di sloggarsi e ricollegarsi", Toast.LENGTH_LONG).show();
            }
            //Toast.makeText(getApplicationContext(), "I dati completi di : " + contact.getName() + " sono stati mandati alla tua Mail.", Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void send(Contact selContact, Contact logContact) {
        final String senderEmailAddress = "Info@scambio-lavoro.com";
        final String senderEmailPassword = "micste";
        String receiverEmailAddress = "carlo.chimico92@gmail.com";
        String emailSubject = "OGGETTO IMP";
        String emailBody = "Questo è il body";
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "mail.tophost.it");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmailAddress, senderEmailPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(senderEmailAddress));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiverEmailAddress));
            message.setSubject(emailSubject);
            message.setText(emailBody);
            Transport.send(message);
            //new MyNewAsyncContainer(SearchActivity.this).execute(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}
