<template >
  <div class="polldle-option-input row justify-content-center no-gutters">
    <div class="col col-auto">
      <input type="text" class="form-control" readonly>
    </div>
    <div class="col col-auto">
      <!-- Directive v-on with removePolldleOption -->
      <button
        class="btn btn-outline-secondary"
        type="button"
        @click="removePolldleOption(polldleOption)"
      >X</button>
    </div>
  </div>
</template>

<script>
export default {
  name: "CreatePolldleOption",
  data() {
    return {};
  },

  methods: {
    removePolldleOption(polldleOption) {}
  }
};
</script>

<style>
.polldle-option-input {
  margin-bottom: 5px;
}
</style>