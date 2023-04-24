<template>
    <div>
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <span>公告管理</span>
            </div>
            <el-row>
                <el-col :span="10">
                    <el-input placeholder="请输入查询内容" clearable v-model="queryInfo.queryString" class="input-with-select">
                        <el-button slot="append" icon="el-icon-search" @click="querySearch"></el-button>
                    </el-input>
                </el-col>
                <el-col :span="2">
                    <el-button style="margin-left: 10px;" @click="insert" type="primary">添加公告</el-button>
                </el-col>
            </el-row>
            <el-table :data="tableList" stripe>
                <el-table-column prop="title" label="标题"/>
                <el-table-column prop="author" label="发布者"/>
                <el-table-column prop="buildingNum" label="楼栋"/>
                <el-table-column prop="releaseTime" label="发布时间"/>
                <el-table-column label="是否启用">
                    <template slot-scope="scope">
                        <el-switch
                            v-model="scope.row.status"
                            @change="updateStatus(scope.row)"/>
                    </template>
                </el-table-column>
                <el-table-column fixed="right" label="操作" width="150">
                    <template slot-scope="scope">
                        <el-button type="primary" @click="update(scope.row)" size="small" icon="el-icon-edit" circle/>
                        <el-button type="primary" @click="specifics(scope.row)" size="small" icon="el-icon-s-order" circle/>
                    </template>
                </el-table-column>
            </el-table>
            <el-pagination
                @size-change="handlePageSize"
                @current-change="handlePageCurrent"
                :current-page="queryInfo.pageCurrent"
                :page-sizes="[7, 10, 15, 20 ]"
                :page-size="queryInfo.pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="total"/>
        </el-card>

        <!-- 表单添加/修改 -->
        <el-dialog :title="title" :visible.sync="formOpen" width="80%" @close="dialogClose">
            <el-form :model="form" ref="form" :rules="rules">
                <el-form-item label="标题" prop="title">
                    <el-input v-model="form.title"/>
                </el-form-item>
                <el-row>
                    <el-col :span="7">
                        <el-form-item label="发布人" prop="author">
                            <el-input v-model="form.author"/>
                        </el-form-item>
                    </el-col>
                    <el-col :span="7">
                        <el-form-item label="楼栋" prop="buildingNum">
                            <el-input v-model="form.buildingNum"/>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-form-item label="内容" prop="content">
                    <el-input
                        type="textarea"
                        :rows="10"
                        placeholder="请输入公告内容"
                        v-model="form.content">
                    </el-input>
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
    name: "Announcement",
    data() {
        return {
            // 分页查询数据
            queryInfo: {
                // 当前页码
                pageCurrent: 1,
                // 每页数量
                pageSize: 7,
            },
            // 表格数据
            tableList: [],
            // 总记录数
            total: 0,
            // 表单标题
            title: null,
            // 是否打开对话框
            formOpen: false,
            // 表单数据
            form: {},
            // 表单校验
            rules: {
                title: [
                    { required: true, message: '请输入标题', trigger: 'blur' },
                    { min: 1, max: 50, message: '学院名称的长度为1~100之间', trigger: 'blur' }
                ],
                author: [
                    { required: true, message: '请输入发布人', trigger: 'blur' },
                    { min: 1, max: 20, message: '昵称的长度为1~20之间', trigger: 'blur' }
                ],
                buildingNum: [
                    { required: true, message: '请输楼栋号', trigger: 'blur' },
                    { min: 1, max: 20, message: '昵称的长度为1~20之间', trigger: 'blur' }
                ],
                content: [
                    { required: true, message: '请输入公告内容', trigger: 'blur' },
                    { min: 1, max: 500, message: '公告内容的长度为1~500之间', trigger: 'blur' }
                ],
            },
        };
    },
    //页面加载完执行方法
    mounted: function(){},
    //页面初始化
    created() {
        this.findPage();
    },


    methods: {
        // 添加
        insert() {
            this.title = "添加公告";
            this.formOpen = true;
        },
        // 修改
        update(row) {
            this.form = row;
            this.title = "修改公告信息";
            this.formOpen = true;
        },

        /**
         * 页码改变事件
         */
        handlePageCurrent(newPageCurrent) {
            this.queryInfo.pageCurrent = newPageCurrent;
            this.findPage();
        },

        /**
         * 页数据量改变事件
         */
        handlePageSize(newPageSize) {
            this.queryInfo.pageSize = newPageSize;
            this.findPage();
        },


        /**
         * 分页查询
         */
        findPage() {
            this.$ajax.post("/findAnnouncement/getPage", this.queryInfo)
                .then((res) => {
                    this.tableList = res.data.data.records;
                    this.total = res.data.data.total;
                }).catch((res) => {

            })
        },

        /**
         * 模糊查询
         */
        querySearch() {
            this.$ajax.post("/findAnnouncement/querySearch", this.queryInfo).then((res) => {
                this.tableList=res.data.data.records;
                this.total=res.data.data.total;
            });
        },

        /** 改变权限数据的状态 */
        updateStatus(row) {
            console.log(row);
            this.$ajax.put("/findAnnouncement/update", row).then((res) => {
                this.$message.success('状态更新成功！');
            });
        },

        /** 关闭对话框的事件 */
        dialogClose() {
            //将整个表单进行重置，并移除校验效果
            this.form = {};
            this.$refs.form.resetFields();
            this.findPage();
        },

        /** 点击取消 */
        clickCancel() {
            //将整个表单进行重置，并移除校验效果
            this.form = {};
            this.formOpen = false;
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
                    this.$ajax.post('/findAnnouncement/insert', this.form).then((res) => {
                        this.$message.success(res.data.msg);
                        this.formOpen = false;
                        this.findPage();
                    });
                } else {
                    this.$ajax.put('/findAnnouncement/update', this.form).then((res) => {
                        this.$message.success(res.data.msg);
                        this.formOpen = false;
                        this.findPage();
                    });
                }
            });
        },


    },
};
</script>

<style scoped>

.box-card {
    margin-top: 20px;
    border-radius: 8px;
}
</style>