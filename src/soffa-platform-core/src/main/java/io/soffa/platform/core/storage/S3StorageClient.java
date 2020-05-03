package io.soffa.platform.core.storage;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import io.soffa.platform.core.commons.DateSupport;
import io.soffa.platform.core.exception.TechnicalException;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class S3StorageClient implements CloudStorageClient {

    private AmazonS3 s3client;
    private String defaultBucketName;

    private S3StorageClient(AmazonS3 s3client, String defaultBucketName) {
        this.s3client = s3client;
        this.defaultBucketName = defaultBucketName;
    }

    /*
    public S3StorageClient(String endpoint, int port, String accessKey, String secretKey, String defaultBucketName) {
        this(endpoint, port, accessKey, secretKey);
        this.defaultBucketName = defaultBucketName;
    }

    public S3StorageClient(String endpoint, int port, String accessKey, String secretKey) {
        try {
            AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
            HttpProxyConfig proxyConfig = HttpProxyConfig.getProxy();
            ClientConfiguration config = new ClientConfiguration();
            if (proxyConfig != null) {
                config.setProtocol(Protocol.HTTP);
                config.setProxyHost(proxyConfig.getProxyHost());
                config.setProxyPort(proxyConfig.getProxyPort());
                config.setProxyDomain(proxyConfig.getProxyDomain());
                config.setProxyUsername(proxyConfig.getProxyUsername());
                config.setProxyPassword(proxyConfig.getProxyPassword());
            }
            s3client = AmazonS3ClientBuilder
                .standard()
                .withClientConfiguration(config)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("https://" + endpoint + ":" + port, ""))
                .build();

        } catch (Exception e) {
            throw new TechnicalException("MINIO_CLIENT_INIT_ERR", e.getMessage(), e);
        }
    }
     */

    public static S3StorageClient createDefault(String region, String accessKey, String secretKey, String defaultBucketName) {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        AmazonS3 s3client = AmazonS3ClientBuilder
            .standard()
            .withRegion(region)
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .build();
        return new S3StorageClient(s3client, defaultBucketName);
    }


    @Override
    public void upload(String objectName, InputStream source, String contentType, long contentLength) {
        upload(defaultBucketName, objectName, source, contentType, contentLength);
    }

    @Override
    public void upload(String bucket, String objectName, InputStream source, String contentType, long contentLength) {
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentEncoding(StandardCharsets.UTF_8.name());
            metadata.setContentType(contentType);
            metadata.setContentLength(contentLength);
            s3client.putObject(bucket, objectName, source, metadata);
        } catch (Exception e) {
            throw new TechnicalException("MINIO_UPLOAD_ERROR", e.getMessage(), e);
        }

    }

    public String getDownloadUrl(String obectName, long expiresInMinutes) {
        return getDownloadUrl(defaultBucketName, obectName, expiresInMinutes);
    }

    public String getDownloadUrl(String bucket, String objectName, long expiresInMinutes) {
        try {
            return s3client.generatePresignedUrl(bucket, objectName, DateSupport.plusHours(new Date(), 2)).toURI().toString();
        } catch (Exception e) {
            throw new TechnicalException("MINIO_DOWNLOAD_URL_ERROR", e.getMessage(), e);
        }
    }


}
