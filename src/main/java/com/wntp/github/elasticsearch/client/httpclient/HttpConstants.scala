package com.wntp.github.elasticsearch.client.httpclient


/**
  * Created by wunan on 16/12/19.
  */
object HttpConstants {
  /**
    * 默认报警阈值，当池中可用连接数小于5% 则报警
    */
  val SHRESHOLD: Double = 0.02
  val DEFAULT_SOCKET_TIMEOUT: Int = 30000
  val DEFAULT_CONNECT_TIMEOUT: Int = 30000
  /**
    * 单个路由默认最大连接数
    */
  val DEFAULT_MAX_PER_ROUTE: Int = 20
  /**
    * 连接池默认最大连接数
    */
  val DEFAULT_MAX_TOTAL: Int = 50
  val GBK_CHARSET: String = "GBK"
  /**
    * UA
    */
  val CUSTOM_USER_AGENT: String = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:30.0) Gecko/20100101 Firefox/30.0"
  //val URL_JOINER: Joiner.MapJoiner = Joiner.on("&").withKeyValueSeparator("=")

}
