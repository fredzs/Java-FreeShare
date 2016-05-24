# -*- coding:utf-8 -*-
import DbConnection

class DataCheck:
    def __init__(self):
        dc = DbConnection()
        self.conn = dc.getConnection()
    
    def check(self):
        conn = self.conn
         