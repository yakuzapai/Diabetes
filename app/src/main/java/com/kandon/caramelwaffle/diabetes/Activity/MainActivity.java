package com.kandon.caramelwaffle.diabetes.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kandon.caramelwaffle.diabetes.Fragment.MainFragment;
import com.kandon.caramelwaffle.diabetes.R;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;

    int num = 0;
    Context context;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;

    RelativeLayout row1;
    RelativeLayout row2;
    RelativeLayout rowSus;
    RelativeLayout row3;
    RelativeLayout row4;
    RelativeLayout row5;
    RelativeLayout row6;
    RelativeLayout row7;
    RelativeLayout row9;
    RelativeLayout row10;

    private Boolean exit = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main);
        initInstances();
        setInstances();
    }

    private void initInstances() {
        context = MainActivity.this;
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        row1 = (RelativeLayout)findViewById(R.id.row1);
        row2 = (RelativeLayout)findViewById(R.id.row2);
        row3 = (RelativeLayout)findViewById(R.id.row3);
        rowSus = (RelativeLayout)findViewById(R.id.rowSus);
        row4 = (RelativeLayout)findViewById(R.id.row4);
        row5 = (RelativeLayout)findViewById(R.id.row5);
        row6 = (RelativeLayout)findViewById(R.id.row6);
        row7 = (RelativeLayout)findViewById(R.id.row7);

        row9 = (RelativeLayout)findViewById(R.id.row9);
        row10 = (RelativeLayout)findViewById(R.id.row10);
    }

    private void setInstances() {
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this
                ,drawerLayout,
                R.string.openDrawer,
                R.string.closeDrawer
        );
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.contentContainer, MainFragment.newInstance())
                .commit();

        row1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        row2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Information.class);
                startActivity(intent);
            }
        });

        row3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        rowSus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,FoodActivity.class));
            }
        });

        row4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,MedicineActivity.class);
                startActivity(intent);
            }
        });
        row5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ExportActivity.class);
                startActivity(intent);
            }
        });

        row6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=โรงพยาบาล");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
        row7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,CallActivity.class);
                startActivity(intent);
            }
        });

        row9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,RefActivity.class);
                startActivity(intent);
            }
        });

        row10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent intent = new Intent(context,LoadingActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_edit) {
            Intent intent = new Intent(context,Information.class);
            startActivity(intent);
            return true;
        }
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }
}
