<template>
  <div @click="flurryConfigModal = true">
    <span><Icon type="ios-navigate"/> FlurryConfig</span>
    <Modal
      v-model="flurryConfigModal"
      title="flurryConfigModal"
      @on-ok="ok"
      :mask-closable="false"
      :loading="true"
      @on-cancel="cancel">
      <div slot="footer">
        <Button type="text" size="large" @click="cancel">取消</Button>
        <Button type="primary" size="large" @click="ok" :loading="loading"
                :disabled="!changedSetting.chosenProjects.length||this.changedSetting.allProjects===undefined">确定
        </Button>
      </div>
      <!--做一个配置页面-->
      <div class="flurry-config-content" v-if="this.authority===0">
        <Form :label-width="80">
          <FormItem label="privateToken" class="git-form-item">
            <Input class="flurry-input" v-model="changedSetting.privateToken" placeholder="Enter PrivateToken"
                   clearable/>
          </FormItem>
          <FormItem label="Interval" class="git-form-item">
            <Input class="flurry-input" v-model="changedSetting.interval" placeholder="Enter Interval" clearable/>
          </FormItem>
        </Form>
        <Button class="validate-keys" type="primary" shape="circle" icon="ios-search" size="small"
                @click="validate_keys_new"></Button>
      </div>
      <Tabs style="margin-top: 20px">
        <TabPane :label="project.platform" :name="project.platform" icon="logo-apple"
                 v-for="(project,index) in changedSetting.allProjects" :key="index">
          <div style="margin: auto;width: 350px;height: 350px;overflow: auto">
            <CheckboxGroup v-model="changedSetting.chosenProjects">
              <Checkbox :label="app.id" v-for="app in project.apps" :key="app.id"
                        class="flurry-checkbox">
                <span>{{app.projectName}}</span>
              </Checkbox>
            </CheckboxGroup>
          </div>
        </TabPane>
      </Tabs>
    </Modal>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import flurry from '@/service/flurry'

export default {
  name: 'flurry-config',
  props: {
    boardName: {
      type: String
    }
  },
  components: {},
  data() {
    return {
      flurryConfigModal: false,
      loading: false,
      changedSetting: {
        name: '',
        privateToken: '',
        interval: 0,
        chosenProjects: [],
        allProjects: []
      }
    }
  },
  computed: {
    ...mapState('Flurry', {
      // flurryInterval: state => state.flurryInterval,
      // flurryPrivateToken: state => state.flurryPrivateToken,
      // chosenFlurryProjects: state => state.chosenFlurryProjects,
      // allFlurryProjects: state => state.allFlurryProjects,
      eventLogs: state => state.eventLogs,
      geographyDashBoard: state => state.geographyDashBoard,
      technicalDashBoard: state => state.technicalDashBoard
    }),
    ...mapState({
      account: state => state.account,
      authority: state => state.authority
    })
  },
  created() {
    this.setSetting()
  },
  methods: {
    ...mapActions('Flurry', [
      'saveEventLogsSetting',
      'saveGeographyDashBoardSetting',
      'saveTechnicalDashBoardSetting',
      'setFlurryData',
      'setLineData',
      'setBarData'
    ]),
    async ok() {
      this.loading = true
      let receiveData = await flurry.saveFlurrySetting(this.account, this.changedSetting)
      console.log('flurry receiveData:', receiveData)
      if (receiveData.status === 200) {
        switch (this.boardName) {
          case 'Event Logs':
            console.log('Event Logs')
            await this.saveEventLogsSetting(this.changedSetting)
            this.setFlurryData(receiveData.projects)
            break
          case 'Geography DashBoard':
            console.log('Geography DashBoard')
            await this.saveGeographyDashBoardSetting(this.changedSetting)
            this.setLineData(receiveData.projects)
            break
          case 'Technical DashBoard':
            console.log('Technical DashBoard')
            await this.saveTechnicalDashBoardSetting(this.changedSetting)
            this.setBarData(receiveData.projects)
            break
        }
      }
      this.flurryConfigModal = false
      this.loading = false
    },
    cancel() {
      this.flurryConfigModal = false
      this.loading = false
    },
    async validate_keys_new() {
      let receiveData = await flurry.getFlurryProject({
        name: this.changedSetting.name,
        privateToken: this.changedSetting.privateToken
      })
      this.changedSetting.allProjects = receiveData.projects.boardData
      console.log(this.changedSetting.allProjects)
      // error handle
      // switch (receiveData.status) {
      //   case 'success':
      //     break
      //   case 'hostOrIp':
      //     alert('IP 错误')
      //     break
      //   case 'gitToken':
      //     alert('Token 错误')
      //     break
      // }
    },
    async setSetting() {
      switch (this.boardName) {
        case 'Event Logs':
          this.changedSetting.interval = this.eventLogs.interval
          this.changedSetting.privateToken = this.eventLogs.privateToken
          this.changedSetting.name = this.eventLogs.name
          this.changedSetting.chosenProjects = this.eventLogs.chosenProjects
          this.changedSetting.allProjects = this.eventLogs.allProjects
          break
        case 'Geography DashBoard':
          this.changedSetting.interval = this.geographyDashBoard.interval
          this.changedSetting.privateToken = this.geographyDashBoard.privateToken
          this.changedSetting.name = this.geographyDashBoard.name
          this.changedSetting.chosenProjects = this.geographyDashBoard.chosenProjects
          this.changedSetting.allProjects = this.geographyDashBoard.allProjects
          break
        case 'Technical DashBoard':
          this.changedSetting.interval = this.technicalDashBoard.interval
          this.changedSetting.privateToken = this.technicalDashBoard.privateToken
          this.changedSetting.name = this.technicalDashBoard.name
          this.changedSetting.chosenProjects = this.technicalDashBoard.chosenProjects
          this.changedSetting.allProjects = this.technicalDashBoard.allProjects
          break
      }
    }
  }
}
</script>
<style scoped>
  .flurry-config-content {
    display: inline-block;
    position: relative;
    transform: translateX(40%);
  }

  .flurry-input {
    width: 190px;

  }

  .validate-keys {
    float: right;
  }

  .flurry-checkbox {
    display: block;
    line-height: 30px;
    font-size: 14px;
    padding-left: 15%;
  }
</style>
