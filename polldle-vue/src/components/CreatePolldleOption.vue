<template >
  <div class="polldle-option-input row justify-content-center no-gutters">
    <div class="col col-auto">
      <input
        type="text"
        class="form-control"
        readonly
        v-bind:value="polldleOption.text"
        v-bind:title="polldleOption.text"
      >
    </div>
    <div class="col col-auto">
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
    removePolldleOption(polldleOption) {
      this.$emit("removed-polldle-option", polldleOption);
    }
  },
  props: {
    polldleOption: {
      type: Object,
      required: true
    }
  }
};
</script>

<style>
.polldle-option-input {
  margin-bottom: 5px;
}
</style>