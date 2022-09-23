<template>
  <div class="homepage-hero-module">
    <div class="video-container">
      <div  class="filter">
        <div style="width: 400px; margin: 100px auto">
          <div style="margin: 20px 0; text-align: center; font-size: 24px"><b>设置新密码</b></div>
          <el-form :model="form" :rules="rules" ref="userForm">
            <el-form-item prop="username">
              <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="form.username" placeholder="请输入用户名"></el-input>
            </el-form-item>
            <el-form-item prop="newPassword">
              <el-input placeholder="请设置新密码" size="medium" style="margin: 5px 0" prefix-icon="el-icon-lock" show-password v-model="form.newPassword" ></el-input>
            </el-form-item>
            <el-form-item prop="confirmPassword">
              <el-input placeholder="请确认新密码" size="medium" style="margin: 5px 0" prefix-icon="el-icon-lock" show-password v-model="form.confirmPassword" ></el-input>
            </el-form-item>
            <el-form-item style="margin: 30px 0; text-align: right">
              <el-button type="warning" size="small"  autocomplete="off" @click="$router.push('/login')">返回登录</el-button>
              <el-button type="primary" size="small"  autocomplete="off" @click="selectpassword">确定</el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>
      <video autoplay loop muted class="fillWidth" v-on:canplay="canplay">
        <source src="../assets/preview.mp4" type="video/mp4"/>
        浏览器不支持 video 标签，建议升级浏览器。
      </video>
    </div>
  </div>
</template>

<script>
import {setRoutes} from "@/router";
import ValidCode from "@/components/ValidCode";
export default {
  name: "SelectPassword",
  components: {
    ValidCode
  },
  data() {
    return {
      form:{},
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 5 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
        ],
      },
      validCode: ''
    }
  },

  methods: {
    // 接收验证码组件提交的 4位验证码\
    canplay() {
      this.vedioCanPlay = true
    },
    createValidCode(data) {
      this.validCode = data
    },
    selectpassword() {
      this.$refs['userForm'].validate((valid) => {
        if (valid) {  // 表单校验合法
          if (this.form.newPassword !== this.form.confirmPassword) {
            this.$message.error("2次输入的新密码不相同")
            return false
          }
          this.request.post("/user/password1", this.form).then(res => {
            if(res.code === '200') {
              this.$message.success("更改密码成功")
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      });
    }
  }
}
</script>

<style scoped>
.homepage-hero-module,
.video-container {
  position: relative;
  height: 100vh;
  overflow: hidden;
}

.video-container .poster img{
  z-index: 0;
  position: absolute;
}

.video-container .filter {
  z-index: 1;
  position: absolute;
  /*background: rgba(0, 0, 0, 0.4);*/
  width: 100%;
}

.fillWidth {
  width: 100%;
}
</style>
