```aidl
package com.chips.cpsmerchant.plugin;

import java.io.File;

public class FileUtils {
    /**
     * 删除 目录
     * @param file
     */
    public static void deleteDirectoryLegacyIO(File file) {
        File[] list = file.listFiles();
        if (list != null) {
            for (File temp : list) {
                deleteDirectoryLegacyIO(temp);
            }
        }
        if (file.delete()) {
           //System.out.printf("删除成功 : %s%n", file);
        } else {
            //System.err.printf("删除失败 : %s%n", file);
        }
    }
}

```