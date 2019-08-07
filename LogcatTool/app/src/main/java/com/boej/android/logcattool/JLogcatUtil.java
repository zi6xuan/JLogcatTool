package com.boej.android.logcattool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class JLogcatUtil {
    private static JLogcatUtil _inst = null;
    private static final String UTF8 = "UTF-8";
    private boolean _isLoop = true;

    public static JLogcatUtil inst() {
        if (_inst == null) {
            _inst = new JLogcatUtil();
        }
        return _inst;
    }

    public void logcatGet(final File path) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                logGetThread(path);
            }
        });
        thread.setName("JLogThread");
        thread.start();
    }

    public void stopGet() {
        _isLoop = false;
    }

    private void logGetThread(File path) {
        List<String> getCommandLine = new ArrayList<>();
        createGetCommand(getCommandLine);

        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
//            createCleanCommand();
            // 获取 logcat
            Process process = Runtime.getRuntime().exec(
                    getCommandLine.toArray(new String[getCommandLine.size()]));

            reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), UTF8));
            writer = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(path), UTF8));

            while (_isLoop) {
                try {
                    String str = reader.readLine();
                    if (str != null && !str.isEmpty()) {
//                        createCleanCommand();
                        // 写数据
                        writer.write(str);
                        writer.newLine();
                        writer.flush();
                    }
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            reader.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    private void createCleanCommand() {
        try {
            List<String> commandLine = new ArrayList<>();
            commandLine.add("logcat");
            commandLine.add("-c");
            Runtime.getRuntime().exec(commandLine.toArray(new String[commandLine.size()]));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createGetCommand(List<String> commandLine) {
        commandLine.add("logcat");
//        commandLine.add("-b");
//        commandLine.add("main");
//        commandLine.add("-v");
//        commandLine.add("time");

//        // 过滤 TAG
//        if (mTags != null && mTags.length > 0) {
//            commandLine.add("-s");
//            commandLine.addAll(Arrays.asList(mTags));
//        }
//
//        // 过滤类别
//        if (mLevels != null && mLevels.length > 0) {
//            commandLine.add("sh");
//            commandLine.add("-c");
//            for (String level : mLevels) {
//                commandLine.add("*:" + level);
//            }
//        }
//
//        // 过滤 tag:level
//        if (!mTagWithLevel.isEmpty()) {
//            for (Map.Entry<String, String> entry : mTagWithLevel.entrySet()) {
//                commandLine.add(entry.getKey() + ":" + entry.getValue());
//            }
//
//            /**
//             * 没有 tag 和 level 的时候想要 tag:level 生效就得再加上 *:S，
//             * 再加上 *:S 意思是只让 tag:level 生效
//             */
//            boolean addCommand = (mTags == null || mTags.length == 0) &&
//                    (mLevels == null || mLevels.length == 0);
//            if (addCommand) {
//                commandLine.add("*:S");
//            }
//        }
    }
}
