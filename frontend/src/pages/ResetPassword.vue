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
            <span>ResetPassword</span>
            <span style="width:60px;text-align:center;cursor: pointer;float: right;font-weight: normal;color: #b9b9b9;"
                  @click="back">back</span>
          </p>
          <div class="form-con">
            <Form ref="resetPasswordForm" :model="form" :rules="rules">
              <FormItem prop="userName">
                <Input v-model="form.account" placeholder="Account">
                <span slot="prepend">
                <Icon :size="16" type="person"></Icon>
              </span>
                </Input>
              </FormItem>
              <FormItem prop="password">
                <Input type="password" v-model="form.password" placeholder="Password">
                <span slot="prepend">
                <Icon :size="14" type="locked"></Icon>
              </span>
                </Input>
              </FormItem>
              <FormItem prop="password">
                <Input type="password" v-model="form.confirmPassword" placeholder="Confirm Password">
                <span slot="prepend">
                <Icon :size="14" type="locked"></Icon>
              </span>
                </Input>
              </FormItem>
              <FormItem>
                <Button @click="handleSubmit" type="primary" long>Confirm</Button>
              </FormItem>
            </Form>
          </div>
        </Card>
      </div>
    </div>
    <Modal v-model="modal2" :class-name="'vertical-center-modal'" :closable="false">
      <p style="font-size: 20px;text-align: center">Reset Your Password?</p>
      <div slot="footer">
        <Button type="text" size="large" @click="cancel" style="width: 45%;">Cancel</Button>
        <Button type="primary" size="large" @click="confirm" style="width: 45%;">Confirm</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
import user from '@/service/user'
export default {
  name: 'reset-password',
  data() {
    return {
      modal2: false,
      form: {
        account: '',
        password: '',
        confirmPassword: ''
      },
      user: {},
      rules: {
        account: [
          {required: true, message: '账号不能为空', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '密码不能为空', trigger: 'blur'}
        ],
        confirmPassword: [
          {required: true, message: '确认密码不能为空', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    handleSubmit() {
      if(this.form.password===this.form.confirmPassword){
        this.modal2 = true
      }else {
        this.$Message.success('密码与确认密码不一致')
      }
    },
    cancel() {
      this.modal2 = false
    },
    async confirm() {
        this.modal2 = false
        let receiveData = await user.resetPassword({account: this.form.account, password: this.form.password})
        if (receiveData.status === 'success') {
          this.$Message.success('success')
          this.$router.push({
            name: 'ContentShow'
          })
        }else{
          this.$Message.success('fail')
        }
    },
    back() {
      this.$router.push({
        name: 'ContentShow'
      })
    }
  }
}
</script>
<style lang="less">
  .vertical-center-modal {
    display: flex;
    align-items: center;
    justify-content: center;

    .ivu-modal {
      top: 0;
    }
  }
</style>
