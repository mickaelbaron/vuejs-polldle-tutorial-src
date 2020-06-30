<template>
  <div>
    <createPolldle />
    <footerPolldle />
  </div>
</template>

<script>
import footerPolldle from "@/components/FooterPolldle.vue";
import createPolldle from "@/components/CreatePolldle.vue";

export default {
  name: "App",
  components: { footerPolldle, createPolldle }
};
</script>

<style>
</style>
