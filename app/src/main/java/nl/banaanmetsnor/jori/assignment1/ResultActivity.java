package nl.banaanmetsnor.jori.assignment1;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

public class ResultActivity extends AppCompatActivity {

    private static final String TAG = ResultActivity.class.getSimpleName();
    TextView phoneNumberResultView;
    String phoneNumber = "";
    TextView phoneNumberRegionView;
    String phoneNumberRegionCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
    }

    protected void onStart(){
        super.onStart();
        //Set the phoneNumber in the phoneNumberResultView
        phoneNumberResultView = (TextView)findViewById(R.id.phoneNumberResultView);
        phoneNumber = getIntent().getStringExtra("phoneNumber");
        phoneNumberResultView.setText(phoneNumber);
        //Set the Country code for the phoneNumber in the phoneNumberCountryView
        phoneNumberRegionView = (TextView)findViewById(R.id.phoneNumberRegionView);
        phoneNumberRegionCode = getRegionCode(phoneNumber);
        phoneNumberRegionView.setText(phoneNumberRegionCode);
    }

    public void onBackButtonClick(View v){
        finish();
    }

    public void onCallButtonClick(View v){
        Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        startActivity(dialIntent);
    }

    public String getRegionCode(String phoneNumberString){
        Phonenumber.PhoneNumber phoneNumber;
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        String regionCode = "Not available";
        try {
            phoneNumber = phoneNumberUtil.parse(phoneNumberString, "");
            regionCode = phoneNumberUtil.getRegionCodeForNumber(phoneNumber) + "";
        } catch (NumberParseException e) {
            System.err.println(e);
            Log.e(TAG, e.toString());
        } catch (Exception e) {
            System.err.println(e);
            Log.e(TAG, e.toString());
        }
        return regionCode;
    }
}
