package lk.nirmalsakila.feedreader;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebActivity extends AppCompatActivity {

    GlobalClass globalClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        globalClass = (GlobalClass) this.getApplication();
        setTheme(globalClass.isDarkThemeEnabled()? R.style.AppThemeDark : R.style.AppThemeLight);
        setContentView(R.layout.activity_web);

        String url = getIntent().getStringExtra("URL");
        Log.d("PostActivity","Lanuching webview URL : " + url);
        WebView webview = findViewById(R.id.webView);
//        webview.getSettings().setJavaScriptEnabled(true);
//        webview.setWebViewClient(new WebViewClient(){
//            ProgressDialog progressDialog;
//
//            //If you will not use this method url links are opeen in new brower not in webview
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//
//            //Show loader on url load
//            public void onLoadResource (final WebView view, String url) {
//                if (progressDialog == null) {
//                    // in standard case YourActivity.this
//                    progressDialog = new ProgressDialog(view.getContext());
//                    progressDialog.setMessage("Loading...");
//                    progressDialog.show();
//                }
//            }
//            public void onPageFinished(WebView view, String url) {
//                try{
//                    if (progressDialog.isShowing()) {
//                        progressDialog.dismiss();
//                        progressDialog = null;
//                    }
//                }catch(Exception exception){
//                    exception.printStackTrace();
//                }
//            }
//        });
        webview.setWebViewClient(new WebViewClient());
        webview.loadUrl(url);
    }
}
