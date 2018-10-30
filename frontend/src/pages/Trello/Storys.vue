<template>
  <div>
    <div class="trello-content" v-if="isShowTrello">
      <agile v-if="trelloRefreshState===true&&storysAllCard.length!==0&&storysAllCard.length!==undefined"
             :arrows="true"
             :speed="750"
             :autoplaySpeed="300000"
             :dots="true"
             :timing="'ease-in-out'"
             :fade="false"
             :autoplay="true"
             :pauseOnHover="false">
        <div v-for="(project,index) in storysAllCard"
             :key="index">
          <div style="width: 90%;margin: auto;"
               v-if="project.doneCardsNum!==0||project.testCardsNum!==0||project.todoCardsNum!==0||project.todoCards.length!==0">
            <Row :gutter="12">
              <Col span="11" v-if="clientWidth>600">
              <div style="text-align: center;">
                <div style="margin-top: 50px;height: 50px;">
                  <sonal :sonar="project.sonarData" v-if="clientWidth>750"></sonal>
                </div>
                <AnalsisiPie :project="project" class="AnalsisiPie"
                             :projectName="project.projectName"
                             v-if="(project.doneCardsNum!==0||project.testCardsNum!==0||project.todoCardsNum!==0)&&trelloRefreshState">
                </AnalsisiPie>
              </div>
              </Col>
              <Col :span="TrelloWidth" v-if="project.todoCards">
              <span style="font-weight: bold;display: block;margin: 20px 10px 0">TODO</span>
              <div style="overflow: auto;height: 250px;">
                <StoryCard :projectCard="project.todoCards"></StoryCard>
              </div>
              </Col>
            </Row>
            <Row>
              <span style="font-weight: bold;text-align:center;display: block;margin-top: 20px;margin-bottom: 5px">{{project.projectName}}</span>
            </Row>
          </div>
          <div style="width: 95%;margin: auto;min-height: 320px;"
               v-else-if="project.doneCardsNum===0&&project.testCardsNum===0&&project.todoCardsNum===0&&project.todoCards.length===0">
            <span style="font-weight: bold;text-align:center;display: block;position: relative;top: 150px;">{{project.name}}</span>
            <span
              style="display: block;text-align: center;font-size: 24px;position: relative;top: 150px;">nothing to do</span>
          </div>
        </div>
      </agile>
    </div>
    <div style="width: 95%;margin: auto;" v-else-if="!isShowTrello">
      <Row :gutter="12">
        <Col span="11" v-if="clientWidth>600">
        <div style="text-align: center;">
          <div class="anim" style="width: 80%;height: 60px;margin-top: 45px;margin: 45px auto"></div>
          <div class="anim" style="width: 50%;height: 200px;margin-top:15px;margin: -30px auto"></div>
        </div>
        </Col>
        <Col :span="TrelloWidth" style="">
        <Card :padding="5" style="margin: 10px;" v-for="(todoCards,index) in 4"
              :key="index">
          <div>
            <div id="card_img" style="vertical-align: middle;display: inline-block;">
              <div class="anim" style="width: 50px;height: 50px;margin-right: 5px"></div>
            </div>
            <div id="card_content" style="width: 55%;vertical-align: middle;display: inline-block;">
              <div class="anim" style="width: 100%;height: 15px;"></div>
              <div class="anim" style="width: 50%;height: 15px;margin-top:15px "></div>
            </div>
            <div id="card_progress" style="width: 15%;float: right;vertical-align: middle;display: inline-block;">
              <div class="anim" style="width: 100%;height: 15px;margin-top:15px;"></div>
            </div>
          </div>
        </Card>
        </Col>
      </Row>
    </div>
  </div>

</template>

<script>

import StoryCard from '@/pages/Trello/StoryCard'
import AnalsisiPie from '@/pages/Trello/AnalsisiPie'
import { mapState, mapActions } from 'vuex'
import Row from 'iview/src/components/grid/row'
import Sonal from '@/pages/Trello/Sonal'
import Sonar from '@/pages/Sonar/Sonar'

export default {
  name: 'Story',
  components: {
    Row,
    StoryCard,
    AnalsisiPie,
    Sonal,
    Sonar
  },
  data() {
    return {
      isShowTrello: true,
      timeout: 0
    }
  },
  computed: {
    ...mapState('Trello', {
      storysAllCard: state => state.storysAllCard,
      trelloRefreshState: state => state.trelloRefreshState
    }),
    ...mapState({
      TrelloWidth: state => state.TrelloWidth,
      clientWidth: state => state.clientWidth
    })
  },
  methods: {
    ...mapActions('Trello', [
      'setTrelloRefreshState'
    ]),
    TrelloRefreshState() {
      if (this.trelloRefreshState === false) {
        let that = this
        this.$nextTick(() => {
          that.setTrelloRefreshState(true)
          console.log('console true')
        })
      }
    },
    showTrello() {
      this.isShowTrello = true
    }
  },
  watch: {
    clientWidth: function () {
      this.$store.dispatch('initAllSlice')
    },
    trelloRefreshState: function () {
      this.TrelloRefreshState()
    }
  },
  created() {
    this.TrelloRefreshState()
  },
  mounted() {
    this.$eventBus.$on('showTrello', this.showTrello)
  },
  destroyed() {
    this.$eventBus.$off('showTrello', this.showTrello)
  }
}
</script>

<style scoped>
  .trello-content {
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
