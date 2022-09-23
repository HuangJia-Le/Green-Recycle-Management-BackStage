<template>
  <div>
    <div style="margin: 5px 0">
      <el-input style="width: 200px" placeholder="请输入商品名称" suffix-icon="el-icon-search" v-model="item"></el-input>
      <el-input style="width: 200px" placeholder="请输入积分最小值" suffix-icon="el-icon-sort-down" class="ml-5" v-model="mingrade"></el-input>
      <el-input style="width: 200px" placeholder="请输入积分最大值" suffix-icon="el-icon-sort-up" class="ml-5" v-model="maxgrade"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button type="warning" @click="set">重置</el-button>
    </div>
  <div style="margin-left:1%;margin-right:1%">
    <el-row :gutter="20">
      <el-col :span="6" v-for="(item) in tableData" :key="item.id" :offset="1" >
        <div style="margin-top:15px">
          <el-card :body-style="{ padding: '5px'}" shadow="hover">
            <img :src="item.cover" class="image">
            <div>
              <span style="font-weight:bold; font-size: 20px;">{{item.name}}</span><br>
              <span>所需的公益积分:{{item.grade}}</span><br>
              <span>概述:{{item.overview}}</span><br>
              <div class="bottom clearfix" style="font-size: 15px;text-align: right">
                <el-button type="text" class="button" @click="handleAdd">立即下单</el-button>
              </div>
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
          :page-sizes="[9, 15, 21, 27]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

  </div>
    <el-dialog title="订单信息" :visible.sync="dialogFormVisible" width="30%" >
      <el-form label-width="80px" size="small">
        <el-form-item label="订单物品">
          <el-input v-model="form.charityItem" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="订单数量">
          <el-input v-model="form.orderNum" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="联系方式">
          <el-input v-model="form.telephone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="订单地址">
          <el-input v-model="form.address" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import * as echarts from 'echarts'

export default {
  name: "C_Market",
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
      item:"",
      mingrade:0,
      maxgrade:0,
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
      this.request.get("/charity-item/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          item:this.item,
          mingrade: this.mingrade,
          maxgrade: this.maxgrade
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    save() {
      this.form.type="公益商城订单"
      this.form.state="等待发货"
      this.form.user=this.user.username
      this.request.post("/order/user1", this.form).then(res => {
        if (res.code === '200') {
          this.$message.success("兑换成功")
          this.dialogFormVisible = false
          this.load()
        } else {
          this.$message.error("对不起，您的公益积分不足以兑换该商品或者该物品数量不足")
        }
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
      this.item = ""
      this.mingrade = 0
      this.maxgrade = 0
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
