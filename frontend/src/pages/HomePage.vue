<template>
  <div>
    <NavBar />
    <div class='col-md-8 mx-auto'>
      <h1>Home</h1>
      <p>Welcome to the home page</p>
    </div>
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