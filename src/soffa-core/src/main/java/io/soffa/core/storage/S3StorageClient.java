package io.soffa.core.storage;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import io.soffa.core.exception.TechnicalException;
import io.soffa.core.http.HttpProxyConfig;
import io.soffa.core.lang.DateSupport;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class S3StorageClient implements CloudStorageClient {

    private AmazonS3 s3client;
    private String defaultBucketName;

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
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://" + endpoint + ":" + port, ""))
                .build();

        } catch (Exception e) {
            throw new TechnicalException("MINIO_CLIENT_INIT_ERR", e.getMessage(), e);
        }
    }

    @Override
    public void upload(InputStream source, String objectName, String contentType) {
        upload(source, defaultBucketName, objectName, contentType);
    }

    @Override
    public void upload(InputStream source, String bucket, String objectName, String contentType) {
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentEncoding(StandardCharsets.UTF_8.name());
            metadata.setContentType(contentType);
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
