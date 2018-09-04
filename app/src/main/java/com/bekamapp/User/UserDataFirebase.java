package com.bekamapp.User;

import java.util.List;

public class UserDataFirebase {
    private int ID;
    private List<String> reviews;

    public UserDataFirebase(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public List<String> getReviews() {
        return reviews;
    }

    public void setReviews(List<String> reviews) {
        this.reviews = reviews;
    }
}
