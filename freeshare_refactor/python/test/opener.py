# -*- coding: utf8 -*-
'''
Created on 2012-11-21
获取处理请求的opener
@author: zhaowei
'''
import urllib2, cookielib
def getoperner():
    redirectHandler = urllib2.HTTPRedirectHandler()
    cookie = cookielib.CookieJar()
    opener = urllib2.build_opener(redirectHandler, urllib2.HTTPCookieProcessor(cookie))
    urllib2.install_opener(opener)
    return opener