<style scoped lang="scss">
  @import '../common/scss/login.scss';
</style>
<template>
  <div style="height: 100%">
    <div class="login" @keydown.enter="handleSubmit">
      <div class="login-con">
        <Card :bordered="false">
          <p slot="title">
            <Icon type="log-in"></Icon>
            <span>欢迎登录</span>
            <span style="width:60px;text-align:center;cursor: pointer;float: right;font-weight: normal;color: #b9b9b9;" @click="back">back</span>
          </p>
          <div class="form-con">
            <Form ref="loginForm" :model="form" :rules="rules">
              <FormItem prop="userName">
                <Input v-model="form.account" placeholder="请输入用户名">
                <span slot="prepend">
                <Icon :size="16" type="person"></Icon>
              </span>
                </Input>
              </FormItem>
              <FormItem prop="password">
                <Input type="password" v-model="form.password" placeholder="请输入密码">
                <span slot="prepend">
                <Icon :size="14" type="locked"></Icon>
              </span>
                </Input>
              </FormItem>
              <FormItem>
                <Button @click="handleSubmit" type="primary" long>登录</Button>
              </FormItem>
            </Form>
            <!--<p class="login-tip">输入任意用户名和密码即可</p>-->
          </div>
        </Card>
      </div>
    </div>
  </div>
</template>

<script>
import user from '@/service/user'
import Header from '@/components/Header'
import md5 from 'js-md5'
import { mapActions } from 'vuex'
export default {
  name: 'register-pages',
  components: {
    Header
  },
  data() {
    return {
      form: {
        account: '',
        password: ''
      },
      postForm: {
        account: '',
        password: ''
      },
      user: {},
      rules: {
        account: [
          {required: true, message: '账号不能为空', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '密码不能为空', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    ...mapActions('Sonar', [
      'saveSonarSetting'
    ]),
    ...mapActions('Git', [
      'saveCITestingSetting',
      'saveMergeRequestSetting'
    ]),
    ...mapActions('Sentry', [
      'saveSentrySetting'
    ]),
    ...mapActions('Trello', [
      'saveTrelloSetting'
    ]),
    ...mapActions('Flurry', [
      'saveEventLogsSetting',
      'saveGeographyDashBoardSetting',
      'saveTechnicalDashBoardSetting'
    ]),
    handleSubmit() {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          this.userLogin()
        }
      })
    },
    async userLogin() {
      this.postForm.account = this.form.account
      // this.postForm.password = md5(this.form.password)
      this.postForm.password = this.form.password
      let receiveData = await user.login(this.postForm)
      // receiveAllData有什么用？
      // let receiveAllData = await user.getAllData(this.form.account)
      this.user = receiveData.user
      console.log('receiveData:',receiveData)
      if (receiveData.status === 'success') {

        // if (receiveData.user.boardSetting === undefined) {
        if (receiveData.user.boardSetting.length === 0||(receiveData.user.boardSetting.length !== 0&&receiveData.user.boardChoice.length === 0)) {
          this.$router.push({
            name: 'AllSetting'
          })
        } else {
          this.$store.dispatch('setAuthority', receiveData.user.authority)
          this.$store.dispatch('setAllBoards', receiveData.user.boardMenu)
          let userSetting = receiveData.user.boardSetting
          for (let i = 0; i < userSetting.length; i++) {
            switch (userSetting[i].name) {
              case 'CI Testing':
                this.saveCITestingSetting(userSetting[i])
                break
              case 'Trello':
                this.saveTrelloSetting(userSetting[i])
                break
              case 'Sentry':
                this.saveSentrySetting(userSetting[i])
                await this.$store.dispatch('setTrelloAndSentry', userSetting[i].sentryTrelloMapping)
                await this.$store.dispatch('setGitLabAndSentry', userSetting[i].sentryGitLabMapping)
                break
              case 'Merge Request':
                this.saveMergeRequestSetting(userSetting[i])
                break
              case 'Sonar':
                this.saveSonarSetting(userSetting[i])
                console.log('sonarSetting1.:', userSetting[i].chosenProjects)
                break
              case 'Event Logs':
                this.saveEventLogsSetting(userSetting[i])
                break
              case 'Geography DashBoard':
                this.saveGeographyDashBoardSetting(userSetting[i])
                break
              case 'Technical DashBoard':
                this.saveTechnicalDashBoardSetting(userSetting[i])
                break
            }
          }
          await this.$store.dispatch('setAccount', this.form.account)
          await this.$store.dispatch('setPriority', '1')
          // await this.$store.dispatch('setUser', this.user)
          // this.saveGitSetting(this.user)
          // this.saveSentrySetting(this.user)
          // this.saveTrelloSetting(this.user)
          // this.saveSonarSetting(this.user)
          // this.saveFlurrySetting(this.user)

          // this.saveFlurrySetting(this.user)
          // alert('登录成功')

          // if(!window.localStorage){
          //   alert("浏览器不支持localstorage");
          //   return false;
          // }else{
          //   var storage=window.localStorage;
          //   storage.setItem("userName",this.form.account);
          //   storage.setItem("password",this.form.password);
          // }
          this.$router.push({
            name: 'ContentShow'
          })
        }
      } else {
        this.$Message.success('登录失败')
      }
    },
    back(){
      this.$router.push({
        name: 'ContentShow'
      })
    }
  }
}
</script>
