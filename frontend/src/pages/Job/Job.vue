<template>
  <div>
    <div v-if="isShowMergeRequest">
      <agile v-if="gitRefreshState===true&&this.JobCarouseItem.length!==0&&this.JobCarouseItem.length!==undefined"
             :arrows="true"
             :speed="750"
             :dots="true"
             :timing="'ease-in-out'"
             :fade="false"
             :autoplay="false"
             :pauseOnHover="false"
      >
        <div v-for="(Merge,index) in JobCarouseItem"
             :key="index" class="job-carousel">
          <div class="job-content">
            <Row v-for="(merge,index) in Merge"
                 :key="index">
              <Col :xs="24" :sm="24" :md="24" :lg="24">
              <JobCard :merge="merge"></JobCard>
              </Col>
            </Row>
          </div>
        </div>
      </agile>
    </div>
    <div v-else-if="!isShowMergeRequest" style="width: 95%;margin: 10px auto;height: 300px">
      <Row v-for="(merge,index) in 4"
           :key="index">
        <Card :padding="9" style="margin: 5px">
          <div>
            <div class="anim" style="float: right;position: absolute;top: 15px;right: 12px;width: 30px;height: 30px;"></div>
            <div style="padding-left: 20px">
              <div class="anim" style="width: 30%;height: 15px;"></div>
              <div class="anim" style="width: 70%;height: 15px;margin-top:15px "></div>
            </div>
          </div>
        </Card>
      </Row>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapState, mapActions } from 'vuex'
import JobCard from '@/pages/Job/JobCard'
export default {
  name: 'Design',
  data() {
    return {
      isShowMergeRequest: true
    }
  },
  methods: {
    ...mapActions('Git', [
      'setGitRefreshState'
    ]),
    GitRefreshState() {
      if (this.gitRefreshState === false) {
        let that = this
        this.$nextTick(function () {
          that.setGitRefreshState(true)
        })
      }
    },
    showMergeRequest() {
      this.isShowMergeRequest = true
    }
  },
  components: {
    JobCard
  },
  computed: {
    ...mapState('Git', {
      gitRefreshState: state => state.gitRefreshState
    }),
    ...mapGetters('Git', {
      JobCarouseItem: 'JobCarouseItem'
    })
  },
  watch: {
    gitRefreshState: function () {
      this.GitRefreshState()
    }
  },
  created() {
    this.GitRefreshState()
  },
  destroyed() {
    this.$eventBus.$off('showMergeRequest', this.showMergeRequest)
  },
  mounted() {
    this.$eventBus.$on('showMergeRequest', this.showMergeRequest)
  }
}
</script>

<style scoped>
  .job-content {
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
