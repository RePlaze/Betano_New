package com.example.betano;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MainFragment.OnPlayClickListener {

    private static final String PREFS_NAME = "MyPrefs";
    private static final String TUTORIAL_SHOWN_KEY = "TutorialShown";
    private static final String PREF_FIRST_LAUNCH = "FirstLaunch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout startGameButton = findViewById(R.id.start_game_layout);
        RelativeLayout recordButton = findViewById(R.id.record_layout);
        RelativeLayout exitButton = findViewById(R.id.exit_layout);

        startGameButton.setOnClickListener(this);
        recordButton.setOnClickListener(this);
        exitButton.setOnClickListener(this);

        // Check if the tutorial has been shown before
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean firstLaunch = preferences.getBoolean(PREF_FIRST_LAUNCH, true);
        if (firstLaunch) {
            showOnboardingDialog(preferences);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_game_layout:
                // Start Game button clicked
                startNewGame();
                break;
            case R.id.record_layout:
                // Record button clicked
                Intent recordIntent = new Intent(MainActivity.this, RecordActivity.class);
                startActivity(recordIntent);
                break;
            case R.id.exit_layout:
                // Exit button clicked
                finish();
                break;
        }
    }

    private void startNewGame() {
        // Clear the game score in shared preferences
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        preferences.edit().remove("GameScore").apply();

        // Start the game activity
        Intent gameIntent = new Intent(MainActivity.this, GameActivity.class);
        startActivity(gameIntent);
    }

    private void showOnboardingDialog(SharedPreferences prefs) {
        // Display onboarding screens as a pop-up
        View onboardingView = getLayoutInflater().inflate(R.layout.activity_onboarding, null);

        // Customize onboarding screens, add animations, etc.

        // Set up ViewPager2
        ViewPager2 viewPager = onboardingView.findViewById(R.id.viewPager);
        DotsIndicator dotsIndicator = onboardingView.findViewById(R.id.dotsIndicator);

        List<OnboardingItem> onboardingItems = new ArrayList<>();
        onboardingItems.add(new OnboardingItem(R.drawable.card1, getString(R.string.title_1), getString(R.string.description_1)));
        onboardingItems.add(new OnboardingItem(R.drawable.card3, getString(R.string.title_2), getString(R.string.description_2)));
        onboardingItems.add(new OnboardingItem(R.drawable.card2, getString(R.string.title_3), getString(R.string.description_3)));

        OnboardingAdapter onboardingAdapter = new OnboardingAdapter(onboardingItems);
        viewPager.setAdapter(onboardingAdapter);
        dotsIndicator.setViewPager2(viewPager);

        // Show the onboarding view as a pop-up
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this, R.style.AlertDialog);
        builder.setView(onboardingView)
                .setPositiveButton("Got it", (dialog, which) -> {
                    // Onboarding completed, store the launch status
                    prefs.edit().putBoolean(PREF_FIRST_LAUNCH, false).apply();

                    // Add MainFragment with animation
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    MainFragment mainFragment = new MainFragment();
                    mainFragment.setOnPlayClickListener(this);

                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);
                    transaction.replace(R.id.fragment_container, mainFragment);
                    transaction.commit();
                })
                .setCancelable(false)
                .setOnDismissListener(dialog -> {
                    // Onboarding dismissed, store the launch status
                    prefs.edit().putBoolean(PREF_FIRST_LAUNCH, false).apply();

                    // Add MainFragment with animation
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    MainFragment mainFragment = new MainFragment();
                    mainFragment.setOnPlayClickListener(MainActivity.this);

                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);
                    transaction.replace(R.id.fragment_container, mainFragment);
                    transaction.commit();
                })
                .show();
    }

    public void openStore(View view) {
        // Start the StoreActivity
        Intent intent = new Intent(MainActivity.this, Store.class);
        startActivity(intent);
    }

    @Override
    public void onPlayClick() {
        startNewGame();
    }
}
