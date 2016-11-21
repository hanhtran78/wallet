package hanhit.mywalletapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import hanhit.mywalletapp.adapter.AdapterGridViewLogin;

public class LoginActivity extends AppCompatActivity {

    private final String[] mStrNumber = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "", "0", ""};
    private GridView mGridView;
    private TextView mMessageLogin;
    private RadioButton mRadButton1, mRadButton2, mRadButton3, mRadButton4;
    private TextView mTitleLogin;

    private String mCodeInput = "";
    private String mNewCode = "";
    private String mConfirmCode = "";
    private String pass = "";
    private int countInput = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWidgets();
        mTitleLogin.setText(R.string.title_login);

        if (getLoginStateBefore()) {
            mMessageLogin.setText(R.string.title_enter_your_passcode);
        } else mMessageLogin.setText(R.string.title_set_new_passcode);

    }

    public void saveLogin(String code) {
        SharedPreferences sp = getSharedPreferences("Login", 0);
        SharedPreferences.Editor Ed = sp.edit();
        Ed.putString("Psw", code);
        Ed.commit();
    }

    public boolean getLoginStateBefore() {
        boolean result = false;
        SharedPreferences sp1 = this.getSharedPreferences("Login", 0);
        if (sp1.getString("Psw", null) != null) {
            result = true;
            pass = sp1.getString("Psw", null).toString();
        }
        return result;
    }

    public void startWalletManagerActivity() {
        Intent startActivity = new Intent(LoginActivity.this, WalletManagerActivity.class);
        startActivity(startActivity);
        this.finish();
    }

    public void checkRadioButton(int position) {
        switch (position) {
            case 1:
                mRadButton1.performClick();
                break;
            case 2:
                mRadButton2.performClick();
                break;
            case 3:
                mRadButton3.performClick();
                break;
            case 4:
                mRadButton4.performClick();
                break;
        }
    }

    public void getWidgets() {
        mTitleLogin = (TextView) findViewById(R.id.toolbar_title);
        mMessageLogin = (TextView) findViewById(R.id.txtMessageLogin);
        mGridView = (GridView) findViewById(R.id.gridView);
        mRadButton1 = (RadioButton) findViewById(R.id.radioButton1);
        mRadButton2 = (RadioButton) findViewById(R.id.radioButton2);
        mRadButton3 = (RadioButton) findViewById(R.id.radioButton3);
        mRadButton4 = (RadioButton) findViewById(R.id.radioButton4);

        mGridView.setAdapter(new AdapterGridViewLogin(LoginActivity.this, mStrNumber, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                login(button.getText().toString());
            }
        }));
    }

    private void login(String code) {
        mCodeInput += code;
        checkRadioButton(++countInput);

        if (countInput == 4) {
            if (mMessageLogin.getText().equals(getResources().getString(R.string.title_enter_your_passcode))) {
                if (mCodeInput.equals(pass)) {
                    startWalletManagerActivity();
                } else {
                    Toast.makeText(this, getResources().getString(R.string.msg_login_fail), Toast.LENGTH_SHORT).show();
                    reInputCode();
                }
            } else if (mMessageLogin.getText().equals(getResources().getString(R.string.title_set_new_passcode))) {
                mNewCode = mCodeInput;
                mMessageLogin.setText(R.string.title_confirm_passcode);
                reInputCode();
            } else if (mMessageLogin.getText().equals(getResources().getString(R.string.title_confirm_passcode))) {
                mConfirmCode = mCodeInput;
                if (mNewCode.equals(mConfirmCode)) {
                    saveLogin(mConfirmCode);
                    startWalletManagerActivity();
                } else {
                    reInputCode();
                    Toast.makeText(LoginActivity.this, R.string.msg_confirm_passcode_fail, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void reInputCode() {
        mCodeInput = "";
        countInput = 0;
        mRadButton1.setChecked(false);
        mRadButton2.setChecked(false);
        mRadButton3.setChecked(false);
        mRadButton4.setChecked(false);
    }
}
