package com.lovecoding.yangying.camera;

/**
 * Created by yangying on 18/2/22.
 */
import com.lovecoding.yangying.tools.UpdateSharedPreferences;
import com.lovecoding.yangying.tools.ReadProperties;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class ImageUploadUtils {
    private static final String TAG = "uploadFile";
    private static final int TIME_OUT = 10 * 10000000; // 超时时间
    private static final String CHARSET = "utf-8"; // 设置编码
    public static final int SUCCESS = 1;
    public static final int FAILURE = 0;

    private static Map<String,String> setParams(File file, String comment){
        Map<String,String> params = new HashMap<String,String>();
        params.put("create_user", UpdateSharedPreferences.getStringValue("username"));
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy/MM/dd HH:mm:ss");
        Date curDate =  new Date(System.currentTimeMillis());
        String strDate = formatter.format(curDate);
        params.put("create_time", strDate);
        params.put("image_name", file.getName());
        params.put("comment", comment);
        return params;
    }

    public static int uploadFile(File file, String comment) {
        String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
        String PREFIX = "--", LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data"; // 内容类型
        String RequestURL = ReadProperties.getStringProperties("hostname") +
                ReadProperties.getStringProperties("uploadImageServlet");
        try {
            URL url = new URL(RequestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setDoInput(true); // 允许输入流
            conn.setDoOutput(true); // 允许输出流
            conn.setUseCaches(false); // 不允许使用缓存
            conn.setRequestMethod("POST"); // 请求方式
            conn.setRequestProperty("Charset", CHARSET); // 设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
                    + BOUNDARY);
            if (file != null) {
                /**
                 * 当文件不为空，把文件包装并且上传
                 */
                OutputStream outputSteam = conn.getOutputStream();

                DataOutputStream dos = new DataOutputStream(outputSteam);
                StringBuffer sb = new StringBuffer();

                sb.append(LINE_END);
                Map<String,String> params = setParams(file, comment);
                if(params!=null){//根据格式，开始拼接文本参数
                    for(Map.Entry<String,String> entry:params.entrySet()){
                        sb.append(PREFIX).append(BOUNDARY).append(LINE_END);//分界符
                        sb.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINE_END);
                        sb.append("Content-Type: text/plain; charset=" + CHARSET + LINE_END);
                        sb.append("Content-Transfer-Encoding: 8bit" + LINE_END);
                        sb.append(LINE_END);
                        sb.append(entry.getValue());
                        sb.append(LINE_END);//换行！
                    }
                }

                /**
                 * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
                 * filename是文件的名字，包含后缀名的 比如:abc.png
                 */
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);
                sb.append("Content-Disposition: form-data; name=\"img\"; filename=\""
                        + file.getName() + "\"" + LINE_END);
                sb.append("Content-Type: application/octet-stream; charset="
                        + CHARSET + LINE_END);
                sb.append(LINE_END);
                dos.write(sb.toString().getBytes());
                InputStream is = new FileInputStream(file);
                byte[] bytes = new byte[1024];
                int len = 0;
                while ((len = is.read(bytes)) != -1) {
                    dos.write(bytes, 0, len);
                }
                is.close();
                dos.write(LINE_END.getBytes());
                byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
                        .getBytes();
                dos.write(end_data);
                dos.flush();
                /**
                 * 获取响应码 200=成功 当响应成功，获取响应的流
                 */
                int res = conn.getResponseCode();
                if (res == 200) {
                    return SUCCESS;
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return FAILURE;
    }
}
