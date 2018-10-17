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
import android.widget.Filter;
import android.widget.Filterable;

import com.blackspider.rxinstantsearch.R;
import com.blackspider.rxinstantsearch.databinding.ContactRowItemBinding;
import com.blackspider.util.network.model.Contact;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class ContactsAdapterFilterable extends
        RecyclerView.Adapter<ContactsAdapterFilterable.MyViewHolder> implements Filterable {
    private Context context;
    private List<Contact> contactList;
    private List<Contact> contactListFiltered;
    private ContactsAdapterListener listener;

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ContactRowItemBinding mBinding;

        MyViewHolder(ContactRowItemBinding binding) {
            super(binding.getRoot());

            mBinding = binding;

            mBinding.getRoot().setOnClickListener((view) ->
                    listener.onContactSelected(contactListFiltered.get(getAdapterPosition())));
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


    public ContactsAdapterFilterable(Context context, List<Contact> contactList, ContactsAdapterListener listener) {
        this.context = context;
        this.contactList = contactList;
        this.contactListFiltered = contactList;
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
        holder.bind(contactListFiltered.get(position));
    }

    @Override
    public int getItemCount() {
        return contactListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    contactListFiltered = contactList;
                } else {
                    List<Contact> filteredList = new ArrayList<>();
                    for (Contact row : contactList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())
                                || row.getPhone().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    contactListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                contactListFiltered = (ArrayList<Contact>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ContactsAdapterListener {
        void onContactSelected(Contact contact);
    }
}