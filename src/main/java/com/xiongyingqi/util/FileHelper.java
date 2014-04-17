/**
 * spark_src
 */
package com.xiongyingqi.util;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * @author XiongYingqi
 * @version 2013-6-24 下午10:47:33
 */
public class FileHelper {

    public static final long KB = 1024;
    public static final long MB = 1024 * KB;
    public static final long GB = 1024 * MB;
    public static final long TB = 1024 * GB;
    public static final long PB = 1024 * TB;

    //	public static final long EB = 1024 * PB;
    //	public static final long ZB = 1024 * EB;
    //	public static final long YB = 1024 * ZB;
    //	public static final long BB = 1024 * YB;
    //	public static final long NB = 1024 * BB;
    //	public static final long DB = 1024 * NB;

    public static boolean checkFileSizeLessThen(File file, long size) {
        try {
            fileNotFullAndExists(file);
        } catch (Exception e) {
            return false;
        }

        return file.length() <= size;
    }

    public static File appendStringToFile(File file, String string) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            fileWriter.append(string);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }


    /**
     * 根据后缀名从文件夹获取文件 <br>
     * 2013-9-6 下午3:15:56
     *
     * @param folder
     * @param suffix
     * @return
     */
    public static File[] listFilesBySuffix(File folder, final String suffix) {
        File[] files = folder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File paramFile, String paramString) {
                String newSuffix = suffix;
                if (!newSuffix.startsWith(".")) {
                    newSuffix = "." + newSuffix;
                }
                if (paramString.endsWith(newSuffix)) {
                    return true;
                }
                return false;
            }
        });
        return files;
    }


    /**
     * 从url地址读取文件信息并写入到临时文件 <br>
     * 2013-9-2 下午2:57:15
     *
     * @param urlStr
     * @return
     * @throws java.io.IOException
     */
    public static File readURL(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        // System.out.println(" ----------------- url ----------------- ");
        // System.out.println(urlStr);
        // System.out.println(url.getPath());
        // System.out.println(url.getHost());
        // System.out.println(" ---------------------------------- ");
        InputStream inputStream = null;
        if (url.getHost().trim().equals("")) {
            // inputStream = new FileInputStream(url.getFile());
            File file = new File(url.getFile());
            return file;
        } else {
            inputStream = url.openStream();
        }
        return readInputStream(inputStream);
    }

    public static String readInputStreamToString(InputStream inputStream) {
        return readInputStreamToString(inputStream, "UTF-8");
    }

    public static String readInputStreamToString(InputStream inputStream, Charset charset) {
        String rs = null;
        try {
            Reader reader = new InputStreamReader(inputStream, charset);
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null){
                builder.append(line);
            }
            rs = builder.toString();

//            ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 1024 * 1024);
//            int length = -1;
//            int bufferSize = 128;
//            byte[] buffer = new byte[bufferSize];
//            while ((length = inputStream.read(buffer)) != -1) {
//                if (length != bufferSize) {
//                    System.arraycopy(buffer, 0, buffer, 0, length);
//                }
//                EntityHelper.print(new String(buffer));
//                byteBuffer.put(buffer);
//            }
//            rs = new String(byteBuffer.array(), encoding);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // try {
            // inputStream.close();
            // } catch (IOException e1) {
            // e1.printStackTrace();
            // }
            try {
                inputStream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return rs;
    }
    public static String readInputStreamToString(InputStream inputStream, String encoding) {
        String rs = null;
        try {
            Reader reader = new InputStreamReader(inputStream, encoding);
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null){
                builder.append(line);
            }
            rs = builder.toString();

//            ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 1024 * 1024);
//            int length = -1;
//            int bufferSize = 128;
//            byte[] buffer = new byte[bufferSize];
//            while ((length = inputStream.read(buffer)) != -1) {
//                if (length != bufferSize) {
//                    System.arraycopy(buffer, 0, buffer, 0, length);
//                }
//                EntityHelper.print(new String(buffer));
//                byteBuffer.put(buffer);
//            }
//            rs = new String(byteBuffer.array(), encoding);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // try {
            // inputStream.close();
            // } catch (IOException e1) {
            // e1.printStackTrace();
            // }
            try {
                inputStream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return rs;
    }

    /**
     * 从流读取文件内容 <br>
     * 2013-9-2 下午2:52:39
     *
     * @param inputStream
     * @return
     */
    public static File readInputStream(InputStream inputStream) {
        String filePath = System.getProperty("java.io.tmpdir", "tmp/");
        filePath += System.currentTimeMillis() + StringUtil.randomString(8) + ".tmp";
        return readInputStream(inputStream, filePath);
    }

    /**
     * 将文件转换为URL地址 <br>
     * 2013-9-2 下午10:07:14
     *
     * @param file
     * @return
     */
    public static URL toURL(File file) {
        if (file == null || !file.exists()) {
            return null;
        }
        URI uri = file.toURI();
        URL url = null;
        try {
            url = uri.toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * 将流读入到文件内 <br>
     * 2013-9-2 下午10:04:17
     *
     * @param inputStream
     * @param file
     * @return
     */
    public static File readInputStream(InputStream inputStream, File file) {
        try {
            validateFile(file);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            int length = -1;
            try {
                int bufferSize = 128;
                byte[] buffer = new byte[bufferSize];
                while ((length = inputStream.read(buffer)) != -1) {
                    if (length != bufferSize) {
                        System.arraycopy(buffer, 0, buffer, 0, length);
                    }
                    outputStream.write(buffer);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // try {
                // inputStream.close();
                // } catch (IOException e1) {
                // e1.printStackTrace();
                // }
                try {
                    inputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                try {
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            return null;
        }
        return file;
    }

    public static File readInputStream(InputStream inputStream, String path) {
        File file = new File(path);
        return readInputStream(inputStream, file);
    }


    public static File validateFile(File file) throws Exception {
        if (file != null) {
            if (file.isDirectory()) {
                if (!file.exists()) {
                    file.mkdirs();
                }
            } else {
                File parentFolder = file.getParentFile();
                if (parentFolder != null && !parentFolder.exists()) {
                    parentFolder.mkdirs();
                }
                file.createNewFile();
            }
        } else {
            throw new Exception("传入 的引用为null！");
        }
        return file;
    }

    public static void copyFile(String originFilePath, String targetFilePath) {
        File originFile = new File(originFilePath);
        File targetFile = new File(targetFilePath);
        copyFile(originFile, targetFile);
    }

    public static void copyFile(URL originURL, File targetFile) {
        try {
            copyFile(originURL.openStream(), targetFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void copyFile(InputStream inputStream, File targetFile) {
        if (inputStream == null) {
            return;
        }
        if (!targetFile.getParentFile().exists()) {
            targetFile.getParentFile().mkdirs();
        }
        if (!targetFile.exists()) {
            try {
                targetFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(targetFile);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        byte[] buffer = new byte[1024 * 1024]; // 1M的缓存
        try {
            int length = inputStream.read(buffer);
            while (length > 0) {
                byte[] targetBuffer = new byte[length];
                System.arraycopy(buffer, 0, targetBuffer, 0, length);
                outputStream.write(targetBuffer);
                length = inputStream.read(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.flush();
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void copyFile(File originFile, File targetFile) {

        if (originFile.length() == targetFile.length()) {
            return;
        }
        try {
            validateFile(targetFile);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (!targetFile.exists()) {
            try {
                targetFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(originFile);

            outputStream = new FileOutputStream(targetFile);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        // System.out.println("Copy file: " + originFile);
        // System.out.println("Target file: " + targetFile);
        byte[] buffer = new byte[1024 * 1024]; // 1M的缓存
        try {
            int length = inputStream.read(buffer);
            while (length > 0) {
                byte[] targetBuffer = new byte[length];
                System.arraycopy(buffer, 0, targetBuffer, 0, length);
                outputStream.write(targetBuffer);
                length = inputStream.read(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.flush();
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 检查文件不为空且存在 <br>
     * 2013-6-24 下午10:52:54
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static void fileNotFullAndExists(File file) throws Exception {
        if (file == null || !file.exists()) {
            throw new Exception("文件不存在！");
        }
    }

    /**
     * 读取文件内容并返回字节数组 <br>
     * 2013-9-3 下午12:10:06
     *
     * @param iconFile
     * @return
     */
    public static byte[] readFileToBytes(File iconFile) throws Exception {
        try {
            validateFile(iconFile);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        long fileSize = iconFile.length();
        if (fileSize > Integer.MAX_VALUE) {
            throw new Exception("读取的文件过大！");
        }
        byte[] data = new byte[(int) fileSize];// 由于文件已经确定，因此大小也可以确定

        try {
            int length = -1;

            FileInputStream inputStream = new FileInputStream(iconFile);
            try {
                int bufferSize = 128;
                byte[] buffer = new byte[bufferSize];
                int offset = 0;
                while ((length = inputStream.read(buffer)) != -1) {
                    // if(length != bufferSize){
                    // System.arraycopy(buffer, 0, data, offset, length);
                    // }
                    System.arraycopy(buffer, 0, data, offset, length);// 从缓冲区拷贝数组
                    offset += length;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            return null;
        }
        return data;
    }

    /**
     * 将字节组写入文件 <br>
     * 2013-9-3 下午2:39:21
     *
     * @param data
     * @param file
     */
    public static File writeBytesToFile(byte[] data, File file) {
        try {
            validateFile(file);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            outputStream.write(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    e.printStackTrace();
                }
            }
        }
        return file;

    }

    /**
     * 将对象写入文件 <br>
     * 2013-9-6 下午1:08:58
     *
     * @param targetFile
     * @param serializable
     */
    public static void serializeObjectToFile(File targetFile, Serializable serializable) {
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(targetFile);
            try {
                objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(serializable);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeOutStream(objectOutputStream);
            closeOutStream(fileOutputStream);
        }
    }

    public static void closeInputStream(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeOutStream(OutputStream outputStream) {
        if (outputStream != null) {
            try {
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Object unSerializeObjectFromFile(File targetFile) {
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        Object object = null;
        try {
            fileInputStream = new FileInputStream(targetFile);
            try {
                objectInputStream = new ObjectInputStream(fileInputStream);
                try {
                    object = objectInputStream.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeInputStream(objectInputStream);
            closeInputStream(fileInputStream);
        }
        return object;
    }


    public static void closeReader(Reader reader) {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeWriter(Writer writer) {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void flushAndCloseWriter(Writer writer) {
        if (writer != null) {
            try {
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
