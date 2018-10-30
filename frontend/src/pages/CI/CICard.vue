<template>
  <a :href="CICardCol.url" target="_blank">
    <Card class="ci-card" :style="{backgroundColor: cardColor}" :padding="8" style="">
      <span class="ci-card_text text-bold">{{CICardCol.projectName}}</span><br/>
      <span class="ci-card_text">commit:{{CICardCol.authorName}}</span><br/>
      <span class="ci-card_text">branch:{{CICardCol.branch}}</span>
    </Card>
  </a>
</template>
<script>
export default {
  props: {
    CICardCol: {
      type: Object
    }
  },
  data() {
    return {
      cardColor: '#fff'
    }
  },
  created() {
    switch (this.CICardCol.status) {
      case 'false':
        this.cardColor = '#ee3f16'
        break
      case 'true':
        this.cardColor = '#1abe6a'
        break
      default:
        this.cardColor = '#ff9901'
        break
    }
  }
}
</script>
<style scoped lang="scss">
.ci-card{
  margin:3px;
  &_text{
    text-align: center;
    font-size: 26px;
    text-overflow: ellipsis;
    overflow: hidden;
    white-space: nowrap;
    width: 99%;
    display: inline-block;
    line-height: 30px;
    color: white;
  }
  .text-bold{
    font-weight: bold
  }
}
</style>
