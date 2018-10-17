package com.blackspider.rxinstantsearch.ui.adapter;
/*
 *  ****************************************************************************
 *  * Created by : Arhan Ashik on 10/17/2018 at 12:59 PM.
 *  * Email : ashik.pstu.cse@gmail.com
 *  *
 *  * Last edited by : Arhan Ashik on 10/17/2018.
 *  *
 *  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
 *  ****************************************************************************
 */

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.blackspider.rxinstantsearch.R;
import com.blackspider.rxinstantsearch.databinding.ContactRowItemBinding;
import com.blackspider.util.network.model.Contact;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class ContactsAdapter extends
        RecyclerView.Adapter<ContactsAdapter.MyViewHolder>{
    private Context context;
    private List<Contact> contactList;
    private ContactsAdapterListener listener;

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ContactRowItemBinding mBinding;

        MyViewHolder(ContactRowItemBinding binding) {
            super(binding.getRoot());

            mBinding = binding;

            mBinding.getRoot().setOnClickListener((view) ->
                    listener.onContactSelected(contactList.get(getAdapterPosition())));
        }

        void bind(Contact contact){
            mBinding.name.setText(contact.getName());
            mBinding.phone.setText(contact.getPhone());

            Glide.with(context)
                    .load(contact.getProfileImage())
                    .apply(RequestOptions.circleCropTransform())
                    .into(mBinding.thumbnail);
        }
    }


    public ContactsAdapter(Context context, List<Contact> contactList, ContactsAdapterListener listener) {
        this.context = context;
        this.contactList = contactList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        ContactRowItemBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.contact_row_item, parent, false);

        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.bind(contactList.get(position));
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public interface ContactsAdapterListener {
        void onContactSelected(Contact contact);
    }
}