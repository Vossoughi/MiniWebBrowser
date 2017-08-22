// Author: Kaveh Vossoughi
// Date  : 12/12/16
// Homework assignment : Extra credit 2
// Objective: This is a Web Browser Activity. It has a WebView
// which the content of webpage will be displayed there.
// It also includes a progress bar to show the progress of
// loading the webpage.
//******************************************************************
package vossoughi.minibrowser;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.*;
import android.webkit.*;
import android.widget.*;

public class BrowserActivity extends Activity implements View.OnClickListener
{
   EditText url;
   WebView wv;
   ProgressBar mProgress;
   Context context;
   //****************************onCreate()****************************
   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      getWindow().requestFeature(Window.FEATURE_PROGRESS);
      setContentView(R.layout.activity_browser);
      url = (EditText) findViewById(R.id.addressBar);
      mProgress = (ProgressBar) findViewById(R.id.progressBar);
      context = getApplicationContext();
      wv = (WebView) findViewById(R.id.browser);
      wv.setInitialScale(50); // 50% scale down
      wv.getSettings().setJavaScriptEnabled(true);
      wv.setWebViewClient(new WebViewClient()
      {
         public boolean shouldOverrideUrlLoading(WebView view, String url)
         {
            view.loadUrl(url);
            return true;
         }
      });
      url.setOnKeyListener(new View.OnKeyListener()
      {
         @Override
         public boolean onKey(View v, int keyCode, KeyEvent event)
         {
            if ((event.getAction() == event.ACTION_UP) &&
                  (keyCode == event.KEYCODE_ENTER))
            {
               wv.loadUrl(url.getText().toString());
               return true;
            }
            return false;
         }
      });
      Button b = (Button) findViewById(R.id.goButton);
      b.setOnClickListener(this);

      wv.setWebChromeClient(new WebChromeClient()
      {
         @Override
         public void onProgressChanged(WebView view, int progress)
         {
            mProgress.setProgress(progress * 1000);
         }
      });
      wv.setWebViewClient(new WebViewClient()
      {
         public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
         {
            Toast.makeText(context, "Oops! " + description, Toast.LENGTH_SHORT).show();
         }
      });
   }
   //****************************onClick()****************************
   @Override
   public void onClick(View v)
   {
      wv.loadUrl(url.getText().toString());
   }
}