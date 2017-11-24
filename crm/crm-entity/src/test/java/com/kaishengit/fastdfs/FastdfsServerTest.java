package com.kaishengit.fastdfs;


import org.apache.commons.io.IOUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import java.io.*;
import java.util.Properties;




public class FastdfsServerTest {
    @Test
    public void updateTest() throws IOException, MyException {
        Properties properties = new Properties();
        properties.setProperty(ClientGlobal.PROP_KEY_TRACKER_SERVERS,"192.168.56.27:22122");
        //初始化配置
        ClientGlobal.initByProperties(properties);

        //跟踪客户
        TrackerClient trackerClient = new TrackerClient();
        //跟踪服务器?
        TrackerServer trackerServer = trackerClient.getConnection();
        //储存服务器
        StorageClient storageClient = new StorageClient(trackerServer,null);

        InputStream in = new FileInputStream("D:/mm.jpg");
        //将流转化为字节数组
        byte[] bytes = IOUtils.toByteArray(in);

         String[] fileArray = storageClient.upload_file(bytes,"jpg",null);
        for(String file : fileArray){
            System.out.println(file);
        }
        in.close();
    }
    @Test
    public void downloadTest() throws IOException, MyException {
        Properties properties = new Properties();
        properties.setProperty(ClientGlobal.PROP_KEY_TRACKER_SERVERS,"192.168.56.27:22122");
        //初始化配置
        ClientGlobal.initByProperties(properties);
        //跟踪客户
        TrackerClient trackerClient = new TrackerClient();
        //跟踪服务器
        TrackerServer trackerServer = trackerClient.getConnection();
        //储存服务器
        StorageClient storageClient = new StorageClient(trackerServer,null);

        byte[] bytes = storageClient.download_file("group1","M00/00/00/wKg4G1oOVgqAbFx2AAD8kaitpIs086.jpg");
        OutputStream out = new FileOutputStream("D:/mm1.jpg");
        out.write(bytes);
        out.flush();
        out.close();
    }
}