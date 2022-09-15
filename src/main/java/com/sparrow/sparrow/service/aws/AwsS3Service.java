package com.sparrow.sparrow.service.aws;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.kms.model.NotFoundException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sparrow.sparrow.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;
@Slf4j
@RequiredArgsConstructor
@Component
public class AwsS3Service {

    private final UserRepository userRepository;

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;  // S3 버킷 이름


    // delete file
    public void deleteFile(String fileName) {
//        log.info("file name : "+ fileName);
        try {
            amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, fileName));
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
        }
    }

    public boolean doesFileExist(String path) throws AmazonClientException,
            AmazonServiceException {

//        log.info(path);
        boolean isValidFile = true;
        try {
            ObjectMetadata objectMetadata = amazonS3Client.getObjectMetadata(bucket, path);

        } catch (NotFoundException nfe) {
            isValidFile = false;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            isValidFile = false;
        }
        return isValidFile;
    }

    public String upload(MultipartFile multipartFile, String dirName, Long userId) throws IOException {
        // 이미 사진이 존재하는 지 체크
        File uploadFile = convert(multipartFile)  // 파일 변환할 수 없으면 에러
                .orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File convert fail"));

        return upload(uploadFile, dirName, userId);
    }

    // S3로 파일 업로드하기
    private String upload(File uploadFile, String dirName, Long userId) {
        String fileName = dirName + "/" + UUID.randomUUID();  // S3에 저장된 파일 이름
        String uploadImageUrl = putS3(uploadFile, fileName); // s3로 업로드

        removeNewFile(uploadFile);
        return uploadImageUrl;
    }

    // S3로 업로드
    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    // 로컬에 저장된 이미지 지우기
    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
//            log.info("File delete success");
            return;
        }
//        log.info("File delete fail");
    }

    // 로컬에 파일 업로드 하기
    private Optional<File> convert(MultipartFile file) throws IOException {
//        log.info("file test1");
        // MultipartFile -> File, local(tempImage) -> 변환 -> S3 -> local 삭제
        File convertFile = new File(System.getProperty("user.dir") + "/tempImage/" + file.getOriginalFilename());
//        log.info("file test2");
//        log.info(convertFile.getPath());

        if (convertFile.createNewFile()) { // 바로 위에서 지정한 경로에 File이 생성됨 (경로가 잘못되었다면 생성 불가능)
            try (FileOutputStream fos = new FileOutputStream(convertFile)) { // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
//                log.info("file test3");
                fos.write(file.getBytes());
            }
//            log.info("file test4");
            return Optional.of(convertFile);
        }
//        log.info("file test5");

        return Optional.empty();
    }

    public Path load(String filename){
        return Paths.get(System.getProperty("user.dir") + "/capture-file").resolve(filename);
    }
//
//    public Resource loadAsResource(String filename) {
//        try {
//            Path file = load(filename);
//            Resource resource = new UrlResource(file.toUri());
//            if (resource.exists() || resource.isReadable()) {
//                return resource;
//            }
//            else {
//                throw new RuntimeException("Could not read file: " + filename);
//            }
//        }
//        catch (MalformedURLException e) {
//            throw new RuntimeException("Could not read file: " + filename, e);
//        }
//    }
}