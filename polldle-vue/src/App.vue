<template>
  <div>
    <router-view />
    <footerPolldle />
  </div>
</template>

<script>

import footerPolldle from "@/components/FooterPolldle.vue";

export default {
  name: "App",
  components : { footerPolldle }
};
</script>

<style>
</style>
