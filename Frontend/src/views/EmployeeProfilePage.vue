<template>
  hello!
</template>


<script setup>
import {ref, onBeforeMount} from 'vue';
import {Api} from "@/api/Api.js";

const api = new Api();

onBeforeMount(async () => {
  const jwt = localStorage.getItem("jwt")
  const header = {
    Authorization: 'Bearer ' + jwt
  }
  const response = await api.get("/profile/get/current", header);
  const data = await response.json();
  console.log(data.result);
});

</script>

<style scoped>

</style>
