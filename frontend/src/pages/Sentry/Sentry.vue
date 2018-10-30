<template>
  <div>
    <div ref="carousel" id="carousel" v-if="isShowSentry">
      <agile v-if="sentryCarouselItem.length!==0&&sentryRefreshState===true"
             :arrows="true"
             :speed="750"
             :dots="true"
             :timing="'ease'"
             :fade="false"
             :autoplay="false"
             :pauseOnHover="true"
             :pauseOnDotsHover="true"
      >
        <div v-for="(issue,index) in sentryCarouselItem"
             :key="index" class="sentry-carousel">
          <div class="sentry-content">
            <Row v-for="(issueRow,index) in issue"
                 :key="index">
              <Col :span="SentryWidth" v-for="(issueCol,index) in issueRow" :key="index">
              <SentryCard :issue="issueCol"></SentryCard>
              </Col>
            </Row>
          </div>
        </div>
      </agile>
    </div>
    <div style="width: 95%;max-height: 100%;margin: 10px auto;background-color: yellow" v-else-if="!isShowSentry">
        <Col :span="SentryWidth" v-for="(issueCol,index) in SentrySliceNum" :key="index">
        <Card class="cardstyle" :padding="9" style="margin: 5px">
          <div>
            <div style="float: right;width: 20px;height: 20px"></div>
            <div style="padding-left: 20px">
              <div class="anim" style="width: 95%;height: 15px;"></div>
              <div class="anim" style="width: 70%;height: 15px;margin-top:12px "></div>
            </div>
          </div>
        </Card>
        </Col>
    </div>
  </div>
</template>

<script>
import SentryCard from '@/pages/Sentry/SentryCard'
import { mapState, mapGetters, mapActions } from 'vuex'

export default {
  name: 'Exception',
  components: {
    SentryCard
  },
  data() {
    return {
      isShowSentry: false
    }
  },
  computed: {
    ...mapState('Sentry', {
      sentryRefreshState: state => state.sentryRefreshState
    }),
    ...mapState({
      SentrySliceNum: state => state.SentrySliceNum,
      SentryWidth: state => state.SentryWidth,
      clientWidth: state => state.clientWidth
    }),
    ...mapGetters('Sentry', {
      sentryCarouselItem: 'sentryCarouselItem'
    })
  },
  created() {
    if (this.sentryRefreshState === false) {
      let that = this
      this.$nextTick(function () {
        that.setSentryRefreshState(true)
      })
    }
  },
  mounted() {
    this.$eventBus.$on('showSentry', this.showSentry)
  },
  destroyed() {
    this.$eventBus.$off('showSentry', this.showSentry)
  },
  methods: {
    ...mapActions('Sentry', [
      'setSentryRefreshState'
    ]),
    showSentry() {
      this.isShowSentry = true
      console.log('触发状态变更')
    }
  },
  watch: {
    clientWidth: function () {
      this.$store.dispatch('initAllSlice')
    },
    sentryRefreshState: function () {
      if (this.sentryRefreshState === false) {
        let that = this
        this.$nextTick(function () {
          that.setSentryRefreshState(true)
        })
      }
    }
  }
}
</script>

<style scoped lang="scss">
  .sentry-content {
    height: 100%;
  }

  @keyframes placeHolderShimmer {
    0% {
      background-position: -468px 0
    }
    100% {
      background-position: 468px 0
    }
  }

  .anim {
    animation-duration: 1s;
    animation-fill-mode: forwards;
    animation-iteration-count: infinite;
    animation-name: placeHolderShimmer;
    animation-timing-function: linear;
    background: #f6f7f8;
    background: linear-gradient(to right, #eeeeee 8%, #dddddd 18%, #eeeeee 33%);
    background-size: 800px 104px;
    height: 40px;
    position: relative;
  }
</style>
