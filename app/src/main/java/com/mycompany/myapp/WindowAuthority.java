package com.mycompany.myapp;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import com.mycompany.myapp.rom.HuaweiUtils;
import com.mycompany.myapp.rom.MeizuUtils;
import com.mycompany.myapp.rom.MiuiUtils;
import com.mycompany.myapp.rom.OppoUtils;
import com.mycompany.myapp.rom.QikuUtils;
import com.mycompany.myapp.rom.RomUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
public class WindowAuthority
{
	public static boolean permissionWindow(Context context) {
        //6.0 版本之后由于 google 增加了对悬浮窗权限的管理，所以方式就统一了
        if (Build.VERSION.SDK_INT < 23) {
            if (RomUtils.checkIsMiuiRom()) {
                return MiuiUtils.checkFloatWindowPermission(context);
            } else if (RomUtils.checkIsMeizuRom()) {
                return MeizuUtils.checkFloatWindowPermission(context);
            } else if (RomUtils.checkIsHuaweiRom()) {
                return HuaweiUtils.checkFloatWindowPermission(context);
            } else if (RomUtils.checkIs360Rom()) {
                return QikuUtils.checkFloatWindowPermission(context);
            } else if (RomUtils.checkIsOppoRom()) {
                return OppoUtils.checkFloatWindowPermission(context);
            }
        }
		if (RomUtils.checkIsMeizuRom()) {
            return MeizuUtils.checkFloatWindowPermission(context);
        } else {
            Boolean result = true;
            if (Build.VERSION.SDK_INT >= 23) {
                try {
                    Class clazz = Settings.class;
                    Method canDrawOverlays = clazz.getDeclaredMethod("canDrawOverlays", Context.class);
                    result = (Boolean) canDrawOverlays.invoke(null, context);
                } catch (Exception e) {}
            }
            return result;
        }
    }

	public static void applyPermission(Context context) {
        if (Build.VERSION.SDK_INT < 23) {
            if (RomUtils.checkIsMiuiRom()) {
                MiuiUtils.applyMiuiPermission(context);
            } else if (RomUtils.checkIsMeizuRom()) {
                MeizuUtils.applyPermission(context);
            } else if (RomUtils.checkIsHuaweiRom()) {
               	HuaweiUtils.applyPermission(context);
            } else if (RomUtils.checkIs360Rom()) {
                QikuUtils.applyPermission(context);
            } else if (RomUtils.checkIsOppoRom()) {
                OppoUtils.applyOppoPermission(context);
            }
        } else {
			if (RomUtils.checkIsMeizuRom()) {
				MeizuUtils.applyPermission(context);
			} else {
				if (Build.VERSION.SDK_INT >= 23) {
					try {
						Class clazz = Settings.class;
						Field field = clazz.getDeclaredField("ACTION_MANAGE_OVERLAY_PERMISSION");
						Intent intent = new Intent(field.get(null).toString());
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						intent.setData(Uri.parse("package:" + context.getPackageName()));
						context.startActivity(intent);
					} catch (NoSuchFieldException e) {} catch (IllegalArgumentException e) {} catch (IllegalAccessException e) {}
				}
			}
        }
    }
}
