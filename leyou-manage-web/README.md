# leyou-manage-web

> A Vue.js project

## Build Setup

####前台项目文件夹及文件描述
- build:web热部署的环境和一些配置,不用修改
- config:配置文件
    - index.js里面有端口号和ip的配置
- dist 打包文件,项目会打包到这里面
- node_modules:vue的一些插件和项目依赖,相当于java的lib
- src源码文件夹
    - assets:一些资源
    - components:全局组件
        - tree:树状组件
        - messages: 消息组件
        - form:form组件
        - cascader: 级联组件
    - page:页面,其实也都是组件
    - router:路由
- static一些静态资源
- index.html项目唯一的页面,其他都是组件
- package.json很重要的文件,一些配置描述和脚本,用于构件项目依赖,和pom差不多
    - devDependencies:开发时依赖,编译时用,编译完就不需要了
    - dependencies:依赖,打包完就不需要了
    - 脚本:
        - "dev": "webpack-dev-server --inline --progress --config build/webpack.dev.conf.js":热部署脚本
        - "start": "npm run dev":热部署命令
