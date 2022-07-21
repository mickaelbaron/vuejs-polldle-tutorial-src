<script setup>
// Add properties definition on polldleOption object
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
      <!-- Bind both value and title attributes with polldleOption property -->
      <input
        type="text"
        class="form-control"
        readonly
        :value="polldleOption.text"
        :title="polldleOption.text"
      />
    </div>
    <div class="col col-auto">
      <!-- Trigger an event to the parent component -->
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
