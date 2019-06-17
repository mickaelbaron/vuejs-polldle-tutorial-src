<template>
  <div>
    <router-view/>
    <footerPolldle/>
  </div>
</template>

<script>

import footerPolldle from "@/components/FooterPolldle.vue";

export default {
  name: "app",
  components : { footerPolldle }
};
</script>

<style>
</style>
