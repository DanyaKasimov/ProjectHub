<template>
  <div class="container-register" :class="{ mobile: isMobile, desktop: !isMobile }">
    <div class="container-video">
      <video autoplay muted loop class="background-video">
        <source :src="poster" type="video/mp4"/>
      </video>
      <LogoTextComponent class="logo-text" v-if="!isMobile"/>
    </div>
    <div class="container-content">
      <div v-if="isCountStep === 0">
        <h1 class="content-header">Регистрация компании</h1>
        <div class="steps">
          <div class="step-line" :class="{'step-active' : isCountStep >= 0}"></div>
          <div class="step-line" :class="{'step-active' : isCountStep === 1}"></div>
        </div>
        <InputComponent class="inputs" :name="'Название'" :description="'Введите полное название компании.'"/>
        <InputComponent class="inputs" :name="'ИНН'" :description="'Введите ИНН компании (10 цифр).'"/>
        <InputComponent class="inputs" :name="'Домен'" :description="'Введите домен компании.'"/>
        <button class="button-next" @click="nextStep">Следующий шаг</button>
      </div>

      <div v-else>
        <h1 class="content-header">Регистрация компании</h1>
        <div class="steps">
          <div class="step-line" :class="{'step-active' : isCountStep >= 0}"></div>
          <div class="step-line" :class="{'step-active' : isCountStep === 1}"></div>
        </div>
        <div class="mini-inputs">
          <InputComponent class="inputs second margin-2" :name="'Имя'" :description="'Введите ваше имя.'"/>
          <InputComponent class="inputs second margin-2" :name="'Фамилия'" :description="'Введите вашу фамилию.'"/>
        </div>
        <InputComponent class="inputs margin-2" :name="'Отчество'" :description="'Введите ваше отчество.'"/>
        <InputComponent class="inputs margin-2" :name="'Должность'" :description="'Введите вашу должность.'"/>
        <InputComponent class="inputs margin-2" :name="'Почта'" :description="'Введите вашу почту.'"/>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref, onMounted, onUnmounted} from "vue";
import poster from "@/assets/poster.mp4";
import LogoTextComponent from "@/components/LogoTextComponent.vue";
import InputComponent from "@/components/InputComponent.vue";

const isMobile = ref(false);
const isCountStep = ref(0);


const updateScreenSize = () => {
  isMobile.value = window.innerWidth <= 1024;
};

onMounted(() => {
  updateScreenSize();
  window.addEventListener("resize", updateScreenSize);
});

onUnmounted(() => {
  window.removeEventListener("resize", updateScreenSize);
});

const nextStep = () => {
  if (isCountStep < 1) isCountStep.value++;
}
</script>

<style scoped>
/* Общие стили */
.container-register {
  position: relative;
  width: 100%;
  height: 100vh;
  display: flex;
  align-items: center;
}

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

.container-content {
  position: absolute;
  top: 50%;
  right: 2%;
  transform: translateY(-50%);
  width: 40%;
  padding: 20px;
  background-color: var(--bg-color-1, rgba(255, 255, 255, 0.8));
  z-index: 99;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.25);
  border-radius: 20px;
}

/* Desktop версия */
.container-register.desktop {
  flex-direction: row;
}

.container-register.desktop .container-content {
  position: absolute;
  top: 50%;
  right: 2%;
  transform: translateY(-50%);
  width: 40%;
  height: 540px;
  background-color: var(--bg-color-1, rgba(255, 255, 255, 0.8));
  border-radius: 20px;
}

/* Mobile версия */
.container-register.mobile {
  flex-direction: column;
}

.container-register.mobile .container-video {
  height: 20vh; /* Видео занимает верхнюю часть */
}

.container-register.mobile .container-content {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  width: 100%;
  height: 80vh;
  background-color: var(--bg-color-1, rgba(255, 255, 255, 0.95));
  box-shadow: none; /* Лёгкая тень сверху */
  border-top-left-radius: 50px; /* Закругление сверху */
  border-top-right-radius: 50px;
  padding: 0;
  z-index: 1;
}

.container-register.mobile .container-video {
  height: 20vh; /* Больше места для видео на маленьких экранах */
}

.container-register.mobile .container-content {
  height: 80vh; /* Контент занимает оставшуюся часть экрана */
  border-top-left-radius: 16px;
  border-top-right-radius: 16px;
}

.content-header {
  width: 320px;
  margin: 5% auto;
  color: var(--color-text-1);
  font-family: "Roboto Light", serif;
  font-size: 30px;
  font-weight: 700;
}

.logo-text {
  margin-top: 20vh;
  margin-left: 5%;
}

.steps {
  width: 95%;
  margin: 0 auto 40px;
  display: flex;
  justify-content: space-between;
}

.step-line {
  height: 5px;
  width: 48%;
  background-color: var(--line-non-active);
  border-radius: 5px;
}

.step-active {
  background-color: var(--line-active);
}

.inputs {
  margin: 0 auto 60px;
  width: 95%;
}

.second {
  width: 49%;
  margin-left: 0;
  padding-left: 0;
}
.second:last-child {
  margin-right: 0;
}
.mini-inputs {
  display: flex;
  margin: 0 auto;
  justify-content: space-between;
  width: 95%;
}

.margin-2 {
  margin: 0 auto 50px;

}
.button-next {
  width: 95%;
  margin-left: 2.5%;
  border: none;
  background-color: var(--line-active);
  height: 45px;
  border-radius: 10px;
  color: var(--bg-color-1);
  font-size: 18px;
  margin-bottom: 10px;
}

.button-next:hover {
  background-color: var(--line-active-hover);
}

</style>
