package com.zhuli.file.model;

import com.zhuli.file.request.FileController;
import lombok.Data;
import sun.rmi.runtime.Log;

import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Properties;

/**
 * @Description 加载配置文件
 * @Author zhuli
 * @Date 2021/6/7/3:55 PM
 */
@Data
public class VersionModel {

    private String version;
    private int type;
    private String url;
    private String content;

    public VersionModel() {
        //加载项目同级目录下的配置文件
        if (FileController.filePath == null) return;
        Properties prop = new Properties();
        try {
            String path = FileController.filePath + "version.properties";
            System.err.println(path);
            InputStreamReader in = new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8);
            prop.load(in);
            version = prop.getProperty("app.version");
            type = Integer.parseInt(prop.getProperty("app.type"));
//            url = getLocalHostLANAddress().getHostAddress() + prop.getProperty("app.url");
            url = "http:" + getLocalHostLANAddress().getHostAddress() + ":8088/api/file/download";
            content = prop.getProperty("app.content");
            
        } catch (IOException e) {
            System.err.println("加载项目同级目录下的version.properties文件失败，请检查是否存在文件，文件内容为：\n{" +
                    "# 版本号\n" +
                    "app.version=1.0.0\n" +
                    "# 类型\n" +
                    "app.type=3\n" +
                    "# 下载地址\n" +
                    "app.url=http:/api/file/download\n" +
                    "# 更新说明\n" +
                    "app.content=apk版本更新}");
            e.printStackTrace();
        }
    }


    // 正确的IP拿法，即优先拿site-local地址
    private static InetAddress getLocalHostLANAddress() throws UnknownHostException {
        try {
            InetAddress candidateAddress = null;
            // 遍历所有的网络接口
            for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements(); ) {
                NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
                // 在所有的接口下再遍历IP
                for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements(); ) {
                    InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
                    if (!inetAddr.isLoopbackAddress()) {// 排除loopback类型地址
                        if (inetAddr.isSiteLocalAddress()) {
                            // 如果是site-local地址，就是它了
                            return inetAddr;
                        } else if (candidateAddress == null) {
                            // site-local类型的地址未被发现，先记录候选地址
                            candidateAddress = inetAddr;
                        }
                    }
                }
            }
            if (candidateAddress != null) {
                return candidateAddress;
            }
            // 如果没有发现 non-loopback地址.只能用最次选的方案
            InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
            if (jdkSuppliedAddress == null) {
                throw new UnknownHostException("The JDK InetAddress.getLocalHost() method unexpectedly returned null.");
            }
            return jdkSuppliedAddress;
        } catch (Exception e) {
            UnknownHostException unknownHostException = new UnknownHostException(
                    "Failed to determine LAN address: " + e);
            unknownHostException.initCause(e);
            throw unknownHostException;
        }
    }

}
