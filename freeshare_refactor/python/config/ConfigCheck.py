# -*- coding:utf-8 -*-
#在上线和开发中，检查和更新url的配置文件
import os
import Const
class ConfigCheck:
    def __init__(self):
        self.flag = False #标识上线测试还是线下。False为线下测试
        
        self.initPath()
        self.initData()
       
    def initPath(self):
        path = os.getcwd() 
        try:
            path = path[:path.index("\python")]
        except:
            print "ERROR: PATH SPLITING IS ERROR"
            
        path += "\\src\\resource\\url.properties"
        print path
        self.path = path
        
    def initData(self):
        dict = {}
        c = Const.Const()
        if self.flag == False:
            dict = c.test  
        else:
            dict = c.online
        self.dict = dict
        
    def update(self):
        dict = self.dict
        f = open(self.path, "r+")
        list = []
        for k in dict.keys():
           list.append(k + " = " + dict[k] + "\n")
        f.writelines(list)
        f.close()

if __name__ == "__main__":
    print "main..."
    cc = ConfigCheck()
    cc.update()