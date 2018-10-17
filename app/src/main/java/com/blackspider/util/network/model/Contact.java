package com.blackspider.util.network.model;
/*
 *  ****************************************************************************
 *  * Created by : Arhan Ashik on 10/17/2018 at 12:49 PM.
 *  * Email : ashik.pstu.cse@gmail.com
 *  *
 *  * Last edited by : Arhan Ashik on 10/17/2018.
 *  *
 *  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
 *  ****************************************************************************
 */

import com.google.gson.annotations.SerializedName;

public class Contact {
    private String name;

    @SerializedName("image")
    private
    String profileImage;

    private String phone;
    private String email;

    public String getName() {
        return name;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    /**
     * Checking contact equality against email
     */
    @Override
    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof Contact)) {
            return ((Contact) obj).getEmail().equalsIgnoreCase(email);
        }
        return false;
    }
}
