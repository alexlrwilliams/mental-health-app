<template>
  <div class="container">
    <!-- Leave Button -->
    <b-button class="leave-video-btn" variant="danger" @click="leave" to="/">
      Leave meeting
    </b-button>
    <!-- Video call screen -->
    <div class="video-list">
      <video v-for="item in videoList" :key="item.id" autoplay playsinline ref="videos" :muted="item.muted" :id="item.id" class="video">
        <p>User has left </p>
      </video>
    </div>
  </div>
</template>

<script>
    import { defineComponent } from 'vue';
    import { io } from "socket.io-client";
    const SimpleSignalClient = require('simple-signal-client');
    export default /*#__PURE__*/defineComponent({
        name: 'vue-webrtc',
        components: {
        },
        data() {
            return {
                signalClient: null,
                videoList: [],
                canvas: null,
                socket: null,
                socketURL: 'https://weston-vue-webrtc-lobby.azurewebsites.net'
            };
        },
        props: {
            roomId: {
                type: String,
                default: 'public-room-v2'
            },
            peerOptions: {
                type: Object,  // NOTE: use these options: https://github.com/feross/simple-peer
                default() {
                    return {};
                }
            },
            ioOptions: {
                type: Object,  // NOTE: use these options: https://socket.io/docs/v4/client-options/
                default() {
                    return { rejectUnauthorized: false, transports: ['polling', 'websocket'] };
                }
            },
            deviceId: {
                type: String,
                default: null
            }
        },
        mounted() {
          this.join();
        },
        beforeDestroy() {
          this.leave();
        },
        methods: {
            async join() {
                var that = this;
                this.socket = io(this.socketURL, this.ioOptions);
                this.signalClient = new SimpleSignalClient(this.socket);
                let constraints = {
                    video: true,
                    audio: true
                };
                if (that.deviceId) {
                    constraints.video = { deviceId: { exact: that.deviceId } };
                }
                const localStream = await navigator.mediaDevices.getUserMedia(constraints);
                this.joinedRoom(localStream, true);
                this.signalClient.once('discover', (discoveryData) => {
                    async function connectToPeer(peerID) {
                        if (peerID == that.socket.id) return;
                        try {
                            const { peer } = await that.signalClient.connect(peerID, that.roomId, that.peerOptions);
                            that.videoList.forEach(v => {
                                if (v.isLocal) {
                                    that.onPeer(peer, v.stream);
                                }
                            })
                        } catch (e) {
                          console.log(e);
                        }
                    }
                    discoveryData.peers.forEach((peerID) => connectToPeer(peerID));
                    that.$emit('opened-room', that.roomId);
                });
                this.signalClient.on('request', async (request) => {
                    const { peer } = await request.accept({}, that.peerOptions)
                    that.videoList.forEach(v => {
                        if (v.isLocal) {
                            that.onPeer(peer, v.stream);
                        }
                    })
                })
                this.signalClient.discover(that.roomId);
            },
            onPeer(peer, localStream) {
                var that = this;
                peer.addStream(localStream);
                peer.on('stream', (remoteStream) => {
                    that.joinedRoom(remoteStream, false);
                    peer.on('close', () => {
                        var newList = [];
                        that.videoList.forEach(function (item) {
                            if (item.id !== remoteStream.id) {
                                newList.push(item);
                            }
                        });
                        that.videoList = newList;
                        that.$emit('left-room', remoteStream.id);
                    });
                    peer.on('error', (err) => {
                        console.log(err);
                    });
                });
            },
            joinedRoom(stream, isLocal) {
                var that = this;
                let found = that.videoList.find(video => {
                    return video.id === stream.id
                })
                if (found === undefined) {
                    let video = {
                        id: stream.id,
                        muted: isLocal,
                        stream: stream,
                        isLocal: isLocal
                    };
                    that.videoList.push(video);
                }
                setTimeout(function () {
                    for (var i = 0, len = that.$refs.videos.length; i < len; i++) {
                        if (that.$refs.videos[i].id === stream.id) {
                            that.$refs.videos[i].srcObject = stream;
                            break;
                        }
                    }
                }, 500);
                that.$emit('joined-room', stream.id);
            },
            leave() {
                this.videoList.forEach(v => v.stream.getTracks().forEach(t => t.stop()));
                this.videoList = [];
                this.signalClient.peers().forEach(peer => peer.removeAllListeners())
                this.signalClient.destroy();
                this.signalClient = null;
                this.socket.destroy();
                this.socket = null;
            },
            }
        }
    );
</script>

<style scoped>
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
    margin-top: 30px;
    margin-bottom: 10px;
    width: 350px;
}
.video-list {
    background: rgb(79, 78, 78);
    height: auto;
    display: flex;
    flex-direction: row;
    justify-content: space-evenly;
    flex-wrap: wrap;
    border: 1px solid black;
    padding: 5px;
    border-radius: 10px;
    width: 100%;

}

.video {
    background: #c5c4c4;
    border: 1px solid black;
    border-radius: 15px;
    width: 30vw;
}

@media (max-width:960px) {
  .video {
    width: 100%
  }
}
</style>
