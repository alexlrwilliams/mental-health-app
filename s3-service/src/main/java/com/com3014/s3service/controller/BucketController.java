package com.com3014.s3service.controller;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.com3014.s3service.service.BucketService;
import com.com3014.s3service.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/aws/buckets")
public class BucketController {

    private final BucketService bucketService;

    private final FileService fileService;

    @Autowired
    public BucketController(BucketService bucketService, FileService fileService) {
        this.bucketService = bucketService;
        this.fileService = fileService;
    }

    @GetMapping
    public List<Bucket> listBuckets() {
        return bucketService.listBuckets();
    }

    @PostMapping("/{bucketName}")
    public Bucket createS3Bucket(@PathVariable String bucketName) {
        return bucketService.createS3Bucket(bucketName);
    }

    @DeleteMapping("/{bucketName}")
    public void deleteBucket(@PathVariable String bucketName) {
        bucketService.deleteBucket(bucketName);
    }

    @PostMapping("/{bucketName}/upload")
    public void upload(@RequestParam MultipartFile file, @PathVariable String bucketName) throws IOException {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        String fileName = String.format("%s", file.getOriginalFilename());
        fileService.upload(bucketName, fileName, Optional.of(metadata), file.getInputStream());
    }

    @GetMapping(value = "/{bucketName}/{fileName}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public byte[] downloadFile(@PathVariable String bucketName, @PathVariable String fileName) throws IOException {
        byte[] content;
        final S3Object s3Object = fileService.download(bucketName, fileName);
        final S3ObjectInputStream stream = s3Object.getObjectContent();
        content = IOUtils.toByteArray(stream);
        s3Object.close();
        return content;
    }
}
