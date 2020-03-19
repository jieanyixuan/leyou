<template>
  <v-card>
      <v-flex xs12 sm10>
        <!--
        :treeData="treeData"
        treeData属性是使用本地数据,
        url属性是调用远程数据,
        treeData会覆盖掉url,因为我们是远程调用,所以不加,
        我们用treeData属性来查看效果,按照本地的样式开发
        -->
        <v-tree url="/item/category/list"

                :isEdit="isEdit"
                @handleAdd="handleAdd"
                @handleEdit="handleEdit"
                @handleDelete="handleDelete"
                @handleClick="handleClick"
        />
      </v-flex>
  </v-card>
</template>

<script>
  //import {treeData} from "../../mockDB";

  export default {
    name: "category",
    data() {
      return {
        isEdit:true,
       // treeData,
      }
    },
    methods: {
      handleAdd(node) {
        this.$http.post("/item/category/add",node)
          .then(() => {
            this.$message.success("添加成功");
          })
          .catch(() => {
            this.$message.error("添加失败");
          }).then(() => {
            this.reload();
        })
      },
      handleEdit(id, name) {
        console.log("edit... id: " + id + ", name: " + name)
      },
      handleDelete(id) {
        console.log("delete ... " + id)
      },
      handleClick(node) {
        console.log(node)
      }
    }
  };
</script>

<style scoped>

</style>
