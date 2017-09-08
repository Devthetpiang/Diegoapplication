package com.xavey.diego.api.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by astk on 4/25/16.
 *
 */
public class CallNumber implements Serializable {

    private String _id;
    private String number;
    private String status;
    private String assigned_to;
    private Date last_called;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String n) {
        this.number = n;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String s) {
        this.status = s;
    }

    public String getAssignedTo () { return this.assigned_to; }

    public void setAssignedTo(String name) {
        this.assigned_to= name;
    }

    public Date getLastCalled () { return this.last_called; }

    public void setLastCalled(Date t) {
        this.last_called= t;
    }

}
