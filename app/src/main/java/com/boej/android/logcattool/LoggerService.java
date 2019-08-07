package com.boej.android.logcattool;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


public class LoggerService extends Service {
    public LoggerService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        File path = new File("/sdcard/boej/JLog" + sdf.format(new Date())+".log");
        JLogcatUtil.inst().logcatGet(path);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
