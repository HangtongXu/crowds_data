package com.xht.controller;

import com.xht.pojo.*;
import com.xht.service.MissionDetailsService;
import com.xht.service.UserService;
import com.xht.utils.FileZips;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;


/**
 * 文件上传
 */
@RestController
public class FileController {

    @Autowired
    private UserService userService;
    @Autowired
    private MissionDetailsService missionDetailsService;

    @RequestMapping("/uploadImg")
    public String imgUpload(@RequestParam(value = "file") MultipartFile file, UserMessage  userMessage) throws IOException{
        if (file.isEmpty()) {
            System.out.println("文件为空");
            return "forbidden";
        }
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        String filePath = "C://Users//11548//Desktop//codes//img//"; // 上传后的路径
        fileName =userMessage.getUid()+"-people-photo" + suffixName; // 新文件名
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
        String filename = "/user-img/" + fileName;
        userMessage.setImg(filename);
        userService.updateUserImg(userMessage);
        return filename;
    }
    @RequestMapping("/uploadMissionFile")
    public String pictureUpload(@RequestParam(value = "file") MultipartFile file, @RequestParam(value = "mid")int mid, @RequestParam(value = "wid")int wid) throws IOException{
        if (file.isEmpty()) {
            System.out.println("文件为空");
            return "forbidden";
        }
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        String filePath = "C://Users//11548//Desktop//codes//missionFile//"+mid+"//"+wid+"//"; // 上传后的路径
        fileName =mid+"missionfile" + suffixName; // 新文件名
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {//路径不存在，则新建文件夹
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
        String filename = "/mission-file/"+mid+"/"+wid;
        //更新文件位置至数据库并更新相关状态
        missionDetailsService.uploadMissionFile(filename,mid,wid);
        return filename;
    }

    @GetMapping("/fileDownload")
    public Object fileDownload(@RequestParam("mid")int mid, final HttpServletResponse response,final HttpServletRequest request){
        System.out.println(mid);
        String path="C:/Users/11548/Desktop/codes/missionFile/"+mid;
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + mid+".zipd");
        try {
            FileZips.toZip(path,response.getOutputStream(),true);
            response.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

