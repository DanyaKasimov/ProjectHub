<template>
  <div v-if="isDesktop">

    <div class="error-container">
      <transition-group name="error-message" tag="div">
        <div
          class="error-message"
          v-for="error in errors"
          :key="error.id"
        >
          <div class="error-message-text">{{ error.message }}</div>
        </div>
      </transition-group>
    </div>

    <div class="container-video">
      <video autoplay muted loop class="background-video">
        <source :src="poster" type="video/mp4"/>
      </video>
      <LogoTextComponent class="logo-text"/>
    </div>


    <div class="container-content">
      <LoaderSquareComponent class="loader" v-if="loading"/>
      <div v-else>
        <h1 class="content-header">Вход</h1>
        <div class="content-first">
          <InputComponent v-model="employeeData.username" :name="'Имя пользователя'" required
                          :description="'Введите ваш логин'" class="input-default"/>
          <InputComponent v-model="employeeData.password" required :name="'Пароль'"
                          :description="'Введите ваш пароль'"
                          class="input-default"/>
          <button style="margin-top: 8%" @click="signin" class="button-next">Войти</button>
        </div>
      </div>
    </div>
  </div>

  <div v-else>
    <!-- Контент для мобильных -->
  </div>
</template>
<script setup>
import {ref, reactive, onMounted, onUnmounted} from "vue";
import poster from "@/assets/poster.mp4";
import LogoTextComponent from "@/components/LogoTextComponent.vue";
import InputComponent from "@/components/InputComponent.vue";
import {Api} from "@/api/Api.js";
import LoaderSquareComponent from "@/components/LoaderSquareComponent.vue";
import router from "@/router/index.js";

const api = new Api()

const employeeData = reactive({
  username: '',
  password: '',
})


const isDesktop = ref(true);
const loading = ref(false);
const errors = ref([]);


const updateScreenSize = () => {
  isDesktop.value = window.innerWidth > 1024;
};

onMounted(() => {
  updateScreenSize();
  window.addEventListener("resize", updateScreenSize);
});

onUnmounted(() => {
  window.removeEventListener("resize", updateScreenSize);
});

const signin = async () => {
  loading.value = true
  const response = await api.post('/auth/signin', null, employeeData);
  if (response.ok) {
    const responseData = await response.json();
    const jwt = responseData.result
    localStorage.setItem("jwt", jwt)
    setTimeout(() => {
      loading.value = false
    }, 1000)
    await router.push("/employee/profile")
  } else {
    loading.value = false
    const errorJson = await response.json();
    addError(errorJson.errorMessage);
  }
};

function addError(message) {
  const error = {id: Date.now(), message};
  errors.value.push(error);
  setTimeout(() => {
    removeError(error.id);
  }, 4000);
}


function removeError(id) {
  errors.value = errors.value.filter(error => error.id !== id);
}
</script>


<style scoped>
.container-video {
  position: relative;
  width: 100%;
  height: 100vh;
  overflow: hidden;
}

.background-video {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
  z-index: -1;
}

.logo-text {
  margin-top: 20vh;
  margin-left: 5%;
}

.container-content {
  position: absolute;
  top: 50%;
  right: 0;
  transform: translateY(-50%);
  height: 100vh;
  width: 44%;
  background-color: var(--bg-color-1);
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.25);
  border-radius: 40px 0 0 40px;
  z-index: 99;
}

.content-header {
  width: 100px;
  margin: 13% auto 0;
  font-family: "DejaVu Sans Mono", monospace;
}

.input-default {
  margin: 60px auto 0 auto;
  width: 85%;
}

.input-default-2 {
  margin: 40px auto 0 auto;
  width: 85%;
}

.content-first {
  margin-bottom: 10%;
}

.content-second {
  margin-bottom: 5%;
}

.content-second-header {
  width: 420px;
  margin: 2% auto 0;
  font-size: 16px;
  font-family: "DejaVu Sans Mono", monospace;
  font-weight: 100;
}

.button-next {
  width: 85%;
  border: none;
  height: 50px;
  border-radius: 10px;
  color: white;
  margin-left: 7.5%;
  background-color: var(--line-active);
  font-family: "DejaVu Sans Mono", monospace;
}

.button-back {
  width: 85%;
  border: 1px solid var(--line-active);
  height: 50px;
  border-radius: 10px;
  color: var(--line-active);
  margin-left: 7.5%;
  background-color: transparent;
  font-family: "DejaVu Sans Mono", monospace;
}


.error-container {
  position: fixed;
  top: 20px;
  right: 20px;
  z-index: 1000;
}

.error-message {
  background-color: rgba(255, 79, 79, 0.9);
  color: white;
  border-radius: 10px;
  margin-bottom: 10px;
  width: 300px;
  display: flex;
  justify-content: center;
  align-content: center;
}

.error-message-text {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 90%;
  padding: 20px 0;
}

.loader {
}
</style>
