# -*- encoding:utf-8 -*-
'''
Created on 2012-11-21
各种测试
@author: zhaowei
'''
import urllib, opener, alltestfunc
opener = opener.getoperner()
#暂时思考，待确认
#opener添加了HTTPRedirectHandler，所以可以自动处理302跳转和跳转之后的返回
#否则，必须代码实现获取跳转url，和返回url
print "begain login ..."
opener.open("http://sharepytest.free4lab.com")
data = urllib.urlencode({"email":"free@free4lab.com","epsw":"telestar","passwordMd5":"40cc1685b089b83039c635980cc4ec50"})
accounturl = "http://account.free4lab.com/loginCheck"
opener.open(accounturl, data)

#开始测试各种发布资源
print "begain publishtest..."
print "--------------------------"
print "publish article..."
alltestfunc.article(opener)
print "publish article end"
