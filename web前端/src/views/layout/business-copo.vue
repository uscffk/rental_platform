<template>
    <fragment class="heightAll widthAll">
        <!--Emitted value instead of an instance of Error   v-for还要加key属性-->
        <!--index+''才行-->
        <el-submenu :index="index + ''" :key="index" class="widthAll"
                    v-for="(menu,index) in $router.options.routes[2].menus"> <!--menus是菜单名列表-->
            <template slot="title">
                <i :class="$router.options.routes[2].icons[index]"></i>
                <span>{{menu}}</span>
            </template>
            <el-menu-item :index=" $router.options.routes[2].children[list].path"
                          :key="index2"
                          v-for="(list,index2) in $router.options.routes[2].pathIndex[index]">
                {{$router.options.routes[2].children[list].name}}
            </el-menu-item>
        </el-submenu>
    </fragment>
</template>

<script>
export default {
    name: "business-compo"
}
</script>

<style scoped>

</style>