<template>
  <div>
    <NavBar />
    <h1>Home</h1>
    <p>Welcome to the home page</p>
  </div>
</template>

<script>
import NavBar from '../components/NavBar' 
export default {
  name: 'HomePage',
  components: {
    NavBar 
  }
}
</script>