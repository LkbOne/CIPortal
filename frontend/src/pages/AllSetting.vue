<template>
  <div style="width: 950px;height: 400px;margin: auto;position:relative;top: 250px;">
    <Card style="width: 700px;height: 400px;display: inline-block">
      <Tabs value="name1">
        <TabPane label="Git Config" name="name1">
          <div class="git-config-content">
            <Form ref="gitLabForm" :label-width="100" :rules="gitLabRules" :model="gitLabForm">
              <FormItem label="IP" prop="gitIP">
                <Input v-model="gitLabForm.gitIP" placeholder="Enter IP" clearable/>
              </FormItem>
              <FormItem label="privateToken" prop="gitPrivateToken">
                <Input v-model="gitLabForm.gitPrivateToken" placeholder="Enter PrivateToken"
                       clearable/>
              </FormItem>
              <Button class="validate-keys" type="primary" shape="circle" icon="ios-search" size="small"
                      @click="validate_keys_new('gitLab')"></Button>
            </Form>
          </div>
        </TabPane>
        <TabPane label="Trello Config" name="name2">
          <div class="trello-config-content">
            <Form :label-width="100" ref="trelloForm" :rules="trelloRules" :model="trelloForm">
              <FormItem label="Token" prop="trelloPrivateToken">
                <Input class="trello-input" v-model="trelloForm.trelloPrivateToken" placeholder="Enter Token"
                       clearable/>
              </FormItem>
              <FormItem label="AppKey" prop="trelloAppKey">
                <Input class="trello-input" v-model="trelloForm.trelloAppKey" placeholder="Enter AppKey" clearable/>
              </FormItem>
              <Button class="validate-keys" type="primary" shape="circle" icon="ios-search" size="small"
                      @click="validate_keys_new('trello')"></Button>
            </Form>
          </div>
        </TabPane>
        <TabPane label="Sentry Config" name="name3">
          <div class="sentry-config-content">
            <Form :label-width="100" ref="sentryForm" :model="sentryForm">
              <FormItem label="Token">
                <Input class="sentry-input" v-model="sentryForm.sentryPrivateToken" placeholder="Enter Token"
                       clearable/>
              </FormItem>
              <Button class="validate-keys" type="primary" shape="circle" icon="ios-search" size="small"
                      @click="validate_keys_new('sentry')"></Button>
            </Form>
          </div>
        </TabPane>
        <TabPane label="Sonar Config" name="name4">
          <div class="sonar-config-content">
            <Form :label-width="100" ref="sonarForm" :model="sonarForm">
              <FormItem label="Host">
                <Input class="sonar-input" v-model="sonarForm.sonarHost" placeholder="Enter Token..." clearable/>
              </FormItem>
              <Button class="validate-keys" type="primary" shape="circle" icon="ios-search" size="small"
                      @click="validate_keys_new('sonar')"></Button>
            </Form>
          </div>
        </TabPane>
        <TabPane label="Flurry Config" name="name5">
          <div class="flurry-config-content">
            <Form :label-width="100" ref="flurryForm" :model="flurryForm">
              <FormItem label="privateToken" class="git-form-item">
                <Input class="flurry-input" v-model="flurryForm.flurryPrivateToken" placeholder="Enter PrivateToken"
                       clearable/>
              </FormItem>
              <Button class="validate-keys" type="primary" shape="circle" icon="ios-search" size="small"
                      @click="validate_keys_new('flurry')"></Button>
            </Form>
          </div>
        </TabPane>
      </Tabs>
      <div style="position: absolute;bottom: 50px;right: 50px;display: inline-block">
        <Button type="text" size="large" @click="cancel">取消</Button>
        <Button type="primary" size="large" @click="handleSubmit" :disabled="!buttonshow">确定</Button>
      </div>
    </Card>
    <Card style="width:250px;height: 400px;display: inline-block;float: right">
      <span>Git Config</span><span style="float: right">{{this.gitValidateStatus}}</span><br/>
      <span>Trello Config</span><span style="float: right">{{this.trelloValidateStatus}}</span><br/>
      <span>Sentry Config</span><span style="float: right">{{this.sentryValidateStatus}}</span><br/>
      <span>Sonar Config</span><span style="float: right">{{this.sonarValidateStatus}}</span><br/>
      <span>Flurry Config</span><span style="float: right">{{this.flurryValidateStatus}}</span><br/>
    </Card>
    <Modal
      v-model="chooseBoard"
      title="Please choose boards"
      @on-ok="ok"
      @on-cancel="cancel">
      <div slot="footer">
        <Button style="margin-left: 8px" @click="cancel">Cancel</Button>
        <Button type="primary" @click="ok">Submit</Button>
      </div>
      <Form :model="boardChoiceForm" :label-width="80" ref="boardChoiceForm" :rules="boardChoiceRules">
        <FormItem prop="boardName">
          <CheckboxGroup v-model="boardChoiceForm.boardName">
            <Checkbox :label="board" v-for="board in boardChoice" :key="board">
              <span>{{board}}</span>
            </Checkbox>
          </CheckboxGroup>
        </FormItem>
        <!--<FormItem>-->
          <!--<Button type="primary" @click="ok">Submit</Button>-->
          <!--<Button style="margin-left: 8px" @click="cancel">Cancel</Button>-->
        <!--</FormItem>-->
      </Form>
    </Modal>
  </div>
</template>
<script>
import flurry from '@/service/flurry'
import gitlab from '@/service/gitlab'
import sentry from '@/service/sentry'
import sonal from '@/service/sonal'
import storys from '@/service/storys'
import user from '@/service/user'
export default {
  data() {
    return {
      chooseBoard: false,
      boardChoice: [],
      gitFlag: '',
      trelloFlag: '',
      sentryFlag: '',
      sonarFlag: '',
      flurryFlag: '',
      loading: 0,
      gitLabForm: {
        name: '',
        gitIP: '10.222.48.47',
        gitPrivateToken: 'bSSzMHFP7fB5gpGUM27w'
      },
      trelloForm: {
        name: '',
        trelloPrivateToken: 'a4f99d894674645f9debb528cbd0ae86bf88d5119eb67e125d48904e2b369983',
        trelloAppKey: '2f5cba1d75d5e6e2303d17e39f5a9ea6'
      },
      sentryForm: {
        name: '',
        sentryPrivateToken: 'd49f8f5f4b274144a664d2ab120e76bf40a9e604a37341bba9b78be431cbd9bf'
      },
      sonarForm: {
        name: '',
        sonarHost: '9000'
      },
      flurryForm: {
        name: '',
        flurryPrivateToken: 'eyJhbGciOiJIUzI1NiIsImtpZCI6ImZsdXJyeS56dXVsLnByb2Qua2V5c3RvcmUua2V5LjIifQ.eyJpc3MiOiJodHRwczovL3p1dWwuZmx1cnJ5LmNvbTo0NDMvdG9rZW4iLCJpYXQiOjE1MzY4MjI1NDgsImV4cCI6MzMwOTM3MzEzNDgsInN1YiI6IjQyODA4NCIsImF1ZCI6IjQiLCJ0eXBlIjo0LCJqdGkiOiI2NTUzIn0.gRcCgdhXZPApIgZrKLF1tary_jlpyG_CP5qBPanFOlA'
      },
      boardChoiceForm: {
        boardName: []
      },
      gitLabRules: {
        gitIP: [
          {required: true, message: 'The IP cannot be empty', trigger: 'blur'}
        ],
        gitPrivateToken: [
          {required: true, message: 'The Token cannot be empty', trigger: 'blur'}
        ]
      },
      trelloRules: {
        trelloPrivateToken: [
          {required: true, message: 'The Token cannot be empty', trigger: 'blur'}
        ],
        trelloAppKey: [
          {required: true, message: 'The AppKey cannot be empty', trigger: 'blur'}
        ]
      },
      boardChoiceRules:{
        boardName: [
          { required: true, type: 'array', max: 4, message: 'Choose Four board at best', trigger: 'change' }
        ],
      }
    }
  },
  computed: {
    buttonshow: function () {
      return this.gitFlag === 'SUCCESS' && this.trelloFlag === 'SUCCESS' && this.loading === 0
      // return this.gitFlag === 'SUCCESS' && this.loading === 0
    },
    gitValidateStatus: function () {
      if (this.gitLabForm.gitIP === '' && this.gitLabForm.gitPrivateToken === '') {
        return '未填写'
      } else if (this.gitFlag === 'LOADING') {
        return 'LOADING'
      } else if (this.gitFlag === 'ERROR IP') {
        return 'ERROR IP'
      } else if (this.gitFlag === 'ERROR TOKRN') {
        return 'ERROR TOKEN'
      } else if (this.gitFlag === 'SUCCESS') {
        return 'SUCCESS'
      } else if (this.gitFlag === 'TIME OUT') {
        return 'TIME OUT'
      }
    },
    trelloValidateStatus: function () {
      if (this.trelloForm.trelloPrivateToken === '' && this.trelloForm.trelloAppKey === '') {
        return '未填写'
      } else if (this.trelloFlag === 'LOADING') {
        return 'LOADING'
      } else if (this.trelloFlag === 'ERROR TOKEN') {
        return 'ERROR TOKEN'
      } else if (this.trelloFlag === 'ERROR KEY') {
        return 'ERROR KEY'
      } else if (this.trelloFlag === 'SUCCESS') {
        return 'SUCCESS'
      } else if (this.trelloFlag === 'TIME OUT') {
        return 'TIME OUT'
      }
    },
    sentryValidateStatus: function () {
      if (this.sentryForm.sentryPrivateToken === '') {
        return '未填写'
      } else if (this.sentryFlag === 'LOADING') {
        return 'LOADING'
      } else if (this.sentryFlag === 'ERROR TOKEN') {
        return 'ERROR TOKEN'
      } else if (this.sentryFlag === 'SUCCESS') {
        return 'SUCCESS'
      } else if (this.sentryFlag === 'TIME OUT') {
        return 'TIME OUT'
      }
    },
    sonarValidateStatus: function () {
      if (this.sonarForm.sonarHost === '') {
        return '未填写'
      } else if (this.sonarFlag === 'LOADING') {
        return 'LOADING'
      } else if (this.sonarFlag === 'ERROR HOST') {
        return 'ERROR HOST'
      } else if (this.sonarFlag === 'SUCCESS') {
        return 'SUCCESS'
      } else if (this.sonarFlag === 'TIME OUT') {
        return 'TIME OUT'
      }
    },
    flurryValidateStatus: function () {
      if (this.flurryForm.flurryPrivateToken === '') {
        return '未填写'
      } else if (this.flurryFlag === 'LOADING') {
        return 'LOADING'
      } else if (this.flurryFlag === 'ERROR TOKEN') {
        return 'ERROR TOKEN'
      } else if (this.flurryFlag === 'SUCCESS') {
        return 'SUCCESS'
      } else if (this.flurryFlag === 'TIME OUT') {
        return 'TIME OUT'
      }
    }
  },
  methods: {
    cancel() {
      this.chooseBoard = false
    },
    async ok() {
        this.$refs.boardChoiceForm.validate((valid) => {
          if (valid) {
            this.turn2HomePage()
          }else{
          }
        })
      // let receiveData = await user.sendBoardChoice(this.boardChoice)
      // if (receiveData.status === 200) {
      //   // this.$store.dispatch('setBoardsSetting', receiveData.projects)
      //   console.log('projects:', receiveData.projects)
      //   this.$router.push({
      //     name: 'ContentShow'
      //   })
      // }
    },
    async turn2HomePage(){
      let receiveData = await user.sendBoardChoice(this.boardChoiceForm.boardName)
      if (receiveData.status === 200) {
        // this.$store.dispatch('setBoardsSetting', receiveData.projects)
        console.log('projects:', receiveData.projects)
        this.$router.push({
          name: 'ContentShow'
        })
      }
    },
    async validate_keys_new(name) {
      let receiveData
      switch (name) {
        case 'gitLab':
          this.gitFlag = 'LOADING'
          this.loading++
          receiveData = await gitlab.initGitlabProject({
            name: this.gitLabForm.name,
            ip: this.gitLabForm.gitIP,
            privateToken: this.gitLabForm.gitPrivateToken
          })
          this.loading--
          console.log('receiveData:', receiveData)
          switch (receiveData.status) {
            case 'HOSTORIP':
              this.gitFlag = 'ERROR IP'
              break
            case 'TOKEN':
              this.gitFlag = 'ERROR TOKRN'
              break
            case 'fail':
              this.gitFlag = 'TIME OUT'
              break
            case 'success':
              this.gitFlag = 'SUCCESS'
              break
          }
          break
        case 'trello':
          this.loading++
          this.trelloFlag = 'LOADING'
          receiveData = await storys.initTrelloProject({
            name: this.trelloForm.name,
            privateToken: this.trelloForm.trelloPrivateToken,
            appKey: this.trelloForm.trelloAppKey
          })
          this.loading--
          console.log('receiveData:', receiveData)
          switch (receiveData.status) {
            case 'TOKEN':
              this.trelloFlag = 'ERROR TOKEN'
              break
            case 'KEY':
              this.trelloFlag = 'ERROR KEY'
              break
            case 'fail':
              this.trelloFlag = 'TIME OUT'
              break
            case 'success':
              this.trelloFlag = 'SUCCESS'
              break
          }
          break
        case 'sentry':
          this.loading++
          this.sentryFlag = 'LOADING'
          receiveData = await sentry.initSentryProject({
            name: this.sentryForm.name,
            privateToken: this.sentryForm.sentryPrivateToken
          })
          this.loading--
          console.log('receiveData:', receiveData)
          switch (receiveData.status) {
            case 'TOKEN':
              this.sentryFlag = 'ERROR TOKEN'
              break
            case 'fail':
              this.sentryFlag = 'TIME OUT'
              break
            case 'success':
              this.sentryFlag = 'SUCCESS'
              break
          }
          break
        case 'sonar':
          this.loading++
          this.sonarFlag = 'LOADING'
          receiveData = await sonal.initSonarProject({
            name: this.sonarForm.name,
            host: this.sonarForm.sonarHost
          })
          this.loading--
          console.log('receiveData:', receiveData)
          switch (receiveData.status) {
            case 'HOSTORIP':
              this.sonarFlag = 'ERROR HOST'
              break
            case 'fail':
              this.sonarFlag = 'TIME OUT'
              break
            case 'success':
              this.sonarFlag = 'SUCCESS'
              break
          }
          break
        case 'flurry':
          this.loading = false
          this.flurryFlag = 'LOADING'
          receiveData = await flurry.initFlurryProject({
            name: this.flurryForm.name,
            privateToken: this.flurryForm.flurryPrivateToken
          })
          this.loading--
          console.log('receiveData:', receiveData)
          switch (receiveData.status) {
            case 'TOKEN':
              this.flurryFlag = 'ERROR TOKEN'
              break
            case 'fail':
              this.flurryFlag = 'TIME OUT'
              break
            case 'success':
              this.flurryFlag = 'SUCCESS'
              break
          }
          break
      }
    },
    // async handleSubmit() {
    //   let that=this
    //   this.$refs.gitLabForm.validate((valid) => {
    //     if (valid) {
    //       this.$refs.trelloForm.validate((valid) => {
    //         if (valid) {
    //           console.log('????')
    //         }else{
    //           console.log('wuwuwu')
    //         }
    //       })
    //     }else{
    //       console.log('wuwuwu')
    //     }
    //   })
    // },
    async handleSubmit() {
      let receiveData = await user.selectBoard()
      console.log('receiveData', receiveData)
      if (receiveData.status === 200) {
        this.boardChoice = []
        for (let i = 0; i < receiveData.projects.length; i++) {
          for (let j = 0; j < receiveData.projects[i].boardName.length; j++) {
            this.boardChoice.push(receiveData.projects[i].boardName[j])
          }
        }
        this.chooseBoard = true
      }
      console.log('this.boardChoiceform:', this.boardChoiceForm.boardName)
    },
    test() {
      return true
    }
  }
}
</script>
<style scoped>
  .git-config-content, .trello-config-content, .sentry-config-content, .sonar-config-content, .flurry-config-content {
    width: 400px;
    margin: 20px auto 0;
  }

  .validate-keys {
    float: right;
  }
</style>
