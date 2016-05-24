class Const:
    def __init__(self):
        
        online = {}
        online["APIPrefix_FreeAccount"] = "http://authentication.free4lab.com"
        online["APIPrefix_FreeTag"] = "http://freetag.free4lab.com/api"
        online["APIPrefix_FreeSearch"] = "http://newfreesearch.free4lab.com"
        online["APIPrefix_FreeMessage"] = "http://webrtcmessage.free4lab.com/"
        online["APIPrefix_FreeFront"] = "http://front.free4lab.com/"
        online["APIPrefix_Recommend"] = "http://recommend.free4lab.com"
        online["APIPrefix_FreeDisk"] = "http://freedisk.free4lab.com"
        self.online = online
        
        test = {}
        test["APIPrefix_FreeAccount"] = "http://authentication.free4lab.com"
        test["APIPrefix_FreeTag"] = "http://freetag.free4lab.com/api"
        test["APIPrefix_FreeSearch"] = "http://freetestsearch.free4lab.com"
        test["APIPrefix_FreeMessage"] = "http://webrtcmessage.free4lab.com/"
        test["APIPrefix_FreeFront"] = "http://front.free4lab.com/"
        test["APIPrefix_Recommend"] = "http://recommend.free4lab.com"
        test["APIPrefix_FreeDisk"] = "http://freedisk.free4lab.com"
        self.test = test