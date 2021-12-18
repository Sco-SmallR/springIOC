package framework.context;

import framework.beans.factory.BeanFactory;

/**
 * @Author: SmallRong
 * @Description:定义非延时加载功能
 * @Date: Created in 17:53 2021/12/7
 * @Modified By;
 */
public interface ApplicationContext extends BeanFactory {
    //进行配置文件加载并进行对象创建
    void refresh() throws IllegalStateException, Exception;
}
