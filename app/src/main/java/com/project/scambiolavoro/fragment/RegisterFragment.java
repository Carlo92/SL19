package com.project.scambiolavoro.fragment;


import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
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
import com.project.scambiolavoro.GetPhoneNumber;
import com.project.scambiolavoro.ImagePicker;
import com.project.scambiolavoro.R;
import com.project.scambiolavoro.activity.SearchActivity;
import com.project.scambiolavoro.models.Contact;
import com.project.scambiolavoro.widgets.DropDown;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Objects;

import static android.Manifest.permission.*;
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

    String TAG = "PhoneActivityTAG";
    //Activity activity = PhoneActivity.this; use instead getActivity
    String wantPermission = Manifest.permission.READ_PHONE_STATE;
    private static final int PERMISSION_REQUEST_CODE = 1;

    //declare all fields
    private EditText name;
    private EditText surname;
    private DropDown gender;
    private EditText birthDate;
    private EditText fromPlace;
    private EditText work;
    private EditText workExp;
    private EditText workPlace;
    private EditText mobile;

    private EditText mail;

    private EditText pwd;
    private EditText repwd;
    private TextView photo;
    private ImageView imageView;
    private Button register;
    private CheckBox checkBox;
    private NestedScrollView nestedScrollView;

    private boolean isScrolled;
    private boolean isNowChecked;



    private String getPhone() {
        TelephonyManager phoneMgr = (TelephonyManager) this.getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity(), wantPermission) != PackageManager.PERMISSION_GRANTED) {
            return "";
        }
        return phoneMgr.getLine1Number();
    }

    private void requestPermission(String permission){
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)){
            Toast.makeText(getActivity(), "Phone state permission allows us to get phone number. Please allow it for additional functionality.", Toast.LENGTH_LONG).show();
        }
        ActivityCompat.requestPermissions(getActivity(), new String[]{permission},PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "Phone number: " + getPhone());
                } else {
                    Toast.makeText(getActivity(),"Permission Denied. We can't get phone number.", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private boolean checkPermission(String permission){
        if (Build.VERSION.SDK_INT >= 23) {
            int result = ContextCompat.checkSelfPermission(getActivity(), permission);
            return result == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
        }
    }
    //





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
        name = view.findViewById(R.id.et_name);
        // Surname
        surname = view.findViewById(R.id.et_surname);
        // Gender
        gender = view.findViewById(R.id.gender);

        // Dropdown arraylist. Two genders: male and female



        ArrayList<String> gendercases = new ArrayList<>();
        gendercases.add("Uomo");
        gendercases.add("Donna");

        // Set Options to gender defined earlier
        gender.setOptions(gendercases);

        //Something about my Dropdown ahahah ;)

        // Birthday
        birthDate = view.findViewById(R.id.birthdate);

        // From Place

        fromPlace = view.findViewById(R.id.fromPlace);

        // work

        work = view.findViewById(R.id.work);
//        work.setText("Falegname");

        // work Experience

        workExp = view.findViewById(R.id.workExp);

        // workPlace

        workPlace = view.findViewById(R.id.workPlace);

        // mail

        mail = view.findViewById(R.id.et_email);

        mobile = view.findViewById(R.id.et_phone);


        // mobile

    //    phone = view.findViewById(R.id.phone);

        mobile.setText(getPhone());


       /* if (mPhoneNumber != null) {
            phone.setText(mPhoneNumber);
        }
        else
            phone.setText("333");*/

       // phone.setText(mPhoneNumber);


      //  phone.setText(new GetPhoneNumber.askPe);

        // Password

        pwd = view.findViewById(R.id.et_password);

        // Confirm Password

        repwd = view.findViewById(R.id.et_repassword);


        // take photo
        photo = view.findViewById(R.id.photo);

        imageView = view.findViewById(R.id.thumbnail2);

        nestedScrollView = view.findViewById(R.id.policyprivacy);

        checkBox = view.findViewById(R.id.et_checkauth);

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


        register = view.findViewById(R.id.btn_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if (validateData(name, surname,
                        gender, birthDate,
                        fromPlace, work, workExp, workPlace,
                        mail, pwd, repwd)) {
                    sendData(name, surname, gender, birthDate,
                            fromPlace, work, workExp, workPlace, mobile,
                            mail, pwd);
                }


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




        nestedScrollView.getViewTreeObserver()
                .addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                        if (nestedScrollView.getChildAt(0).getBottom()
                                <= (nestedScrollView.getHeight() + nestedScrollView.getScrollY())) {
                            isScrolled = true;
                            //scroll view is at bottom
                        } else {
                            isScrolled = false;
                            //scroll view is not at bottom
                        }
                    }
                });


//Please Note: All this stuff it's usefull because reveals in every moment when checkbox
        //is checked or not! On the other hand, if it there wasn't, checkbox reveals just on
        //the start istance! Not very clever ;)
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkBox.isChecked()) {
                    isNowChecked = true;
                }
                else{

                    isNowChecked = false;
                    //Same! Error!!
                }

            }
        });







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
                                 EditText fromPlace, EditText work, EditText workExp, EditText workPlace,
                                 EditText mail,
                                 EditText pwd, EditText repwd) {

        String nameGot = name.getText().toString().trim();
        String surnameGot = surname.getText().toString().trim();
        String genderGot = gender.getText().toString().trim();
        String birthDateGot = birthDate.getText().toString().trim();
        String fromPlaceGot = fromPlace.getText().toString().trim();
        String workGot = work.getText().toString().trim();
        String pwdGot = pwd.getText().toString().trim();
        String repwdGot = repwd.getText().toString().trim();

        String emailInput = mail.getText().toString().trim();


        if (nameGot.isEmpty() || surnameGot.isEmpty() || genderGot.isEmpty() || emailInput.isEmpty() ||
                birthDateGot.isEmpty() || fromPlaceGot.isEmpty() || workGot.isEmpty() || pwdGot.isEmpty()
                || repwdGot.isEmpty() || (!pwdGot.equals(repwdGot)) || !isScrolled || !isNowChecked) {

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
            }
            if (fromPlaceGot.isEmpty()) {
                fromPlace.setError("Inserire il luogo di provenienza");
            }
            if (workGot.isEmpty()) {
                work.setError("Inserisci il Tuo Lavoro");
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

            if (!isScrolled || !isNowChecked) {

                if (!isScrolled && !isNowChecked){
                    checkBox.setError("Leggi la Policy, scrolla fino al fondo e conferma di aver letto e accettato il regolamento");
                }
                else {
                    if (!isScrolled) {
                        checkBox.setError("Leggi la Policy e scrolla fino a fondo");
                    }
                    if (!isNowChecked) {
                        checkBox.setError("Conferma di aver letto e accettato il regolamento");
                    }
                }




            }




            return false;
        } else {

            return true;
        }


    }

    //parameters must be exactly the same for  the contact


    private void sendData(EditText name, EditText surname, DropDown gender,
                          EditText birthDate, EditText fromPlace,
                          EditText work, EditText workExp,
                          EditText workPlace, EditText mobile, EditText email,
                          EditText pwd) {


        final String emailInput = email.getText().toString().trim();
        final String pwdGot = pwd.getText().toString().trim();
        final String nameGot = name.getText().toString().trim();
        final String surnameGot = surname.getText().toString().trim();
        final String genderGot = gender.getText().toString().trim();
        final String birthDateGot = birthDate.getText().toString().trim();
        final String fromPlaceGot = fromPlace.getText().toString().trim();
        final String workGot = work.getText().toString().trim();
        final String workExpGot = workExp.getText().toString().trim();
        final String workPlaceGot = workPlace.getText().toString().trim();
        final String mailGot = email.getText().toString().trim();
        final String mobileGot = mobile.getText().toString().trim();
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
                            final String phoneGot = user.getPhoneNumber();


                            //generate key for the user that must be unique and the same for database and photo in storage


                            // image address


                            final StorageReference imageReference = imagesFolder.child(gid + ".jpg");


                            imageReference.putBytes(myByt).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {

                                            Contact contact = new Contact(nameGot, surnameGot, genderGot,
                                                    birthDateGot, fromPlaceGot, workGot,
                                                    workExpGot, workPlaceGot, phoneGot, mailGot, uri.toString());
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


