<template>
  <Layout class="content-layout" v-if="this.contentName!==''">
    <div class="content-layout_header">
      <div class="content-layout_header_title">
        <Icon type="ios-film-outline" size="16"></Icon>
        <span class="content-layout_header_title_text">{{this.contentName}}</span>
      </div>
      <!--放大按钮，放大界面还没做-->
      <!--<button v-if="this.type==='homePage'" class="header-button enlarge" @click="toEnlargeBoard" ><img src="@/common/images/enlarge.png" width="30px" height="30px"/></button>-->
      <div class="shake-rotate shake-constant shake-constant--hover" style="float: right;width: 40px;height: 40px" v-if="this.type==='boardsSetting'">
        <button class="header-button delete" @click="this.deleteBoard"><img src="@/common/images/NOTEPAD _ DELETE-2.png" width="25px" height="25px"/></button>
      </div>
      <div v-if="priority==='1'&&this.type==='homePage'" class="board-config">
        <SentryConfig v-if="contentName==='Sentry'" :boardName="contentName"></SentryConfig>
        <GitLabConfig v-if="contentName==='CI Testing'" :boardName="contentName"></GitLabConfig>
        <GitLabConfig v-if="contentName==='Merge Request'" :boardName="contentName"></GitLabConfig>
        <SonarConfig v-if="contentName==='Sonar'" :boardName="contentName"></SonarConfig>
        <TrelloConfig v-if="contentName==='Trello'" :boardName="contentName"></TrelloConfig>
        <FlurryConfig v-if="contentName==='Event Logs'" :boardName="contentName"></FlurryConfig>
        <FlurryConfig v-if="contentName==='Geography DashBoard'" :boardName="contentName"></FlurryConfig>
        <FlurryConfig v-if="contentName==='Technical DashBoard'" :boardName="contentName"></FlurryConfig>
      </div>
    </div>
    <Content class="content-layout_content" id="board">
      <slot name="showBoard"></slot>
    </Content>
  </Layout>
  <div v-else-if="this.contentName===''"></div>
</template>
<script>
import {mapState} from 'vuex'
import SentryConfig from '@/components/SentryConfig'
import GitLabConfig from '@/components/GitLabConfig'
import SonarConfig from '@/components/SonarConfig'
import TrelloConfig from '@/components/TrelloConfig'
import FlurryConfig from '@/components/FlurryConfig'
export default {
  name: 'BaseContent',
  components: {
    SentryConfig,
    GitLabConfig,
    SonarConfig,
    TrelloConfig,
    FlurryConfig
  },
  props: {
    contentName: {
      type: String
    },
    type: {
      type: String
    }
  },
  computed: {
    ...mapState({
      priority: state => state.priority
    })
  },
  methods: {
    deleteBoard() {
      if (this.contentName) {
        this.$eventBus.$emit('deleteBoard', this.contentName)
      }
    }
    // toEnlargeBoard() {
    //   switch (this.contentName) {
    //     case 'CI Testing': {
    //       // this.$router.push({
    //       //   name: 'ContentShow'
    //       // })
    //       break
    //     }
    //     case 'Sentry': {
    //       // this.$router.push({
    //       //   name: 'ContentShow'
    //       // })
    //       break
    //     }
    //     case 'Merge Request': {
    //       // this.$router.push({
    //       //   name: 'ContentShow'
    //       // })
    //       break
    //     }
    //     case 'Trello': {
    //       // this.$router.push({
    //       //   name: 'ContentShow'
    //       // })
    //       break
    //     }
    //     case 'Sonar': {
    //       // this.$router.push({
    //       //   name: 'ContentShow'
    //       // })
    //       break
    //     }
    //     case 'Event Logs': {
    //       this.$router.push({
    //         name: 'EnlargeFlurryBoard'
    //       })
    //       break
    //     }
    //   }
    // }
  }
}
</script>

<style scoped lang="scss">
  .content-layout {
    &_header{
      background-color: #fff;
      border: 1px solid #cccccc;
      height: 50px;
      &_title{
        position: relative;
        top: 13px;
        left: 20px;
        display: inline-block;
        &_text{
          font-weight: bold;
          font-size: 16px
        }
      }
    }
    &_content{
      height: 100%;
      min-height: 350px;
      background-color: #fff;
      border: 1px solid #cccccc
    }
  }

  .header-button {
    float: right;
    background: none;
    height: 30px;
    outline:none;
    border: none;
    position: relative;
    cursor: pointer;
    top: 4px;
    right: 10px;
  }

  .board-config{
    float: right;
    height: 40px;
    line-height: 40px;
    cursor: pointer;
    position: relative;
    right: 20px;
  }
</style>
