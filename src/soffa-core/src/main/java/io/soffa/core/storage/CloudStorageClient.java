package io.soffa.core.storage;

import java.io.InputStream;

public interface CloudStorageClient {

    void upload(InputStream source, String bucket, String objectName, String contentType);

    void upload(InputStream source, String objectName, String contentType);

    String getDownloadUrl(String bucket, String objectName, long expiresInMinutes);

    String getDownloadUrl(String objectName, long expiresInMinutes);

}
