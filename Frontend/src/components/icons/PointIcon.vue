<template>
  <div class="online-status"></div>
</template>

<script>
export default {
  name: 'PointIcon',
};
</script>

<style scoped>
.online-status {
  width: 12px;
  height: 12px;
  background-color: #00ff0a; /* Зеленый цвет */
  border-radius: 50%; /* Делаем круглую форму */
  animation: pulse 3s infinite; /* Анимация пульсации */
}

@keyframes pulse {
  0% {
    transform: scale(0.9); /* Начальный размер */
    opacity: 0.8; /* Начальная прозрачность */
  }
  50% {
    transform: scale(1.1); /* Увеличение размера */
    opacity: 1; /* Полная видимость */
  }
  100% {
    transform: scale(0.9); /* Возврат к начальному размеру */
    opacity: 0.8; /* Возврат к начальной прозрачности */
  }
}
</style>
