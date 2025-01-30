<template>
  <div class="container">
    <MenuComponent/>
    <div class="profile-content">
      <div class="activity">
        <div class="activity-content">
          <PointIcon class="point"/>
          В сети</div>
      </div>

      <div class="profile-avatar"></div>

      <div class="profile-fio">
        <div class="fio-item"> {{ data.data.surname }}</div>
        <div class="fio-item"> {{ data.data.name }}</div>
        <div class="fio-item"> {{ data.data.patronymic }}</div>
        <div class="line"></div>
        <div class="employee-data">
          <div class="employee-item-label">Должность</div>
          <div class="employee-item-content">{{ data.data.position }}</div>

          <div class="employee-item-label">Подразделение</div>
          <div class="employee-item-content">Application Core</div>

          <div class="employee-item-label">Руководитель</div>
          <div class="employee-item-content">А.С.Иванов</div>
          <div class="line"></div>

        </div>
      </div>

      <div class="profile-info">
        <div class="info-header">Личная информация</div>
        <div class="info-items">
          <AddressIcon class="icon" :color="'var(--color-text-1)'"/>
          <div class="info-item-data"> Казань </div>
        </div>
        <div class="info-items">
          <BirthdayIcon class="icon" :color="'var(--color-text-1)'"/>
          <div class="info-item-data"> 02.01.2004 </div>
        </div>
        <div class="info-items">
          <AddressIcon class="icon" :color="'var(--color-text-1)'"/>
          <div class="info-item-data"> Мужской </div>
        </div>

        <div class="info-header header-second">Контакты</div>
        <div class="info-items">
          <AddressIcon class="icon" :color="'var(--color-text-1)'"/>
          <div class="info-item-data"> +79393376939 </div>
        </div>
        <div class="info-items">
          <BirthdayIcon class="icon" :color="'var(--color-text-1)'"/>
          <div class="info-item-data"> kasimovdanil637@gmail.com </div>
        </div>
        <div class="info-items">
          <AddressIcon class="icon" :color="'var(--color-text-1)'"/>
          <div class="info-item-data"> dykasimov620@bars.ph </div>
        </div>

        <div class="info-items">
          <AddressIcon class="icon" :color="'var(--color-text-1)'"/>
          <div class="info-item-data"> Kasimov_Danil </div>
        </div>


        <div class="info-header header-second">Дополнительно</div>
        <div class="employee-item-label">О себе</div>
        <div class="employee-item-content" style="width: 490px">Следует отметить, что разбавленное изрядной долей эмпатии, рациональное мышление выявляет срочную потребность благоприятных перспектив.</div>
        <div class="employee-item-label">Хобби</div>
        <div class="employee-item-content" style="width: 490px"> Рыбалка, просмотр фильмов и сериалов </div>

      </div>

    </div>
  </div>
</template>


<script setup>
import {ref, onBeforeMount, reactive} from 'vue';
import {Api} from "@/api/Api.js";
import MenuComponent from "@/components/MenuComponent.vue";
import AddressIcon from "@/components/icons/AddressIcon.vue";
import BirthdayIcon from "@/components/icons/BirthdayIcon.vue";
import PhoneIcon from "@/components/icons/PhonenumberIcon.vue";
import GenderIcon from "@/components/icons/GenderIcon.vue";
import PointIcon from "@/components/icons/PointIcon.vue";

const api = new Api();

let data = ref(Object);

onBeforeMount(async () => {
  const jwt = localStorage.getItem("jwt")
  const header = {
    Authorization: 'Bearer ' + jwt
  }
  const response = await api.get("/profile/get/current", header);
  const json = await response.json();
  data.value = json.result
  console.log(data.value)
});

</script>

<style scoped>
.container {
  width: 100%;
  height: 100vh;
  background-color: var(--bg-color-2);
}

.profile-content {
  width: 88%;
  height: 95vh;
  background-color: var(--bg-color-1);
  border-radius: 30px;
  position: absolute;
  right: 2.5vh;
  top: 2.5vh;
}

.activity {
  width: 95px;
  height: 40px;
  border-top-left-radius: 20px;
  border-bottom-left-radius: 20px;
  background-color: var(--bg-color-2);
  position: absolute;
  right: 0;
  top: 30px;
}

.activity-content {
  display: flex;
  color: var(--bg-color-1);
  margin-top: 10px;
  font-size: 16px;
  font-weight: 700;
  font-family: var(--font-1), fantasy;
}

.point {
  margin-top: 3px;
  width: 15px;
  margin-left: 15px;
  margin-right: 7px;
}
.profile-avatar {
  width: 220px;
  height: 260px;
  border: var(--border-input);
  border-radius: 20px;
  position: absolute;
  left: 5%;
  top: 5%;
  background-image: url("../../public/Фото на паспорт.jpg");
  background-position: center; /* Центрирует изображение */
  background-size: cover; /* Растягивает изображение, чтобы оно заполнило блок */
  background-repeat: no-repeat;
}

.profile-fio {
  position: absolute;
  left: 5%;
  top: 45%;
}

.fio-item {
  margin-bottom: 0.5em;
  font-size: 26px;
  font-weight: 800;
  color: var(--color-text-1);
  font-family: var(--font-1), monospace;
}

.line {
  height: 1px;
  width: 220px;
  border-bottom: var(--border-input);
  margin-top: 30px;
  margin-bottom: 20px;
}

.employee-data {
  margin-top: 10px;
  width: 220px;
}

.employee-item-label {
  color: var(--color-text-2);
  font-family: var(--font-1), monospace;
  font-size: 14px;
  margin-bottom: 5px;
}

.employee-item-content {
  color: var(--color-text-1);
  font-family: var(--font-1), monospace;
  font-size: 16px;
  font-weight: 700;
  margin-bottom: 20px;
  width: 100%;
  display: flex;
  flex-wrap: nowrap;
}

.profile-info {
  position: absolute;
  left: 27%;
  top: 5%;
}

.info-header {
  font-weight: 700;
  color: var(--color-text-2);
  font-size: 20px;
  font-family: var(--font-1), monospace;
  margin-bottom: 20px;
}

.info-items {
  display: flex;
  margin-top: 5px;
}

.icon {
  margin-left: -4px;
}


.info-item-data {
  color: var(--color-text-1);
  font-family: var(--font-1), monospace;
  font-weight: 700;
  margin-top: 2px;
  margin-left: 10px;
  padding: 1px;
  background-color: var(--bg-color-3);
  width: 450px;
  height: 18px;
  align-items: center;
  border: var(--border-input-2);
  justify-content: center;
  align-content: center;
  border-radius: 4px;
  text-align: center;
  font-size: 14px;
}

.header-second {
  margin-top: 50px;
}
</style>
