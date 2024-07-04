package com.example;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;
import java.util.Map;

public class S3SinkConfig extends AbstractConfig {
    public static final String AWS_ACCESS_KEY_ID = "aws.access.key.id";
    public static final String AWS_SECRET_ACCESS_KEY = "aws.secret.access.key";
    public static final String S3_BUCKET_NAME = "s3.bucket.name";

    public S3SinkConfig(Map<?, ?> originals) {
        super(config(), originals);
    }

    public static ConfigDef config() {
        return new ConfigDef()
                .define(AWS_ACCESS_KEY_ID, ConfigDef.Type.STRING, ConfigDef.Importance.HIGH, "AWS Access Key ID")
                .define(AWS_SECRET_ACCESS_KEY, ConfigDef.Type.STRING, ConfigDef.Importance.HIGH, "AWS Secret Access Key")
                .define(S3_BUCKET_NAME, ConfigDef.Type.STRING, ConfigDef.Importance.HIGH, "S3 Bucket Name");
    }
}
