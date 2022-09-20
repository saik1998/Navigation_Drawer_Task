package com.firstapp.navigation_drawer_task.ui.callcontacts;

import android.net.Uri;

public class ContactModel {
    Uri imageUri;
    String name,number,image;

    public ContactModel(Uri imageUri, String name, String number, String image) {
        this.imageUri = imageUri;
        this.name = name;
        this.number = number;
        this.image = image;
    }

    public Uri getImageUri() {

        return imageUri;
    }

    public void setImageUri(Uri imageUri) {

        this.imageUri = imageUri;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getNumber() {

        return number;
    }

    public void setNumber(String number) {

        this.number = number;
    }

    public String getImage() {

        return image;
    }

    public void setImage(String image) {

        this.image = image;
    }

    public ContactModel(String name, String number, String image) {
        this.imageUri = imageUri;
        this.name = name;
        this.number = number;
        this.image = image;
    }
}
