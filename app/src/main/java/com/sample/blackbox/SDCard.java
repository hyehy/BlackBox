package com.sample.blackbox;

import android.util.Log;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Locale;

public class SDCard {
    public static String getExternalSDCardPath() {
        Log.d("test", "SDCard-1");
        HashSet<String> hs = getExternalMounts();
        Log.d("test", "SDCard-2");
        for(String extSDCardPath : hs) {
            return extSDCardPath;
        }
        return null;
    }

    public static HashSet<String> getExternalMounts() {
        final HashSet<String> out = new HashSet<String>();
        // 문자 정규식
        //String reg = "(?i).*vold.*(vfat|ntfs|exfat|fat32|ext3|ext4).*rw.*";
        String reg = "(?i).*media_rw.*(storage).*(sdcardfs).*rw.*";
        String s = "";
        Log.d("test", "SDCard-3");

        try {
            final Process process = new ProcessBuilder().command("mount").redirectErrorStream(true).start();
            process.waitFor();
            final InputStream is = process.getInputStream();
            final byte[] buffer = new byte[1024];
            while (is.read(buffer) != -1) {
                s = s + new String(buffer);
            }
            is.close();
        } catch (final Exception e) {
            Log.d("test", "SDCard-4 Error");
            e.printStackTrace();
        }

        // parse output
        final String[] lines = s.split("\n");
        for (String line : lines) {
            Log.d("test", "SDCard-5 : " + line);
            if (!line.toLowerCase(Locale.US).contains("asec")) {
                if (line.matches(reg)) {
                    String[] parts = line.split(" ");
                    for (String part : parts) {
                        if (part.startsWith("/")) {
                            if (!part.toLowerCase(Locale.US).contains("vold") && !part.toLowerCase(Locale.US).contains("/mnt/")) {
                                out.add(part);
                            }
                        }
                    }
                }
            }
        }
        return out;
    }
}
