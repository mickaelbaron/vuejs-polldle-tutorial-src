<template>
  <div class="container">
    <!-- Directive v-if with !isErrorState() -->
    <div v-if="!isErrorState()">
      <!-- Mustache with question -->
      <h1>{{ question }}</h1>
      <div class="row">
        <div class="col-8">
          <!-- Instance of highcharts component -->
          <!-- Declaring Ref attribute -->
          <highcharts :options="options" ref="highcharts"/>
        </div>
        <div class="col-4">
          <!-- Directive v-for with data -->
          <div v-for="polldleOption in data" :key="polldleOption.name">{{polldleOption.name}}: {{polldleOption.y}}</div>
        </div>
      </div>

      <!-- Directive v-if with isEmptyState() -->
      <div v-if="isEmptyState()">
        <h2>No vote at this moment, keep in touch. Results update in real-time.</h2>
      </div>
    </div>
     <!-- Directive v-else -->
     <!-- Mustache with errorMessage -->
    <div v-else class="error-message alert alert-danger" role="alert">{{ errorMessage }}</div>
  </div>
</template>

<script>
import capitalizeFirstLetter from "./utils.js";
// Import the VueHighcharts plugin.
import VueHighcharts from "vue-highcharts";

// Use the VueHighcharts plugin.
import Vue from "vue";
Vue.use(VueHighcharts);

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
      // Use reference on hightcharts component.
      var chart = this.$refs.highcharts.chart;
      chart.series[0].update(
        {
          data: this.data
        },
        true
      );
    }
  },
  // Use created hook to initialize EventSource object.
  created() {
    var source = new EventSource(
      "http://127.0.0.1:9991" +
      "/polldles/" +
      this.$route.params.pathurl +
      "/votes/sse"
    );

    source.addEventListener(
      "update-polldleresult",
      e => {
        var result = JSON.parse(e.data);
        this.options.title.text = "  ";
        this.question = capitalizeFirstLetter(result.question);

        this.data = result.results.map(val => ({
          name: val.name,
          y: val.counter
        }));

        this.total = result.results
          .map(val => val.counter)
          .reduce((partial_sum, a) => partial_sum + a);

        if (this.total > 0) {
          this.state = stateResult.RESULT;
        } else {
          this.state = stateResult.EMPTY;
        }

        this.options.series[0].data = this.data;
      },
      false
    );

    source.onerror = () => {
      this.state = stateResult.ERROR;
      this.errorMessage = "Problem to retrieve Polldle result.";
    };
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