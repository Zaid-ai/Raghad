package com.example.hayat.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.hayat.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ImageView side_menu;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private String UID;
    private View nav_HeaderView;
    private TextView txv_user_email, txv_user_name;
    private FragmentTransaction fragmentTransaction;
    private Fragment fragment;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private void creat() {
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        UID = auth.getCurrentUser().getUid().toString();
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.nav_view);
        nav_HeaderView = navigationView.getHeaderView(0);
        txv_user_name = nav_HeaderView.findViewById(R.id.txv_user_name);
        txv_user_email = nav_HeaderView.findViewById(R.id.txv_user_email);
        side_menu = findViewById(R.id.nav_menu);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        creat();
        DocumentReference documentReference = firestore.collection("users").document(UID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                txv_user_email.setText(value.getString("Email"));
                txv_user_name.setText(value.getString("Name"));
            }
        });
        side_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu();
            }
        });
        navigationView.setNavigationItemSelectedListener(this);
    }

    // To open any fragment use this method
    public void openFragment(Fragment fragment, String tag) {
        try {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            if (getSupportFragmentManager().findFragmentById(R.id.frame_main)
                    != fragment) {
                fragmentTransaction.replace(R.id.frame_main, fragment, tag);
                fragmentTransaction.addToBackStack(tag);
                fragmentTransaction.commit();
            }

        } catch (Exception e) {

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                drawerLayout.closeDrawer(GravityCompat.START);
                //openFragment(new HomeFragment(), "HomeFragment");
                break;
            case R.id.nav_secvices:
                drawerLayout.closeDrawer(GravityCompat.START);
                //openFragment(new CatigoriesFragment(), "CatigoriesFragment");
                break;
            case R.id.nav_profile:
                drawerLayout.closeDrawer(GravityCompat.START);
                //openFragment(new ProfileFragment(), "ProfileFragment");
                break;
            case R.id.signout:
                drawerLayout.closeDrawer(GravityCompat.START);
                auth.signOut();
                break;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }

    // side menu control
    private void menu() {
        if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.openDrawer(GravityCompat.START);
        } else
            drawerLayout.closeDrawer(GravityCompat.END);
    }
}
