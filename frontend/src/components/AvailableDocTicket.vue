<template>
    <b-form-radio-group>
        <div class="row">
            <b-form-radio class="btn appointment-type-radios__radio-item" ><h4>Dr. Ahmed Henine </h4> <p>Royal Hospital (Guildford)</p></b-form-radio>
            <b-form-radio class="btn appointment-type-radios__radio-item" ><h4>Dr. Alex Williams </h4> <p>Kingstong Hospital (London)</p></b-form-radio>
        </div>
    </b-form-radio-group>
</template>

<style scoped>
.available-doc-ticket {
    width: 47%;

}
.card {
  margin-right: 20px;
  margin-bottom: 20px;
}
  .appointment-type-radios {
    display: flex;
    justify-content: center;
  }
  .appointment-type-radios__radio-item {
    width: 47%;

    display: flex !important;
    justify-content: center;
    align-items: center;
    border: 1px solid black;
    display: inline-block;
    padding: 5px;
    margin-right: 20px;
    margin-bottom: 20px;
  }
</style>