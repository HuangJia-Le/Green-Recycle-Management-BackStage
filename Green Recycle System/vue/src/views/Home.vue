<template>
  <div style="color: #666;font-size: 14px;">
    <div style="padding-bottom: 20px;  font-size: 20px">
      <b>欢迎你来到绿色慈善回收系统！{{ user.nickname }}   有事请联系管理员邮箱：1722589735@qq.com</b>
    </div>
    <div class="block">
      <el-carousel height="500px">
        <el-carousel-item v-for="item in imgList" :key="item.id">
          <img :src="item.idView" class="image">
        </el-carousel-item>
      </el-carousel>
    </div>
    <div style="height: 1px; background: #ddd; margin: 10px 0"/>
    <el-row :gutter="30">
      <el-col :span="12">
        <div style="padding: 5px 0; font-size: 20px">捐赠物品对应获得的公益积分</div>
        <el-table :data="tableData" style="width: 62%">
          <el-table-column prop="item" label="物品种类" width="180"> </el-table-column>
          <el-table-column prop="grade" label="公益积分/斤" width="180"> </el-table-column>
        </el-table>
      </el-col>
      <el-col :span="12">
        <div style="padding: 5px 0; font-size: 20px">回收物品对应获得的公益积分</div>
        <el-table :data="tableData1" style="width: 62%">
          <el-table-column prop="item" label="物品种类" width="180"> </el-table-column>
          <el-table-column prop="grade" label="公益积分/斤" width="180"> </el-table-column>
        </el-table>
      </el-col>
    </el-row>


  </div>
</template>

<script>
export default {
  name: "Home",
  data() {
    return {
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      imgList:[
        {id:0,idView:require('D:\\CodeProject\\毕设后台\\pure-design-master\\files\\公益背景.jpg')},
        {id:1,idView:require('D:\\CodeProject\\毕设后台\\pure-design-master\\files\\绿色.jpg')},
        {id:2,idView:require('D:\\CodeProject\\毕设后台\\pure-design-master\\files\\绿色1.jpg')},
        {id:3,idView:require('D:\\CodeProject\\毕设后台\\pure-design-master\\files\\爱心文摘.png')}
      ],
      tableData:[],
      tableData1:[],
      pageNum:1,
      pageSize:10,
    }
  },
  created() {
    this.load()
    this.load1()
  },
  methods: {
    load() {
      this.request.get("/item-type/page1", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
        }
      }).then(res => {

        this.tableData = res.data.records
        this.total = res.data.total

      })
    },
    load1() {
      this.request.get("/item-type/page2", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
        }
      }).then(res => {

        this.tableData1 = res.data.records
        this.total = res.data.total

      })
    }
  }
}
</script>

<style>
.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 150px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}
</style>
