package com.domain.codegenerator;

/**
 * 配置信息
 */
public class GeneratorConfig {

    /**
     * 此处根据自己情况，修改
     */
    public static String PROJECT_PATH = "E:/pay/Banana/parent/service/"; // 项目路径

    /**
     * 下面的位置是相对PROJECT_PATH,根据项目情况修改
     */
    public static String GENERATOR_CONFIG = "src/main/resources/generatorConfig.xml";
    public static String ENTITY_GEN_PATH = "src/main/java/com/domain/cms/domain"; // entity路径
    public static String DAO_GEN_PATH = "src/main/java/com/domain/cms/dao"; // 产生的DAO路径
    public static String DAO_IMPL_GEN_PATH = "src/main/java/com/domain/cms/dao/impl"; // 产生的DAO Impl路径
    public static String SERVICE_GEN_PATH = "src/main/java/com/domain/cms/service"; // 产生的service路径
    public static String SERVICE_IMPL_GEN_PATH = "src/main/java/com/domain/cms/service/impl"; // 产生的service Impl路径
    public static String RESOURCE_PATH = "src/main/resources"; // 资源路径
    public static String TEMPL_PATH = "src/main/java/com/domain/codegenerator/templ"; // 模版路径

}
