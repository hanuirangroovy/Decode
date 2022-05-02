package com.ssafy.escapesvr.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ssafy.escapesvr.configuration.AwsS3Config;
import com.ssafy.escapesvr.entity.Theme;
import com.ssafy.escapesvr.repository.ThemeRepository;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.UUID;

import static com.google.common.io.Files.getFileExtension;

@Service
@Transactional
public class UrlToMultipartServiceImpl implements UrlToMultipartService{

    @Autowired
    ThemeRepository themeRepo;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.url}")
    private String url;

    @Autowired
    AwsS3Config amazonS3;

    // url을 파일로 만든 후 s3에 저장
    @Override
    public void changeUrl(){
        List<Theme> themeList=themeRepo.findAll();

//        for(Theme theme:themeList){
        FileItem fileItem=null;
        try{
            //url을 inputstream으로 변경
            InputStream inputStream =   new URL(themeList.get(0).getPosterUrl()).openStream();
            // url -> jpg로 변환후 파일 생성
            File file = File.createTempFile("poster_", ".jpg", new File("C:\\Users\\multicampus\\Documents\\example"));
            byte[] binary = IOUtils.toByteArray(inputStream);
            FileUtils.writeByteArrayToFile(file, binary);
            FileInputStream input = new FileInputStream(file);
            themeList.get(0).setPosterUrl("https://escape-bucket.s3.ap-northeast-2.amazonaws.com/"+file.getName());
            amazonS3.amazonS3Client().putObject(bucket,file.getName(),file);
            themeRepo.save(themeList.get(0));

        }catch (IOException e){
            System.out.println(e);
        }
        //    MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
    }
}
