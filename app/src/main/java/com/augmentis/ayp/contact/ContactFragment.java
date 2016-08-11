package com.augmentis.ayp.contact;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.augmentis.ayp.contact.model.Contact;
import com.augmentis.ayp.contact.model.ContactLab;
import com.augmentis.ayp.contact.model.PictureUtils;

import java.io.File;
import java.util.UUID;

/**
 * Created by Noppharat on 8/11/2016.
 */
public class ContactFragment extends Fragment{

    private static final String CONTACT_ID = "ContactFragment.CONTACT_ID";
    private static final int REQUEST_CAPTURE_PHOTO = 1111;

    private Contact contact;
    private File photoFile;
    private EditText nameEditText;
    private EditText telEditText;
    private EditText emailEditText;
    private ImageView photoView;
    private ImageButton photoButton;
    private Button deleteButton;

    public ContactFragment(){

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
            contact.setContactName(nameEditText.getText().toString());
            contact.setContactTelNum(telEditText.getText().toString());
            contact.setContactEmail(emailEditText.getText().toString());
            if(ContactLab.getInstance(getActivity()).getContactById(contact.getContactId()) == null){
                ContactLab.getInstance(getActivity()).addContact(contact);
            }else{
                ContactLab.getInstance(getActivity()).updateContact(contact);
            }
            Log.d("CONTACT", "set-----------------------");
    }

    public static ContactFragment newInstance(UUID contactId){
        Bundle args = new Bundle();
        args.putSerializable(CONTACT_ID, contactId);
        ContactFragment contactFragment = new ContactFragment();
        contactFragment.setArguments(args);
        return contactFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID contactId = (UUID) getArguments().getSerializable(CONTACT_ID);
        contact = ContactLab.getInstance(getActivity()).getContactById(contactId);
        if(contact == null){
            contact = new Contact();
        }
//        if(ContactLab.getInstance(getActivity()).getPhotoFile(contact) == null){
//
//        }else{
//            photoFile = ContactLab.getInstance(getActivity()).getPhotoFile(contact);
//        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contact, container, false);

        photoView = (ImageView) v.findViewById(R.id.contact_photo);

        photoButton = (ImageButton) v.findViewById(R.id.contact_camera);
//        PackageManager packageManager = getActivity().getPackageManager();
//        final Intent captureImageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        boolean canTakePhoto = photoButton != null
//                && captureImageIntent.resolveActivity(packageManager) != null;
//        if(canTakePhoto){
//            Uri uri = Uri.fromFile(photoFile);
//            captureImageIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//        }
//        photoButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivityForResult(captureImageIntent, REQUEST_CAPTURE_PHOTO);
//            }
//        });

        nameEditText = (EditText) v.findViewById(R.id.contact_name_edit_text);
        nameEditText.setText(contact.getContactName());

        telEditText = (EditText) v.findViewById(R.id.contact_tel_edit_text);
        telEditText.setText(contact.getContactTelNum());

        emailEditText = (EditText) v.findViewById(R.id.contact_email_edit_text);
        emailEditText.setText(contact.getContactEmail());

        deleteButton = (Button) v.findViewById(R.id.contact_delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContactLab.getInstance(getActivity()).deleteContact(contact.getContactId());
                getActivity().finish();
            }
        });

        updatePhotoView();
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK){
            return;
        }

        if(requestCode == REQUEST_CAPTURE_PHOTO){
            updatePhotoView();
        }
    }

    public void updatePhotoView(){
        if(photoFile == null || !photoFile.exists()){
            photoView.setImageBitmap(null);
        }else{
            Bitmap bitmap = PictureUtils.getScaleBitmap(photoFile.getPath(), getActivity());
            photoView.setImageBitmap(bitmap);
        }
    }
}
