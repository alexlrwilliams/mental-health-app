<template>
    <div class="container">
        <div class="video">
            <video id="localVideo" autoplay></video>
            <video id="remoteVideo" autoplay></video>
        </div>
        <div class="buttons">
            <b-button class="join-video-btn" variant='success' @click="join">Join</b-button>
            <b-button class="leave-video-btn" variant='danger' @click="leave">Leave</b-button>
        </div>

        <VueWebRTC
            ref="webrtc"
            :auto="false"
            :local-media-options="{ video: true, audio: true }"
            :remote-media-options="{ video: true, audio: true }"
            :ice-servers="[{ urls: 'stun:stun.l.google.com:19302' }]"
            @local-media-stream="onLocalMediaStream"
            @remote-media-stream="onRemoteMediaStream"
        />

    </div>
</template>

<script>
//import VueWebRTC from 'vue-webrtc'
export default {
        methods: {
        join() {
            this.$refs.webrtc.join()
        },
        leave() {
            this.$refs.webrtc.leave()
        },
        onLocalMediaStream(stream) {
            const localVideo = document.getElementById('localVideo')
            localVideo.srcObject = stream
        },
        onRemoteMediaStream(stream) {
            const remoteVideo = document.getElementById('remoteVideo')
            remoteVideo.srcObject = stream
        }
      }
}
</script>

<style>
.container {
  display: flex;
  align-items: center;
  flex-flow: column;
}
.buttons {
  position: absolute;
  bottom:   0;
  margin-bottom: 50px;
}
.join-video-btn{

  margin-right: 5px;
  width: 150px;
}
.leave-video-btn {

  margin-left: 5px;
  width: 150px;
}
</style>
