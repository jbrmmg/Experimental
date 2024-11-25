package com.jbr.exp.codeword.utils;

import com.jbr.exp.codeword.CwMain;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;

public class Utils {
    static Logger log = Logger.getLogger(Utils.class.getName());

    static void deleteFile(File file) {
        if(!file.delete()) {
            log.warn("Delete file returned false: " + file.getAbsolutePath());
        }
    }

    public static String reset(String filePattern, String directory, String working) throws IOException {
        // Does the working directory exist?
        if(!Files.exists(new File(working).toPath())) {
            Files.createDirectories(new File(working).toPath());
        } else {
            // Delete all the files in the working directory.
            File[] files = new File(working).listFiles();
            if(files != null) {
                for (File file : files) {
                    deleteFile(file);
                }
            }
        }

        File[] files = new File(directory).listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.matches(filePattern);
            }
        });

        // If there are more than one file, delete the oldest.
        FileTime newest = null;
        String newestFile = null;
        if(files != null) {
            for (File file : files) {
                FileTime fileTime = Files.getLastModifiedTime(file.toPath());
                if(newest == null || fileTime.compareTo(newest) > 0) {
                    newest = fileTime;
                    newestFile = file.getName();
                }
            }

            log.info("Screen shot file: " + newestFile);
            for(File file : files) {
                if(!file.getName().equals(newestFile)) deleteFile(file);
            }
        }

        log.info("Reset the directories");
        return newestFile;
    }

    public static int validateRGB(int x, int y, int value) {
        String hex = Integer.toHexString(value).toUpperCase();
        if(hex.length() > 6) {
            hex = hex.substring(hex.length() - 6);
        }

        String R = hex.substring(0, 2);
        String G = hex.substring(2, 4);
        String B = hex.substring(4);

        if(!R.equals(G) || !G.equals(B) || !B.equals(R)) {
            log.warn("RGB value: " + R + ", " + G + ", " + B + ", not a grey scale");
        }

        return value;
    }
}
