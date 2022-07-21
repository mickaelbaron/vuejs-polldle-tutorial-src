<script setup>
import { ref } from 'vue'

const description = ref(
  'PollDLE ~= Poll + (last part of famous DooDLE app). PollDLE is an open source project developped by Mickael BARON. Powered by Vue.js (frontend) and Java (backend).'
)
</script>

<template>
  <div class="container">
    <!-- Directive v-once -->
    <p class="footer"><!-- Mustache with description --></p>
  </div>
</template>

<style>
.footer {
  font-size: 15px;
  color: #6c757d;
  text-decoration: underline;
  text-decoration-color: yellow;
  text-align: right;
  margin-top: 4em;
  margin-right: 2em;
}
</style>
