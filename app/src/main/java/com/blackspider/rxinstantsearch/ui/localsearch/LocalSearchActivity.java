package com.blackspider.rxinstantsearch.ui.localsearch;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.blackspider.rxinstantsearch.R;
import com.blackspider.rxinstantsearch.databinding.ActivityLocalSearchBinding;
import com.blackspider.rxinstantsearch.ui.adapter.ContactsAdapterFilterable;
import com.blackspider.util.helper.AndroidUtil;
import com.blackspider.util.network.ApiClient;
import com.blackspider.util.network.ApiService;
import com.blackspider.util.network.model.Contact;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class LocalSearchActivity extends AppCompatActivity
        implements ContactsAdapterFilterable.ContactsAdapterListener {
    private static final String TAG = LocalSearchActivity.class.getSimpleName();

    private ActivityLocalSearchBinding mBinding;

    private CompositeDisposable disposable = new CompositeDisposable();
    private ApiService apiService;
    private ContactsAdapterFilterable mAdapter;
    private List<Contact> contactsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_search);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_local_search);

        setSupportActionBar(mBinding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mAdapter = new ContactsAdapterFilterable(this, contactsList, this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mBinding.recyclerView.setLayoutManager(mLayoutManager);
        mBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        mBinding.recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        mBinding.recyclerView.setAdapter(mAdapter);

        //AndroidUtil.whiteNotificationBar(this, mBinding.recyclerView);

        apiService = ApiClient.getClient().create(ApiService.class);

        disposable.add(RxTextView.textChangeEvents(mBinding.inputSearch)
                .skipInitialValue()
                .debounce(300, TimeUnit.MILLISECONDS)
                /*.filter(new Predicate<TextViewTextChangeEvent>() {
                    @Override
                    public boolean test(TextViewTextChangeEvent textViewTextChangeEvent)
                    throws Exception {
                        return TextUtils.isEmpty(textViewTextChangeEvent.text().toString())
                        || textViewTextChangeEvent.text().toString().length() > 2;
                    }
                })*/
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(searchContacts()));


        // source: `gmail` or `linkedin`
        // fetching all contacts on app launch
        // only gmail will be fetched
        fetchContacts("gmail");
    }

    private DisposableObserver<TextViewTextChangeEvent> searchContacts() {
        return new DisposableObserver<TextViewTextChangeEvent>() {
            @Override
            public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {
                Log.d(TAG, "Search query: " + textViewTextChangeEvent.text());
                mAdapter.getFilter().filter(textViewTextChangeEvent.text());
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };
    }

    private void fetchContacts(String source) {
        disposable.add(apiService.getContacts(source, null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Contact>>() {
                    @Override
                    public void onSuccess(List<Contact> contacts) {
                        contactsList.clear();
                        contactsList.addAll(contacts);
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }));
    }

    @Override
    public void onContactSelected(Contact contact) {
        Toast.makeText(this, "Calling " + contact.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        disposable.clear();
        super.onDestroy();
    }
}
