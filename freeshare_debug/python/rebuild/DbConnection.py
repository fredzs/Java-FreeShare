# -*- coding:utf-8 -*-
import MySQLdb
class DbConnection:
    def __init__(self):
        self.host = "http://daas.free4lab.com"
        self.user = "freeshare"
        self.paswd = "freeshare"
        self.db = "freeshare_release"
        
    def getConnection(self):
        connection = MySQLdb.connect(self.host, self.user, self.paswd, self.db)
        return connection
       