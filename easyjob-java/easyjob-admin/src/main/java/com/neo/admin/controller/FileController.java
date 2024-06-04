package com.neo.admin.controller;

import com.neo.admin.annotation.GlobalInterceptor;
import com.neo.common.entity.config.AppConfig;
import com.neo.common.entity.constants.Constants;
import com.neo.common.entity.enums.DateTimePatternEnum;
import com.neo.common.entity.enums.ImageFileUploadTypeEnum;
import com.neo.common.entity.enums.ImportTemplateTypeEnum;
import com.neo.common.entity.enums.ScaleFilter;
import com.neo.common.exceptionhandler.EasyJobException;
import com.neo.common.uilts.CommonUtils;
import com.neo.common.uilts.DateUtil;
import com.neo.common.uilts.R;
import com.neo.common.uilts.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @Description TODO
 * @Author Lenove
 * @Date 2024/6/2
 * @ClassName FileController
 * @MethodName
 * @Params
 */
@RestController
@RequestMapping("/file")
public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    @Resource
    private AppConfig appConfig;

    /**
     * @param file     上传的文件
     * @param ImagType 图片类型(0,分类图片;1,轮播图;2,分享小图;3,分享大图)
     * @Description TODO
     * @Author Lenove
     * @Date 2024/6/2
     * @MethodName uploadFile
     * @Return: 返回文件路径
     */
    @PostMapping("uploadFile")
    @GlobalInterceptor
    public R uploadFile(MultipartFile file, Integer ImagType) {
        ImageFileUploadTypeEnum fileUploadTypeEnum = ImageFileUploadTypeEnum.getType(ImagType);
        //根据时间创建储存文件的文件夹
        String month = DateUtil.format(new Date(), DateTimePatternEnum.YYYYMM.getPattern());
        String folderName = appConfig.getProjectFolder() + month;
        File folder = new File(folderName);
        if (!folder.exists()) {
            folder.mkdir();
        }
        /**
         * fileSuffix:文件后缀名
         * realFileName:储存时的文件名称
         * realFilePath:文件储存路径
         * localFile:文件路径(文件储存路径+文件名称)
         */
        String fileSuffix = CommonUtils.getFileSuffix(file.getOriginalFilename());
        String realFileName = CommonUtils.getRandomString(Constants.LENGTH_30) + fileSuffix;
        String realFilePath = month + "/" + realFileName;
        File localFile = new File(appConfig.getProjectFolder() + realFilePath);
        try {
            file.transferTo(localFile);
            //裁剪图片
            if (fileUploadTypeEnum != null) {
                ScaleFilter.createThumbnail(localFile, fileUploadTypeEnum.getMaxWidth(), fileUploadTypeEnum.getMaxWidth(), localFile);
            }
        } catch (IOException e) {
            logger.error("文件上传失败", e);
            throw new EasyJobException(ResultCode.ERROR_BAD, "文件上传失败");
        }

        return R.ok().data(realFilePath);
    }

    @RequestMapping("/getImage/{imageFolder}/{imageName}")
    @GlobalInterceptor
    public void getImage(HttpServletResponse response,
                         @PathVariable("imageFolder") String imageFolder,
                         @PathVariable("imageName") String imageName) {

        readImage(response, imageFolder, imageName);
    }

    private void readImage(HttpServletResponse response, String imageFolder, String imageName) {
        if (!StringUtils.hasText(imageFolder) || !StringUtils.hasText(imageName)) {
            return;
        }
        // 后缀名获取
        String fileSuffix = CommonUtils.getFileSuffix(imageName);
        String filePath = appConfig.getProjectFolder() + imageFolder + "/" + imageName;
        // 将 imageSuffix 中的点号替换为空字符串
        fileSuffix = fileSuffix.replace(".", "");
        String contentType = "image/" + fileSuffix;
        // 设置响应的内容类型为图像类型
        response.setContentType(contentType);
        // 设置响应的缓存控制头，允许缓存 30 天
        response.setHeader("Cache-Control", "max-2592000");
        readFile(response, filePath);
    }

    private void readFile(HttpServletResponse response, String filePath) {
        if (!CommonUtils.pathIsOk(filePath)) {
            return;
        }
        OutputStream outputStream = null;
        FileInputStream fileInputStream = null;
        try {
            File file = new File(filePath);
            byte[] byteData = new byte[1024];
            outputStream = response.getOutputStream();
            int len = 0;
            while ((len = fileInputStream.read(byteData)) != -1) {
                outputStream.write(byteData, 0, len);
            }
            outputStream.flush();
        } catch (Exception e) {
            logger.error("读取文件异常", e);
        } finally {
            fileFinally(outputStream, fileInputStream);
        }
    }

    /**
     * @Description type表示参数类型:
     * 0, "/template/template_question.xlsx", "问题模板.xlsx",
     * 1, "/template/template_exam.xlsx", "试题库模板.xlsx";
     * @Author Lenove
     * @Date 2024/6/2
     * @MethodName downloadTemplate
     * @Param null
     * @Return: null
     */
    @RequestMapping("/downloadTemplate")
    public void downloadTemplate(HttpServletResponse response, HttpServletRequest request, Integer type) {
        ImportTemplateTypeEnum templateTypeEnum = ImportTemplateTypeEnum.getByType(type);
        if (templateTypeEnum == null) {
            throw new EasyJobException(ResultCode.ERROR_600, "类型错误");
        }
        OutputStream out = null;
        InputStream in = null;
        try {
            String fileName = templateTypeEnum.getTemplateName();
            // 设置响应的内容类型为下载类型
            response.setContentType("application/x-msdownload; charset=UTF-8");
            // 根据浏览器类型设置文件名的编码方式
            if (request.getHeader("User-Agent").toLowerCase().indexOf("msie") > 0) {//IE浏览器
                fileName = URLEncoder.encode(fileName, "UTF-8");
            } else {
                fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), "ISO8859-1");
            }
            // 设置响应的头部信息，包括文件名
            response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
            //读取文件
            ClassPathResource resource = new ClassPathResource(templateTypeEnum.getTemplatePath());
            //
            in = resource.getInputStream();
            byte[] byteData = new byte[1024];
            out = response.getOutputStream();
            int len = 0;
            while ((len = in.read(byteData)) != -1) {
                out.write(byteData, 0, len);
            }
            out.flush();
        } catch (Exception e) {
            logger.error("读取文件异常", e);
        } finally {
            fileFinally(out, in);
        }
    }

    protected void fileFinally(OutputStream out, InputStream in) {
        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                logger.error("IO异常", e);
            }
        }
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                logger.error("IO异常", e);
            }
        }
    }
}
