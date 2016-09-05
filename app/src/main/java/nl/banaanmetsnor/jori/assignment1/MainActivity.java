package nl.banaanmetsnor.jori.assignment1;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onNumberButtonClick(View v){
        Button button = (Button)v;
        addCharacterToPhoneNumberView((String)button.getText());
    }

    public void onClearButtonClick(View v){
        clearPhoneNumberView();
    }

    public void onPlusButtonClick(View v){
        Button button = (Button)v;
        addCharacterToPhoneNumberView((String)button.getText());
    }

    public void onShowCountryClick(View v){
        //Check if the value is a valid phonenumber
        //If the phonenumber is valid show the resultActivity
        TextView phoneNumberView = (TextView)findViewById(R.id.phoneNumberView);
        String phoneNumber = (String)phoneNumberView.getText();
        if(isValidPhoneNumber(phoneNumber)){
            Intent resultActivityIntent = new Intent("nl.banaanmetsnor.jori.assignment1.ResultActivity");
            resultActivityIntent.putExtra("phoneNumber", phoneNumber);
            startActivity(resultActivityIntent);
        }else{
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Wrong input");
            alertDialog.setMessage("The phonenumber couldn't be recognised.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    dialog.dismiss();
                }
            });
            alertDialog.show();
        }
    }

    public void clearPhoneNumberView(){
        TextView phoneNumberView = (TextView)findViewById(R.id.phoneNumberView);
        phoneNumberView.setText("");
    }

    public void addCharacterToPhoneNumberView(String number){
        TextView phoneNumberView = (TextView)findViewById(R.id.phoneNumberView);
        String currentPhoneNumber = (String)phoneNumberView.getText();
        phoneNumberView.setText(currentPhoneNumber + number);
        //Check if there's more then two characters in the string
        String currenText = (String)phoneNumberView.getText();
        if(currenText.length() > 1){
            //Check if the first two number are zeroes.
            if(hasTwoLeadingZeroes(currenText)){
                //If so convert the two leading zeroes to a plus and set the textView
                phoneNumberView.setText(convertLeadingTwoZeroesToPlus(currenText));
            }
        }
    }

    public Boolean hasTwoLeadingZeroes(String s){
        return (s.charAt(0) == '0' && s.charAt(1) == '0');
    }

    public String convertLeadingTwoZeroesToPlus(String s){
        String numberWithoutFirstTwoCharacters = s.substring(2);
        return "+" + numberWithoutFirstTwoCharacters;
    }

    public Boolean isValidPhoneNumber(String s){
        Boolean valid = false;
        Phonenumber.PhoneNumber phoneNumber;
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        try {
            phoneNumber = phoneNumberUtil.parse(s, "");
            valid = true;
        } catch (NumberParseException e) {
            System.err.println(e);
            Log.e(TAG, e.toString());
        } catch (Exception e) {
            System.err.println(e);
            Log.e(TAG, e.toString());
        }
        return valid;
    }
}
