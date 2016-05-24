package com.free4lab.freeshare.action.recommend;


import com.free4lab.freeshare.action.BaseAction;

/**
 * tuijian
 * 
 * @author Administrator
 * 
 */
public class LogAction extends BaseAction {
    private static final long serialVersionUID = 8219440757744050045L;
    private String logType;
    private Long itemId ;
    private String result;

    public String execute() {
        try {
//            result = RecommendUtil.log(getSessionUID(),itemId,logType);;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    public String getLogType() {
        return logType;
    }
    
    public void setLogType(String logType) {
        this.logType = logType;
    }


    public Long getItemId() {
        return itemId;
    }



    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }



    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}

