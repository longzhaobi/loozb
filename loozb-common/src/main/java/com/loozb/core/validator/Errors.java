package com.loozb.core.validator;

/**
 * @Author： 龙召碧
 * @Date: Created in 2017-3-3 19:45
 */
public class Errors {
    private boolean hasErrors = false;

    private String msg;

    public boolean isHasErrors() {
        return hasErrors;
    }

    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
