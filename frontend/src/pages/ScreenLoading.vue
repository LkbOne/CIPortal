<template>
  <div>
    <Layout>
      <headers></headers>
      <Content :style="{margin: '88px 20px 0', background: '#fff', minHeight: '500px'}">
        <Row>
          <Col style="" :xs="24" :sm="24" :md="12" :lg="12">
          <div class="frametest" ref="carousel" id="carousel">
            <Layout>
              <Header class="screen-loading-header">
                <div class="title">
                  <Icon type="ios-film-outline" size="16"></Icon>
                  <span style="font-weight: bold;font-size: 16px">CI Testing</span>
                </div>
                <button class="enlarge-button"><img src="../common/images/enlarge.png" width="30px" height="30px"/>
                </button>
              </Header>
              <Content :style="{background: '#fff', minHeight: '350px'}"
                       style="background-color: #fff;border: 1px solid #cccccc">
                <div style="width: 95%;height: 320px;margin: 10px auto;overflow: auto;">
                  <Row v-for="(cards,index) in ciSlice" :key="index">
                    <Col :span="ciCardWidth" v-for="(cards,index) in 3" :key="index">
                    <Card style="background-color: #fff;margin: 5px" :padding="8">
                      <div span="11" class="anim" style="width: 90%;height: 15px;"></div>
                      <div span="11" class="anim" style="width: 35%;height: 15px;margin-top:15px "></div>
                    </Card>
                    </Col>
                  </Row>
                </div>
              </Content>
            </Layout>
          </div>
          </Col>
          <Col style="" :xs="24" :sm="24" :md="12" :lg="12">
          <div class="frametest">
            <Layout>
              <Header style="background-color: #fff;border: 1px solid #cccccc;height: 50px">
                <div class="title">
                  <Icon type="ios-film-outline" size="16"></Icon>
                  <span style="font-weight: bold;font-size: 16px">Trello</span>
                </div>

                <button class="enlarge-button"><img src="../common/images/enlarge.png" width="30px" height="30px"/>
                </button>
              </Header>
              <Content :style="{background: '#fff', minHeight: '350px'}"
                       style="background-color: #fff;border: 1px solid #cccccc">
                <div class="demo-carousel">
                  <div style="width: 95%;margin: auto;">
                    <Row :gutter="12">
                      <Col span="11" v-if="clientWidth>785">
                      <div style="text-align: center;">
                        <div class="anim" style="width: 80%;height: 60px;margin-top: 45px;margin: 45px auto"></div>
                        <div class="anim" style="width: 50%;height: 200px;margin-top:15px;margin: -30px auto"></div>
                      </div>
                      </Col>
                      <Col :span="trelloWidth" style="">
                      <span style="font-weight: bold;display: block;margin: 10px">TODO</span>
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
                          <div id="card_progress"
                               style="width: 15%;float: right;vertical-align: middle;display: inline-block;">
                            <div class="anim" style="width: 100%;height: 15px;margin-top:15px;"></div>
                          </div>
                        </div>
                      </Card>
                      </Col>
                    </Row>
                  </div>
                </div>
              </Content>
            </Layout>
          </div>
          </Col>
        </Row>
        <Row>
          <Col style="" :xs="24" :sm="24" :md="12" :lg="12">
          <div class="frametest">
            <Layout>
              <Header style="background-color: #fff;border: 1px solid #cccccc;height: 50px">
                <div class="title">
                  <Icon type="ios-film-outline" size="16"></Icon>
                  <span style="font-weight: bold;font-size: 16px">Sentry</span>
                </div>

                <button class="enlarge-button"><img src="../common/images/enlarge.png" width="30px" height="30px"/>
                </button>
              </Header>
              <Content :style="{background: '#fff', minHeight: '430px'}"
                       style="background-color: #fff;border: 1px solid #cccccc">
                <div class="demo-carousel">
                  <div style="width: 95%;margin: 10px auto;">
                    <Row v-for="(issueRow,index) in 4"
                         :key="index">
                      <Col :span="sentryWidth" v-for="(issueCol,index) in sentrySlice" :key="index">
                      <Card class="cardstyle" :padding="9" style="margin: 5px">
                        <div>
                          <div style="float: right;width: 20px;height: 20px"></div>
                          <div style="padding-left: 20px">
                            <div class="anim" style="width: 95%;height: 15px;"></div>
                            <div class="anim" style="width: 70%;height: 15px;margin-top:12px "></div>
                            <div class="anim" style="width: 50%;height: 15px;margin-top:12px "></div>
                          </div>
                        </div>
                      </Card>
                      </Col>
                    </Row>
                  </div>
                </div>
              </Content>
            </Layout>
          </div>
          </Col>
          <Col style="" :xs="24" :sm="24" :md="12" :lg="12">
          <div class="frametest">
            <Layout>
              <Header style="background-color: #fff;border: 1px solid #cccccc;height: 50px">
                <div class="title">
                  <Icon type="ios-film-outline" size="16"></Icon>
                  <span style="font-weight: bold;font-size: 16px">Merge Resquest</span>
                </div>

                <button class="enlarge-button"><img src="../common/images/enlarge.png" width="30px" height="30px"/>
                </button>
              </Header>
              <Content :style="{background: '#fff', minHeight: '430px'}"
                       style="background-color: #fff;border: 1px solid #cccccc">
                <div style="width: 95%;margin: 10px auto;height: 300px">
                  <Row v-for="(merge,index) in 5"
                       :key="index">
                    <Col :xs="24" :sm="24" :md="24" :lg="24">
                    <Card :padding="9" style="margin: 5px">
                      <div>
                        <div class="anim"
                             style="float: right;position: absolute;top: 15px;right: 12px;width: 30px;height: 30px;"></div>
                        <div style="padding-left: 20px">
                          <div class="anim" style="width: 30%;height: 15px;"></div>
                          <div class="anim" style="width: 70%;height: 15px;margin-top:15px "></div>
                        </div>
                      </div>
                    </Card>
                    </Col>
                  </Row>
                </div>
              </Content>
            </Layout>
          </div>
          </Col>
        </Row>
      </Content>
    </Layout>
  </div>
</template>
<script>
import headers from '@/components/Header.vue'

export default {
  name: 'screen-loading',
  components: {headers},
  data() {
    return {
      ciSlice: 0,
      ciCardWidth: 0,
      trelloSlice: 0,
      trelloWidth: 0,
      sentrySlice: 0,
      sentryWidth: 0,
      clientWidth: window.innerWidth / 2
    }
  },
  // computed: {
  //   ...mapGetters({
  //     clientWidth: 'clientWidth'
  //   })
  // },
  methods: {
    dealAllSlice() {
      if (this.clientWidth < 550 && this.clientWidth > 400) {
        this.ciSlice = 2
        this.ciCardWidth = 12
      } else if (this.clientWidth < 400) {
        this.ciSlice = 1
        this.ciCardWidth = 24
      } else {
        this.ciSlice = 3
        this.ciCardWidth = 8
      }

      if (this.clientWidth < 785) {
        this.trelloWidth = 24
      } else {
        this.trelloWidth = 13
      }

      if (this.clientWidth < 550) {
        this.sentrySlice = 1
        this.sentryWidth = 24
      } else {
        this.sentrySlice = 2
        this.sentryWidth = 12
      }
    }
  },
  created() {
    this.dealAllSlice()
  },
  watch: {
    clientWidth: function () {
      this.dealAllSlice()
    }
  },
  mounted() {
    const that = this
    onresize = () => {
      return (() => {
        this.clientWidth = that.$refs.carousel.offsetWidth
      })()
    }
  }
}
</script>

<style scoped>
  .frametest {
    margin: 10px;
  }

  .title {
    position: relative;
    top: -9px;
    left: -20px;
    display: inline-block;
  }

  .enlarge-button {
    float: right;
    background: none;
    height: 30px;
    /* background-color: red; */
    outline: none;
    border: none;
    position: relative;
    cursor: pointer;
    right: -40px;
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

  .screen-loading-header {
    background-color: #fff;
    border: 1px solid #cccccc;
    height: 50px
  }
</style>
