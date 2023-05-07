package com.com3014.s3service.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.com3014.s3service.exception.BucketDoesNotExistException;
import com.com3014.s3service.exception.BucketExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BucketService {

    private final AmazonS3 s3client;

    @Autowired
    public BucketService(AmazonS3 s3client) {
        this.s3client = s3client;
    }

    public Bucket createS3Bucket(String bucketName) {
        if(s3client.doesBucketExistV2(bucketName)) {
            throw new BucketExistsException("Bucket %s already exists.".formatted(bucketName));
        }
        return s3client.createBucket(bucketName);
    }

    public List<Bucket> listBuckets(){
        return s3client.listBuckets();
    }

    public void deleteBucket(String bucketName){
        try {
            s3client.deleteBucket(bucketName);
        } catch (AmazonServiceException e) {
            throw new BucketDoesNotExistException("bucket with name %s does not exist".formatted(bucketName));
        }
    }
}
