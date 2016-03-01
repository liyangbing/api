package com.domain.app.security;

/**
 *
 * 建立登录判断后返回给浏览器的结果类型, (
 * 这里有两种: 传统登录页面或者ajax结果).
 *
 */
public enum ResultTypeEnum {
    //整页刷新
    page,

    // json
    json
}