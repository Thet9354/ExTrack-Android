package sp.example.extrack.NavFragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.extrack.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sp.example.extrack.CaptureAct;
import sp.example.extrack.MainActivity;

public class AddTransaction extends Fragment {

    private RadioGroup rg_transactionFlow, rg_transactionPurpose;

    private EditText editTxt_title, editTxt_amount, editTxt_desc;

    private ImageView btn_uploadTransaction;


    private MaterialButton btn_recordDetails, btn_barCodeScanner;

    private static final int requestCamera1 = 12;

    private Context mContext;

    private Bitmap bitmap, defaultBitmap;


    private String mTransactionFlow, mTitle, mAmount, mPurpose, mDesc;

    private int mTransactionImg;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add_transaction, container, false);

        mContext = getActivity();

        FirebaseApp.initializeApp(requireContext());

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
    }

    private void findViews(View v) {

        //RadioGroup
        rg_transactionFlow = v.findViewById(R.id.rg_transactionFlow);
        rg_transactionPurpose = v.findViewById(R.id.rg_transactionPurpose);

        //EditText
        editTxt_title = v.findViewById(R.id.editTxt_title);
        editTxt_amount = v.findViewById(R.id.editTxt_amount);
        editTxt_desc = v.findViewById(R.id.editTxt_desc);

        //Imageview
        btn_uploadTransaction = v.findViewById(R.id.btn_uploadTransaction);


        //Material Button
        btn_recordDetails = v.findViewById(R.id.btn_recordDetails);
        btn_barCodeScanner = v.findViewById(R.id.btn_barCodeScanner);

        initUI();

        pageDirectories();
    }

    private void pageDirectories() {
        btn_barCodeScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanBarCode();
            }
        });

        btn_uploadTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Permission is not granted, request the permission
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.CAMERA},
                            requestCamera1);
                } else {

                    Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(camera, requestCamera1);
                }



            }

        });


        rg_transactionPurpose.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = group.findViewById(checkedId);
                mPurpose = radioButton.getText().toString();

                authenticateBarCodeScanner();
            }
        });

        rg_transactionFlow.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = group.findViewById(checkedId);
                mTransactionFlow = radioButton.getText().toString();
            }
        });

        btn_recordDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mTitle = editTxt_title.getText().toString();
                mAmount = editTxt_amount.getText().toString();
                mDesc = editTxt_desc.getText().toString();

                bitmap = ((BitmapDrawable) btn_uploadTransaction.getDrawable()).getBitmap();


                validateTransactionFlow();
                validateTitle();
                validateAmount();
                validatePurpose();
                validateDescription();
                validatePic();
                validateLocation();
                validateInput();

            }
        });


        Bundle bundle = getArguments();

    }

    private void initUI() {
        defaultBitmap = ((BitmapDrawable) btn_uploadTransaction.getDrawable()).getBitmap();
    }

    private void authenticateBarCodeScanner() {
        if (mPurpose.equals("Food and Drinks") | mPurpose.equals("Shopping") | mPurpose.equals("Groceries"))
        {
            btn_barCodeScanner.setVisibility(View.VISIBLE);
        }
        else
            btn_barCodeScanner.setVisibility(View.GONE);
    }

    private void scanBarCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up to on flash");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null)
        {
            Toast.makeText(mContext, result.getContents(), Toast.LENGTH_SHORT).show();
            //TODO: Retrieve the bar code content and setText it to the Title, amount, and description

            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("Result");
            builder.setMessage(result.getContents());
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
        }
    });

    private void validateInput() {

        if (!validateTransactionFlow() | !validateTitle() | !validateAmount() | !validatePurpose() | !validateDescription() | !validatePic() | !validateLocation())
        {
            return;
        }
        else
        {
            FirebaseDatabase database = FirebaseDatabase.getInstance("Add your own url here");
            DatabaseReference databaseReference  = database.getReference().child("Users");

            String userExpense = "User's Expenses";
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.child("Expenditure").hasChild(userExpense))
                    {
                        long numOfTransactions = snapshot.child("Expenditure").child(userExpense).getChildrenCount();
                        System.out.println(numOfTransactions);
                        for (int i = (int) numOfTransactions; i<=100; i++)
                        {
                            String transactionID = "Transaction " + (numOfTransactions + 1);

                            databaseReference.child("Expenditure").child(userExpense).child(transactionID).child("Flow").setValue(mTransactionFlow);
                            databaseReference.child("Expenditure").child(userExpense).child(transactionID).child("Title").setValue(mTitle);
                            databaseReference.child("Expenditure").child(userExpense).child(transactionID).child("Amount").setValue(mAmount);
                            databaseReference.child("Expenditure").child(userExpense).child(transactionID).child("Purpose").setValue(mPurpose);

                            databaseReference.child("Expenditure").child(userExpense).child(transactionID).child("Description").setValue(mDesc);
                            databaseReference.child("Expenditure").child(userExpense).child(transactionID).child("Image").setValue(mTransactionImg);

                        }
                    }
                    else
                    {
                        databaseReference.child("Expenditure").child(userExpense).child("Transaction 1").child("Flow").setValue(mTransactionFlow);
                        databaseReference.child("Expenditure").child(userExpense).child("Transaction 1").child("Title").setValue(mTitle);
                        databaseReference.child("Expenditure").child(userExpense).child("Transaction 1").child("Amount").setValue(mAmount);
                        databaseReference.child("Expenditure").child(userExpense).child("Transaction 1").child("Purpose").setValue(mPurpose);

                        databaseReference.child("Expenditure").child(userExpense).child("Transaction 1").child("Description").setValue(mDesc);
                        databaseReference.child("Expenditure").child(userExpense).child("Transaction 1").child("Image").setValue(mTransactionImg);

                    }



                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            Toast.makeText(mContext, "Transaction was successful", Toast.LENGTH_SHORT).show();
            TransactionAlert();

        }
    }

    private void TransactionAlert() {
        //if the balance is close to exceeding the budget or already exceeding, send sms to the user
        String alertMessage = "Hey nigga";
        String phoneNumber = "93542856";


        SmsManager smsManager = SmsManager.getDefault();

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(Manifest.permission.SEND_SMS)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                requestPermissions(new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);
            }
        } else {
            // Permission has already been granted
            //Send alert
            alertMessage = "Hey There" + " !" +" \n" +
                    "Your most recent transaction was successful";
            smsManager.sendTextMessage(phoneNumber, null, alertMessage, null, null);
        }



        System.out.println("Passed through");
        startActivity(new Intent(mContext, MainActivity.class));
    }

    private boolean validateLocation() {

        return true;
    }

    private boolean validatePic() {
        if (bitmap.equals(defaultBitmap))
        {
            //User did not upload any pics
            Toast.makeText(mContext, "Come on man, it wouldn't hurt to upload a picture", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
            return true;
    }

    private boolean validateDescription() {

        if (mDesc.isEmpty())
        {
            editTxt_desc.setError("Required");
            return false;
        }
        else
            return true;
    }

    private boolean validatePurpose() {

        if (mPurpose.isEmpty())
        {
            Toast.makeText(mContext, "Please select a transaction purpose ", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
            return true;
    }

    private boolean validateAmount() {

        if (mAmount.isEmpty())
        {
            editTxt_amount.setError("Required");
            return false;
        }
        else
            return true;
    }

    private boolean validateTitle() {

        //Regex pattern to allow only alphabets
        Pattern regexName = Pattern.compile("^[a-zA-Z]+$");
        Matcher matcher = regexName.matcher(mTitle);

        if (mTitle.isEmpty())
        {
            editTxt_title.setError("Required");
            return false;
        }
        else if (!matcher.matches())
        {
            editTxt_title.setError("Invalid title");
            return false;
        }
        else
            return true;
    }

    private boolean validateTransactionFlow() {

        if (mTransactionFlow.isEmpty())
        {
            Toast.makeText(mContext, "Please enter your transaction flow", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
            return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == requestCamera1)
        {
            Bitmap imgBitMap = (Bitmap)data.getExtras().get("data");
            int newWidth = 300;
            int newHeight = 200;
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(imgBitMap, newWidth, newHeight, false);
            btn_uploadTransaction.setImageBitmap(resizedBitmap);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Permission has been granted, start the camera activity
        } else {
            // Permission has been denied, show an explanation or disable the feature that requires the permission
        }
    }
}