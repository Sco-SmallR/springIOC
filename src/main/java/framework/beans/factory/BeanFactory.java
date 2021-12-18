package framework.beans.factory;

/**
 * @Author: SmallRong
 * @Description:IOC容器父接口
 * @Date: Created in 17:49 2021/12/7
 * @Modified By;
 */
public interface BeanFactory {
    //根据bean对象的名称获取bean对象
    Object getBean(String name) throws Exception;

    //根据bean对象的名称获取bean对象，并进行类型转换，此处使用泛型方法
    <T> T getBean(String name, Class<? extends T> clazz) throws Exception;
}
