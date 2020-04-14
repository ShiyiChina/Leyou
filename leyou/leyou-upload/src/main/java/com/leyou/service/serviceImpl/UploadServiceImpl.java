package com.leyou.service.serviceImpl;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.DefaultFastFileStorageClient;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.leyou.service.UploadService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class UploadServiceImpl implements UploadService {

    @Autowired
    private FastFileStorageClient storageClient;

    private final List<String> CONTENT_TYPES = Arrays.asList("image/jpeg", "image/gif");
    private final Logger logger = LoggerFactory.getLogger(UploadServiceImpl.class);

    /**
     * 图片上传
     * @param file
     * @return
     */
    @Override
    public String upload(MultipartFile file) {

        String originalFilename = file.getOriginalFilename();
        //检验图片格式
        String contentType = file.getContentType();
        if (!CONTENT_TYPES.contains(contentType)){
            logger.info("文件类型不合法：{}",originalFilename);
            // 文件类型不合法，直接返回null
            return null;
        }
        try {
            //检验图片内容
            BufferedImage bufferedImage = null;
            bufferedImage = ImageIO.read(file.getInputStream());
            if (bufferedImage == null) {
                logger.info("文件内容不合法：{}", originalFilename);
                return null;
            }
            //保存到服务器
            //file.transferTo(new File("D:\\IdeaPractice\\leyou\\image\\" + originalFilename));
            String ext = StringUtils.substringAfterLast(originalFilename, ".");
            StorePath storePath = this.storageClient.uploadFile(file.getInputStream(), file.getSize(), ext, null);

            //生成url地址，返回
            return "http://image.leyou.com/" + storePath.getFullPath();
        }
        catch (IOException e) {
            logger.info("服务器内部错误：{}", originalFilename);
            e.printStackTrace();
        }
        return null;
    }
}
