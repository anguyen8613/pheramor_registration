package b.android.pheramorregistration;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

    String email;
    String password;
    String fullName;
    int zipCode;
    int Height;
    String gender;
    Date dateOfBirth;
    boolean maleInterest;
    boolean femaleInterest;
    int minAge;
    int maxAge;
    String race;
    String religion;
    Bitmap profilePicture;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public int getHeight() {
        return Height;
    }

    public void setHeight(int height) {
        Height = height;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isMaleInterest() {
        return maleInterest;
    }

    public void setMaleInterest(boolean maleInterest) {
        this.maleInterest = maleInterest;
    }

    public boolean isFemaleInterest() {
        return femaleInterest;
    }

    public void setFemaleInterest(boolean femaleInterest) {
        this.femaleInterest = femaleInterest;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public Bitmap getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Bitmap profilePicture) {
        this.profilePicture = profilePicture;
    }
}
