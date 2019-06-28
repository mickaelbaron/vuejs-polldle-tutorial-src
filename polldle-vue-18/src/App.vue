<template>
  <div>
    <!-- Add router-view component -->
    <router-view/>
    <footerPolldle/>
  </div>
</template>

<script>
import footerPolldle from "@/components/FooterPolldle.vue";

export default {
  name: "app",
  components: { footerPolldle }
};
</script>

<style>
</style>
