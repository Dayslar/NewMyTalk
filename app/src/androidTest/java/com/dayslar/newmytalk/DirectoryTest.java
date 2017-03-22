package com.dayslar.newmytalk;

import android.support.test.runner.AndroidJUnit4;

import com.dayslar.newmytalk.utils.MyFileUtils;
import com.dayslar.newmytalk.utils.MyLogger;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;

@RunWith(AndroidJUnit4.class)
public class DirectoryTest {

    @Test
    public void deleteFile() throws InterruptedException {
        MyLogger.printDebug(this.getClass(), FileUtils.deleteQuietly(new File(MyFileUtils.getFolder() + "1.txt")) + "");

    }

    @Test
    public void deleteDirectory() throws InterruptedException, IOException {
       FileUtils.deleteDirectory(new File(MyFileUtils.getFolder()));
    }


}
