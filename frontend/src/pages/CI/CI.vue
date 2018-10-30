<template>
  <div>
    <div class="ci-content" ref="CIContent" id="CIContent" v-if="isShowCI">
      <Row
        v-for="(CICardRow,index) in CICardItem"
        :key="index">
        <Col :span="CICardWidth" v-for="(CICardCol,index) in CICardRow" :key="index">
        <CICard :CICardCol="CICardCol"/>
        </Col>
      </Row>
    </div>
    <div v-else-if="!isShowCI">
      <div class="CI-loading">
        <Row v-for="(CICardRow,index) in CISliceNum" :key="index">
          <Col :span="CICardWidth" v-for="(CICardCol,index) in 3" :key="index">
          <Card class="loading-card" :padding="8">
            <div span="11" class="anim" style="width: 90%;height: 15px;"></div>
            <div span="11" class="anim" style="width: 35%;height: 15px;margin-top:15px "></div>
          </Card>
          </Col>
        </Row>
      </div>
    </div>
  </div>
</template>
<script>
import CICard from '@/pages/CI/CICard'
import { mapGetters, mapState, mapActions } from 'vuex'

export default {
  name: 'CI',
  components: {
    CICard
  },
  data() {
    return {
      isShowCI: true
    }
  },
  computed: {
    ...mapState({
      CICardWidth: state => state.CICardWidth,
      CISliceNum: state => state.CISliceNum,
      clientWidth: state => state.clientWidth
    }),
    ...mapGetters('Git', {
      CICardItem: 'CICardData'
    })
  },
  watch: {
    clientWidth: function () {
      this.$store.dispatch('initAllSlice')
    }
  },
  created() {
    this.$eventBus.$on('showCI', this.showCI)
  },
  methods: {
    ...mapActions('Git', [
      'dealCICard'
    ]),
    showCI() {
      this.isShowCI = true
    }
  },
  destroyed() {
    this.$eventBus.$off('showCI', this.showCI)
  },
  mounted() {
    setTimeout(() => {
      if (this.isShowCI) {
        var content = document.getElementById('CIContent')
        var board = document.getElementById('board')
        content.style.height = board.offsetHeight - 20 + 'px'
      }
    }, 100)
  }
}
</script>

<style scoped>
  .ci-content {
    height: 100%;
    margin: 10px;
    overflow-y: scroll;
    -webkit-overflow-scrolling: touch;
    overflow: auto;
  }

  .CI-loading {
    width: 95%;
    height: 320px;
    margin: 10px auto;
    overflow: auto;
  }

  .loading-card {
    background-color: #fff;
    margin: 5px;
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
