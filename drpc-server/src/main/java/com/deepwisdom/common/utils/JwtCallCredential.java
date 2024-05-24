package com.deepwisdom.common.utils;

import io.grpc.CallCredentials;
import io.grpc.Metadata;
import io.grpc.Status;

import java.util.concurrent.Executor;

/**
* @ClassName: JwtCallCredential.java
* @Author: justin(zhanglei@fuzhi.ai)
* @Date: 2021/8/2 16:25
* @Description: JWT凭证工具类
* @Version: 1.0
*/
public class JwtCallCredential extends CallCredentials {
  private final String jwt;

  public JwtCallCredential(String jwt) {
    this.jwt = jwt;
  }

  @Override public void applyRequestMetadata(RequestInfo requestInfo, Executor executor,
          MetadataApplier metadataApplier) {
  	executor.execute(new Runnable() {
      @Override public void run() {
        try {
          Metadata headers = new Metadata();
          Metadata.Key<String> jwtKey = Metadata.Key.of("jwt", Metadata.ASCII_STRING_MARSHALLER);
          headers.put(jwtKey, jwt);
          metadataApplier.apply(headers);
        } catch (Throwable e) {
          metadataApplier.fail(Status.UNAUTHENTICATED.withCause(e));
        }
      }
    });
  }

  @Override public void thisUsesUnstableApi() {
  }
}
