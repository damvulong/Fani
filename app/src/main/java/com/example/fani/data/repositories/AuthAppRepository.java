

package com.example.fani.data.repositories;

import android.app.Application;
import android.os.SystemClock;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.fani.utils.Constants;
import com.example.fani.utils.Utilities;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthAppRepository {

    private Application application;

    private FirebaseAuth firebaseAuth;
    private MutableLiveData<FirebaseUser> userLiveData;
    private MutableLiveData<Boolean> loggedOutLiveData;

    private long mLastClickTime = 0;

    public AuthAppRepository(Application application) {
        this.application = application;
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.userLiveData = new MutableLiveData<>();
        this.loggedOutLiveData = new MutableLiveData<>();

        if (firebaseAuth.getCurrentUser() != null) {
            userLiveData.postValue(firebaseAuth.getCurrentUser());
            loggedOutLiveData.postValue(false);
        }
    }


    public void login(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(application.getMainExecutor(), task -> {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < Constants.TIME_REPLY) {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();
                    if (task.isSuccessful()) {
                        userLiveData.postValue(firebaseAuth.getCurrentUser());
                        Utilities.showToast(application.getApplicationContext(), "Login Successfully!");
                    } else {
                        Utilities.showToast(application.getApplicationContext(), "Login Failure: " + task.getException().getMessage());
                    }
                });
    }

    public void register(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(application.getMainExecutor(), task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        firebaseUser.sendEmailVerification()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Utilities.showToast(application.getApplicationContext(), "Veryfication Email has been sent");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Utilities.showToast(application.getApplicationContext(), "onFailure: Email not sent " + e.getMessage());
                                    }
                                });
                        userLiveData.postValue(firebaseAuth.getCurrentUser());
                    } else {
                        Utilities.showToast(application.getApplicationContext(), "Registration Failure: " + task.getException().getMessage());
                    }
                });
    }

    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }

    public MutableLiveData<Boolean> getLoggedOutLiveData() {
        return loggedOutLiveData;
    }

    public void logOut() {
        firebaseAuth.signOut();
        loggedOutLiveData.postValue(true);
    }
}
