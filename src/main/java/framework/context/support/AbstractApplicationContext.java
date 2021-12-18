package framework.context.support;

import framework.beans.factory.BeanDefinition;
import framework.beans.factory.support.BeanDefinitionReader;
import framework.beans.factory.support.BeanDefinitionRegistry;
import framework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: SmallRong
 * @Description:ApplicationContext接口的子实现类，用于立即加载
 * @Date: Created in 18:00 2021/12/7
 * @Modified By;
 */
public abstract class AbstractApplicationContext implements ApplicationContext {
    //声明解析器变量
    protected BeanDefinitionReader beanDefinitionReader;
    //定义用于存储bean对象的map容器
    protected Map<String, Object> singletonObjects = new HashMap<String, Object>();
    //声明配置文件路径的变量
    protected String configLocation;

    @Override
    public void refresh() throws IllegalStateException, Exception {
        //加载BeanDefinition对象
        beanDefinitionReader.loadBeanDefinitions(configLocation);
        //初始化bean
        finishBeanInitialization();
    }

    //bean的初始化
    private void finishBeanInitialization() throws Exception {
        //获取注册表对象
        BeanDefinitionRegistry registry = beanDefinitionReader.getRegistry();
        //获取BeanDefinition对象
        String[] beanNames = registry.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            BeanDefinition beanDefinition =
                    registry.getBeanDefinition(beanName);

            //进行bean的初始化
            getBean(beanName);//该类finishBeanInitialization()方法中调用getBean()方法使用到了模板方法模式
        }
    }
}
