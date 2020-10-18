package com.project.scambiolavoro.fragment;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.*;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.project.scambiolavoro.ImagePicker;
import com.project.scambiolavoro.R;
import com.project.scambiolavoro.activity.SearchActivity;
import com.project.scambiolavoro.models.Contact;
import com.project.scambiolavoro.widgets.DropDown;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Objects;

import static android.app.Activity.RESULT_CANCELED;
import static com.android.volley.VolleyLog.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {


    //FirebaseAuth mAuth = FirebaseAuth.getInstance();
    // [START declare_auth]
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
    private final FirebaseStorage storage = FirebaseStorage.getInstance();
    private final StorageReference imagesFolder = storage.getReference().child("images/users");
    private LayoutInflater inflater;
    private ViewGroup container;
    private Bundle savedIstanceState;
    private Bitmap bitmap;
    private byte[] myByt;


    private static final int PICK_IMAGE_ID = 234; // the number doesn't matter
    // [END declare_auth]

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        // Define all variables
        // Name
        final EditText name = view.findViewById(R.id.et_name);
        // Surname
        final EditText surname = view.findViewById(R.id.et_surname);
        // Gender
        final DropDown gender = view.findViewById(R.id.gender);

        // Dropdown arraylist. Two genders: male and female



        ArrayList<String> gendercases = new ArrayList<>();
        gendercases.add("Uomo");
        gendercases.add("Donna");

        // Set Options to gender defined earlier
        gender.setOptions(gendercases);

        //Something about my Dropdown ahahah ;)

        // Birthday
        final EditText birthDate = view.findViewById(R.id.birthdate);

        // From Place

        final EditText fromPlace = view.findViewById(R.id.fromPlace);

        // work

        final EditText work = view.findViewById(R.id.work);

        // work Experience

        final EditText workExp = view.findViewById(R.id.workExp);

        // mail

        final EditText mail = view.findViewById(R.id.et_email);

        // Password

        final EditText pwd = view.findViewById(R.id.et_password);

        // Confirm Password

        final EditText repwd = view.findViewById(R.id.et_repassword);


        // take photo
        final TextView photo = view.findViewById(R.id.photo);

        final ImageView imageView = view.findViewById(R.id.thumbnail2);

        birthDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Calendar mcurrentDate = Calendar.getInstance();
                    int year = mcurrentDate.get(Calendar.YEAR);
                    int month = mcurrentDate.get(Calendar.MONTH);
                    int day = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog mDatePicker = new DatePickerDialog(Objects.requireNonNull(getContext()), new DatePickerDialog.OnDateSetListener() {
                        public void onDateSet(DatePicker datepicker, int selectedYear, int selectedMonth, int selectedDay) {
                            int correctMonth = selectedMonth + 1;
                            // TODO Auto-generated method stub
                            //*      Your code   to get workExp and time
                            Log.e("Date Selected", "Month: " + correctMonth + " Day: " + selectedDay + " Year: " + selectedYear);
                            birthDate.setText(selectedDay + "/" + correctMonth + "/" + selectedYear);
                        }
                    }, year, month, day);
                    mDatePicker.setTitle("Select workExp");
                    mDatePicker.show();

                    //Bisogna implementare un algoritmo che vada a separare la parte logic da quella view (pattern MVC)
                    // Sarebbe bene in questa specifica parte invece crearne uno che mi analizzi il testo del birthDate
                    // in modo che chi abbia la versione vecchia sdk, possa essere analizzato in un numero di anni di esperienza
                    // compatibile con la maggiore età


                }
            }
        });


        workExp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Calendar mcurrentDate = Calendar.getInstance();
                    int year = mcurrentDate.get(Calendar.YEAR);
                    int month = mcurrentDate.get(Calendar.MONTH);
                    int day = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog mDatePicker = new DatePickerDialog(Objects.requireNonNull(getContext()), new DatePickerDialog.OnDateSetListener() {
                        public void onDateSet(DatePicker datepicker, int selectedYear, int selectedMonth, int selectedDay) {
                            int correctMonth = selectedMonth + 1;
                            // TODO Auto-generated method stub
                            //*      Your code   to get workExp and time
                            Log.e("Date Selected", "Month: " + correctMonth + " Day: " + selectedDay + " Year: " + selectedYear);
                            workExp.setText(selectedDay + "/" + correctMonth + "/" + selectedYear);
                        }
                    }, year, month, day);
                    mDatePicker.setTitle("Select workExp");
                    mDatePicker.show();
                }
            }
        });


        final Button register = view.findViewById(R.id.btn_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData(name, mail, pwd);

               /* if (validateData(name, surname,
                        gender, birthDate,
                        fromPlace, work, workExp,
                        mail, pwd, repwd)) {
                    sendData(mail, pwd);
                }*/


            }
        });


        photo.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View view) {
                                         Intent chooseImageIntent = ImagePicker.getPickImageIntent(getContext());
                                         startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
                                     }
                                 }
        );


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView profilePic = getView().findViewById(R.id.thumbnail2);
        if (resultCode != RESULT_CANCELED) {
            if (requestCode == PICK_IMAGE_ID) {
                if (data.getExtras() == null) {
                    bitmap = ImagePicker.getImageFromResult(getContext(), resultCode, data);
                    Glide.with(getContext())
                            .load(bitmap)
                            .apply(RequestOptions.circleCropTransform())
                            .into(profilePic);
                    imageToByte(bitmap);
                    //profilePic.setImageBitmap(bitmap);
                }
                // if you get a photo from camera
                else {
                    bitmap = (Bitmap) data.getExtras().get("data");
                    Glide.with(getContext())
                            .load(bitmap)
                            .apply(RequestOptions.circleCropTransform())
                            .into(profilePic);
                    imageToByte(bitmap);
                    // profilePic.setImageBitmap(bitmap);
                    //  profilePic.setImageBitmap(photo);
                }
            }
        }
    }

    private byte[] imageToByte(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        myByt = baos.toByteArray();
        return myByt;
    }


    private boolean validateData(EditText name, EditText surname,
                                 DropDown gender, EditText birthDate,
                                 EditText fromPlace, EditText work, EditText workExp,
                                 EditText mail,
                                 EditText pwd, EditText repwd) {

        String nameGot = name.getText().toString().trim();
        String surnameGot = surname.getText().toString().trim();
        String genderGot = gender.getText().toString().trim();

        System.out.println(genderGot);
        String birthDateGot = birthDate.getText().toString().trim();
        String pwdGot = pwd.getText().toString().trim();
        String repwdGot = repwd.getText().toString().trim();

        String emailInput = mail.getText().toString().trim();


        if (nameGot.isEmpty() || surnameGot.isEmpty() || genderGot.isEmpty() || emailInput.isEmpty() ||
                birthDateGot.isEmpty() || pwdGot.isEmpty() || repwdGot.isEmpty() || (!pwdGot.equals(repwdGot))) {

            if (nameGot.isEmpty()) {

                name.setError("Inserisci il tuo Nome");
            }


            if (surnameGot.isEmpty()) {

                surname.setError("Inserisci il tuo Cognome");
            }


            if (genderGot.isEmpty()) {
                gender.setError("Seleziona il tuo Sesso");
            }
            if (birthDateGot.isEmpty()) {

                birthDate.setError("Inserire la Data di Nascita");
            } else {
                birthDate.setError(null);
            }


            //Here there is the Birthdate that can't be from the future
            if (pwdGot.isEmpty()) {
                pwd.setError("Digita la tua password");
            }

            if (repwdGot.isEmpty()) {
                repwd.setError("Ridigita la tua Password");
            }

            if (!pwdGot.equals(repwdGot)) {
                repwd.setError("Le due password non coincidono. Inserisci la stessa Password");
            }


            return false;
        } else {

            return true;
        }


    }

    //parameters must be exactly the same for  the contact
    private void sendData(EditText name, EditText email, EditText pwd) {


        String emailInput = email.getText().toString().trim();
        String pwdGot = pwd.getText().toString().trim();
        final String nameGot = name.getText().toString().trim();
        //   showProgressBar();


        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(emailInput, pwdGot)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // N.B: It's already intelligent a little bit 'cause it doesn't accept password String
                            // smaller than 6 characters. However, we must take some other precautions for making it
                            // more stronger (eg. special characters, Uppercase, Lowercase, numbers, etc.
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            // Here you can continue your bond with Second Activity

                            final String gid = user.getUid();


                            //generate key for the user that must be unique and the same for database and photo in storage


                            final StorageReference imageReference = imagesFolder.child(gid + ".jpg");


                            imageReference.putBytes(myByt).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {

                                            Contact contact = new Contact(nameGot, uri.toString(), "3333333333");
                                            dbRef.child(gid).setValue(contact);
                                            Intent myIntent = new Intent(getActivity(), SearchActivity.class);
                                            getActivity().startActivity(myIntent);
                                            getActivity().finish();
                                        }
                                    });
                                }
                            });


                            // MyDataReference myDataReference = new MyDataReference(dbRef);


                            //UploadTask uploadTask = storageRefIMG.child(gid+".png").putFile(bitmap);


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

//https://api.androidhive.info/json/images/keanu.jpg


    }


}


