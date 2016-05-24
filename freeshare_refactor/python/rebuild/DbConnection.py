# -*- coding:utf-8 -*-
import MySQLdb
class DbConnection:
    def __init__(self):
        self.host = "daas.free4lab.com"
        self.user = "freeshare"
        self.passwd = "freeshare"
        self.db = "freeshare"
        self.charset = "utf8"
    def getConnection(self):
        connection = MySQLdb.connect(host = self.host, charset = self.charset, user = self.user, passwd = self.passwd, db = self.db)
        self.connection = connection
        return connection
    
    def closeConnection(self):
        print "close in DbConnection"
        self.connection.close()
        