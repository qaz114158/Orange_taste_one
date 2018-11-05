package net.sourceforge.http.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by terry.c on 06/03/2018.
 */

public class BaseResponse<T> {

    @SerializedName("errornum")
    public String code;

    @SerializedName("errormsg")
    public String msg;

    public T data;

}
