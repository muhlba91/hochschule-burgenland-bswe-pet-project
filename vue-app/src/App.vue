<script setup lang="ts">
import { newMultiplier } from './utils/multiplier'
import { newDividend } from './utils/dividend'
import HelloWorld from './components/HelloWorld.vue'
import TheWelcome from './components/TheWelcome.vue'

const { multiplier, increment } = newMultiplier()
const { dividend, decrement } = newDividend()
</script>

<template>
  <header>
    <img alt="Vue logo" class="logo" src="./assets/logo.svg" width="125" height="125" />

    <div class="wrapper">
      <HelloWorld msg="You did it!" :multiplicand="5" :multiplier="multiplier" :divisor="5" :dividend="dividend" />
      <button id="increment" @click="increment">Increase multiplier.</button>
      <button id="decrement" @click="decrement">Decrease dividend.</button>
    </div>
  </header>

  <main>
    <TheWelcome />
  </main>
</template>

<style scoped>
header {
  line-height: 1.5;
}

.logo {
  display: block;
  margin: 0 auto 2rem;
}

@media (min-width: 1024px) {
  header {
    display: flex;
    place-items: center;
    padding-right: calc(var(--section-gap) / 2);
  }

  .logo {
    margin: 0 2rem 0 0;
  }

  header .wrapper {
    display: flex;
    place-items: flex-start;
    flex-wrap: wrap;
  }
}
</style>
