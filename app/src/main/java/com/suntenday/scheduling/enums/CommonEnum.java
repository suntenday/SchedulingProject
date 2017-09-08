package com.suntenday.scheduling.enums;

/**
 * Created by zhengli08275 on 2017/9/8 0008.
 */

public enum CommonEnum {

    YES("1",true),NO("0",false);

    private String key;
    private Boolean value;

    private CommonEnum(String key, Boolean value){
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

    public static String getKey(Boolean value) {
        for (CommonEnum valueEnum : CommonEnum.values()) {
            if(valueEnum.getValue() == value){
                return valueEnum.getKey();
            }
        }
        return "";
    }

    public static Boolean getValue(String key) {
        for (CommonEnum valueEnum : CommonEnum.values()) {
            if (key.equals(valueEnum.getKey())) {
                return valueEnum.getValue();
            }
        }
        return false;
    }
}
