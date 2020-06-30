<template>
  <div class="container">
    <!-- Directive v-if with !isErrorState() -->
    <div v-if="!isErrorState()">
      <!-- Mustache with question -->
      <h1>{{ question }}</h1>
      <div class="row">
        <div class="col-8">
          <!-- Instance of highcharts component -->
        </div>
        <div class="col-4">
          <!-- Directive v-for with data -->
          <div
            v-for="polldleOption in data"
            :key="polldleOption.name"
          >
            {{ polldleOption.name }}: {{ polldleOption.y }}
          </div>
        </div>
      </div>

      <!-- Directive v-if with isEmptyState() -->
      <div v-if="isEmptyState()">
        <h2>No vote at this moment, keep in touch. Results update in real-time.</h2>
      </div>
    </div>
    <!-- Directive v-else -->
    <!-- Mustache with errorMessage -->
    <div
      v-else
      class="error-message alert alert-danger"
      role="alert"
    >
      {{ errorMessage }}
    </div>
  </div>
</template>

<script>
// Import the VueHighcharts plugin.

// Use the VueHighcharts plugin.

const stateResult = {
  RESULT: "result",
  EMPTY: "empty",
  ERROR: "error"
};

var options = {
  chart: {
    plotBackgroundColor: null,
    plotBorderWidth: null,
    plotShadow: false,
    type: "pie",
    animation: false
  },
  title: {
    text: null
  },
  plotOptions: {
    series: {
      animation: false
    }
  },
  tooltip: {
    borderWidth: 1,
    shadow: true
  },
  credits: {
    enabled: false
  },
  series: [
    {
      name: "Vote",
      colorByPoint: true,
      data: []
    }
  ],
  loading: false
};

export default {
  name: "ResultPolldle",
  data: () => ({
    total: 0,
    state: null,
    question: "",
    errorMessage: "",
    options: options,
    data: []
  }),
  // Watcher on data
  watch: {
    data() {
    }
  },
  methods: {
    isResultState() {
      return this.state === stateResult.RESULT;
    },

    isEmptyState() {
      return this.state === stateResult.EMPTY;
    },

    isErrorState() {
      return this.state === stateResult.ERROR;
    }
  }
};
</script>

<style>
</style>