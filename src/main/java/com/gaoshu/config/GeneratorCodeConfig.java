package com.gaoshu.config;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.LikeTable;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @Title: GeneratorCodeConfig
 * @Description: 自动生成mybatisplus的相关代码
 * @author: gaoshu
 * @date: 2021/11/24 15:17
 */
public class GeneratorCodeConfig {

    // 表前缀
    private static final String TABLE_PREFIX = "gaoshu_";
    // 模块名
//    private static final String MODULE = "/mybatis-plus";
    private static final String MODULE = "";
    // 建表方式， 1,精确匹配， 2，模糊匹配
    private static final int MATCHING_TYPE = 2;
    // 项目所在目录
    private static final String projectPath = System.getProperty("user.dir")+ "/src/main/resources";
    private static final String mapperProjectPath = projectPath+ "/mapper";

    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        mpg.setGlobalConfig(getGlobalConfig());
        // 数据源配置
        mpg.setDataSource(getDataSourceConfig());
        // 包配置
        mpg.setPackageInfo(getPackageConfig());
        // 策略配置
        mpg.setStrategy(getStrategyConfig());
        // 自定义输出配置
        mpg.setCfg(getCfg());
        // 自定义模板配置
//        mpg.setTemplate(getTemplateConfig());
        // 模板引擎,默认Velocity引擎
//        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }



    /**
     * 全局配置
     * @return
     */
    private static GlobalConfig getGlobalConfig() {
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOutputDir(projectPath);
        globalConfig.setAuthor("gaoshu");
        globalConfig.setOpen(false);
        //实体属性 Swagger2 注解
        globalConfig.setSwagger2(true);
        //覆盖已生成文件
        globalConfig.setFileOverride(true);
        globalConfig.setDateType(DateType.ONLY_DATE);

        return globalConfig;
    }

    /**
     * 数据源配置
     * @return
     */
    private static DataSourceConfig getDataSourceConfig() {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl("jdbc:mysql://82.157.156.205:3306/gaoshu_blog?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useSSL=false&allowPublicKeyRetrieval=true");
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("123456");
        return dataSourceConfig;
    }

    private static StrategyConfig getStrategyConfig() {
        StrategyConfig strategyConfig = new StrategyConfig();

//        strategyConfig.setSuperEntityClass("com.baomidou.mybatisplus.extension.activerecord.Model");

        // 公共父类
//        strategy.setSuperControllerClass("com.baomidou.ant.common.BaseController");
        // 写于父类中的公共字段
//        strategy.setSuperEntityColumns("id");

        switch (MATCHING_TYPE){
            case 1:
                strategyConfig.setInclude(scanner("表名，多个英文逗号分割").split(","));
                break;
            case 2:
                strategyConfig.setTablePrefix(TABLE_PREFIX);
                strategyConfig.setLikeTable(new LikeTable(TABLE_PREFIX));
//                strategyConfig.setLikeTable(new LikeTable("" + scanner("模糊匹配字符").split(","), SqlLike.RIGHT));
                break;
            default:
                break;
        }
//        strategyConfig.setControllerMappingHyphenStyle(true);
        strategyConfig.setRestControllerStyle(true);
        strategyConfig.setSkipView(true);
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setEntityLombokModel(true);

        List<TableFill> tableFillList=new ArrayList<>();
        tableFillList.add(new TableFill("create_time", FieldFill.INSERT));
        tableFillList.add(new TableFill("update_time", FieldFill.UPDATE));
        tableFillList.add(new TableFill("create_user_id", FieldFill.INSERT));
        strategyConfig.setTableFillList(tableFillList);

//        strategyConfig.setSuperMapperClass("com.kakarote.core.servlet.BaseMapper");
//        strategyConfig.setSuperServiceClass("com.kakarote.core.servlet.BaseService");
//        strategyConfig.setSuperServiceImplClass("com.kakarote.core.servlet.BaseServiceImpl");

        return strategyConfig;
    }

    /**
     * 包配置
     * @return
     */
    private static PackageConfig getPackageConfig(){
        // 包配置
        PackageConfig packageConfig = new PackageConfig();
//        pc.setModuleName(scanner("模块名"));
        packageConfig.setParent("com.gaoshu");
        packageConfig.setEntity("entity.PO");
//        packageConfig.setMapper("mapper.auto");
//        packageConfig.setService("service");
//        packageConfig.setServiceImpl("service.impl");
        return packageConfig;
    }

    /**
     * 自定义输出配置
     * @return
     */
    private static InjectionConfig getCfg(){
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        // 如果模板引擎是 freemarker
        //String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
         String templatePath = "/templates/mapper.xml.vm";
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return mapperProjectPath + MODULE
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        /*cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir("调用默认方法创建的目录");
                return false;
            }
        });*/
        cfg.setFileOutConfigList(focList);
        return cfg;
    }

    /**
     * mapper.xxl模板配置
     * @return
     */
    private static TemplateConfig getTemplateConfig(){
        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
//        templateConfig.setController("templates/java/controller.java").setMapper("templates/java/mapper.java");
        templateConfig.setService(null);
        templateConfig.setController(null);
        templateConfig.setXml(null);
        return templateConfig;
    }

}
