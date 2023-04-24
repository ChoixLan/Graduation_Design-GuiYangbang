<template>
    <div>
      <el-card class="box-class">
        <div slot="header" class="clearfix">
          <span>失物招领管理</span>
        </div>
        <el-row>
          <el-col :span="10">
            <el-input placeholder="请输入查询内容" clearable v-model="queryInfo.queryString" class="input-with-select">
              <el-button slot="append" icon="el-icon-search" @click="querySearch"></el-button>
            </el-input>
          </el-col>
        </el-row>
        <el-table :data="tableList" stripe>
          <el-table-column label="类型">
            <template slot-scope="scope">
              {{ !scope.row.status? "失物" : "招领" }}
            </template>
          </el-table-column>
          <el-table-column prop="author" label="发布人"/>
          <el-table-column prop="content" label="内容"/>
          <el-table-column prop="image" label="图片">
            <template slot-scope="scope">
              <el-image style="width: 100px; height: 100px" :src="scope.row.image" :preview-src-list="srcList" @click="tapimage(scope.row.image)"></el-image>
            </template>
          </el-table-column>
          <el-table-column prop="tel" label="联系方式"/>
          <el-table-column label="是否启用">
            <template slot-scope="scope">
              <el-switch
                v-model="scope.row.statusx"
                @change="updateStatus(scope.row)"/>
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
    </div>
</template>

<script>
export default {
    name: "LostArticle",
    data() {
        return {
            // 图片预览数组
            srcList:[],
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
         * 分页查询
         */
        findPage() {
            this.$ajax.post("/lostarticle/getPage", this.queryInfo)
                .then((res) => {
                    this.tableList = res.data.data.records;
                    this.total = res.data.data.total;
                }).catch((res) => {

            })
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
         * 模糊查询
         */
        querySearch() {
            this.$ajax.post("/lostarticle/querySearch", this.queryInfo).then((res) => {
                this.tableList=res.data.data.records;
                this.total=res.data.data.total;
            });
        },

        /**
         * 图片点击
         */
        tapimage:function (param1) {
            this.srcList = [param1]
        },

        /** 改变失物招领启用的状态 */
        updateStatus(row) {
            console.log(row);
            this.$ajax.put("/lostarticle/update", row).then((res) => {
                this.$message.success('状态更新成功！');
            });
        },
    }
};
</script>

<style scoped>

</style>