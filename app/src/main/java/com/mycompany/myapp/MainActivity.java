package com.mycompany.myapp;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import com.mycompany.myapp.rom.HuaweiUtils;
import com.mycompany.myapp.rom.MeizuUtils;
import com.mycompany.myapp.rom.MiuiUtils;
import com.mycompany.myapp.rom.OppoUtils;
import com.mycompany.myapp.rom.QikuUtils;
import com.mycompany.myapp.rom.RomUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		if (WindowAuthority.permissionWindow(this)) {
            MyWindowManager myWindowManager = MyWindowManager.getInstance();
			myWindowManager.createNormalView(this.getApplicationContext());
        } else {
            WindowAuthority.applyPermission(this);
        }
    }
}
