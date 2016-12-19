package com.wntp.github.elasticsearch.client.util

import java.io.UnsupportedEncodingException
import java.net.{URLEncoder, URISyntaxException}
import java.util
import com.ning.http.client.AsyncHttpClient
import com.wntp.github.elasticsearch.client.httpclient.HttpConstants

import org.apache.http.HttpHeaders
import org.apache.http.client.methods.{HttpPost, HttpRequestBase}
import org.apache.http.entity.StringEntity
import scala.collection.JavaConversions._

/**
  * Created by wunan on 16/12/19.
  */
class HttpUtils {
  /**
    * 增加HTTP header
    *
    * @param headerMap
    * @param httpRequestBase
    */
  def addHeader(headerMap: util.Map[String, String], httpRequestBase: HttpRequestBase) {
    if (headerMap != null && headerMap.size() > 0) {
      for (entry <- headerMap.entrySet) {
        httpRequestBase.addHeader(entry.getKey, entry.getValue)
      }
    }
    httpRequestBase.addHeader(HttpHeaders.USER_AGENT, HttpConstants.CUSTOM_USER_AGENT)
  }

  /**
    * 增加HTTP header
    *
    * @param headerMap
    * @param builder
    */
  def addHeader(headerMap: util.Map[String, String], builder: AsyncHttpClient.BoundRequestBuilder()) {
    if (headerMap != null && headerMap.size() > 0) {
      for (entry <- headerMap.entrySet) {
        builder.addHeader(entry.getKey, entry.getValue)
      }
    }
  }

  /**
    * 增加请求参数
    *
    * @param queryMap
    * @param builder
    */
  def addQueryParameter(queryMap: util.Map[String, String], charset: String, builder: AsyncHttpClient.BoundRequestBuilder) {
    if (queryMap != null && queryMap.size() > 0) {
      val encodedQueryMap: util.Map[String, String] = HttpUtils.encodeParameters(queryMap, charset)
      for (entry <- encodedQueryMap.entrySet) {
        builder.addQueryParameter(entry.getKey, entry.getValue)
      }
    }
  }

  /**
    * 增加请求体
    *
    * @param content
    * @param charset
    * @param httpPost
    * @throws UnsupportedEncodingException
    */
  @throws(classOf[UnsupportedEncodingException])
  def addBody(content: String, charset: String, httpPost: HttpPost) {
    if (content != null && !content.isEmpty) {
      httpPost.setEntity(new StringEntity(content, charset))
    }
  }

  /**
    * 增加请求体
    *
    * @param content
    * @param charset
    * @param builder
    * @throws UnsupportedEncodingException
    */
  @throws(classOf[UnsupportedEncodingException])
  def addBody(content: String, charset: String, builder: AsyncHttpClient#BoundRequestBuilder) {
    if (!Strings.isNullOrEmpty(content)) {
      builder.setBody(content.getBytes(charset))
    }
  }

  /**
    * 构造URL查询
    *
    * @param url
    * @param queryMap
    * @param charset
    * @return
    * @throws URISyntaxException
    */
  @throws(classOf[URISyntaxException])
  def composeUrl(url: String, queryMap: util.Map[String, String], charset: String): String = {
    if (queryMap == null || queryMap.size == 0) {
      return url
    }
    val toSendMap: util.Map[String, String] = encodeParameters(queryMap, charset)
    var requestUrl: String = url
    val questionMarkPos: Int = url.indexOf("?")
    if (questionMarkPos == -1) {
      requestUrl += "?" + HttpConstants.URL_JOINER.join(toSendMap)
    }
    else {
      requestUrl = requestUrl.substring(0, questionMarkPos + 1) + HttpConstants.URL_JOINER.join(toSendMap)
    }
    return requestUrl
  }

  /**
    * 对参数进行URLencode
    *
    * @param parameters
    * @param charset
    * @return
    */
  def encodeParameters(parameters: util.Map[String, String], charset: String): util.Map[String, String] = {
    val encodedMap: util.Map[String, String] = Maps.transformValues(parameters, new Function[String, String]() {
      def apply(input: String): String = {
        try {
          return URLEncoder.encode(input, charset)
        }
        catch {
          case e: UnsupportedEncodingException => {
            Throwables.propagate(e)
          }
        }
        throw new IllegalArgumentException("unknown Exception")
      }
    })
    return encodedMap
  }

}
