package com.bjio.my.shop.web.admin.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 文件上传控制器
 * <p>Title: UploadController</p>
 * <p>Description: </p>
 *
 * @author jiofier
 * @version 1.0.0
 * @date 2019/12/13 1:30
 */
@Controller
public class UploadController {

    public static final String UPLOAD_PATH = "/static/upload/";

    /**
     * 文件上传
     * @param dropFile
     * @param editFile
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "upload",method = RequestMethod.POST)
    public Map<String, Object> upload(MultipartFile dropFile,MultipartFile editFile, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();

        MultipartFile myFile = dropFile==null?editFile:dropFile;

        // 获取上传的原始文件名
        String fileName = myFile.getOriginalFilename();
        // 设置文件上传路径
        String filePath = request.getSession().getServletContext().getRealPath(UPLOAD_PATH);
        // 获取文件后缀
        String fileSuffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());

        // 判断并创建上传用的文件夹
        File file = new File(filePath);
        if(!file.exists()){
            file.mkdir();
        }

        // 重新设置文件名为 UUID，以确保唯一
        file = new File(filePath, UUID.randomUUID().toString().concat(fileSuffix));

        try {
            myFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 返回 JSON 数据，这里只带入了文件名
        // 通过 dropFile 上传图片
        if(dropFile!=null){
            result.put("fileName",UPLOAD_PATH.concat(file.getName()));
        }

        //通过 editFile 上传图片
        else{
            String serverPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
            result.put("errno",0);
            result.put("data",new String[]{serverPath+UPLOAD_PATH+file.getName()});
        }

        return result;
    }
}
