package com.leyou.controller;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("upload")
public class UploadController {
    public static final  List<String> list = Arrays.asList("jpg", "png");
//    @Value("${user.httpImage}")
//    private String httpImage;
    @RequestMapping("image")
    public String upload(@RequestParam("file") MultipartFile file){

        try {
            String name = file.getOriginalFilename();
            String lastname = name.substring(name.lastIndexOf(".")+1);
            if(!list.contains(lastname)){
                return null;
            }
            BufferedImage read = ImageIO.read(file.getInputStream());
            if(read==null){
                return null;
            }
            String newName= UUID.randomUUID().toString()+name;
//            //加载客户端配置文件，配置文件中指明了tracker服务器的地址
//            ClientGlobal.init("fastdfs.conf");
//            //验证配置文件是否加载成功
//            System.out.println(ClientGlobal.configInfo());
//
//            //创建TrackerClient对象，客户端对象
//            TrackerClient trackerClient = new TrackerClient();
//
//            //获取到调度对象，也就是与Tracker服务器取得联系
//            TrackerServer trackerServer = trackerClient.getConnection();
//
//            //创建存储客户端对象
//            StorageClient storageClient = new StorageClient(trackerServer,null);
//

//            NameValuePair[] params = new NameValuePair[1];
//            NameValuePair p = new NameValuePair();
//            p.setName("创建时间");p.setValue("333");
//            params[0] = p;
//            String[] upload_file = storageClient.upload_file("D:/test.jpg", "jpg", params);

//            String[] upload_file = storageClient.upload_appender_file(file.getBytes(), lastname, null);
//
//            for (String string : upload_file) {
//                System.out.println(string);
//            }
//
//            return httpImage+upload_file[0]+"/"+upload_file[1];
            file.transferTo(new File("d:/upload/"+newName));
            return "http://image.leyou.com/"+newName;
        } catch (IOException  e) {
            e.printStackTrace();
        }


        return  null;

    }
}
