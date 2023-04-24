<template>
    <el-container class="main-class">
        <el-header>
            <el-row style="height: 60px">
                <el-col :span="2" style="height: 60px">
                    <el-avatar :size="60" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"></el-avatar>
                </el-col>
                <el-col :span="15" class="title">
                    贵院帮<span>——管理员({{ name }})</span>
                </el-col>
                <el-col :span="7" style="height: 60px">
                    <el-button type="info" class="logout" @click="logoutButton">退出登录</el-button>
                </el-col>
            </el-row>
        </el-header>
        <el-container>
            <!--侧栏-->
            <el-aside :width="menuActive ? '200px' : '60px'">
                <div class="menu-button" @click="menuActive = !menuActive">
                    <i class="el-icon-s-operation"></i>
                </div>
                <!--router:开启index作为path跳转    unique-opened:只展开一个子页面 -->

                <el-menu
                    :collapse="!menuActive"
                    :collapse-transition=false
                    default-active="2"
                    class="el-menu-vertical-demo"
                    @open="handleOpen"
                    @close="handleClose"
                    background-color="#545c64"
                    unique-opened
                    router
                    text-color="#fff"
                    active-text-color="#ffd04b">
                    <el-menu-item index="/home/userInfo">
                        <i class="el-icon-menu"></i>
                        <span slot="title">用户管理</span>
                    </el-menu-item>
                    <el-menu-item index="/home/announcement">
                        <i class="el-icon-menu"></i>
                        <span slot="title">公告管理</span>
                    </el-menu-item>
                    <el-menu-item index="/home/upkeep">
                        <i class="el-icon-menu"></i>
                        <span slot="title">维修管理</span>
                    </el-menu-item>
                    <el-menu-item index="/home/utou">
                        <i class="el-icon-menu"></i>
                        <span slot="title">代领管理</span>
                    </el-menu-item>
                    <el-menu-item index="/home/lostarticle">
                        <i class="el-icon-menu"></i>
                        <span slot="title">失物招领</span>
                    </el-menu-item>
                </el-menu>
            </el-aside>
            <!--主体-->
            <el-main>
                <!--面包屑-->
                <el-breadcrumb separator-class="el-icon-arrow-right">
                    <el-breadcrumb-item :to="{ path: '/' }" style="font-size: 15px; font-weight: bold">-首页-</el-breadcrumb-item>
                </el-breadcrumb>
                <!--只有首页才显示欢迎语句-->
                <span v-show="$router.currentRoute.path === '/'">欢迎访问</span>
                <!-- 作为主体的子路由 -->
                <router-view/>
            </el-main>
        </el-container>
    </el-container>
</template>

<script>
export default {
    data() {
        return {
            name: "admin",
            menuActive: true,
        }
    },
    name: "Home",
    components: {},
    methods: {

        handleOpen(key, keyPath) {
            console.log(key, keyPath);
        },
        handleClose(key, keyPath) {
            console.log(key, keyPath);
        },

        logoutButton() {
            this.$confirm('您将退出系统, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            })
            .then(() => {
                //设置请求头的token
                const config = {
                    headers: {
                        token: sessionStorage.getItem("token"),
                    }
                };
                //调后端退出接口
                this.$ajax.get("/user/logout", config)
                .then((res) => {
                    //清空本地缓存
                    sessionStorage.clear();
                    //跳转到登录页面
                    this.$router.replace('/login');
                    this.$message.success(res.data.msg);
                    setTimeout(() => {
                        //代码
                        window.location.reload()
                    }, 1000);
                });
            })
            .catch(() => {});
        }

    }
};
</script>

<style scoped>
/* scoped 受保护的样式，当前样式只在当前页面生效，避免样式污染 */

/* 主容器样式 */
.main-class {
    height: 100%;
}

.el-header {
    background-color: #B3C0D1;
    color: #333;
    text-align: center;
    line-height: 60px;
}

.el-aside {
    background-color: #D3DCE6;
    color: #333;
    text-align: center;
    line-height: 200px;
}

.el-main {
    background-color: #E9EEF3;
    color: #333;
    text-align: center;
    /*line-height: 160px;*/
}

.title {
    text-align: left;
    font-size: 28px;
    font-family: 华文行楷;
}

.logout {
    float: right;
    margin-top: 10px;
}

.menu-button {
    line-height: 60px;
    cursor: pointer;
}

</style>
