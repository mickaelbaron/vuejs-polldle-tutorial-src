<template>
  <div class="container">
    <!-- Directive v-once -->
    <p class="footer"><!-- Mustache with description --></p>
  </div>
</template>

<script>
export default {
  name: "FooterPolldle",
  data() {
    return {
      description:
        "PollDLE ~= Poll + (last part of famous DooDLE app). PollDLE is an open source project developped by Mickael BARON and Mahamadou DIABATE - Powered by Vue.js and Java."
    };
  }
};
</script>

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