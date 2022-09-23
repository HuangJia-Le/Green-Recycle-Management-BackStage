<template>
  <div>
    <el-row :gutter="10" style="margin-bottom: 60px">
      <el-col :span="6">
        <el-card style="color: #409EFF">
          <div><i class="el-icon-user-solid" /> 用户总数</div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold">
            <span>{{TotalUser}}</span>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #F56C6C">
          <div><i class="el-icon-money" /> 普通用户</div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold">
            <span>{{user}}</span>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #67C23A">
          <div><i class="el-icon-bank-card" /> 慈善团体</div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold">
            <span>{{charity}}</span>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #E6A23C">
          <div><i class="el-icon-s-shop" /> 回收公司</div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold">
            <span>{{recycle}}</span>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="10" style="margin-bottom: 60px">
      <el-col :span="6">
        <el-card style="color: #409EFF">
          <div><i class="el-icon-user-solid" /> 订单总数</div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold">
            <span>{{TotalOrder}}</span>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #F56C6C">
          <div><i class="el-icon-money" /> 慈善订单</div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold">
            <span>{{Charity}}</span>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #67C23A">
          <div><i class="el-icon-bank-card" /> 回收订单</div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold">
            <span>{{Recycle}}</span>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #E6A23C">
          <div><i class="el-icon-s-shop" /> 慈善商城订单</div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold">
            <span>{{Other}}</span>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <div id="main" style="width: 500px; height: 450px"></div>
      </el-col>
      <el-col :span="12">
        <div id="pie" style="width: 500px; height: 400px"></div>
      </el-col>
    </el-row>

  </div>
</template>

<script>
import * as echarts from 'echarts'

export default {
  name: "Home",
  data() {
    return {
      TotalUser:0,
      user:0,
      charity:0,
      recycle:0,
      TotalOrder:0,
      Charity:0,
      Recycle:0,
      Other:0
    }
  },
  mounted() {  // 页面元素渲染之后再触发
    var option = {
      title: {
        text: '各季度数据统计',
        subtext: '趋势图',
        left: 'center'
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      xAxis: {
        type: 'category',
        data: ["第一季度", "第二季度", "第三季度", "第四季度"]
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          name: "订单",
          data: [],
          type: 'bar'
        },
        {
          name: "订单",
          data: [],
          type: 'line'
        },
        {
          name: "用户数量",
          data: [],
          type: 'bar'
        },
        {
          name: "用户数量",
          data: [],
          type: 'line'
        }
      ]
    };
    // 饼图
    var pieOption = {
      title: {
        text: '各季度订单量统计',
        subtext: '比例图',
        left: 'center'
      },
      tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b} : {c} ({d}%)'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      series: [
        {
          name: "慈善订单",
          type: 'pie',
          radius: '55%',
          center: ['25%', '70%'],
          label:{            //饼图图形上的文本标签
            normal:{
              show:true,
              position:'inner', //标签的位置
              textStyle : {
                fontWeight : 300 ,
                fontSize : 14,    //文字的字体大小
                color: "#fff"
              },
              formatter:'{c}({d}%)'
            }
          },
          data: [],  // 填空
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        },
        {
          name: "回收订单",
          type: 'pie',
          radius: '55%',
          center: ['75%', '50%'],
          label:{            //饼图图形上的文本标签
            normal:{
              show:true,
              position:'inner', //标签的位置
              textStyle : {
                fontWeight : 300 ,
                fontSize : 14,    //文字的字体大小
                color: "#fff"
              },
              formatter:'{d}%'
            }
          },
          data: [],  // 填空
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        },
      ],

    };
    var chartDom = document.getElementById('main');
    var myChart = echarts.init(chartDom);
    var pieDom = document.getElementById('pie');
    var pieChart = echarts.init(pieDom);
// 填充柱状图的数据
    this.request.get("/echarts/members").then(res => {
      // 填空
      // option.xAxis.data = res.data.x
      option.series[0].data = res.data
      option.series[1].data = res.data

      // 数据准备完毕之后再set
      myChart.setOption(option);


      pieOption.series[0].data = [
        {name: "第一季度", value: res.data[0]},
        {name: "第二季度", value: res.data[1]},
        {name: "第三季度", value: res.data[2]},
        {name: "第四季度", value: res.data[3]},
      ]
      pieChart.setOption(pieOption)
    })
    this.request.get("/echarts/members1").then(res => {
      // 填空
      // option.xAxis.data = res.data.x

      option.series[2].data = res.data
      option.series[3].data = res.data
      // 数据准备完毕之后再set
      myChart.setOption(option);

    })
// 填充饼图的数据
    this.request.get("/echarts/members2").then(res => {
      pieOption.series[0].data = [
        {name: "第一季度", value: res.data[0]},
        {name: "第二季度", value: res.data[1]},
        {name: "第三季度", value: res.data[2]},
        {name: "第四季度", value: res.data[3]},
      ]
      pieChart.setOption(pieOption)
    })
    this.request.get("/echarts/members3").then(res => {

      pieOption.series[1].data = [
        {name: "第一季度", value: res.data[0]},
        {name: "第二季度", value: res.data[1]},
        {name: "第三季度", value: res.data[2]},
        {name: "第四季度", value: res.data[3]},
      ]
      pieChart.setOption(pieOption)
    })
    //获取总订单数量
    this.request.get("/echarts/members4").then(res => {
      this.TotalOrder=res.data
    })
    this.request.get("/echarts/members5").then(res => {
      this.Charity=res.data
    })
    this.request.get("/echarts/members6").then(res => {
      this.Recycle=res.data
    })
    this.request.get("/echarts/members7").then(res => {
      this.Other=res.data
    })
    this.request.get("/echarts/members8").then(res => {
      this.TotalUser=res.data
    })
    this.request.get("/echarts/members9").then(res => {
      this.user=res.data
    })
    this.request.get("/echarts/members10").then(res => {
      this.charity=res.data
    })
    this.request.get("/echarts/members11").then(res => {
      this.recycle=res.data
    })
  }
}
</script>

<style scoped>

</style>
