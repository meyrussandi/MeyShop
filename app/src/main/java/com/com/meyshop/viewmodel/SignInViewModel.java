package com.com.meyshop.viewmodel;

import com.com.meyshop.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SignInViewModel extends ViewModel {
    private final MutableLiveData<User> data = new MutableLiveData<>();
    DatabaseReference reference;

    public boolean checkUser(final User user){
        if(!user.getUsername().equals("")&&!user.getPassword().equals("")){
            return user.getUsername().equalsIgnoreCase("mey") && user.getUsername().equalsIgnoreCase("rosandi");
        }
        return false;
    }

    public MutableLiveData<User> getUser(){
        return data;
    }

}
