<template>
  <div>
    <Card :padding="5" style="margin: 10px;" v-for="(todoCards,index) in projectCard"
          :key="index">
      <a :href="todoCards.cardUrl" target="_blank">
        <div class="story-content" @mousedown="down()" @mouseup="up()" @touchstart="down()" @touchEnd="up()">
          <div class="story-content_img">
            <div class="story-content_img_todo" v-show="todoCards.state==='todo'">Task</div>
            <div class="story-content_img_bug" v-show="todoCards.state==='bug'">BUG</div>
          </div>
          <div class="story-content_content">
            <span class="story-content_content_text text-bold">{{todoCards.cardName}}</span><br/>
            <span class="story-content_content_text" v-show="todoCards.state==='todo'">Owner:<span
              v-for="(members,index) in todoCards.members"
              :key="index">{{members}} </span></span>
          </div>
          <div class="story-content_progress" v-if="clientWidth/2>170">
            <Progress :percent=todoCards.percent v-if="clientWidth/2>360"></Progress>
            <span v-else-if="clientWidth/2<500" style="float: right;padding-right:35px ">{{todoCards.percent}}%</span>
          </div>
        </div>
      </a>
    </Card>
  </div>
</template>
<script>
import { mapState } from 'vuex'

export default {
  props: {
    projectCard: {
      type: Array
    }
  },
  data() {
    return {
      timeout: 0
    }
  },
  computed: {
    ...mapState({
      clientWidth: state => state.clientWidth
    })
  },
  methods: {
    down() {
      clearInterval(this.timeout)
      this.timeout = setTimeout(function () {
        alert('鼠标长按事件')
      }, 2000)
    },
    up() {
      clearTimeout(this.timeout)
    }
  }
}
</script>
<style scoped lang="scss">
  .story-content {
    /*min-width: 340px;*/
    &_img {
      vertical-align: middle;
      display: inline-block;
      &_todo {
        width: 40px;
        height: 40px;
        background-color: #16d0ca;
        border-radius: 5px;
        color: #fff;
        text-align: center;
        line-height: 40px;
      }
      &_bug {
        width: 40px;
        height: 40px;
        background-color: #ee3f16;
        border-radius: 5px;
        color: #fff;
        text-align: center;
        line-height: 40px
      }
    }
    &_content {
      padding-left: 10px;
      vertical-align: middle;
      width: 60%;
      display: inline-block;
      &_text {
        text-overflow: ellipsis;
        overflow: hidden;
        white-space: nowrap;
        width: 85%;
        display: inline-block;
        line-height: 15px;
        color: #495060;
      }
    }
    .text-bold {
      font-weight: bold;
    }
    &_progress {
      vertical-align: middle;
      width: 23%;
      display: inline-block;
      color: #495060;
    }
  }
</style>
