<template>
  <div v-if="isDesktop">

    <div class="container-video">
      <video autoplay muted loop class="background-video">
        <source :src="poster" type="video/mp4"/>
      </video>
      <LogoTextComponent class="logo-text"/>
    </div>

    <div class="container-content">
      <h1 class="content-header">Регистрация компании</h1>

      <transition name="fade">
        <div v-if="isFirstWindow" class="content-first">
          <InputComponent v-model="companyData.name" :name="'Название'" :description="'Введите полное название компании.'" class="input-default"/>
          <InputComponent v-model="companyData.inn" :name="'ИНН'" :description="'Введите ИНН компании.'" class="input-default"/>
          <InputComponent v-model="companyData.domain" :name="'Домен'" :description="'Введите домен компании.'" class="input-default"/>
          <button style="margin-top: 8%" @click="goToNextStep" class="button-next">Следующий шаг</button>
        </div>
      </transition>

      <transition name="fade">
        <div v-if="!isFirstWindow" class="content-second">
          <h2 class="content-second-header">Введите данные первого сотрудника компании</h2>
          <InputComponent v-model="employeeData.name" :name="'Имя'" class="input-default-2"/>
          <InputComponent v-model="employeeData.surname" :name="'Фамилия'" class="input-default-2"/>
          <InputComponent v-model="employeeData.patronymic" :name="'Отчество'" class="input-default-2"/>
          <InputComponent v-model="employeeData.position" :name="'Должность'" class="input-default-2"/>
          <InputComponent v-model="employeeData.email" :name="'Электронная почта'" class="input-default-2"/>
          <button @click="registerCompany" style="margin-top: 5%" class="button-next">Зарегистрировать компанию</button>
          <button style="margin-top: 1%" @click="returnToFirstWindow" class="button-back">Вернуться на предыдущий шаг</button>
        </div>
      </transition>
    </div>
  </div>

  <div v-else>
    <!-- Контент для мобильных -->
  </div>
</template>
<script setup>
import { ref, reactive, onMounted, onUnmounted } from "vue";
import poster from "@/assets/poster.mp4";
import LogoTextComponent from "@/components/LogoTextComponent.vue";
import InputComponent from "@/components/InputComponent2.vue";

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
  role: '',
  email: '',
})

const isDesktop = ref(true);
const isFirstWindow = ref(true);

const updateScreenSize = () => {
  isDesktop.value = window.innerWidth > 1024;
};

const goToNextStep = () => {
  isFirstWindow.value = false;
};

const returnToFirstWindow = () => {
  isFirstWindow.value = true;
};

onMounted(() => {
  updateScreenSize();
  window.addEventListener("resize", updateScreenSize);
});

onUnmounted(() => {
  window.removeEventListener("resize", updateScreenSize);
});

const registerCompany = () => {
  console.log(companyData)
  console.log(employeeData)
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
</style>
