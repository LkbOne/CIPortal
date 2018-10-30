<template>
  <div @click="init()">
    <span><Icon type="ios-analytics">sonal</Icon> Sonar Config</span>
    <Modal
      v-model="sonarConfigModal"
      title="sonarConfigModal"
      @on-ok="ok"
      :mask-closable="false"
      :loading="true"
      @on-cancel="cancel">
      <div slot="footer">
        <Button type="text" size="large" @click="cancel">取消</Button>
        <Button type="primary" size="large" @click="ok" :loading="loading"
                :disabled="!changedSetting.chosenProjects.length||this.changedSetting.allProjects===undefined">
          确定
        </Button>
      </div>
      <!--做一个配置页面-->
      <div class="sonar-config-content" v-if="this.authority===0">
        <Form :label-width="80">
          <FormItem label="Host">
            <Input class="sonar-input" v-model="changedSetting.host" placeholder="Enter Token..." clearable/>
          </FormItem>
          <FormItem label="Interval">
            <Input class="sonar-input" v-model="changedSetting.interval" placeholder="Enter Interval..."
                   clearable/>
          </FormItem>
        </Form>
        <Button class="validate-keys" type="primary" shape="circle" icon="ios-search" size="small"
                @click="validate_keys_new"></Button>
      </div>

      <div id="forSonarConfig">
        <div>
          <CheckboxGroup v-model="changedSetting.chosenProjects">
            <Checkbox :label="project.id" v-for="project in changedSetting.allProjects" :key="project.id"
                      class="sonar-checkbox">
              <span>{{project.projectName}}</span>
            </Checkbox>
          </CheckboxGroup>
        </div>
        <!--<div style="text-align: center" v-if="this.authority===0">-->
        <!--<mapping style="display: inline-block" :trelloProjects="allTrelloProjectsTemp" :sentryProjects="[]"-->
        <!--:sonarProjects="allSonarProjectsTemp" v-if="this.flag===true"></mapping>-->
        <!--</div>-->
      </div>
    </Modal>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import sonal from '@/service/sonal'
import mapping from '@/components/DealMapping'

export default {
  components: {mapping},
  props: {
    boardName: {
      type: String
    }
  },
  data() {
    return {
      allTrelloProjectsTemp: [],
      allSonarProjectsTemp: [],
      sonarConfigModal: false,
      // choicesSonal: [],
      // allSonarProjectData: [],
      loading: false,
      sliceNum: 0,
      changedSetting: {
        name: '',
        host: '',
        chosenProjects: [],
        allProjects: [],
        interval: '',
        sonarTrelloMapping: {}
      },
      flag: false
    }
  },
  computed: {
    ...mapState('Sonar', {
      // sonarHost: state => state.sonarHost,
      chosenSonarProjects: state => state.sonar.chosenProjects,
      allSonarProjects: state => state.sonar.allProjects,
      // sonarInterval: state => state.sonarInterval,
      sonar: state => state.sonar
    }),
    ...mapState('Trello', {
      chosenTrelloProjects: state => state.trello.chosenProjects,
      allTrelloProjects: state => state.trello.allProjects
    }),
    ...mapState({
      account: state => state.account,
      // sonarTrelloMapping: state => state.sonarTrelloMapping,
      clientWidth: state => state.clientWidth,
      authority: state => state.authority
    })
  },
  created() {
    this.setSetting()
  },
  methods: {
    ...mapActions('Sonar', [
      'saveSonarSetting',
      'setSonarData'
    ]),
    async ok() {
      this.loading = true
      this.changedSetting.sonarTrelloMapping = this.sonarTrelloMapping
      let receiveData = await sonal.changeSonarSetting(this.account, this.changedSetting)
      this.setSonarData(receiveData.projects)
      switch (this.boardName) {
        case 'Sonar':
          await this.saveSonarSetting(this.changedSetting)
          break
      }
      this.sonarConfigModal = false
      this.loading = false
      this.flag = false
    },
    cancel() {
      this.loading = false
      this.sonarConfigModal = false
      this.flag = false
    },
    async validate_keys_new() {
      let receiveData = await sonal.getProjectIdAndName({
        name: this.changedSetting.name,
        host: this.changedSetting.host
      })
      console.log('sonarReceiveData:', receiveData)
      this.changedSetting.allProjects = receiveData.projects.boardData
      // error handle
      // switch (receiveData.status) {
      //   case 'success':
      //     break
      //   case 'hostOrIp':
      //     alert('Host 错误')
      //     break
      // }
    },
    async setSetting() {
      switch (this.boardName) {
        case 'Sonar':
          this.changedSetting.interval = this.sonar.interval
          this.changedSetting.host = this.sonar.host
          this.changedSetting.name = this.sonar.name
          this.changedSetting.chosenProjects = this.sonar.chosenProjects
          this.changedSetting.allProjects = this.sonar.allProjects
          this.changedSetting.sonarTrelloMapping = this.sonar.sonarTrelloMapping
          break
      }
      this.changedSetting.chosenTrelloProjects = this.chosenTrelloProjects
      this.changedSetting.allTrelloProjects = this.allTrelloProjects
      for (let i = 0; i < this.allTrelloProjects.length; i++) {
        this.allTrelloProjectsTemp.push(this.allTrelloProjects[i])
      }
      for (let i = 0; i < this.allSonarProjects.length; i++) {
        this.allSonarProjectsTemp.push(this.allSonarProjects[i])
      }
    },
    init() {
      this.flag = true
      this.sonarConfigModal = true
      this.allTrelloProjectsTemp = []
      this.allSonarProjectsTemp = []
      for (let j = 0; j < this.chosenTrelloProjects.length; j++) {
        let index = this.allTrelloProjects.findIndex(list => list.id === this.chosenTrelloProjects[j])
        if (index !== -1) {
          this.allTrelloProjectsTemp.push(this.allTrelloProjects[index])
        }
      }
      for (let j = 0; j < this.chosenSonarProjects.length; j++) {
        let index = this.allSonarProjects.findIndex(list => list.id === this.chosenSonarProjects[j])
        if (index !== -1) {
          this.allSonarProjectsTemp.push(this.allSonarProjects[index])
        }
      }
    },
    chosenChanged() {
      console.log(this.chosenTrelloProjects.length)
      this.allSonarProjectsTemp = []
      this.allTrelloProjectsTemp = []
      for (let j = 0; j < this.changedSetting.chosenSonarProjects.length; j++) {
        let index = this.allSonarProjects.findIndex(list => list.id === this.changedSetting.chosenSonarProjects[j])
        if (index !== -1) {
          this.allSonarProjectsTemp.push(this.allSonarProjects[index])
        }
      }
      for (let j = 0; j < this.chosenTrelloProjects.length; j++) {
        let index = this.allTrelloProjects.findIndex(list => list.id === this.chosenTrelloProjects[j])
        if (index !== -1) {
          this.allTrelloProjectsTemp.push(this.allTrelloProjects[index])
        }
      }
    }
  }
}
</script>

<style scoped>
  #forSonarConfig {
    overflow: auto;
    height: 500px
  }

  .sonar-config-content {
    display: inline-block;
    position: relative;
    transform: translateX(40%);
  }

  .sonar-input {
    width: 190px;

  }

  .validate-keys {
    float: right;
  }

  .sonar-checkbox {
    display: block;
    line-height: 30px;
    font-size: 14px;
    padding-left: 35%;
  }
</style>
