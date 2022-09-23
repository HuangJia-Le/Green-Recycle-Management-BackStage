<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入用户" suffix-icon="el-icon-search" v-model="user"></el-input>
      <el-input style="width: 200px" placeholder="请输入订单状态" suffix-icon="el-icon-message" class="ml-5" v-model="company"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button type="warning" @click="reset">重置</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
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
      <el-upload :action="'http://' + serverIp + ':9090/order/import1'" :show-file-list="false" accept="xlsx" :on-success="handleExcelImportSuccess" style="display: inline-block">
        <el-button type="primary" class="ml-5">导入 <i class="el-icon-bottom"></i></el-button>
      </el-upload>
      <el-button type="primary" @click="exp" class="ml-5">导出 <i class="el-icon-top"></i></el-button>
    </div>

    <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'"  @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="ID" width="80" sortable></el-table-column>
      <el-table-column prop="user" label="用户" width="140"></el-table-column>
      <el-table-column prop="company" label="公益团体"></el-table-column>
      <el-table-column prop="orderItem" label="订单类型" width="120"></el-table-column>
      <el-table-column prop="description" label="订单物品" width="120"></el-table-column>
      <el-table-column prop="orderNum" label="订单数量"></el-table-column>
      <el-table-column prop="grade" label="公益积分"></el-table-column>
      <el-table-column prop="telephone" label="联系方式"></el-table-column>
      <el-table-column prop="address" label="订单地址"></el-table-column>
      <el-table-column prop="state" label="订单状态"></el-table-column>
      <el-table-column prop="createtime" label="创建时间" sortable></el-table-column>
      <el-table-column label="操作"  width="200" align="center">
        <template slot-scope="scope">
          <el-button type="success" @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>

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
        <el-form-item label="用户">
          <el-select  clearable v-model="form.user" placeholder="请选择用户" style="width: 100%">
            <el-option v-for="item in users" :key="item.name" :label="item.name" :value="item.username"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="公益团体">
          <el-select clearable v-model="form.company" placeholder="请选择公益团体" style="width: 100%">
            <el-option v-for="item in charitys" :key="item.name" :label="item.name" :value="item.nickname"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="订单类型">
          <el-select clearable v-model="form.orderItem" placeholder="请选择物品种类" style="width: 100%">
            <el-option v-for="item in types" :key="item.name" :label="item.name" :value="item.item"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="订单物品">
          <el-input v-model="form.description" autocomplete="off"></el-input>
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
        <el-form-item label="订单状态">
          <el-select clearable v-model="form.state" placeholder="请选择订单状态" style="width: 100%">
            <el-option v-for="item in states" :key="item.name" :label="item.name" :value="item.state"></el-option>
          </el-select>
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
  name: "CC_Order",
  data() {
    return {
      charity: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      serverIp: serverIp,
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      user: "",
      company: "",
      form: {},
      dialogFormVisible: false,
      multipleSelection: [],
      states:[],
      charitys:[],
      users:[],
      types:[]
    }
  },
  created() {
    this.load()
  },
  //请求分页查询数据
  methods: {
    load() {
      this.request.get("/order/page6", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          user: this.user,
          company: this.company,
          charity:this.charity.nickname
        }
      }).then(res => {

        this.tableData = res.data.records
        this.total = res.data.total

      })
      //获取编辑单里面的选项
      this.request.get("/order-state").then(res => {
        this.states = res.data
      }),
          this.request.get("/user/charity").then(res => {
            this.charitys = res.data
          }),
          this.request.get("/user/user").then(res => {
            this.users = res.data
          }),
          this.request.get("/item-type").then(res => {
            this.types = res.data
          })
    },
    save() {
      this.form.type="慈善订单"
      this.request.post("/order/user3", this.form).then(res => {
        if (res.code === '200') {
          this.$message.success("保存成功")
          this.dialogFormVisible = false
          this.load()
        } else {
          this.$message.error("保存失败")
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
    del(id) {
      this.request.delete("/order/" + id).then(res => {
        if (res.code === '200') {
          this.$message.success("删除成功")
          this.load()
        } else {
          this.$message.error("删除失败")
        }
      })
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
      this.user = ""
      this.company = ""
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
    exp() {
      window.open(`http://${serverIp}:9090/order/export1`)
    },
    handleExcelImportSuccess() {
      this.$message.success("导入成功")
      this.load()
    },
    filesUploadSuccess(res){    //上传图片
      console(res)
      this.form.avatarUrl = res.data
    }
  }
}
</script>


<style>
.headerBg {
  background: #eee!important;
}
</style>
