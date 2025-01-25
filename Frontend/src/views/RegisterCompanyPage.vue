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
        <h1 class="content-header">Регистрация компании</h1>

        <transition name="fade">
          <div v-if="isFirstWindow" class="content-first">
            <InputComponent v-model="companyData.name" :name="'Название'" required
                            :description="'Введите полное название компании.'" class="input-default"/>
            <InputComponent v-model="companyData.inn" :name="'ИНН'" required :min="10" :max="10"
                            :description="'Введите ИНН компании.'"
                            class="input-default"/>
            <InputComponent v-model="companyData.domain" required :name="'Домен'"
                            :description="'Введите домен компании.'"
                            class="input-default"/>
            <button style="margin-top: 8%" @click="switchStep" class="button-next">Следующий шаг</button>
          </div>
        </transition>

        <transition name="fade">
          <div v-if="!isFirstWindow" class="content-second">
            <h2 class="content-second-header">Введите данные первого сотрудника компании</h2>
            <InputComponent required v-model="employeeData.name" :name="'Имя'" class="input-default-2"/>
            <InputComponent required v-model="employeeData.surname" :name="'Фамилия'" class="input-default-2"/>
            <InputComponent required v-model="employeeData.patronymic" :name="'Отчество'" class="input-default-2"/>
            <InputComponent required v-model="employeeData.position" :name="'Должность'" class="input-default-2"/>
            <InputComponent required is-email v-model="employeeData.email" :name="'Электронная почта'"
                            class="input-default-2"/>
            <button @click="registerCompany" style="margin-top: 5%" class="button-next">Зарегистрировать компанию
            </button>
            <button style="margin-top: 1%" @click="switchStep" class="button-back">Вернуться на предыдущий шаг</button>
          </div>
        </transition>
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

const companyData = reactive({
  name: '',
  inn: '',
  domain: '',
})

const employeeData = reactive({
  name: '',
  surname: '',
  patronymic: '',
  position: '',
  companyId: '',
  role: 'ADMIN',
  email: '',
})

const isDesktop = ref(true);
const isFirstWindow = ref(true);
const loading = ref(false);
const errors = ref([]);


const updateScreenSize = () => {
  isDesktop.value = window.innerWidth > 1024;
};

const switchStep = () => {
  isFirstWindow.value = !isFirstWindow.value;
};

onMounted(() => {
  updateScreenSize();
  window.addEventListener("resize", updateScreenSize);
});

onUnmounted(() => {
  window.removeEventListener("resize", updateScreenSize);
});

const companyId = ref('')

const registerCompany = async () => {
  loading.value = true
  const response = await api.post('/company/add', null, companyData);
  if (response.ok) {
    const responseData = await response.json();
    companyId.value = responseData.result
    await addEmployee()
  } else {
    const errorJson = await response.json();
    addError(errorJson.errorMessage);
    loading.value = false
  }
};

const addEmployee = async () => {
  employeeData.companyId = companyId.value
  const response = await api.post('/user-management/add', null, employeeData);
  if (response.ok) {
    const responseData = await response.json();
    console.log(responseData.result);
    loading.value = false
    await router.push("/employee/login")
  } else {
    const errorJson = await response.json();
    addError(errorJson.errorMessage);
    const resp = await api.delete('/company/delete-empty/' + companyId.value);
    if (!resp.ok) {
      const errorJson = await response.json();
      addError(errorJson.errorMessage);
    }
    loading.value = false
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
  width: 400px;
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

.fade-enter-active, .fade-leave-active {
  transition: opacity 0.5s ease, transform 0.5s ease;
}

.fade-enter-from {
  opacity: 0;
  transform: translateY(20px);
}

.fade-enter-to {
  opacity: 1;
  transform: translateY(0);
}

.fade-leave-from {
  opacity: 1;
  transform: translateY(0);
}

.fade-leave-to {
  opacity: 0;
  transform: translateY(0);
  transition: none;
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
</style>
