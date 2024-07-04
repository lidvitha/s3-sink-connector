package com.example;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.apache.kafka.connect.sink.SinkRecord;
import org.apache.kafka.connect.sink.SinkTask;
import java.util.Collection;
import java.util.Map;

public class S3SinkTask extends SinkTask {
    private AmazonS3 s3Client;
    private String bucketName;

    @Override
    public void start(Map<String, String> props) {
        String accessKeyId = props.get(S3SinkConfig.AWS_ACCESS_KEY_ID);
        String secretAccessKey = props.get(S3SinkConfig.AWS_SECRET_ACCESS_KEY);
        bucketName = props.get(S3SinkConfig.S3_BUCKET_NAME);

        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKeyId, secretAccessKey);
        s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.US_EAST_1)
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
    }

    @Override
    public void put(Collection<SinkRecord> records) {
        for (SinkRecord record : records) {
            String key = "kafka/" + record.kafkaPartition() + "/" + record.kafkaOffset();
            s3Client.putObject(bucketName, key, record.value().toString());
        }
    }

    @Override
    public void stop() {
        // Cleanup resources if necessary
    }

    @Override
    public String version() {
        return "1.0";
    }
}
