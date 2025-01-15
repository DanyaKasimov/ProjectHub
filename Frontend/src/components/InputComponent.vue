<template>
  <div class="input">
    <label :class="{ float: username }" for="username">{{ props.name }}</label>
    <input id="username" type="text" v-model="username" class="content-input">
    <span class="input-description">{{ props.description }}</span>
  </div>
</template>

<script setup>
import {ref, defineProps} from "vue";

const props = defineProps({
  name: "",
  description: ""
})

const username = ref("");

</script>


<style scoped>
.input {
  width: 100%;
  border-radius: 10px;
  height: 40px;
  border: var(--border-input);
  position: relative;
  margin: 40px auto 0 auto;
  text-align: left;
}

.content-input {
  width: 96.5%;
  height: 40px;
  padding: 0 10px;
  font-size: 16px;
  background-color: var(--color-input);
  border: none;
  outline: none;
  color: var(--color-text-1);;
  border-radius: 10px;
  font-weight: 700;
}

label {
  position: absolute;
  left: 2%;
  top: 50%;
  transform: translateY(-50%);
  font-size: 16px;
  color: var(--color-text-1);
  font-family: "Roboto Light", serif;
  transition: 0.3s ease;
  pointer-events: none;
}

label.float {
  top: -35%;
  font-size: 15px;
  color: var(--color-text-1);
}

.input-description {
  color: var(--color-text-1);
  font-family: "Roboto Light", serif;
  font-size: 15px;
  margin-top: 3px;
}
</style>
