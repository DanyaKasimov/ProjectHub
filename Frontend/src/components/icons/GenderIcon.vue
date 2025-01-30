<template>
  <svg
    xmlns="http://www.w3.org/2000/svg"
    viewBox="0 0 100 100"
    :width="size"
    :height="size"
  >
    <!-- Общий контур лица -->
    <circle cx="50" cy="50" r="45" fill="none" stroke="var(--color-text-1)" stroke-width="2" />

    <!-- Левая половина (женское лицо) -->
    <g transform="translate(-25, 0)">
      <!-- Волосы -->
      <path
        d="M50 20c-10 0-15 5-15 15v10c0 10 5 15 15 15"
        fill="var(--color-female, #FF69B4)"
      />
      <!-- Глаз -->
      <circle cx="40" cy="45" r="3" fill="var(--color-text-1)" />
      <!-- Нос -->
      <path d="M45 55 Q50 60 55 55" fill="none" stroke="var(--color-text-1)" stroke-width="2" />
      <!-- Рот -->
      <path d="M40 65 Q50 70 60 65" fill="none" stroke="var(--color-text-1)" stroke-width="2" />
    </g>

    <!-- Правая половина (мужское лицо) -->
    <g transform="translate(25, 0)">
      <!-- Волосы -->
      <path
        d="M50 20c10 0 15 5 15 15v10c0 10-5 15-15 15"
        fill="var(--color-male, #1E90FF)"
      />
      <!-- Глаз -->
      <circle cx="60" cy="45" r="3" fill="var(--color-text-1)" />
      <!-- Нос -->
      <path d="M55 55 Q60 60 65 55" fill="none" stroke="var(--color-text-1)" stroke-width="2" />
      <!-- Рот -->
      <path d="M50 65 Q60 70 70 65" fill="none" stroke="var(--color-text-1)" stroke-width="2" />
    </g>
  </svg>
</template>

<script>
export default {
  name: 'GenderIcon',
  props: {
    size: {
      type: [Number, String],
      default: 24, // Размер иконки по умолчанию
    },
  },
};
</script>
