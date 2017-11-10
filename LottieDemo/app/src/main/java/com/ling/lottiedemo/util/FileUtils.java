package com.ling.lottiedemo.util;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by Luooh on 2017/4/27.
 */
public class FileUtils {

    private static final int BUFF_SIZE = 20 * 1024;

    public static List<String> getJsonAssets(Context context, String path) throws IOException {
        String[] assetList = context.getAssets().list(path);
        List<String> files = new ArrayList<>();
        for (String asset : assetList) {
            if (asset.toLowerCase().endsWith(".json")) {
                files.add(asset);
            }
        }
        return files;
    }

    /**
     * @param fileName
     */
    public static File copyAssets(String fileName,Context context) {
        AssetManager assetManager = context.getAssets();
        File cacheDir = context.getCacheDir();
        File lottieDir = new File(cacheDir, "lottie");
        if (!lottieDir.exists()) {
            lottieDir.mkdir();
        }

        InputStream is = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        File newFile = new File(lottieDir, fileName);
        if(newFile.exists()) {
            return newFile;
        }
        try {
            is = assetManager.open(fileName);
            fos = new FileOutputStream(newFile);
            bos = new BufferedOutputStream(fos);
            byte buffer[] = new byte[1024 * 100];
            int length;
            while ((length = is.read(buffer)) != -1) {
                bos.write(buffer, 0, length);
            }
            return newFile;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeStream(is);
            IOUtils.closeStream(fos);
            IOUtils.closeStream(bos);
        }
        return null;
    }

    public static void unzipFile(File zipFile, String folderPath) throws IOException {
        File desDir = new File(folderPath);
        if (!desDir.exists()) {
            desDir.mkdirs();
        }
        InputStream in = null;
        OutputStream out = null;

        try {
            ZipFile zf = new ZipFile(zipFile);
            for (Enumeration<?> entries = zf.entries(); entries.hasMoreElements(); ) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                if (!entry.isDirectory()) {
                    String str = folderPath + File.separator + entry.getName();
                    str = new String(str.getBytes("8859_1"), "GB2312");
                    File desFile = new File(str);
                    File fileParentDir = desFile.getParentFile();
                    if (!fileParentDir.exists()) {
                        fileParentDir.mkdirs();
                    }
                    in = zf.getInputStream(entry);
                    out = new FileOutputStream(desFile);
                    byte buffer[] = new byte[BUFF_SIZE];
                    int realLength;
                    while ((realLength = in.read(buffer)) > 0) {
                        out.write(buffer, 0, realLength);
                    }
                }
            }
        } catch (IOException e) {
            throw new IOException(e);
        } finally {
            IOUtils.closeStream(in);
            IOUtils.closeStream(out);
        }
    }
}
