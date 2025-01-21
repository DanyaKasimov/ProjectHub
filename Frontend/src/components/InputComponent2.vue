<template>
  <div class="input">
    <label :class="{ float: localValue.length > 0 }" for="label_name">{{ props.name }}</label>
    <input id="label_name" type="text" v-model="localValue" class="content-input">
    <span class="input-description">{{ props.description }}</span>
  </div>
</template>

<script setup>
import {ref, defineProps, watch, defineEmits} from "vue";

const props = defineProps({
  name: String,
  description: String,
  modelValue: String
});

const emit = defineEmits(["update:modelValue"]);

const localValue = ref(props.modelValue);

watch(localValue, (newValue) => {
  emit("update:modelValue", newValue);
});

watch(() => props.modelValue, (newValue) => {
  localValue.value = newValue;
});
</script>

<style scoped>
.input {
  width: 100%;
  height: 40px;
  border: var(--border-input);
  position: relative;
  text-align: left;
  border-radius: 10px;
}

.content-input {
  width: 96.5%;
  height: 40px;
  padding: 0 10px;
  font-size: 16px;
  background-color: var(--input-color-1);
  border: none;
  outline: none;
  color: var(--color-text-1);
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
  font-family: "DejaVu Sans Mono", monospace;
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
  font-family: "DejaVu Sans Mono", monospace;
  font-size: 15px;
  margin-top: 3px;
}
</style>
