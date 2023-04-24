package com.gyu.code;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class MyCode {

    public static void main(String[] args) {
        AutoGenerator generator = new AutoGenerator();

        // 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl("jdbc:mysql://localhost:3306/gyubang?serverTimezone=Asia/Shanghai");
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("root");
        generator.setDataSource(dataSourceConfig);// 数据源配置添加到 generator

        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOutputDir(System.getProperty("user.dir") + "/src/main/java");// 设置输出到的目录
        globalConfig.setOpen(false);// 生成结束后是否打开文件夹
        globalConfig.setAuthor("choi");//设置作者
        globalConfig.setFileOverride(true);//设置是否覆盖原文件

        globalConfig.setMapperName("%sDao");
        globalConfig.setServiceName("%sService");
        globalConfig.setServiceImplName("%sServiceImpl");

        globalConfig.setBaseResultMap(false);//为模型对象生成结果映射
        generator.setGlobalConfig(globalConfig);// 全局配置添加到 generator 上

        // 包配置, 生成的代码放在哪个包下
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("com.gyu"); //修改为项目的包名称
        packageConfig.setEntity("domain");
        packageConfig.setMapper("dao");
        //packageConfig.setXml("mapper");
        generator.setPackageInfo(packageConfig); // 包配置添加到 generator

        // 策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);// 下划线驼峰命名转换
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setVersionFieldName("Version");//设置乐观锁字段名
        strategyConfig.setLogicDeleteFieldName("deleted");//设置逻辑删除字段名
        strategyConfig.setEntityLombokModel(true);// 开启lombok
        strategyConfig.setRestControllerStyle(true); // 开启Rest风格Controller
        strategyConfig.setInclude("comment");//需要生成的表,注释了全部表生成代码
        generator.setStrategy(strategyConfig);
        //generator.setTemplateEngine(new FreemarkerTemplateEngine());
        generator.execute();// 开始生成

    }
}