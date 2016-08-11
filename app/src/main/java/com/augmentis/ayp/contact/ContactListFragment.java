package com.augmentis.ayp.contact;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.augmentis.ayp.contact.model.Contact;
import com.augmentis.ayp.contact.model.ContactLab;
import com.augmentis.ayp.contact.model.PictureUtils;

import java.io.File;
import java.util.List;

/**
 * Created by Noppharat on 8/11/2016.
 */
public class ContactListFragment extends Fragment{

    private RecyclerView _contactRecyclerView;
    private ContactAdapter _adapter;
    private File photoFile;
    private Contact _contact;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.contact_list_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_item_new_contact);
    }

    private static final int REQUEST_CONTACT = 11122;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_new_contact:
                _contact = new Contact();
                Intent intent = ContactActivity.newIntent(getActivity(), _contact.getContactId());
                startActivityForResult(intent, REQUEST_CONTACT);
//                ContactLab.getInstance(getActivity()).addContact(contact);


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
        Log.d("CONTACT", "----------------------------------------------------back to list");
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contact_list, container, false);

        _contactRecyclerView = (RecyclerView) v.findViewById(R.id.contact_recycler_view);
        _contactRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        updateUI();
        return v;
    }

    public void updateUI(){
        ContactLab contactLab = ContactLab.getInstance(getActivity());
        List<Contact> contacts = contactLab.getContacts();
        if(_adapter == null){
            _adapter = new ContactAdapter(contacts, this);
            _contactRecyclerView.setAdapter(_adapter);
        }else{
            _adapter.setContacts(contactLab.getContacts());
            _adapter.notifyDataSetChanged();
        }
    }

    private class ContactHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        public ImageView _photoView;
        public TextView _nameTextView;

        Contact _contact;
        int _position;

        public ContactHolder(View itemView) {
            super(itemView);
            _photoView = (ImageView) itemView.findViewById(R.id.list_item_image_view_card);
            _nameTextView = (TextView) itemView.findViewById(R.id.list_item_contact_name_text_view_card);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void bind(Contact contact, int position){
            _contact = contact;
            photoFile = ContactLab.getInstance(getActivity()).getPhotoFile(_contact);
            Bitmap bitmap = PictureUtils.getScaleBitmap(photoFile.getPath(), getActivity());
            _photoView.setImageBitmap(bitmap);

            _nameTextView.setText(_contact.getContactName());
            _position = position;
        }

        @Override
        public void onClick(View view) {

        }

        @Override
        public boolean onLongClick(View view) {
            Intent intent = ContactActivity.newIntent(getActivity(), _contact.getContactId());
            startActivity(intent);
            return true;
        }
    }

    private class ContactAdapter extends RecyclerView.Adapter<ContactHolder>{
        private List<Contact> _contacts;
        private Fragment _fragment;

        public ContactAdapter(List<Contact> contacts, Fragment fragment){
            _contacts = contacts;
            _fragment = fragment;
        }

        public void setContacts(List<Contact> contacts){
            _contacts = contacts;
        }

        @Override
        public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View v = layoutInflater.inflate(R.layout.list_item_contact_card, parent, false);
            return new ContactHolder(v);
        }

        @Override
        public void onBindViewHolder(ContactHolder holder, int position) {
            Contact contact = _contacts.get(position);
            holder.bind(contact, position);
        }

        @Override
        public int getItemCount() {
            return _contacts.size();
        }
    }
}
