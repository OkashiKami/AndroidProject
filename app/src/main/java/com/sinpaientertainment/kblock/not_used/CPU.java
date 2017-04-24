package com.sinpaientertainment.kblock.not_used;
/*
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.sinpaientertainment.heapstack.R;
import com.sinpaientertainment.heapstack.managers.ImageTaskManager;
import com.sinpaientertainment.heapstack.vars;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.sinpaientertainment.heapstack.vars.Scene.SplashHide;

public class CPU extends AppCompatActivity {
    public static CPU context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main);
        handelSceneTransfer(vars.Scene.SplashShow);
        // Account Initialization
        if(!result(vars.account.init(this))) return;
        if(!result(vars.account.login.init(this))) return;
        if(!result(vars.account.register.init(this))) return;
        if(!result(vars.toolbar.init(this))) return;
        if(!result(vars.search.init(this))) return;
        if(!result(vars.chat.init(this))) return;
        if(!result(vars.backend.init(this))) return;
        if(!result(vars.account.check(this))) { /*Do Nothing */ /* }
        if(!result(vars.drawer.init(this)))
        // Start Update
        Update();

    }

    public boolean result (Object[] obj) {
        if((boolean)obj[1]) { System.err.println((String)obj[0]); return true; }
        else { System.err.println((String)obj[0]); return false; }
    }


    public void Update() {
       BackendlessUser user =Backendless.UserService.CurrentUser();
        if(user != null){
            String wantUsername = (String) user.getProperty("username");
            String haveUsername = ((TextView)findViewById(R.id.drawerUsernameTV)).getText().toString();
            if(!haveUsername.equals(wantUsername))
            {
                ((TextView)findViewById(R.id.drawerUsernameTV)).setText(wantUsername);
            }
            String wantEmail = user.getEmail();
            String haveEmail = ((TextView)findViewById(R.id.drawerEmailTV)).getText().toString();
            if(!haveEmail.equals(wantEmail))
            {
                ((TextView)findViewById(R.id.drawerEmailTV)).setText(wantEmail);
            }
            try{
                CircleImageView imageView = (CircleImageView) findViewById(R.id.profileimage);
                new ImageTaskManager(imageView).execute(user.getProperty("profileimage").toString());
            }
            catch (Exception e){
                System.out.println(e.getCause());
            }
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Update();
            }
        }, 100);
    }
    public void makeToast(String sender, String message) {
        if(sender.equals("") && !message.equals("")){
            Toast.makeText(CPU.this, message, Toast.LENGTH_LONG).show();
        }
        else if(!sender.equals("") && !message.equals("")){
            System.out.println("["+sender+"]: " + message);
            Toast.makeText(CPU.this, message, Toast.LENGTH_LONG).show();
        }
        else if(sender.equals("") && message.equals("")) {
            System.out.print("the sender and the message line is empty");
        }
    }
    public void handelSceneTransfer (vars.Scene scene){
        View v;
        switch (scene)
        {
            // Show the splash Screen
            case SplashShow:
                v = findViewById(R.id.splashView);
                if(v != null) v.setVisibility(View.VISIBLE);
                findViewById(R.id.map).setVisibility(View.GONE);
                break;
            // show Login page
            case Account_Login:
                v = vars.account.layout;
                if(v != null)
                {
                    vars.account.layout.setVisibility(View.VISIBLE);
                    vars.account.login.layout.setVisibility(View.VISIBLE);
                    vars.account.register.layout.setVisibility(View.GONE);
                    findViewById(R.id.main_home).setVisibility(View.GONE);
                    findViewById(R.id.map).setVisibility(View.GONE);
                }
                break;
            // show Registration page
            case Account_Register:
                v = vars.account.layout;
                if(v != null)
                {
                    vars.account.layout.setVisibility(View.VISIBLE);
                    vars.account.login.layout.setVisibility(View.GONE);
                    vars.account.register.layout.setVisibility(View.VISIBLE);
                    findViewById(R.id.map).setVisibility(View.GONE);
                }
                break;
            case Maps:
            {

//                 v = findViewById(R.id.map);
//                if(v != null )
//                    v.setVisibility(View.VISIBLE);
//                findViewById(R.id.main_home).setVisibility(View.GONE);

                Intent myIntent = new Intent(CPU.this, MapView.class);
                startActivity(myIntent);

            }
            break;
            // show Home Page
            case Home: {
                vars.account.layout.setVisibility(View.GONE);
                vars.account.login.layout.setVisibility(View.GONE);
                vars.account.register.layout.setVisibility(View.VISIBLE);
                findViewById(R.id.main_home).setVisibility(View.VISIBLE);
                findViewById(R.id.map).setVisibility(View.GONE);
            }
                break;
            // Hide the splash screen
            case SplashHide:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        View v = findViewById(R.id.splashView);
                        if(v != null )v.setVisibility(View.GONE);
                        findViewById(R.id.map).setVisibility(View.GONE);
                    }
                }, 5000);
                break;
        }
    }
    // All account related task
    public void handelLogin(EditText email, EditText password, final Switch remember){
        handelSceneTransfer(vars.Scene.SplashShow);
        InputMethodManager imm = (InputMethodManager)CPU.context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(vars.account.login.layout.getWindowToken(), 0);

        Backendless.UserService.login(email.getText().toString(), password.getText().toString(), new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser user) {
                if(remember.isChecked())
                {
                    //Save userdata in shared preferences
                    vars.sharedPrefs.edit().putBoolean("account_HasAccount", true).apply();
                    vars.sharedPrefs.edit().putString("account_Email", user.getEmail()).apply();
                    vars.sharedPrefs.edit().putString("account_Password", user.getPassword()).apply();
                    vars.sharedPrefs.edit().putBoolean("account_Remember", remember.isChecked()).apply();

                    // Switch current view
                    handelSceneTransfer(vars.Scene.Home);
                    handelSceneTransfer(SplashHide);
                }
                else
                {
                    // Switch current view
                    handelSceneTransfer(vars.Scene.Home);
                    handelSceneTransfer(SplashHide);

                    // Clear login screen input data
                    vars.account.login.clear();
                }
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                makeToast("Backend", fault.getMessage());
                handelSceneTransfer(SplashHide);
            }
        });
    }
    public void handelRegistration(final EditText rFirstname, final EditText rLastname, final EditText rUsername, EditText rEmail, final EditText rPassword1, final EditText rPassword2){
        InputMethodManager imm = (InputMethodManager)CPU.context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(vars.account.register.layout.getWindowToken(), 0);


        BackendlessUser user = new BackendlessUser();
        user.setProperty("firstname", rFirstname.getText().toString());
        user.setProperty("lastname", rLastname.getText().toString());
        user.setProperty("username", rUsername.getText().toString());
        user.setEmail(rEmail.getText().toString());
        if(rPassword1.getText().toString().equals(rPassword2.getText().toString()) && rPassword1.getText().toString().length() > 6)
        {
            Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
                @Override
                public void handleResponse(BackendlessUser userRenault) {
                    handelSceneTransfer(vars.Scene.Account_Login);
                    vars.account.register.clear();
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    makeToast("Backend", fault.getMessage());
                }
            });
        }
    }
    // Menu Inflater
    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        return true;
    }
    @Override public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.app_bar_search:
                findViewById(R.id.searchview).setVisibility(View.VISIBLE);
                return true;
            case R.id.app_bar_settings:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    // Logout of application
    public void handelLogout()
    {
        handelSceneTransfer(vars.Scene.SplashShow);
        DrawerLayout dLayout = (DrawerLayout) findViewById(R.id.mainDrawerLayout);
        dLayout.closeDrawers();
        Backendless.UserService.logout(new AsyncCallback<Void>() {
            @Override
            public void handleResponse(Void aVoid) {

                // Clear all saved Data
                vars.sharedPrefs.edit().putBoolean("account_HasAccount", false).apply();
                vars.sharedPrefs.edit().putString("account_Email", null).apply();
                vars.sharedPrefs.edit().putString("account_Password", null).apply();
                vars.sharedPrefs.edit().putBoolean("account_Remember", true).apply();
                // Rerun to Login Screen
                handelSceneTransfer(vars.Scene.SplashShow);
                handelSceneTransfer(vars.Scene.Account_Login);
                handelSceneTransfer(SplashHide);
                handelSceneTransfer(SplashHide);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                makeToast("Backend", fault.getMessage());
                handelSceneTransfer(SplashHide);
            }
        });
    }
    //This code will search the application database for any data in the database
    public void handelSearch(String query) {

    }
}
*/