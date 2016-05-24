# -*- encoding:utf8 -*-
'''
Created on 2012-11-21
测试函数
@author: zhaowei
'''
import urllib

    
def article(opener):
    print "article test..."
    publishurl="http://sharepytest.free4lab.com/upload/publish"
    itemdata=urllib.urlencode({"name":"python脚本创建文章","description":"python脚本创建文章","type":"4","writegroups":"262,","toMySelf":""})
    html = opener.open(publishurl, itemdata)
    #eval在转化字典的时候，不认识null。所以定一个null变量为None让eval识别并转化
    null = None 
    itemid = eval(html.read(), null)["id"]
    itemurl = "http://sharepytest.free4lab.com/item?id=" + str(itemid);
    if itemurl.__len__() < 0:
        print "error"
    else:
        try:
            html = opener.open(itemurl)
            #TODO 检验页面元素的正确性，根据页面元素判断是否最终发布资源成功
            #TODO 根据发布资源所选择的权限判断资源的相关操作是否正常.可搜索到、进入该资源所归属群组可看到等
            
        finally:
            print "finally delete item..."
            delete(itemid, opener)
        
def delete(self, opener):
    if self != None:
        delurl = "http://sharepytest.free4lab.com/resource/deleteitem"
        data = urllib.urlencode(({"itemId":self}))
        opener.open(delurl, data)
        
        
        