<template>
  <div id="app">
	<MainHeader></MainHeader>
    <router-view></router-view>
  </div>
</template>

<script>
  import MainHeader from './components/header';
  
  
  export default {
    name: 'app',
	components: {
		MainHeader,
	},
  }
</script>

<style>
	#app {
		width: 80%;
		margin:0 auto;
	}
</style>
