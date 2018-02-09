package com.tangqiang.adb.event;

/**
 * TODO
 *
 * @author Tom
 * @version 1.0 2018-02-09 0009 Tom create
 * @date 2018-02-09 0009
 * @copyright Copyright © 2018 Grgbanking All rights reserved.
 */
public class EventItem {

    /**
     * 事件发生时间的秒数
     **/
    public long secTime;
    public long msTime;//	事件发生时间的微妙数
    public String deviceId;//android设备事件输入文件名
    public Type type;    //事件类型码
    public int code;//扫描码或键值
    public int value;//值

    private EventItem(long secTime, long msTime, String deviceId, Type type, int code, int value) {
        this.secTime = secTime;
        this.msTime = msTime;
        this.deviceId = deviceId;
        this.type = type;
        this.code = code;
        this.value = value;
    }

    public static EventItem from(String line) {
        try {
            String[] message = line.replaceAll("\\[|\\]|:|\\.", " ").replaceAll("  ", " ").trim().split(" ");
            if (message.length == 6) {
                long secTime = Long.valueOf(message[0]);
                long msTime = Long.valueOf(message[1]);
                String deviceId = message[2];
                int type = Integer.valueOf(message[3]);
                Type en = type == 0 ? Type.SYN : type == 1 ? Type.KEY : type == 3 ? Type.ABS : Type.UNKNOW;
                int code = Integer.valueOf(message[4], 16);
                int value = Integer.valueOf(message[5], 16);
                return new EventItem(secTime, msTime, deviceId, en, code, value);
            }
        } catch (Exception e) {
            System.out.println("EventItem.from :" + e.getMessage());
        }
        return null;
    }

    @Override
    public String toString() {
        return "EventItem (sec:" + secTime + " ms:" + msTime + " type:" + type + " code:" + code + " value:" + value + ")";
    }


    public enum Type {
        KEY,
        ABS,
        SYN,
        UNKNOW
    }
}
