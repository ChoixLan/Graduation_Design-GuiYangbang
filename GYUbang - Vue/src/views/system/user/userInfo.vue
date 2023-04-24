<template>
    <div>
        <el-card class="main-card">
            <div slot="header" class="clearfix">
                <span>用户管理</span>
            </div>
            <el-row>
                <el-col :span="12">
                    <el-input placeholder="请输入内容" clearable v-model="queryInfo.queryString" class="input-with-select">
                        <el-select v-model="queryInfo.classification" slot="prepend" placeholder="按类别">
                            <el-option label="学院" value="1"></el-option>
                            <el-option label="昵称" value="2"></el-option>
                            <el-option label="楼栋" value="3"></el-option>
                            <el-option label="权限" value="4"></el-option>
                        </el-select>
                        <el-button slot="append" icon="el-icon-search" @click="querySearch"></el-button>
                    </el-input>
                </el-col>
                <el-col :span="2">
                    <el-button style="margin-left: 10px;" @click="insert" type="primary">添加用户</el-button>
                </el-col>
            </el-row>
            <el-table :data="tableList" stripe>
                <el-table-column prop="avatar" label="头像">
                    <template slot-scope="scope">
                        <el-avatar shape="square" :size="large" :src="scope.row.avatar"></el-avatar>
                    </template>
                </el-table-column>
                <el-table-column prop="college" label="学院"/>
                <el-table-column prop="nickName" label="昵称"/>
                <el-table-column prop="sex" label="性别">
                    <template slot-scope="scope">
                        {{ scope.row.sex? "女" : "男" }}
                    </template>
                </el-table-column>
                <el-table-column prop="openId" label="用户小程序标识"/>
                <el-table-column prop="buildingNum" label="楼栋"/>
                <el-table-column prop="permission" label="权限"/>
                <el-table-column fixed="right" label="操作" width="150">
                    <template slot-scope="scope">
                        <el-button type="primary" @click="update(scope.row)" size="small" icon="el-icon-edit" circle/>
                        <el-button type="danger" @click="deleteById(scope.row.id)" size="small" icon="el-icon-delete" circle/>
                    </template>
                </el-table-column>
            </el-table>
            <el-pagination
                @size-change="handlePageSize"
                @current-change="handlePageCurrent"
                :current-page="queryInfo.pageCurrent"
                :page-sizes="[6, 10, 15, 20 ]"
                :page-size="queryInfo.pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="total"/>
        </el-card>

        <!-- 表单添加/修改 -->
        <el-dialog :title="title" :visible.sync="open" width="30%" @close="dialogClose">
            <el-form :model="form" ref="form" :rules="rules">
                <el-form-item label="学院" prop="college">
                    <el-input v-model="form.college"/>
                </el-form-item>
                <el-form-item label="昵称" prop="nickName">
                    <el-input v-model="form.nickName"/>
                </el-form-item>
                <el-form-item label="楼栋" prop="buildingNum">
                    <el-input v-model="form.buildingNum"/>
                </el-form-item>
                <el-form-item label="权限" prop="permission">
                    <el-input v-model="form.permission"/>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="clickCancel">取 消</el-button>
                <el-button type="primary" @click="clickOk">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
export default {
    data() {
        return {
            // 分页查询数据
            queryInfo: {
                // 当前页码
                pageCurrent: 1,
                // 每页数量
                pageSize: 6,
                // 类别
                classification: "按分类查询",
                // 查询关键字
                queryString: null
            },
            // 表格数据
            tableList: [],
            // 总记录数
            total: 0,
            // 表单标题
            title: null,
            // 是否打开对话框
            open: false,
            // 表单数据
            form: {},
            // 表单校验
            rules: {
                college: [
                    { required: true, message: '请输入学院信息', trigger: 'blur' },
                    { min: 1, max: 50, message: '学院名称的长度为1~50之间', trigger: 'blur' }
                ],
                nickName: [
                    { required: true, message: '请输入昵称', trigger: 'blur' },
                    { min: 1, max: 20, message: '昵称的长度为1~20之间', trigger: 'blur' }
                ],
                userName: [
                    { required: true, message: '请输入账号', trigger: 'blur' },
                    { min: 1, max: 100, message: '昵称的长度为1~100之间', trigger: 'blur' }
                ],
                password: [
                    { required: true, message: '请输入密码', trigger: 'blur' },
                    { min: 1, max: 100, message: '昵称的长度为1~100之间', trigger: 'blur' }
                ],
                buildingNum: [
                    { required: true, message: '请输楼栋号', trigger: 'blur' },
                    { min: 1, max: 20, message: '昵称的长度为1~20之间', trigger: 'blur' }
                ],
                permission: [
                    { required: true, message: '请输入权限', trigger: 'blur' },
                    { min: 1, max: 10, message: '昵称的长度为1~10之间', trigger: 'blur' }
                ],
            },

        }
    },
    //页面加载完执行方法
    mounted: function(){
    },
    //页面初始化
    created() {
        this.findPage();
    },
    methods: {
        // 添加
        insert() {
            this.title = "新增用户";
            this.open = true;
        },
        // 修改
        update(row) {
            this.form = row;
            console.log(row);
            this.title = "修改用户信息";
            this.open = true;
        },

        // 搜索
        querySearch() {
            this.getPageUserByClassification();
        },

        getPageUserByClassification() {
            this.$ajax.post("/findUser/getPageUserByClassification", this.queryInfo)
                .then((res) => {
                    this.tableList=res.data.data.records;
                    this.total=res.data.data.total;
                }).catch((res) => {

            })
        },

        // 分页查询
        findPage() {
            this.$ajax.post("/findUser/getPageUser", this.queryInfo)
                .then((res) => {
                    this.tableList=res.data.data.records;
                    this.total=res.data.data.total;
                }).catch((res) => {

            })
        },

        // 页码改变事件
        handlePageCurrent(newPageCurrent) {
            this.queryInfo.pageCurrent = newPageCurrent;
            this.findPage();
        },

        // 页数据量改变事件
        handlePageSize(newPageSize) {
            this.queryInfo.pageSize = newPageSize;
            this.findPage();
        },

        /** 删除用户信息 */
        deleteById(id) {
            this.$confirm('您将操作将永久删除编号为{' + id +'} 的数据, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                //调后端删除用户接口
                this.$ajax.delete(`/findUser/deleteUserById/${id}`).then((res) => {
                    this.$message.success(res.data.msg);
                    this.queryInfo.pageCurrent = 1;
                    this.findPage();
                });
            }).catch(() => {});
        },

        /** 关闭对话框的事件 */
        dialogClose() {
            //将整个表单进行重置，并移除校验效果
            this.$refs.form.resetFields();
        },

        /** 点击取消 */
        clickCancel() {
            //将整个表单进行重置，并移除校验效果
            this.form = {};
            this.open = false;
            this.findPage();
        },

        /** 点击确定 */
        clickOk() {
            // 进行表单校验
            this.$refs.form.validate((valid) => {
                //校验不通过
                if (!valid) return this.$message.error('表单校验不通过，请检查后提交！');
                //校验通过 判断是否是新增
                if (this.form.id === undefined || this.form.id === null) {
                    this.$ajax.post('/findUser/insert', this.form).then((res) => {
                        this.$message.success(res.data.msg);
                        this.open = false;
                        this.findPage();
                    });
                } else {
                    this.$ajax.put('/findUser/update', this.form).then((res) => {
                        this.$message.success(res.data.msg);
                        this.open = false;
                        this.findPage();
                    });
                }
            });
        },


    },
}
</script>

<style>
.main-card {
    margin: 20px 0px;
    border-radius: 8px;
    width: 100%;
    height: 100%;
}

.el-select .el-input {
    width: 130px;
}
.input-with-select .el-input-group__prepend {
    background-color: #fff;
}
</style>