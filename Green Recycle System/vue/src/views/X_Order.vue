<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入兑换物品" suffix-icon="el-icon-search" v-model="item"></el-input>
      <el-input style="width: 200px" placeholder="请输入订单状态" suffix-icon="el-icon-message" class="ml-5" v-model="state"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button type="warning" @click="reset">重置</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleAdd">兑换公益商品 <i class="el-icon-circle-plus-outline"></i></el-button>
      <el-popconfirm
          class="ml-5"
          confirm-button-text='确定'
          cancel-button-text='我再想想'
          icon="el-icon-info"
          icon-color="red"
          title="您确定批量删除这些数据吗？"
          @confirm="delBatch"
      >
      </el-popconfirm>
    </div>

    <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'"  @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="ID" width="80" sortable></el-table-column>
      <el-table-column prop="user" label="用户" width="140"></el-table-column>
      <el-table-column prop="charityItem" label="订单物品" width="120"></el-table-column>
      <el-table-column prop="orderNum" label="订单数量"></el-table-column>
      <el-table-column prop="grade" label="公益积分"></el-table-column>
      <el-table-column prop="telephone" label="联系方式"></el-table-column>
      <el-table-column prop="address" label="订单地址"></el-table-column>
      <el-table-column prop="state" label="订单状态"></el-table-column>
      <el-table-column prop="createtime" label="创建时间"></el-table-column>
      <el-table-column label="操作"  width="200" align="center">
        <template slot-scope="scope">
          <el-popconfirm
              class="ml-5"
              confirm-button-text='确定'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="您确定要取消订单吗？"
              @confirm="del(scope.row.state,scope.row)"
          >
            <el-button type="danger" slot="reference">取消订单 <i class="el-icon-remove-outline"></i></el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[2, 5, 10, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

    <el-dialog title="订单信息" :visible.sync="dialogFormVisible" width="30%" >
      <el-form label-width="80px" size="small">
        <el-form-item label="订单物品">
          <el-select clearable v-model="form.charityItem" placeholder="请选择订单物品" style="width: 100%">
            <el-option v-for="item in types" :key="item.name" :label="item.name" :value="item.name"></el-option>
          </el-select>
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
import {serverIp} from "../../public/config";

export default {
  name: "R_Order",
  data() {
    return {
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      serverIp: serverIp,
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      state: "",
      form: {},
      dialogFormVisible: false,
      multipleSelection: [],
      charitys:[],
      users:[],
      types:[],
      item:"",
      itemGrade:0
    }
  },
  created() {
    this.load()
  },
  //请求分页查询数据
  methods: {
    load() {
      this.request.get("/order/pageplus2", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          username: this.user.username,
          state: this.state,
          item:this.item
        }
      }).then(res => {

        this.tableData = res.data.records
        this.total = res.data.total

      })
      //获取编辑单里面的选项
      this.request.get("/user/recycle").then(res => {
        this.charitys = res.data
      }),
          this.request.get("/charity-item").then(res => {
            this.types = res.data
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
    handleAdd() {
      this.dialogFormVisible = true
      this.form = {}
    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogFormVisible = true
    },
    del(state,row) {
      if(state=="等待发货")
      {
        this.form = JSON.parse(JSON.stringify(row))
        this.form.state="取消订单"
        this.request.post("/order/user2", this.form).then(res => {
          if (res.code === '200') {
            this.$message.success("保存成功")
            this.dialogFormVisible = false
            this.load()
          } else {
            this.$message.error("保存失败")
          }
        })
      }else {
        this.$alert('当前状态不可取消订单', '订单状态', {
          confirmButtonText: '确定',

        })
      }
    },
    handleSelectionChange(val) {
      console.log(val)
      this.multipleSelection = val
    },
    delBatch() {
      let ids = this.multipleSelection.map(v => v.id)  // [{}, {}, {}] => [1,2,3]
      this.request.post("/order/del/batch", ids).then(res => {
        if (res.code === '200') {
          this.$message.success("批量删除成功")
          this.load()
        } else {
          this.$message.error("批量删除失败")
        }
      })
    },
    reset() {
      this.item = ""
      this.state = ""
      this.load()
    },
    //获取点击的pagesize，然后更新pagesize
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

  }
}
</script>


<style>
.headerBg {
  background: #eee!important;
}
</style>
