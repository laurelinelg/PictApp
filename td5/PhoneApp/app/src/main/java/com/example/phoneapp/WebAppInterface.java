package com.example.phoneapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class WebAppInterface {
    Context mContext;

    // Instantiate the interface and set the context
    WebAppInterface(Context c) {
        mContext = c;
    }

    @JavascriptInterface
    public void appelerNum(String numero) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+numero));
        mContext.startActivity(intent);
    }
}
