package framework.beans.factory.support;

/**
 * @Author: SmallRong
 * @Description:用来解析配置文件的，而该接口只是定义了规范
 * @Date: Created in 16:07 2021/12/7
 * @Modified By;
 */
public interface BeanDefinitionReader {
    //获取注册表对象
    BeanDefinitionRegistry getRegistry();

    //加载配置文件并在注册表中进行注册
    void loadBeanDefinitions(String configLocation) throws Exception;
}
