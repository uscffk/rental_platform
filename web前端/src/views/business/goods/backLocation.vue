<template>
    <div class="heightAll widthAll"> <!--这样可以设置多个class-->
        
    <el-table
      border=""
      :data="slice"
      :header-cell-style="headStyle"  :cell-style="rowStyle"
      :row-class-name="tableRowClassName"
      style="width: 100%">
      <el-table-column
        prop="id"
        label="编号"
        >
      </el-table-column>

      <el-table-column
        prop="commodityId"
        label="商品编号"
        >
      </el-table-column>

      <el-table-column
        prop="longitude"
        label="经度">
      </el-table-column>

       <el-table-column
        prop="latitude"
        label="纬度"
        sortable>
      </el-table-column>

      <el-table-column
        prop="detailLoc"
        label="详细位置"
        sortable>
      </el-table-column>

      <el-table-column label="操作">
            <template slot-scope="scope">
                <el-button @click="location(scope.row)" size="mini" type="primary">定位</el-button>
                <el-button @click="deleteLocation(scope.row)" size="mini" type="primary">删除</el-button>
            </template>
      </el-table-column>

    </el-table>

         <!--分页组件-->
        <el-pagination :current-page="pageInfo.pageNo"
                       :page-size="pageInfo.pageSize"
                       :total="pageInfo.total"
                       @current-change="change"
                       background
                       class="my-changePage"
                       layout="prev, pager, next">
        </el-pagination>


        
        <el-dialog
            title="租赁站点分布"
            :visible.sync="dialogVisible"
            width="70%"
            > 
        <!-- 百度地图 -->
        <baidu-map class="map" :center="center" :zoom="zoom" @ready="handler" :scroll-wheel-zoom="true">   
                 <bm-point-collection :points="points" shape="BMAP_POINT_SHAPE_STAR" color="red" size="BMAP_POINT_SIZE_BIG" @click="clickHandler"></bm-point-collection>   
                 <bm-city-list anchor="BMAP_ANCHOR_TOP_LEFT" :offset="{width:10,height:10}"></bm-city-list>
                 <bm-geolocation anchor="BMAP_ANCHOR_BOTTOM_RIGHT" :showAddressBar="true" :autoLocation="true"></bm-geolocation>
                 <bm-map-type :map-types="['BMAP_NORMAL_MAP', 'BMAP_HYBRID_MAP']" anchor="BMAP_ANCHOR_TOP_RIGHT"></bm-map-type>
                 <bm-local-search :keyword="keyword" @searchcomplete="search" :auto-viewport="true"></bm-local-search>
                 <bm-navigation anchor="BMAP_ANCHOR_BOTTOM_LEFT"></bm-navigation>
                 <!-- <bm-marker :position="center" :dragging="true" animation="BMAP_ANIMATION_BOUNCE"></bm-marker> -->
                 <bm-local-search :keyword="keyword" @searchcomplete="search" :auto-viewport="true"></bm-local-search>
        </baidu-map>

        </el-dialog>
    </div>

</template>
<script>
import Vue from 'vue'
import BaiduMap from 'vue-baidu-map';
import {queryAllBackLocation} from '../../../api/good';
Vue.use(BaiduMap, {
          /* Visit http://lbsyun.baidu.com/apiconsole/key for details about app key. */
          ak: 'BO1gWgR8LDt1mmYsaTrvhWFszkBxMmYf'
})
export default {
    data(){
      return{
        pageInfo:{
          pageSize:5 ,
          pageNo: 1,
          total: 5,
        },
        center:{
          lng:0,
          lat:0
        },
        slice:null,
        tableData:null,
        dialogVisible:false,
        points:[]
      }
    },
    methods:{
        //定位站点
        location(row){
          this.dialogVisible = true;
            //先清零
            this.points = [];
            this.points.push({
                lng:row.longitude,
                lat:row.latitude
            })
          this.center.lat = row.latitude;
          this.center.lng = row.longitude;
        },

        //删除站点
        deleteLocation(row){

        },

        rowStyle(){
                return "text-align:center"
        },
        headStyle(){
                return "text-align:center"
        },

        change(page) {
          this.pageInfo.pageNo = page;
          this.slice = this.tableData.slice((page - 1) * this.pageInfo.pageSize, page * this.pageInfo.pageSize);
        } ,
        //加点颜色
        tableRowClassName({rowIndex}) {
                    if (rowIndex === 1) {
                    return 'warning-row';
                    } else if (rowIndex === 3) {
                    return 'success-row';
                    }else if(rowIndex === 5){
                        return 'danger-row';
                    }
                    return '';
        },
        
         handler ({BMap, map}) {
            var geolocation = new BMap.Geolocation()
            // 获取逆解析方法实例
            this.myGeo = new BMap.Geocoder()
            this.zoom = 15;
                 // 获取自动定位获取的坐标信息 这鬼东西好像没有用啊
        },
    },

    mounted:function(){
        queryAllBackLocation().then(rs=>{
            console.log(rs)
            this.tableData = rs.data;
            this.pageInfo.total = rs.data.length;
            this.pageInfo.pageNo = 1;
            this.slice = rs.data.slice(0, this.pageInfo.pageSize);
        })
    }
}
</script>

<style scoped>
    .map{
    width: 100%;
    height: 500px;
}

</style>