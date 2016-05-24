# -*- coding:utf-8 -*-
import DbConnection
import datetime
import ErrorLog
class MoveData:
    def __init__(self):
        dc = DbConnection.DbConnection()
        self.dc = dc
        self.conn = dc.getConnection()
        self.cursor = self.conn.cursor()
        self.el = ErrorLog.ErrorLog()
    def close(self):
        self.dc.closeConnection()
    
    def moveData(self):
        try:
            lr_dict = self.list_relation()
            # 获取列表与资源关系，以列表id为key，归属资源为value
            lp_dict = self.list_permission()
            # 获取列表与归属群组关系，以列表id为key，归属群组为value   
            dict = {}
            # 新插入的列表的id为key，元组为value,元祖第一个元素为包含资源，第二个元素归属群组，两个元素分别又是元祖     
            no_dict = {}
            # 原有列表与新插入之后的列表的id对应关系
            errorLogs = []
            
            ls_query = "select * from lists"
            self.cursor.execute(ls_query)
            ls_result = self.cursor.fetchall()
            for r in ls_result:
                self.cursor.execute("""insert into resource values(%s,%s,%s,%s,%s,%s,%s,%s,%s)""",
                (None, -1, r[1], r[2], r[3], "", r[5], None, None))
                try:
                    bl_resources = lr_dict.get(r[0], [])
                    bl_groups = lp_dict.get(r[0], [])
                    dict[self.cursor.lastrowid] = [bl_resources, bl_groups]
                    no_dict[r[0]] = self.cursor.lastrowid
                except:
                    self.cursor.execute("delete from resource where id = %s", self.cursor.lastrowid)
                    raise
           
            rp_query = "insert into resource_permission values (%s, %s, %s, %s)"
            lr_query = "insert into list_relation values (%s, %s, %s, %s, %s)"
            for r in dict.keys():
                contains = dict[r][0]
                lr = []
                i = 0
                for l in contains :
                    if l == None:
                        continue
                    i = i + 1
                    elem = (None, l, r, i, None)
                    lr.append(elem)
                    
                belongs = dict[r][1]
                rp = []
                for p in belongs :
                    if p == None:
                        continue
                    elem = (None, r, p, 'read_write')
                    rp.append(elem)
                try:
                    self.cursor.executemany(rp_query, rp)
                    self.cursor.executemany(lr_query, lr)
                except:
#                    errorLogs.append(rp, "rp exception")
#                    errorLogs.append(lr, "lr exception")
                    raise
                
#            with open("error.txt", 'a+') as file:
#                file.writelines(errorLogs)
#                file.flush()
            self.clear(no_dict)
            self.conn.commit()
        finally:
            try:
                self.close()
            except:
                raise
            
    def list_relation(self):
            lr_dict = {}
            lr_query = "select * from list_relation"
            self.cursor.execute(lr_query)
            lr_result = self.cursor.fetchall()
            
            for r in lr_result:
                if lr_dict.has_key(r[2]):
                    lr_dict.get(r[2]).append(r[1])
                else:
                    lr_dict[r[2]] = [r[1]]
            return lr_dict
        
    def list_permission(self):
        lp_dict = {}
        lp_query = "select * from list_permission"
        self.cursor.execute(lp_query)
        lp_result = self.cursor.fetchall()
        # 可能为空
        for r in lp_result:
            if lp_dict.has_key(r[1]):
                lp_dict.get(r[1]).append(r[2])
            else:
                lp_dict[r[1]] = [r[2]]
        return lp_dict
        
    def clear(self, dict):
        print "clear the data"
        self.cursor = self.conn.cursor()
        try:
            self.clear_collection()
            self.clear_user_score(dict)
        except:
            print "clear exception"
            
    def clear_collection(self):
        print "clear the collection"
        del_query = "delete from freeshare.user_collection where user_collection.type = 'list'"
        try:
            self.cursor.execute(del_query)
        except:
            print "clear_collection exception"
        
    def clear_user_score(self, dict):
        print "clear user score"
        update_query = "update freeshare.user_score set freeshare.user_score.iid = %, freeshare.user_score.type = 'item' where iid = % and freeshare.user_score.type = 'list'"
        try:
            for (k, v) in dict.items():
                self.cursor.execute(update_query, (v, k))
            update_query = "update freeshare.user_score set freeshareuser_score.type = 'item' where freeshare.user_score.type = 'list'"
            self.cursor.execute(update_query)
        except:
            print "clear_user_score exception"
            
                  
if __name__ == "__main__":
    md = MoveData()
    md.moveData()
