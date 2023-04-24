<template>
    <div>
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <span>维修管理</span>
            </div>
            <el-row>
                <el-col :span="10">
                    <el-input placeholder="请输入查询内容" clearable v-model="queryInfo.queryString" class="input-with-select">
                        <el-button slot="append" icon="el-icon-search" @click="querySearch"></el-button>
                    </el-input>
                </el-col>
            </el-row>
            <el-table :data="tableList" stripe>
                <el-table-column prop="cause" label="维修原因"/>
                <el-table-column prop="address" label="维修地址"/>
                <el-table-column prop="author" label="申请人"/>
                <el-table-column prop="applicationTime" label="申请时间"/>
                <el-table-column label="处理">
                    <template slot-scope="scope">
                        {{scope.row.statusIf? "已处理" : "未处理"}}
                    </template>
                </el-table-column>
                <el-table-column label="处理结果">
                    <template slot-scope="scope">
                        {{ scope.row.statusIf? (scope.row.status? "已报修处理" : "不给予处理") : ""}}
                    </template>
                </el-table-column>
                <el-table-column fixed="right" label="操作" width="150">
                    <template slot-scope="scope">
                        <el-button type="primary" @click="update(scope.row)" size="small" icon="el-icon-edit" circle/>
                    </template>
                </el-table-column>
            </el-table>
            <el-pagination
                @size-change="handlePageSize"
                @current-change="handlePageCurrent"
                :current-page="queryInfo.pageCurrent"
                :page-sizes="[ 6, 10, 15, 20 ]"
                :page-size="queryInfo.pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="total"/>
        </el-card>

        <!-- 表单添加/修改 -->
        <el-dialog title="维修申请处理" :visible.sync="formOpen" width="80%" @close="dialogClose">
            <el-form :model="form" ref="form" :rules="rules">
                <el-form-item label="维修原因" prop="cause">
                    <el-input v-model="form.cause" :rows="3" type="textarea" :disabled="true"/>
                </el-form-item>
                <el-row>
                    <el-col :span="7">
                        <el-form-item label="维修地址" prop="address">
                            <el-input v-model="form.address" :disabled="true"/>
                        </el-form-item>
                    </el-col>
                    <el-col :span="7">
                        <el-form-item label="申请人" prop="author">
                            <el-input v-model="form.author" :disabled="true"/>
                        </el-form-item>
                    </el-col>
                    <el-col :span="7">
                        <el-form-item label="申请日期" prop="applicationTime">
                            <el-input v-model="form.applicationTime" :disabled="true"/>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="4">
                        <el-form-item label="处理">
                            <el-radio v-model="form.status" label="true">已报修处理</el-radio>
                            <el-radio v-model="form.status" label="false">不给予处理</el-radio>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-form-item label="处理反馈" prop="statusFeedback">
                    <el-input v-model="form.statusFeedback" :rows="2" type="textarea"/>
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
    name: "Upkeep",
    data() {
        return {
            // 分页查询数据
            queryInfo: {
                // 当前页码
                pageCurrent: 1,
                // 每页数量
                pageSize: 6,
            },
            // 表格数据
            tableList: [],
            // 总记录数
            total: 0,
            // 是否打开表单对话框
            formOpen: false,
            // 表单数据
            form: {},
            // 表单校验
            rules: {
                statusFeedback: [
                    { required: true, message: '请输入处理反馈', trigger: 'blur' },
                    { min: 1, max: 100, message: '昵称的长度为1~20之间', trigger: 'blur' }
                ],
            },
        }
    },

    //页面加载完执行方法
    mounted: function(){},

    //页面初始化
    created() {
        this.findPage();
    },

    methods: {

        /**
         * 处理维修申请
         */
        update(row) {
            this.form = row;
            this.formOpen = true;
        },

        /**
         * 分页查询
         */
        findPage() {
            this.$ajax.post("/upkeep/getPage", this.queryInfo)
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
            this.$ajax.post("/upkeep/querySearch", this.queryInfo).then((res) => {
                this.tableList=res.data.data.records;
                this.total=res.data.data.total;
            });
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
                console.log(this.form);
                this.$ajax.put("/upkeep/update", this.form).then((res) => {
                    this.$message.success(res.data.msg);
                    this.formOpen = false;
                    this.findPage();
                });

            });
        },


    }

};
</script>

<style scoped>
.box-card {
    margin-top: 20px;
    border-radius: 8px;
}
</style>