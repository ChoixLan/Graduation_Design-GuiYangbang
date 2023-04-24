<template>
    <div>
        <el-card class="box-class">
            <div slot="header" class="clearfix">
                <span>快递代领管理</span>
            </div>
            <el-row>
                <el-col :span="10">
                    <el-input placeholder="请输入查询内容" clearable v-model="queryInfo.queryString" class="input-with-select">
                        <el-button slot="append" icon="el-icon-search" @click="querySearch"></el-button>
                    </el-input>
                </el-col>
            </el-row>
            <el-table :data="tableList" stripe>
                <el-table-column prop="authorA" label="发布人"/>
                <el-table-column prop="addressQ" label="取件地址"/>
                <el-table-column prop="addressS" label="送件地址"/>
                <el-table-column prop="money" label="悬赏金额"/>
                <el-table-column label="悬赏取消">
                    <template slot-scope="scope">
                        {{scope.row.statusAx? "发布人已取消" : "未取消"}}
                    </template>
                </el-table-column>
                <el-table-column label="悬赏完成">
                    <template slot-scope="scope">
                        {{ scope.row.statusAx? "发布人已取消" : (scope.row.statusA? "悬赏完成" : "悬赏未完成") }}
                    </template>
                </el-table-column>
                <el-table-column label="悬赏接取">
                    <template slot-scope="scope">
                        {{ scope.row.statusAx? "发布人已取消" :  (scope.row.statusB? "悬赏已接取" : "悬赏未接取") }}
                    </template>
                </el-table-column>
                <el-table-column prop="authorB" label="接取人"/>
                <el-table-column label="悬赏冻结">
                    <template slot-scope="scope">
                        {{ scope.row.statusC? "已冻结" : "正常" }}
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
        <el-dialog title="悬赏管理" :visible.sync="formOpen" width="80%" @close="dialogClose">
            <el-form :model="form" ref="form" :rules="rules">
                <el-row>
                    <el-col :span="4">
                        <el-form-item label="发布人:" prop="authorA">
                            <el-input v-model="form.authorA" :disabled="true"/>
                        </el-form-item>
                    </el-col>
                    <el-col :span="4">
                        <el-form-item label="取件地址:" prop="addressQ">
                            <el-input v-model="form.addressQ" :disabled="true"/>
                        </el-form-item>
                    </el-col>
                    <el-col :span="4">
                        <el-form-item label="送件地址:" prop="addressS">
                            <el-input v-model="form.addressS" :disabled="true"/>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="4">
                        <el-form-item label="悬赏金额:" prop="money">
                            <el-input v-model="form.money" :disabled="true"/>
                        </el-form-item>
                    </el-col>
                    <el-col :span="7">
                        <el-form-item label="接取人:" prop="authorB">
                            <el-input v-model="form.authorB" :disabled="true"/>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="4">
                        <el-form-item label="冻结处理:">
                            <el-radio v-model="form.statusC" label="true">冻结</el-radio>
                            <el-radio v-model="form.statusC" label="false">解除冻结</el-radio>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-form-item label="处理反馈:" prop="statusFeedback">
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
    name: "uToU",
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
        };
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
            this.$ajax.post("/uToU/getPage", this.queryInfo)
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
            this.$ajax.post("/uToU/querySearch", this.queryInfo).then((res) => {
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
                this.$ajax.put("/uToU/update", this.form).then((res) => {
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
.box-class {
    margin-top: 20px;
    border-radius: 8px;
}

</style>