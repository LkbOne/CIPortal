<style scoped>
  .expand-row{
    margin-bottom: 16px;
  }
</style>
<template>
  <div>
    <Tree :data="data1"></Tree>
    <!--<span>Events:</span><p v-for="(event,index) in row.events" :key="index" style="font-size: 12px;padding-left: 20px;line-height: 25px">{{event.name}}</p>-->
  </div>
  <!--<div>-->
    <!--<Row class="expand-row">-->
      <!--<Col span="8">-->
      <!--<span class="expand-key">Job: </span>-->
      <!--<span class="expand-value">{{ row.job }}</span>-->
      <!--</Col>-->
      <!--<Col span="8">-->
      <!--<span class="expand-key">Interest: </span>-->
      <!--<span class="expand-value">{{ row.interest }}</span>-->
      <!--</Col>-->
      <!--<Col span="8">-->
      <!--<span class="expand-key">Birthday: </span>-->
      <!--<span class="expand-value">{{ row.birthday }}</span>-->
      <!--</Col>-->
    <!--</Row>-->
    <!--<Row>-->
      <!--<Col span="8">-->
      <!--<span class="expand-key">Favorite book: </span>-->
      <!--<span class="expand-value">《{{ row.book }}》</span>-->
      <!--</Col>-->
      <!--<Col span="8">-->
      <!--<span class="expand-key">Favorite movie: </span>-->
      <!--<span class="expand-value">{{ row.movie }}</span>-->
      <!--</Col>-->
      <!--<Col span="8">-->
      <!--<span class="expand-key">Favorite music: </span>-->
      <!--<span class="expand-value">{{ row.music }}</span>-->
      <!--</Col>-->
    <!--</Row>-->
  <!--</div>-->
</template>
<script>
export default {
  name: 'event-logs-expand',
  props: {
    row: Object
  },
  data() {
    return {
      data1: [
        {
          title: 'Events',
          expand: true,
          children: []
        }
      ]
    }
  },
  created(){
    for(let i=0;i<this.row.events.length;i++){
      console.log('this.events:',this.row.events[i].name)
      this.data1[0].children.push({title:this.row.events[i].name})
    }
    console.log(this.row)
  }
}
</script>
