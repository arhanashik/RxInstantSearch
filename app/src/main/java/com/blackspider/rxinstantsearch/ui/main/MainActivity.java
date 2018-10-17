package com.blackspider.rxinstantsearch.ui.main;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.blackspider.rxinstantsearch.R;
import com.blackspider.rxinstantsearch.databinding.ActivityMainBinding;
import com.blackspider.rxinstantsearch.ui.localsearch.LocalSearchActivity;
import com.blackspider.rxinstantsearch.ui.remotesearch.RemoteSearchActivity;
import com.blackspider.util.helper.AndroidUtil;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(mBinding.toolbar);
        //AndroidUtil.whiteNotificationBar(this, mBinding.toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickFav(View view){
        Snackbar.make(view,
                "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public void openLocalSearch(View view) {
        startActivity(new Intent(MainActivity.this, LocalSearchActivity.class));
    }

    public void openRemoteSearch(View view) {
        startActivity(new Intent(MainActivity.this, RemoteSearchActivity.class));
    }
}
