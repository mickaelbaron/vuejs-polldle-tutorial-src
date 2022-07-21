<script setup>
defineProps({
  polldleOption: {
    type: Object,
    required: true
  }
})
</script>

<template>
  <div class="polldle-option-input row justify-content-center no-gutters">
    <div class="col col-auto">
      <input
        type="text"
        class="form-control"
        readonly
        :value="polldleOption.text"
        :title="polldleOption.text"
      />
    </div>
    <div class="col col-auto">
      <button
        class="btn btn-outline-secondary"
        type="button"
        @click="$emit('removed-polldle-option', polldleOption)"
      >
        X
      </button>
    </div>
  </div>
</template>

<style>
.polldle-option-input {
  margin-bottom: 5px;
}
</style>
