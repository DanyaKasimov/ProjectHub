<template>
  <div class="container-logo">
    <svg :width="svgWidth" height="300" class="drawing">
      <defs>
        <linearGradient id="animated-gradient-2x" x1="0%" y1="0%" x2="100%" y2="0%" gradientUnits="objectBoundingBox">
          <stop offset="0%" stop-color="white" />
          <stop offset="100%" stop-color="ghostwhite" />
          <animateTransform
            attributeName="gradientTransform"
            type="rotate"
            from="0 0.5 0.5"
            to="360 0.5 0.5"
            dur="10s"
            repeatCount="indefinite"
          />
          <animateTransform
            attributeName="gradientTransform"
            type="scale"
            values="1 1; 1.2 1.2; 1 1"
            keyTimes="0; 0.5; 1"
            dur="10s"
            repeatCount="indefinite"
            additive="sum"
          />
        </linearGradient>
      </defs>
      <text
        v-for="(letter, index) in letters"
        :key="index"
        class="letter"
        :x="xPositions[index]"
        :y="yPositions[index]"
        :style="{
          '--animation-delay': `${delays[index]}s`,
          '--end-offset': `${endOffsets[index]}`,
          'fill': 'url(#animated-gradient-2x)',
          'stroke': 'url(#animated-gradient-2x)',
        }"
        :class="{ 'complete': completeAnimation }"
      >
        {{ letter }}
      </text>
    </svg>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';

const letters = 'PROJECTHUB'.split('');
const animationDuration = 0.5; // Длительность анимации каждой буквы

// Позиции для масштаба 1.5x
const xPositions = [0, 90, 187.5, 300, 367.5, 465, 570, 367.5, 472.5, 570];
const yPositions = [150, 150, 150, 150, 150, 150, 150, 285, 285, 285];

// Конечные значения stroke-dashoffset для масштаба 1.5x
const endOffsets = [525, 350, 520, 545, 320, 500, 500, 500, 500, 500];

// Задержки для каждой буквы: сначала "PROJECT", затем "HUB"
const delays = letters.map((_, index) => index * animationDuration);

// Рассчитываем ширину SVG на основе позиций букв
const svgWidth = computed(() => Math.max(...xPositions) + 250);

// Переменная для управления запуском полной анимации
const completeAnimation = ref(false);

// Общая длительность начальных анимаций
const totalDuration = ((letters.length - 1) * animationDuration + 0.5) * 1000; // в миллисекундах

onMounted(() => {
  setTimeout(() => {
    completeAnimation.value = true;
  }, totalDuration);
});
</script>

<style scoped>
.container-logo {
  position: relative;
  z-index: 2;
  display: flex;
  height: 300px; /* Увеличена высота для масштаба 1.5x */
}

.drawing {
  font-size: 150px; /* Увеличен размер шрифта */
  fill: url(#blue-violet-gradient-2x);
  stroke: url(#blue-violet-gradient-2x);
  stroke-width: 3; /* Увеличена толщина линии */
}

.letter {
  fill-opacity: 0;
  stroke-dasharray: 750; /* Увеличено для масштаба 1.5x */
  stroke-dashoffset: 750; /* Увеличено для масштаба 1.5x */
  animation: drawPartial 0.5s forwards;
  animation-delay: var(--animation-delay);
}

@keyframes drawPartial {
  to {
    stroke-dashoffset: var(--end-offset);
  }
}

.letter.complete {
  animation: drawComplete 3s forwards, fillIn 1s forwards;
  animation-delay: 0s, 1s;
}

@keyframes drawComplete {
  from {
    stroke-dashoffset: var(--end-offset);
  }
  to {
    stroke-dashoffset: 0;
  }
}
</style>
