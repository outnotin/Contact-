package com.augmentis.ayp.contact;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ContactListActivity extends SingleFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_contact_list);
    }

    @Override
    protected Fragment onCreateFragment() {
        return new ContactListFragment();
    }
}
