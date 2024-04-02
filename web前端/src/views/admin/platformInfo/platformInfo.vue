<template>
    <div>
        <el-card class="box-card" style="float:left;margin-left:50px">

            <div class="icon">
                    <i class="el-icon-s-custom" style="color:rgb(64,201,198);font-size:100px"></i>
            </div>
              <div class="icon">
                    <el-row>
                      <el-col  style="margin-left:60px">用户数量</el-col>
                    </el-row>
                     <el-row>
                      <el-col  style="margin-left:75px;margin-top: 30px">{{platformInfo.userNumber}}</el-col>
                    </el-row>
            </div>

        </el-card>

        <el-card class="box-card" style="float:left;margin-left:50px">
            <div class="icon">
                    <i class="el-icon-coordinate" style="color:rgb(54,163,247);font-size:100px"></i>
            </div>
              <div class="icon">
                    <el-row>
                      <el-col  style="margin-left:60px">入驻商家</el-col>
                    </el-row>
                     <el-row>
                      <el-col  style="margin-left:75px;margin-top: 30px">{{platformInfo.manuNumber}}</el-col>
                    </el-row>
            </div>
        </el-card>

        
        <el-card class="box-card" style="float:left;margin-left:50px">
            <div class="icon">
                    <i class="el-icon-s-goods" style="color:rgb(52,191,163);font-size:100px"></i>
            </div>
              <div class="icon">

                    <el-row>
                      <el-col style="margin-left:60px">商品种类</el-col>
                    </el-row>

                    <el-row>
                      <el-col  style="margin-left:75px;margin-top: 30px">{{platformInfo.typeNumber}}</el-col>
                    </el-row>
                   
            </div>
        </el-card>

        
        <el-card class="box-card" style="float:left;margin-left:50px">
            <div class="icon">
                    <i class="el-icon-tickets" style="color:rgb(244,81,108);font-size:100px"></i>
            </div>
              <div class="icon">
                    <el-row>
                      <el-col style="margin-left:60px">总收益</el-col>
                    </el-row>
                     <el-row>
                      <el-col style="margin-left:75px;margin-top: 30px">￥{{platformInfo.profitNumber}}</el-col>
                    </el-row>
            </div>
        </el-card>
        <!-- echarts图表 -->
        <div style="clear:both">
            <div id="chartOne"></div>
            <div id="chartTwo"></div>
            <div id="chartThree"></div>
            <div id="chartFour"></div>
        </div>
    </div>

     
</template>
<style scoped>
#chartFour {
    height: 400px;
    top: 100px;
    float: left;
    width: 50%;
  }

 #chartThree {

    height: 400px;
    top: 100px;
    float: left;
  }
 #chartOne {

    height: 400px;
    top: 100px;
    float: left;
  
  }
  #chartTwo{
    height: 400px;
    top: 100px;
    float: left;
    width: 50%;
    
  }
.icon{
    float: left;
    height: 100px;
    width: 80px;
}
.box-card{
    width:20%;
    height:150px
}
  .text {
    font-size: 14px;
}
  .item {
    margin-bottom: 18px;
}
  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
}
  .clearfix:after {
    clear: both
}
</style>
<script>
import {queryPlatInfo} from '../../../api/platform'
export default {

    name: 'platformInfo' ,
    mounted() {
        queryPlatInfo().then(rs=>{
          this.platformInfo = rs;
          setTimeout(()=>{
             this.refresh();
          },100)  
        });          
    },
  
    methods:{
       refresh(){
         //收益表
           let echarts = require('echarts');
           let myChart1 = echarts.init(document.getElementById('chartTwo'));
           myChart1.resize({
            width: 800,
            height: 400
            });
           var option = {
                title: {
                    x: 'center',
                    text: '收益变化图',
                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                        type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                xAxis: {
                  data: ['1', '2', '3', '4', '5','6','7']
                },
                yAxis: {},
                series: [
                  {
                    data: [10, 22, 28, 23, 19,18,16],
                    type: 'line',
                    smooth: true
                  }
                ]
};
        //设置
        myChart1.setOption(option);
        var _this = this;
        //订单分类表
         let myChart3 = echarts.init(document.getElementById('chartFour'));
           myChart3.resize({
            width: 600,
            height: 400
            });
        var option = {
             title: {
                x: 'center',
                text: '租赁订单分类',            
              },   
             tooltip: {
                trigger: 'axis',
                axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
             },
            series: [
              {
                type: 'pie',
                data: [                                                                                  
                  {
                    value: _this.platformInfo.orderType[2],
                    name: '先租后买'
                  },
                  {
                    value: _this.platformInfo.orderType[0],
                    name: '共享租赁'
                  },
                  {
                    value: _this.platformInfo.orderType[1],
                    name: '以租代售'
                  },
              
                ],
                roseType: 'area'
              }
            ]
  };
    //设置
    myChart3.setOption(option);

       }
},
    data() {
        return {
          platformInfo:null
        }
    }
}
</script>
