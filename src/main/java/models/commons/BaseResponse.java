package models.commons;

import utils.Printer;

public class BaseResponse {

    Printer log = new Printer(BaseResponse.class);

    int code;
    String type;
    String message;

    public int getCode() {return code;}

    public String getMessage() {return message;}

    public String getType() {return type;}

    public void printMessage(){log.new Info("The response message is: " + getMessage());}

    public void printCode(){log.new Info("The response code is: " + getCode());}

    public void printType(){log.new Info("The response type is: " + getType());}

}
