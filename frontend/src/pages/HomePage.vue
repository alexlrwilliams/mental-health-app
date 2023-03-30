<template>
  <div>
    <NavBar />

    <div class='col-md-8 mx-auto'>

      <div class="center">
        <b-button class="book-appointment-btn" variant='primary' href='/bookAppointementForm' ><h4>Book an appointment</h4></b-button>
      </div>

      <div class="appointment-box">
          <b-card class="appointment-box-upcoming">
                <b-card-header>
                  <h4>Upcoming appointments <span class="upcoming-dot"></span></h4>
                </b-card-header>
                <b-card-body>

                </b-card-body>
        </b-card>
      </div>

        <b-card class="appointment-box-previous">
                <b-card-header>
                  <h4>Previous appointments <span class="previous-dot"></span></h4>
                </b-card-header>
                <b-card-body>
                  
                </b-card-body>
        </b-card>

      
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

<style>
.center {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 20vh; /* set the height of the container to 100% of viewport height */
  border-radius: 10px;
}

.book-appointment-btn {
  width: 80%;
  height: 30%;
  background-color: #0a60b6;
  color: white;
  padding: 10px 90px;
  border-radius: 10px;
  font-size: 1.2rem;
  cursor: pointer;
}
  .appointment-box {
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .appointment-box-upcoming{
    width: calc(100% - 10px);
    margin: 0 auto;
    margin-bottom: 15px;
  }
  .appointment-box-previous{
    width: calc(100% - 10px);
    margin: 0 auto;
    margin-top: 15px
  }
  .upcoming-dot {
  height: 13px;
  width: 13px;
  background-color: green;
  border-radius: 50%;
  display: inline-block;
  }
  .previous-dot {
  height: 13px;
  width: 13px;
  background-color: rgb(192, 15, 15);
  border-radius: 50%;
  display: inline-block;
  }
</style>