package com.augmentis.ayp.contact;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class ContactActivity extends SingleFragmentActivity {

    protected static final String CONTACT_ID = "contactActivity.contactId";

    public static Intent newIntent(Context context, UUID id){
        Intent intent = new Intent(context, ContactActivity.class);
        intent.putExtra(CONTACT_ID, id);
        return intent;
    }


    @Override
    protected Fragment onCreateFragment() {
        UUID contactId = (UUID) getIntent().getSerializableExtra(CONTACT_ID);
        Fragment fragment = ContactFragment.newInstance(contactId);
        return fragment;
    }
}
