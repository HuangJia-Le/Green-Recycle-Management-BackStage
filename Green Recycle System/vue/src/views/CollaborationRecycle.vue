<template>
  <div>
    <div style="margin: 5px 0">
      <el-input style="width: 200px" placeholder="请输入回收公司名称" suffix-icon="el-icon-search" v-model="username"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button type="warning" @click="set">重置</el-button>
    </div>
    <div style="margin-left:1%;margin-right:1%">
      <el-row :gutter="20">
        <el-col :span="5" v-for="(item) in tableData" :key="item.id" :offset="1" >
          <div style="margin-top:15px">
            <el-card :body-style="{ padding: '5px'}" shadow="hover">
              <img :src="item.avatarUrl" class="image">
              <div>
                <span style="font-weight:bold; font-size: 20px;">{{item.nickname}}</span><br>
                <span>联系方式1:{{item.email}}</span><br>
                <span>联系方式2:{{item.phone}}</span><br>
                <span>地址:{{item.address}}</span><br>
              </div>
            </el-card>
          </div>
        </el-col>
      </el-row>
      <div style="padding: 10px 0">
        <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="pageNum"
            :page-sizes="[5, 10, 15, 20]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total">
        </el-pagination>
      </div>

    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts'

export default {
  name: "CollaborationRecycle",
  data() {
    return {
//循环卡片列表的数据源
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      tableData: [],
      current_page: 1,
      total: 0,
      pageNum: 1,
      dialogFormVisible: false,
      form: {},
      username:"",
      pageSize: 10,
    }
  },
  created() {
    this.load()
  },
  watch:{
    tableData:function(){
      this.$nextTick(function(){
        /*现在数据已经渲染完毕*/
        //console.log(this.tabledata);
        this.reset=true;
      })
    }
  },
  methods: {
    load() {
      this.request.get("/recycle/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          username:this.username,
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    handleSizeChange(pageSize) {
      console.log(pageSize)
      this.pageSize = pageSize
      this.load()
    },
    //获取点击的pagenum，然后更新pagenum
    handleCurrentChange(pageNum) {
      console.log(pageNum)
      this.pageNum = pageNum
      this.load()
    },
    handleAdd() {
      this.dialogFormVisible = true
      this.form = {}
    },
    set() {
      this.username = ""
      this.load()
    },
  }
}
</script>

<style>

.bottom {
  margin-top: 13px;
  line-height: 12px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.button {
  font-size: 15px;
  padding: 10px;
  min-height: auto;
  margin: auto;
}

.image {
  width: 100%;
  display: block;
}
</style>
