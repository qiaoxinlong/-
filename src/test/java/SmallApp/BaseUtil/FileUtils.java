package SmallApp.BaseUtil;

import org.apache.commons.codec.digest.DigestUtils;
import sun.misc.Cleaner;
import sun.nio.ch.DirectBuffer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangchch on 2016/9/26.
 */
public class FileUtils {
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public static String getMD5(byte[] data) {
        return DigestUtils.md5Hex(data);
    }

    /**
     * 当前目录路径
     */
    public static String getCurrentWorkDir(String path) {
        String result = System.getProperty("user.dir") + FILE_SEPARATOR + path + FILE_SEPARATOR;
        return result;
    }

    /**
     * 当前目录路径
     */

    public static String getCurrentWorkDir() {
        String result = null;
        if (OSUtils.isWindows()) {
            result = System.getProperty("user.dir") + "\\"
                    + "file"
                    + "\\";
        } else if (OSUtils.isMacOS() || OSUtils.isLinux()) {
            result = System.getProperty("user.dir") + "/"
                    + "file"
                    + "/";
        }
        return result;
    }

    /**
     * 当前目录路径
     */
    public static String getResourcesWorkDir() {
        String result = null;
        if (OSUtils.isWindows()) {
            result = System.getProperty("user.dir") + "\\"
                    + "src"
                    + "\\"
                    + "main"
                    + "\\"
                    + "resources"
                    + "\\";
        } else if (OSUtils.isMacOS() || OSUtils.isLinux()) {
            result = System.getProperty("user.dir") + "/"
                    + "src"
                    + "/"
                    + "main"
                    + "/"
                    + "resources"
                    + "/";
        }
        return result;
    }

    /**
     * 左填充
     *
     * @param str
     * @param length
     * @param ch
     * @return
     */
    public static String leftPad(String str, int length, char ch) {
        if (str.length() >= length) {
            return str;
        }
        char[] chs = new char[length];
        Arrays.fill(chs, ch);
        char[] src = str.toCharArray();
        System.arraycopy(src, 0, chs, length - src.length, src.length);
        return new String(chs);

    }

    /**
     * 删除文件
     *
     * @param fileName 待删除的完整文件名
     * @return
     */
    public static boolean delete(String fileName) {
        boolean result = false;
        System.err.println(fileName);
        File f1 = new File(fileName);
        if (f1.exists()) {

            result = f1.delete();
        } else {

            result = true;
        }
        return result;
    }

    /**
     * 通过递归调用删除一个文件夹及下面的所有文件
     *
     * @param folderPath
     */
    public static void deleteFilesInFolder(String folderPath) {
        File file = new File(folderPath);
        if (file.isFile()) {//表示该文件不是文件夹
            file.delete();
        } else {
            //首先得到当前的路径
            String[] childFilePaths = file.list();
            for (String childFilePath : childFilePaths) {
                String childFile = file.getAbsolutePath() + "\\" + childFilePath;
                deleteFilesInFolder(childFile);
            }
            file.delete();
        }
    }

    /***
     * 递归获取指定目录下的所有的文件（不包括文件夹）
     *
     * @param dirPath
     * @return
     */
    public static ArrayList<File> getAllFiles(String dirPath) {
        File dir = new File(dirPath);

        ArrayList<File> files = new ArrayList<File>();

        if (dir.isDirectory()) {
            File[] fileArr = dir.listFiles();
            for (int i = 0; i < fileArr.length; i++) {
                File f = fileArr[i];
                if (f.isFile()) {
                    files.add(f);
                } else {
                    files.addAll(getAllFiles(f.getPath()));
                }
            }
        }
        return files;
    }

    /**
     * 获取指定目录下的所有文件(不包括子文件夹)
     *
     * @param dirPath
     * @return
     */
    public static ArrayList<File> getDirFiles(String dirPath) {
        File path = new File(dirPath);
        File[] fileArr = path.listFiles();
        ArrayList<File> files = new ArrayList<File>();

        for (File f : fileArr) {
            if (f.isFile()) {
                files.add(f);
            }
        }
        return files;
    }

    /**
     * 获取指定目录下特定文件后缀名的文件列表(不包括子文件夹)
     *
     * @param dirPath 目录路径
     * @param suffix  文件后缀
     * @return
     */
    public static ArrayList<File> getDirFiles(String dirPath,
                                              final String suffix) {
        File path = new File(dirPath);
        File[] fileArr = path.listFiles(new FilenameFilter() {
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


    /**
     * 读取文件内容
     *
     * @param fileName 待读取的完整文件名
     * @return 文件内容
     * @throws IOException
     */
    public ArrayList<String> readFile(String fileName) throws IOException {
        ArrayList<String> list = new ArrayList<String>();
        FileReader fr = new FileReader(fileName);
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();
        while (line != null) {
            list.add(line);
            line = br.readLine();
        }
        br.close();
        fr.close();
        return list;
    }


    public static void writeFile(int fileSize, String fileName, boolean isAppend) throws IOException, InterruptedException {

        StringBuilder sb = new StringBuilder();

        long originFileSize = 1024 * 1024 * fileSize;// 10M

        // 生成一个大文件
        for (int i = 0; i < originFileSize; i++) {

            UUID uuid = UUID.randomUUID();
            if (i < originFileSize - uuid.toString().length()) {
                sb.append("A");
            } else {
                sb.append(uuid.toString());
                break;

            }
        }

        System.out.println(FileUtils.write(fileName, sb.toString()));

        // 追加内容
        if (isAppend) {
            sb.setLength(0);
            UUID uuid = UUID.randomUUID();
            sb.append(uuid.toString());
            FileUtils.append(fileName, sb.toString());
        }
    }

    /**
     *
     */
    public static long writeFile(int fileSize, String fileName, String KMGT) throws IOException, InterruptedException {

        System.out.println(FileUtils.getCurrentWorkDir());

        StringBuilder sb = new StringBuilder();
        int bytex = 1;
        if ("K" == KMGT.toUpperCase()) {
            bytex = 512;
        } else if ("M" == KMGT.toUpperCase()) {
            bytex = 1024;
        } else if ("G" == KMGT.toUpperCase()) {
            bytex = 1048576;
        }

        long originFileSize = 1024 * bytex * fileSize;// 10M

        // 生成一个大文件
        for (int i = 0; i < originFileSize; i++) {

            UUID uuid = UUID.randomUUID();
            if (i < originFileSize - uuid.toString().length()) {
                sb.append("A");
            } else {
                sb.append(uuid.toString());
                break;

            }
        }

        System.out.println(FileUtils.write(fileName, sb.toString()));

        // 追加内容
//        if(isAppend){
//            sb.setLength(0);
//            UUID uuid = UUID.randomUUID();
//            sb.append(uuid.toString());
//            FileUtils.append(fileName, sb.toString());
//        }
        return originFileSize;
    }

    /**
     * 写文件
     *
     * @param fileName    目标文件名
     * @param fileContent 写入的内容
     * @return
     * @throws IOException
     */
    public static boolean write(String fileName, String fileContent)
            throws IOException {
        boolean result = false;
        File f = new File(fileName);
        FileOutputStream fs = new FileOutputStream(f);
        byte[] b = fileContent.getBytes();
        fs.write(b);
        fs.flush();
        fs.close();
        result = true;
        return result;
    }


    public void writeFile(StringBuilder value, String fileName) {

        try {
            BufferedWriter fw = new BufferedWriter(new FileWriter(fileName, true));

            StringBuffer buffer = new StringBuffer();
            buffer.append(value.toString());
            buffer.append(LINE_SEPARATOR);
            fw.write(buffer.toString());
            fw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写文件
     *
     * @param fileName        目标文件名
     * @param fileContentList 写入的内容
     * @return
     * @throws Exception
     */
    public static void writeFile(String fileName, ArrayList<String> fileContentList)
            throws Exception {
        if (fileContentList == null) {
            throw new Exception("fileContentList is not null");
        }
        File f = new File(fileName);
        FileOutputStream fs = new FileOutputStream(f);
        for (int i = 0; i < fileContentList.size(); i++) {
            String fileContent = fileContentList.get(i);
            byte[] b = fileContent.getBytes();
            fs.write(b);
            fs.write(LINE_SEPARATOR.getBytes());
            fs.flush();
        }
        fs.close();
    }


    /**
     * 追加内容到指定文件
     *
     * @param fileName
     * @param fileContent
     * @return
     * @throws IOException
     */
    public static boolean append(String fileName, String fileContent)
            throws IOException {
        boolean result = false;
        File f = new File(fileName);
        if (f.exists()) {
            RandomAccessFile rFile = new RandomAccessFile(f, "rw");
            byte[] b = fileContent.getBytes();
            long originLen = f.length();
            rFile.setLength(originLen + b.length);
            rFile.seek(originLen);
            rFile.write(b);
            rFile.close();
        }
        result = true;
        return result;
    }

    /**
     * 拆分文件
     *
     * @param fileName 待拆分的完整文件名
     * @param byteSize 按多少字节大小拆分
     * @return 拆分后的文件名列表
     * @throws IOException
     */
    public List<String> splitBySize(String fileName, int byteSize)
            throws IOException {
        byteSize = 1024 * 1024 * byteSize;
        List<String> parts = new ArrayList<String>();
        File file = new File(fileName);
        int count = (int) Math.ceil(file.length() / (double) byteSize);
        int countLen = (count + "").length();

        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(count,
                count * 3, 1, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(count * 2));

        for (int i = 0; i < count; i++) {
            String partFileName = file.getName() + "."
                    + leftPad((i + 1) + "", countLen, '0') + ".part";
            threadPool.execute(new SplitRunnable(byteSize, i * byteSize,
                    partFileName, file));
            parts.add(partFileName);
        }

        return parts;
    }

    /**
     * 拆分文件
     *
     * @param fileName 待拆分的完整文件名
     * @param byteSize 按多少字节大小拆分
     * @return 拆分后的文件名列表
     * @throws IOException
     */
    public List<String> splitFile(String fileName, int byteSize)
            throws IOException {
        List<String> parts = new ArrayList<String>();
        if ("".equals(fileName) || fileName == null) {
            System.out.println("分割失败");
        }

        File srcFile = new File(fileName);//源文件

        long srcSize = srcFile.length();//源文件的大小
        long destSize = 1024 * 1024 * byteSize;//目标文件的大小（分割后每个文件的大小）

        int number = (int) (srcSize / destSize);
        number = srcSize % destSize == 0 ? number : number + 1;//分割后文件的数目

        //srcFile.getName();//源文件名

        InputStream in = null;//输入字节流
        BufferedInputStream bis = null;//输入缓冲流
        byte[] bytes = new byte[1024 * 1024];//每次读取文件的大小为1MB
        int len = -1;//每次读取的长度值
        try {
            in = new FileInputStream(srcFile);
            bis = new BufferedInputStream(in);
            for (int i = 0; i < number; i++) {

                String destName = srcFile.getName() + "_" + i + ".part";
                parts.add(destName);
                destName = FileUtils.getCurrentWorkDir() + destName;
                System.err.println(destName);
                OutputStream out = new FileOutputStream(destName);
                BufferedOutputStream bos = new BufferedOutputStream(out);
                int count = 0;
                while ((len = bis.read(bytes)) != -1) {
                    bos.write(bytes, 0, len);//把字节数据写入目标文件中
                    count += len;

                    if (count >= destSize) {
                        break;

                    }
                }
                bos.flush();//刷新
                bos.close();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭流
            try {
                if (bis != null) bis.close();
                if (in != null) in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return parts;

    }

    /**
     * 拆分文件
     *
     * @param fileName 待拆分的完整文件名
     * @param byteSize 按多少字节大小拆分
     * @return 拆分后的文件名列表
     * @throws IOException
     */
    public List<String> splitBySize(String fileName, int byteSize, String KMGT)
            throws IOException {
        int bytex = 1;
        if ("K" == KMGT.toUpperCase()) {
            bytex = 512;
        } else if ("M" == KMGT.toUpperCase()) {
            bytex = 1024;
        } else if ("G" == KMGT.toUpperCase()) {
            bytex = 1048576;
        }
        byteSize = 1024 * bytex * byteSize;
        List<String> parts = new ArrayList<>();
        File file = new File(fileName);
        int count = (int) Math.ceil(file.length() / (double) byteSize);
        int countLen = (count + "").length();

        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(count,
                count * 3, 1, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(count * 2));

        for (int i = 0; i < count; i++) {
            String partFileName = file.getName() + "."
                    + leftPad((i + 1) + "", countLen, '0') + ".part";
            threadPool.execute(new SplitRunnable(byteSize, i * byteSize,
                    partFileName, file));
            parts.add(partFileName);
        }

        return parts;
    }
//    /**
//     * 拆分文件
//     *
//     * @param fileName
//     *            待拆分的完整文件名
//     * @param byteSize
//     *            按多少字节大小拆分
//     * @return 拆分后的文件名列表
//     * @throws IOException
//     */
//    public List<String> splitBySize(String fileName, int byteSize, int bytex)
//            throws IOException {
////        int bytex = 1;
////        if     ("K" == KMGT.toUpperCase()){bytex = 512;}
////        else if("M" == KMGT.toUpperCase()){bytex = 1024;}
////        else if("G" == KMGT.toUpperCase()){bytex = 1048576;}
//        byteSize=1024 * bytex * byteSize;
//        List<String> parts = new ArrayList<>();
//        File file = new File(fileName);
//        int count = (int) Math.ceil(file.length() / (double) byteSize);
//        int countLen = (count + "").length();
//
//        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(count,
//                count * 3, 1, TimeUnit.SECONDS,
//                new ArrayBlockingQueue<Runnable>(count * 2));
//
//        for (int i = 0; i < count; i++) {
//            String partFileName = file.getName() + "."
//                    + leftPad((i + 1) + "", countLen, '0') + ".part";
//            threadPool.execute(new SplitRunnable(byteSize, i * byteSize,
//                    partFileName, file));
//            parts.add(partFileName);
//        }
//
//        return parts;
//    }

    /**
     * 合并文件
     *
     * @param dirPath        拆分文件所在目录名
     * @param partFileSuffix 拆分文件后缀名
     * @param partFileSize   拆分文件的字节数大小
     * @param mergeFileName  合并后的文件名
     * @throws IOException
     */
    public void mergePartFiles(String dirPath, String partFileSuffix,
                               int partFileSize, String mergeFileName) throws IOException {
        ArrayList<File> partFiles = FileUtils.getDirFiles(dirPath,
                partFileSuffix);
        Collections.sort(partFiles, new FileComparator());

        RandomAccessFile randomAccessFile = new RandomAccessFile(mergeFileName,
                "rw");
        randomAccessFile.setLength(partFileSize * (partFiles.size() - 1)
                + partFiles.get(partFiles.size() - 1).length());
        randomAccessFile.close();

        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                partFiles.size(), partFiles.size() * 3, 1, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(partFiles.size() * 2));

        for (int i = 0; i < partFiles.size(); i++) {
            threadPool.execute(new MergeRunnable(i * partFileSize,
                    mergeFileName, partFiles.get(i)));
        }

    }

    /**
     * 根据文件名，比较文件
     *
     * @author yjmyzz@126.com
     */
    private class FileComparator implements Comparator<File> {
        public int compare(File o1, File o2) {
            return o1.getName().compareToIgnoreCase(o2.getName());
        }
    }

    /**
     * 分割处理Runnable
     *
     * @author yjmyzz@126.com
     */
    private class SplitRunnable implements Runnable {
        int byteSize;
        String partFileName;
        File originFile;
        int startPos;

        public SplitRunnable(int byteSize, int startPos, String partFileName,
                             File originFile) {
            this.startPos = startPos;
            this.byteSize = byteSize;
            this.partFileName = partFileName;
            this.originFile = originFile;
        }

        public void run() {
            RandomAccessFile rFile;
            OutputStream os;
            try {
                rFile = new RandomAccessFile(originFile, "r");
                byte[] b = new byte[byteSize];
                rFile.seek(startPos);// 移动指针到每“段”开头
                int s = rFile.read(b);
                os = new FileOutputStream(FileUtils.getCurrentWorkDir() + partFileName);
                os.write(b, 0, s);
                os.flush();
                os.close();
                rFile.close();//之前没有
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 合并处理Runnable
     *
     * @author yjmyzz@126.com
     */
    private class MergeRunnable implements Runnable {
        long startPos;
        String mergeFileName;
        File partFile;

        public MergeRunnable(long startPos, String mergeFileName, File partFile) {
            this.startPos = startPos;
            this.mergeFileName = mergeFileName;
            this.partFile = partFile;
        }

        public void run() {
            RandomAccessFile rFile;
            try {
                rFile = new RandomAccessFile(mergeFileName, "rw");
                rFile.seek(startPos);
                FileInputStream fs = new FileInputStream(partFile);
                byte[] b = new byte[fs.available()];
                fs.read(b);
                fs.close();
                rFile.write(b);
                rFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 删除多个文件文件
     *
     * @param fileName 待删除的完整文件列表
     * @return
     */

    public static boolean delete(List<String> fileName) {
        boolean result = false;
        for (int i = 0; i < fileName.size(); i++) {
            String filePartPath = FileUtils.getCurrentWorkDir() + fileName.get(i);

            File f = new File(filePartPath);
            if (f.exists()) {
                result = f.delete();
            } else {
                result = true;
            }

        }
        return result;

    }

    /**
     * 若某文件夹不存在，则创建之
     *
     * @param DirectoryPath
     * @Created by zhenngy on 2016/11/23
     */
    public static boolean createDirectoryIfNotExists(String DirectoryPath) {
        boolean result = false;

        File file = new File(DirectoryPath);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdir();
        } else {
            result = true;
        }

        return result;

    }

    public static String getMd5ByFile(String path) throws FileNotFoundException {
        File file = new File(path);
        String value = null;
        FileInputStream in = new FileInputStream(file);
        MappedByteBuffer byteBuffer = null;
        try {
            byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            BigInteger bi = new BigInteger(1, md5.digest());
            value = bi.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    Cleaner cleaner = ((DirectBuffer) byteBuffer).cleaner();
                    cleaner.clean();
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

    /**
     * 格式化
     *
     * @param jsonStr
     * @return
     * @author lizhgb
     * @Date 2015-10-14 下午1:17:35
     */
    public static String formatJson(String jsonStr) {
        if (null == jsonStr || "".equals(jsonStr)) return "";
        StringBuilder sb = new StringBuilder();
        char last = '\0';
        char current = '\0';
        int indent = 0;
        for (int i = 0; i < jsonStr.length(); i++) {
            last = current;
            current = jsonStr.charAt(i);
            switch (current) {
                case '{':
                case '[':
                    sb.append(current);
                    sb.append('\n');
                    indent++;
                    addIndentBlank(sb, indent);
                    break;
                case '}':
                case ']':
                    sb.append('\n');
                    indent--;
                    addIndentBlank(sb, indent);
                    sb.append(current);
                    break;
                case ',':
                    sb.append(current);
                    if (last != '\\') {
                        sb.append('\n');
                        addIndentBlank(sb, indent);
                    }
                    break;
                default:
                    sb.append(current);
            }
        }

        return sb.toString();
    }

    /**
     * 添加space
     *
     * @param sb
     * @param indent
     * @author lizhgb
     * @Date 2015-10-14 上午10:38:04
     */
    private static void addIndentBlank(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append('\t');
        }
    }

}


