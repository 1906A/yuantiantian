<template>
  <v-card>
      <v-flex xs12 sm10>
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
  // import {treeData} from "../../mockDB";

  export default {
    name: "category",
    data() {
      return {
        isEdit:true,
        // treeData:treeData
      }
    },
    methods: {
      handleAdd(node) {
        console.log("add .... ");
        console.log(node);



      },
      handleEdit(node) {
        //console.log("edit... id: " + id + ", name: " + name)


        console.log("------------------------------");
        console.log(node);
        console.log("------------------------------");
        if (node.id==0){
          this.$http.post("/item/category/add",node)
            .then((res)=>{
              alert(res.data);
              alert("添加成功");
              window.location.reload();
            }).catch((erro)=>{
            alert("添加失败");
          });
        }else{
          this.$http.post("/item/category/edit",node)
            .then((res)=>{
              alert(res.data);
              alert("修改成功");
              window.location.reload();
            }).catch((erro)=>{
            alert("修改失败");
          })
        }



      },
      handleDelete(id) {
        console.log("delete ... " + id)
        this.$http.post("/item/category/del", {id:id})
          .then((res)=>{
            alert(res.data);
          }).catch((erro)=>{
          alert("删除失败");
        })
        // this.$http.get("/item/category/del?id="+id)
        //   .then((res)=>{
        //     alert(res.data);
        //   }).catch((erro)=>{
        //   alert("删除失败");
        // })
        // this.$http.get("/item/category/del",{params:{id:id}})
        //   .then((res)=>{
        //     alert(res.data);
        //   }).catch((erro)=>{
        //   alert("删除失败");
        // })
      },
      handleClick(node) {
        console.log(node)
      }
    }
  };
</script>

<style scoped>

</style>
