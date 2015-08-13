package com.dnakreatif.wayson.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.dnakreatif.wayson.R;

/**
 * Created by arifraqilla on 6/27/2015.
 */
public class AboutActivity extends Activity {

    private EditText inputFullName;
    private EditText inputUsername;
    private EditText inputEmail;
    private EditText inputPassword;
    private Button btnRegister;
    private Button btnLinkToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        inputUsername  = (EditText) findViewById(R.id.username);
        inputFullName  = (EditText) findViewById(R.id.fullname);
        inputEmail     = (EditText) findViewById(R.id.email);
        inputPassword  = (EditText) findViewById(R.id.password);
        btnRegister    = (Button) findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLogin);
    }
}
