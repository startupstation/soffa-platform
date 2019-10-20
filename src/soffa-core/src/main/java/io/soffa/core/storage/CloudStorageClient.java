package io.soffa.core.storage;

import java.io.InputStream;

public interface CloudStorageClient {

    void upload(String bucket, String objectName, InputStream source, String contentType, long contentLength);

    void upload(String objectName,  InputStream source, String contentType, long contentLength);

    String getDownloadUrl(String bucket, String objectName, long expiresInMinutes);

    String getDownloadUrl(String objectName, long expiresInMinutes);

}
