package com.example.programcontentprovider;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class ContactAdaptor extends RecyclerView.Adapter<ContactAdaptor.ContactViewHolder> {
    public ContactAdaptor(List<Contact> contactList) {
        this.contactList = contactList;
    }

    private List<Contact> contactList;
    @NonNull
    @Override
    public ContactAdaptor.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //  create view
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_card_contact,viewGroup,false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdaptor.ContactViewHolder contactViewHolder, int i) {
        //binding view with data
        Contact contact = contactList.get(i);
        contactViewHolder.tvNumber.setText(""+contact.getNumber());
        contactViewHolder.tvName.setText(""+contact.getName());
    }

    @Override
    public int getItemCount() {
        //returning size of item
        return contactList.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        //returning id
        TextView tvName,tvNumber;
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.name);
            tvNumber = itemView.findViewById(R.id.number);
        }

    }
}
