# -*- coding:utf-8 -*-
import os
class ErrorLog:
    def __init__(self):
         with open("error.txt", 'a+') as file:
            self.file = file
    def p(self):
        self.file.readlines()
    def write(self, strings, isClose):
        self.file.writelines(strings)
        if isClose:
            self.close()
        
    def close(self):
        self.file.close()     
           
if __name__ == "__main__":
     el = ErrorLog()
     el.p()