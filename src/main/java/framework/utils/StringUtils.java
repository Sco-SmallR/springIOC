package framework.utils;

/**
 * @Author: SmallRong
 * @Description:
 * @Date: Created in 18:01 2021/12/8
 * @Modified By;
 */
public class StringUtils {
    private StringUtils() {
    }

    //userDao  ==> setUserDao
    public static String getSetterMethodByFieldName(String fiedName) {
        String methodName = "set" + fiedName.substring(0, 1).toUpperCase() + fiedName.substring(1);
        return methodName;
    }
}
