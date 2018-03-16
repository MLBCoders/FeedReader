package lk.nirmalsakila.feedreader;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FeedServiceActivity extends AppCompatActivity {

    final String TAG = "FeedService";
    String serviceName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_service);

        GlobalClass globalClass = (GlobalClass)this.getApplication();

        Fragment newFragment = new LoginFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_container, newFragment);

// Commit the transaction
        transaction.commit();
    }
}
