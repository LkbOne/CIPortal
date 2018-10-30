<template>
  <div id="app">
    <router-view />
    <div id="new-bug" v-on:click="setDisable()">NEW BUG!</div>
  </div>
</template>

<script>
// 记得把css文件删掉
import user from '@/service/user'
import sentry from '@/service/sentry'
export default {
  name: 'App',
  data() {
    return {
      account: 'admin010'
    }
  },
  methods: {
    async setDisable() {
      document.getElementById('new-bug').style.visibility = 'hidden'
      let testReceive=await sentry.deleteBug()
      console.log('kjahdskjfhakffifi:',testReceive)
    },
    async getAllData() {
      await user.getAllData(this.account)
    }
  },
  created() {
    // this.getAllData()
  }
}
</script>

<style>
  .leader-line {
    z-index: 9999
  }

  html, body {
    width: 100%;
    height: 100%;
    background: #f0f0f0;
  }

  .layout, .ivu-layout {
    min-height: 100%
  }

  #app {
    width: 100%;
    height: 100%;
  }

  #new-bug {
    position: fixed;
    top: 0px;
    left: 0;
    right: 0;
    margin-left: auto;
    margin-right: auto;
    width: 100%;
    height: 64px;
    background-color: red;
    animation: ss 1s infinite linear;
    color: white;
    font-size: 35px;
    text-align: center;
    line-height: 64px;
    z-index: 100;
    visibility: hidden;
  }

  @keyframes ss {
    0% {
      background-color: #c9302c;
    }
    25% {
      background-color: blue;
    }
    50% {
      background-color: yellow;
    }
    75% {
      background-color: green;
    }
    100% {
      background-color: red;
    }
  }
</style>
