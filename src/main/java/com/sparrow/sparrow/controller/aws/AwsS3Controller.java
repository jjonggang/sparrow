package com.sparrow.sparrow.controller.aws;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/s3")
@RequiredArgsConstructor
public class AwsS3Controller {

//    private final AwsS3Service awsS3Service;
//
//    @PostMapping("/resource")
//    public AwsS3 upload(@RequestParam("file") MultipartFile multipartFile) throws IOException {
//        return awsS3Service.upload(multipartFile,"upload");
//    }
//
//    @DeleteMapping("/resource")
//    public void remove(AwsS3 awsS3) {
//        awsS3Service.remove(awsS3);
//    }
}