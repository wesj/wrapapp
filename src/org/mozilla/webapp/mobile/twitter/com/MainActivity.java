package org.mozilla.webapp.mobile.twitter.com;

import java.util.List;

import android.os.Bundle;
import android.content.Intent;
import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.net.Uri;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;

public class MainActivity extends Activity {
	private static String LOGTAG = "WebAppWrapper";

	static final int MAP_WEBAPP_REQUEST = 1;
    static private final String LAUNCH_WEBAPP = "LAUNCH_WEBAPP"; 

	protected void showWebApp() {
        String uri = getResources().getString(R.string.launchpath);
        Intent i = getFirefoxIntent(uri);
        if (i != null) {
            startActivity(i);

        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.get_firefox_title)
                   .setMessage(R.string.get_firefox_message)
                   .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                                     Uri.parse("http://nightly.mozilla.org"))
                            );

                            final View v = findViewById(R.id.main_layout);
                            v.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    v.setBackgroundColor(Color.green(255));
                                }
                            }, 5000);

                        }
                   }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                   }).show();
        }
	}

    public Intent getFirefoxIntent(String url) {
        Intent i = new Intent("org.mozilla.gecko.WEBAPP");
        i.setData(Uri.parse(url));
        i.setType("application/firefox-webapp");

        PackageManager packageManager = getPackageManager();
        try {
            List<ResolveInfo> activities = packageManager.queryIntentActivities(i, 0);
            if (activities.size() > 0) {
                return i;
            }
        } catch(Exception ex) {
        }
        return null;
    }

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onResume() {
    	super.onResume();
        showWebApp();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == MAP_WEBAPP_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
            	String index = data.getStringExtra("index");
            	Log.i(LOGTAG, "WEBAPP: Map result: " + index);
            }
        }
    }
}
