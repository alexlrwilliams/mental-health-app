<template>
  <div>
    <div class='col-md-8 mx-auto center'>
      <b-button class="book-appointment-btn" variant='success' to='/book-appointment' ><h4>Book an appointment</h4></b-button>
      <b-card class="appointment-box">
              <b-card-header>
                <h4>Upcoming appointments <span class="upcoming-dot"></span></h4>
              </b-card-header>
              <b-card-body>
                <UpcomingTicket />
              </b-card-body>
      </b-card>

      <b-card class="appointment-box">
              <b-card-header>
                <h4>Previous appointments <span class="previous-dot"></span></h4>
              </b-card-header>
              <b-card-body>
                <PreviousTicket />
              </b-card-body>
      </b-card>

      
    </div>
  </div>
</template>

<script>
import UpcomingTicket from '../components/UpcomingTicket.vue';
import PreviousTicket from '../components/PreviousTicket.vue';
export default {
  name: 'HomePage', 
  components: {
    UpcomingTicket,
    PreviousTicket
  },
}
</script>

<style>
  .center {
    display: flex;
    align-items: center;
    flex-flow: column;
    margin-top: 5%;
    gap: 40px;
  }
  .book-appointment-btn {
    width: 80%;
  }
  .appointment-box {
    width: calc(100% - 10px);
    margin: 0 auto;
    margin-bottom: 15px;
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