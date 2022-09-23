<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入作者" suffix-icon="el-icon-search" v-model="author"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button type="warning" @click="reset">重置</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-button type="primary" @click="add">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
      <el-popconfirm
          class="ml-5"
          confirm-button-text='确定'
          cancel-button-text='我再想想'
          icon="el-icon-info"
          icon-color="red"
          title="您确定批量删除这些数据吗？"
          @confirm="delBatch"
      >
        <el-button type="danger" slot="reference">批量删除 <i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>
      <el-upload :action="'http://' + serverIp + ':9090/paper/import3'" :show-file-list="false" accept="xlsx" :on-success="handleExcelImportSuccess" style="display: inline-block">
        <el-button type="primary" class="ml-5">导入 <i class="el-icon-bottom"></i></el-button>
      </el-upload>
      <el-button type="primary" @click="exp" class="ml-5">导出 <i class="el-icon-top"></i></el-button>
    </div>

    <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'"  @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="pid" label="PID" width="80" sortable></el-table-column>
      <el-table-column prop="title" label="标题" ></el-table-column>
      <el-table-column prop="author" label="作者"></el-table-column>
      <el-table-column prop="createtime" label="创建时间" ></el-table-column>
      <el-table-column label="操作"  width="300" align="center">
        <template slot-scope="scope">
          <el-button type="success" @click="details(scope.row)">详情 <i class="el-icon-edit"></i></el-button>
          <el-button type="success" @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
          <el-popconfirm
              class="ml-5"
              confirm-button-text='确定'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="您确定删除吗？"
              @confirm="del(scope.row.pid)"
          >
            <el-button type="danger" slot="reference">删除 <i class="el-icon-remove-outline"></i></el-button>
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

    <el-dialog title="文章信息" :visible.sync="dialogFormVisible" width="30%" >
      <el-form label-width="80px" size="small">
        <el-form-item label="标题">
          <el-input v-model="form.title" autocomplete="off" style="width: 60%"></el-input>
        </el-form-item>
        <el-form-item label="作者">
          <el-input v-model="form.author" autocomplete="off" style="width: 60%"></el-input>
        </el-form-item>

        <div id="div1"></div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="文章详情" :visible.sync="vis" width="50%" >
      <el-card>
        <div v-html="detail.content"></div>
      </el-card>

    </el-dialog>
  </div>
</template>

<script>
import {serverIp} from "../../public/config";
import E from 'wangeditor'
let editor
export default {
  name: "Recycle_News",
  data() {
    return {
      serverIp: serverIp,
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      author: "",
      form: {},
      dialogFormVisible: false,
      multipleSelection: [],
      detail:{},
      vis:false
    }
  },
  created() {
    this.load()
  },
  //请求分页查询数据
  methods: {
    details(row){
      this.detail=row
      this.vis=true
    },
    load() {
      this.request.get("/paper/page3", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          author: this.author,
        }
      }).then(res => {

        this.tableData = res.data.records
        this.total = res.data.total

      })
    },
    add(){
      this.dialogFormVisible=true
      this.form={}
      this.$nextTick(()=>{
        editor = new E('#div1')//加载富文本框
        editor.create()
      })
    },
    save() {
      this.form.type=3
      this.form.content=editor.txt.html() //获取编辑器里面的值
      this.request.post("/paper", this.form).then(res => {
        if (res.code === '200') {
          this.$message.success("保存成功")
          this.dialogFormVisible = false
          this.load()
        } else {
          this.$message.error("保存失败")
        }
      })
    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogFormVisible = true
    },
    del(pid) {
      this.request.delete("/paper/" + pid).then(res => {
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
      let ids = this.multipleSelection.map(v => v.pid)  // [{}, {}, {}] => [1,2,3]
      this.request.post("/paper/del/batch", ids).then(res => {
        if (res.code === '200') {
          this.$message.success("批量删除成功")
          this.load()
        } else {
          this.$message.error("批量删除失败")
        }
      })
    },
    reset() {
      this.author = ""
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
      window.open(`http://${serverIp}:9090/paper/export3`)
    },
    handleExcelImportSuccess() {
      this.$message.success("导入成功")
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
