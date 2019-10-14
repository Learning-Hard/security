package io.github.learninghard.controller;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;


/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning Hard
 * \* Date: 2019-07-21
 * \* Time: 17:48
 * \* To change this template use File | Settings | File Templates.
 * \* Description: 文件上传操作类
 * \
 */
@RestController
@RequestMapping("/file")
public class FileController {
    Logger logger = LoggerFactory.getLogger(FileController.class);

    /**
     * 文件上传
     * @param file 名字要和传递参数保持一致，不然映射不到文件
     */
    @PostMapping
    public void upload(MultipartFile file) throws IOException {
        logger.info(file.getName());
        logger.info(file.getContentType());
        logger.info(String.valueOf(file.getSize()));

        /*保存文件路径和文件名*/
        File newfile = new File("E:\\用户\\桌面\\",System.currentTimeMillis()+".txt");

        /*将文件保存到指定位置*/
        file.transferTo(newfile);
    }

    /**
     * 文件下载
     * @param id
     * @param response
     * @throws IOException
     */
    @GetMapping("/{id}")
    public void download(@PathVariable(name = "id") String id, HttpServletResponse response) {
        String path = "E:\\用户\\桌面\\" + id + ".jpg";
        /**
         * 将流放在try中，会自动关闭流
         */
        try (InputStream fileInputStream = new FileInputStream(new File(path))) {
            /* 设置ContentType 可以解决输出流乱码问题 */
            response.setContentType("image/jpeg;charset=UTF-8");
            ServletOutputStream outputStream = response.getOutputStream();
            /**
             * Tomcat提供的类 将输入流复制到输出流
             */
            IOUtils.copy(fileInputStream, outputStream);
            outputStream.flush();
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
    }
}
