<template>
    <div>   
        <div id="main" style="width: 100%;height:700px; font-size: 25px;"></div>
    </div>
</template>

<script>

import {queryProfits} from "../../../api/business";
import constants from "../../../store/constants";
import {queryGoods} from "../../../api/good";
import {getUser} from '../../../api/session'

export default {
    name: 'profit',
    data() {
        return {
            goods: [],
            ids: [],
            profits: [],
            GoodIDName: null, // id,name
            GoodIDProfit: null, //id,profit
        }
    },
    // 此时,页面上的元素,已经被渲染完毕了
    mounted() {
        this.refresh();
    },
    methods: {
        refresh() {
            const _this = this;
            _this.goods = [];
            _this.ids = [];
            _this.profits = [];
            _this.GoodIDName = new Map();
            _this.GoodIDProfit = new Map();
            queryGoods({}).then(rs => {
                rs.forEach(function (item) { //初始化 id-name map
                    _this.ids.push(item.id);
                    _this.GoodIDName.set(item.id, item.name);
                    _this.GoodIDProfit.set(item.id, 0);
                    _this.GoodIDName = new Map(_this.GoodIDName);
                    _this.GoodIDProfit = new Map(_this.GoodIDProfit);
                });
                //获取利益
                queryProfits(getUser().id).then(rs => {
                    console.log(rs.data)
                    if (rs.code === constants.state.SUCCESS) {
                        rs.data.forEach(function (item) {
                            let id = item.commodityId;
                            let amount = item.amount;
                            _this.GoodIDProfit.set(id, _this.GoodIDProfit.get(id) + amount);
                            _this.GoodIDProfit = new Map(_this.GoodIDProfit);
                        });
                        _this.ids.forEach(function (id) {
                            _this.goods.push(_this.GoodIDName.get(id));
                            _this.profits.push(_this.GoodIDProfit.get(id))
                        })
                        console.log(_this.GoodIDName)
                         console.log(_this.goods)
                        let echarts = require('echarts');
                        let myChart = echarts.init(document.getElementById('main'));
                        var option = {
                            title: {
                                text: '商品收益图'
                            },
                            tooltip: {
                                trigger: 'axis',
                                axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                                    type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                                }
                            },
                            legend: {
                                data: ['收益/￥']
                            },
                            xAxis: {
                                data: _this.goods,
                                   axisLabel: {
                                                textStyle: {
                                                
                                                fontSize:'10',
                                                fontWeight:'bold'
                                                            },
                                            }, 
                            },
                            yAxis: {},
                            series: [{
                                barWidth: '60%',
                                name: '收益',
                                type: 'bar',
                                data: _this.profits
                            }]
                        }
                        myChart.setOption(option)
                    } else {
                        this.$message.error(rs.message)
                    }
                })
            })
        }
    }
}
</script>

<style lang="less" scoped>

</style>
