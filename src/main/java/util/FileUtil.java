package util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class FileUtil {
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");

    /**
     * 获取指定目录下特定文件后缀名的文件列表(不包括子文件夹)
     *
     * @param dirPath 目录路径
     * @param suffix  文件后缀
     * @return
     */
    public static ArrayList<File> getDirFiles(String dirPath, final String suffix) {
        File path = new File(dirPath);
        File[] fileArr = path.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                String lowerName = name.toLowerCase();
                String lowerSuffix = suffix.toLowerCase();
                if (lowerName.endsWith(lowerSuffix)) {
                    return true;
                }
                return false;
            }

        });
        ArrayList<File> files = new ArrayList<File>();

        for (File f : fileArr) {
            if (f.isFile()) {
                files.add(f);
            }
        }
        return files;
    }

    /**
     * 当前目录路径
     */
    public static String getCurrentWorkDir(String path) {
        String result = System.getProperty("user.dir") + FILE_SEPARATOR + path + FILE_SEPARATOR;
        return result;
    }

    /**
     * 读取文件内容
     *
     * @param fileName 待读取的完整文件名
     * @return 文件内容
     * @throws IOException
     */
    public static String read(String fileName) throws IOException {
        File f = new File(fileName);
        FileInputStream fs = new FileInputStream(f);
        String result = null;
        byte[] b = new byte[fs.available()];
        fs.read(b);
        fs.close();
        result = new String(b);
        return result;
    }

    public static String getJsonData(String fileName) {
        String data = "";
        String basePath = "./src/test/java/" + fileName;
        File file = new File(basePath);// 打开文件
        BufferedReader reader = null;
        try {
            FileInputStream in = new FileInputStream(file);
            reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));// 读取文件
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                data = data + tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException el) {
                }
            }
        }
        return data;
    }

    public static JSONObject getJsonObjectData(String fileName) {
        String data = "";
        JSONObject dataObj = null;
        String basePath = "./src/test/java/" + fileName;
        File file = new File(basePath);// 打开文件
        BufferedReader reader = null;
        try {
            FileInputStream in = new FileInputStream(file);
            reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));// 读取文件
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                data = data + tempString;
            }
            reader.close();
            dataObj = JSONObject.parseObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException el) {
                }
            }
        }

        return dataObj;
    }

    public static JSONObject getFieldJsonData(String fileName) {
        String data = "";
        String basePath = "./" + fileName;
        File file = new File(basePath);// 打开文件
        BufferedReader reader = null;
        try {
            FileInputStream in = new FileInputStream(file);
            reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));// 读取文件
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                data = data + tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException el) {
                }
            }
        }
        return JSONObject.parseObject(data);
    }

    public static String handlePath(String path) {
        String p = StringUtils.replace(path, "\\", "/");
        p = StringUtils.join(StringUtils.split(p, "/"), "/");
        if (!StringUtils.startsWithAny(p, "/") && StringUtils.startsWithAny(path, "\\", "/")) {
            p += "/";
        }
        if (!StringUtils.endsWithAny(p, "/") && StringUtils.endsWithAny(path, "\\", "/")) {
            p = p + "/";
        }
        if (path != null && path.startsWith("/")) {
            p = "/" + p; // linux下路径
        }
        return p;
    }


}
