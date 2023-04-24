<template>
    <div class="form-class">
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <img class="logo" src="../assets/logo.png" />
            </div>
            <div class="text item">
                <el-form :model="form" status-icon :rules="rules" ref="form" label-width="75px">
                    <el-form-item label="账号：" prop="userName">
                        <el-input v-model="form.userName" />
                    </el-form-item>
                    <el-form-item label="密码：" prop="password">
                        <el-input type="password" v-model="form.password" />
                    </el-form-item>
                    <div style="display: flex; flex-wrap: nowrap; justify-content: space-between; height: 60px;">
                        <el-form-item label="验证码：" prop="verificationCode" style="width: 50%">
                            <el-input v-model="form.verificationCode" />
                        </el-form-item>
                        <div class="verificationCode" @click="getCode">{{ MyVerificationCode }}</div>
                    </div>
                    <el-form-item>
                        <el-button type="primary" @click="submit('form')">登录</el-button>
                        <el-button @click="reset('form')">重置</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </el-card>
    </div>
</template>

<script>
export default {
    name: "login",
    data() {
        return {
            MyVerificationCode: "",

            //表单对象
            form: {},
            //表单校验规则
            rules: {
                userName: [
                    { required: true, message: "请输入用户名", trigger: "blur" },
                    { min: 3, max: 20, message: "长度在 3 到 20 个字符", trigger: "blur" }
                ],
                password: [
                    { required: true, message: "请输入密码", trigger: "blur" }
                ],
            }
        };

    },
    //页面加载函数
    created() {},
    //页面加载完执行方法
    mounted: function(){
        this.$nextTick(function () {
            this.getCode();
        })
    },
    methods: {
        //获取验证码
        getCode() {
            this.$ajax.get("/user/getCode")
                .then((res) => {
                    this.MyVerificationCode = res.data.data.VerificationCode;
                    console.log(this.code);
                })
        },

        //表单提交
        submit() {
            this.$refs.form.validate((valid) => {
                //校验失败
                if (!valid) return this.$message({
                    showClose: true,
                    message: '账号或密码校验失败，请检查后再提交！！！',
                    type: 'error'
                });
                if (this.form.verificationCode != this.MyVerificationCode) {
                    this.$message.error("验证码输入错误，请重新输入！");
                    this.getCode();
                } else {
                    //如果校验成功就向后端发送请求登录
                    this.$ajax.post("/user/login", this.form)
                        .then((res) => {
                            const code = res.data.code;

                            if (code == 200) {
                                const token = res.data.data.token;
                                if (res.data.data.permission != "admin") {
                                    this.$message.error("用户权限不足，无法登录后台管理系统！");
                                    this.getCode();
                                } else {
                                    this.$message.success("登录成功！");
                                    this.$store.commit("setToken", token);
                                    setTimeout(() => {
                                        this.$router.push("/")
                                    }, 1000 );
                                }
                            } else {
                                this.$message.error(res.data.msg);
                                this.getCode();
                            }
                        })
                        .catch((res) => {
                            console.log(res);
                        });
                    }
            });
        },
        //表单重置
        reset() {
            this.$refs.form.resetFields();
        },
    }
};
</script>

<style scoped>
/*验证码*/
.verificationCode {
    height: 40px;
    width: 158px;
    background: url("~@/assets/img/bg3.jpg");
    background-size: 100% 100%;
    text-align: center;
    line-height: 40px;
    font-size: 25px;
    font-family: 华文行楷;
    color: #bea9a9;
    letter-spacing: 10px;
    font-style: italic;
    cursor: pointer;
}

.logo {
    width: 50px;
    height: 50px;
}

.form-class {
    /*border: 1px solid red;*/
    width: 30%;
    margin: 10% 35% auto;
    padding: 5px 5px;
}

.text {
    font-size: 14px;
}

.item {
    margin-bottom: 18px;
    margin-top: 20px;
    width: 90%;
}

.clearfix:before,
.clearfix:after {
    display: table;
    content: "";
}

.clearfix:after {
    clear: both
}

.box-card {
    width: 480px;
}
</style>