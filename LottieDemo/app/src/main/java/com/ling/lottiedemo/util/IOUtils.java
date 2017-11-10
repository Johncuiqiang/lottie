package com.ling.lottiedemo.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by Luooh on 2017/4/27.
 */
public class IOUtils {

    public static void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {

            }
        }
    }
}
