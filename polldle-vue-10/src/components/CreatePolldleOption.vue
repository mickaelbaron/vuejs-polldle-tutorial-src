<script setup>
// Add properties definition on polldleOption object
</script>

<template>
  <div class="polldle-option-input row justify-content-center no-gutters">
    <div class="col col-auto">
      <!-- Bind both value and title attributes with polldleOption property -->
      <input
        type="text"
        class="form-control"
        readonly
      />
    </div>
    <div class="col col-auto">
      <button class="btn btn-outline-secondary" type="button">X</button>
    </div>
  </div>
</template>

<style>
.polldle-option-input {
  margin-bottom: 5px;
}
</style>
